package instacollage;

import static spark.Spark.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;

public class CollageController {

	private static final String ACCESS_TOKEN_KEY = "instagram_access_token";
	private final InstagramService instagramService;
	private final CollageService collageService;
	private final FreeMarkerEngine freeMarkerEngine;

	public static void main(String[] args) throws IOException {
		new CollageController();
	}

	public CollageController() throws IOException {
		instagramService = new InstagramService();
		collageService = new CollageService();
		freeMarkerEngine = new FreeMarkerEngine(createFreemarkerConfiguration());
		initializeRoutes();
	}

	private Configuration createFreemarkerConfiguration() {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setClassForTemplateLoading(CollageController.class, "/freemarker");
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}

	private void initializeRoutes() {
		staticFileLocation("/public");
		before((request, response) -> {
			AccessToken accessTokenString = request.session().attribute(ACCESS_TOKEN_KEY);
			if (accessTokenString == null) {
				final String code = request.queryParams("code");
				if (code == null) {

					String error = request.queryParams("error");
					String errorDescription = request.queryParams("error_description");

					if (error == null) {
						response.redirect(instagramService.getAuthorizationUrl());
					} else {
						halt(401, errorDescription);
					}
				} else {
					AccessToken accessToken = instagramService.exchangeCode(code);
					request.session().attribute(ACCESS_TOKEN_KEY, accessToken);
				}
			}
		});

		after((request, response) -> {
			// TODO: what to do?
			// error_type=OAuthAccessTokenError

		});

		exception(Exception.class, (e, request, response) -> {
			response.status(500);
			e.printStackTrace();
			response.body(e.getMessage());
		});

		get("/", (request, response) -> {
			if (request.queryParams("code") != null) {
				response.redirect("/");
			}
			AccessToken accessToken = request.session().attribute(ACCESS_TOKEN_KEY);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("accessToken", accessToken);

			return new ModelAndView(attributes, "index.html");
		}, freeMarkerEngine);
		post("/images", (request, response) -> {
			AccessToken accessToken = request.session().attribute(ACCESS_TOKEN_KEY);
			JSONObject json = new JSONObject(request.body());

			String url = null;
			if (!json.isNull("nextUrl")) {
				url = json.getString("nextUrl");
			}
			response.type("application/json");
			return instagramService.getUserImages(url, accessToken.getAccessToken());
		}, new JsonTransformer());

		post("/collage", (request, response) -> {
			JSONObject json = new JSONObject(request.body());
			String type = json.getString("type");
			JSONArray imagesJsonArray = json.getJSONArray("images");
			List<String> imageList = new ArrayList<String>();
			for (int i = 0; i < imagesJsonArray.length(); i++) {
				String imageUrl = imagesJsonArray.getString(i);
				imageList.add(imageUrl);
			}

			BufferedImage collage = collageService.create(imageList, type);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(collage, "PNG", out);
			byte[] bytes = out.toByteArray();

			String base64bytes = Base64.getEncoder().encodeToString(bytes);
			String imageString = "data:image/png;base64," + base64bytes;

			return new Collage(imageString);
		}, new JsonTransformer());

	}
}

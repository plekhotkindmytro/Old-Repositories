package info.reborncraft.proxy.handlers;

import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkinHandler implements Handler {
	public SkinHandler() {
	}

	public static String SKIN_SERVER = "mineshafter.info";

	public static Pattern SKIN_URL = Pattern
			.compile("http://skins\\.minecraft\\.net/MinecraftSkins/(.+?)\\.png");
	public static Pattern CLOAK_URL = Pattern
			.compile("http://skins\\.minecraft\\.net/MinecraftCloaks/(.+?)\\.png");

	private Map<String, byte[]> skinCache = new Hashtable<String, byte[]>();
	private Map<String, byte[]> cloakCache = new Hashtable<String, byte[]>();

	public void handle(String url, Map<String, String> headers, byte[] body,
			java.io.OutputStream res) {
		Matcher skinMatcher = SKIN_URL.matcher(url);
		Matcher cloakMatcher = CLOAK_URL.matcher(url);

		byte[] skindata = null;

		if (skinMatcher.matches()) {
			skindata = handleSkin(skinMatcher.group(1));
		} else if (cloakMatcher.matches()) {
			skindata = handleCloak(cloakMatcher.group(1));
		}

		info.reborncraft.util.Http.sendResponse(res, "image/png", skindata);
	}

	public byte[] handleSkin(String username) {
		System.out.println("Proxy: Skin");

		if (!this.skinCache.containsKey(username)) {
			String url = "http://" + SKIN_SERVER + "/mcapi/skin/" + username
					+ ".png";
			System.out.println("To: " + url);

			byte[] skindata = info.reborncraft.util.Http.getRequest(url);
			this.skinCache.put(username, skindata);
		}

		return this.skinCache.get(username);
	}

	public byte[] handleCloak(String username) {
		System.out.println("Proxy: Cloak");

		if (!this.cloakCache.containsKey(username)) {
			String url = "http://" + SKIN_SERVER + "/mcapi/cloak/" + username
					+ ".png";
			System.out.println("To: " + url);

			byte[] cloakdata = info.reborncraft.util.Http.getRequest(url);
			this.cloakCache.put(username, cloakdata);
		}

		return this.cloakCache.get(username);
	}
}

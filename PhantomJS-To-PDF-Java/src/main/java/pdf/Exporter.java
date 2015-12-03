package pdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Exporter {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please add path to html file.");
			return;
		}

		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(args[0]), Charset.forName("UTF-8"))) {

			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			Map<String, String> env = System.getenv();
			for (String envName : env.keySet()) {
				System.out.format("%s=%s%n", envName, env.get(envName));
			}

			Process process = Runtime
					.getRuntime()
					.exec(new String[] {
							"/Users/dmytroplekhotkin/Projects/programs/phantomjs-1.9.7-macosx/bin/phantomjs",
							"--ignore-ssl-errors=true", "generatepdf.js",
							builder.toString(), "result.pdf" });
			// process.destroy();
			// Files.copy(process.getInputStream(), Paths.get("phantom.log"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

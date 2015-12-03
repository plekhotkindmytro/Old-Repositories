package info.reborncraft;

import info.reborncraft.util.SimpleRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
	public static final String APPLICATION_NAME = "minecraft";

	public Util() {
	}

	public static enum OS {
		WINDOWS, MACOS, SOLARIS, LINUX, UNKNOWN;
	}

	public static OS getPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win"))
			return OS.WINDOWS;
		if (osName.contains("mac"))
			return OS.MACOS;
		if (osName.contains("linux"))
			return OS.LINUX;
		if (osName.contains("unix"))
			return OS.LINUX;
		return OS.UNKNOWN;
	}

	public static File getWorkingDirectory() {
		String userHome = System.getProperty("user.home", ".");
		File workingDirectory;
		switch (getPlatform()) {
		case LINUX:
		case SOLARIS:
			workingDirectory = new File(userHome, ".minecraft/");
			break;
		case WINDOWS:
			String applicationData = System.getenv("APPDATA");
			String folder = applicationData != null ? applicationData
					: userHome;
			workingDirectory = new File(folder, ".minecraft/");
			break;
		case MACOS:
			workingDirectory = new File(userHome,
					"Library/Application Support/minecraft");
			break;
		default:
			workingDirectory = new File(userHome, "minecraft/");
		}

		return workingDirectory;
	}

	public static boolean grabLauncher(String md5, File file) {
		return grabLauncher(md5, file, 4);
	}

	public static boolean grabLauncher(String md5, File file, int tries) {
		try {
			URL url = new URL(
					"http://s3.amazonaws.com/Minecraft.Download/launcher/launcher.pack.lzma");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection(Proxy.NO_PROXY);

			connection.setUseCaches(false);
			connection.setDefaultUseCaches(false);
			connection.setRequestProperty("Cache-Control",
					"no-store,max-age=0,no-cache");
			connection.setRequestProperty("Expires", "0");
			connection.setRequestProperty("Pragma", "no-cache");
			if (md5 != null) {
				connection.setRequestProperty("If-None-Match",
						md5.toLowerCase());
			}
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(10000);

			int code = connection.getResponseCode();
			if (code / 100 == 2) {
				InputStream inputStream = connection.getInputStream();
				FileOutputStream outputStream = new FileOutputStream(file);

				byte[] buffer = new byte[' '];
				try {
					int read = inputStream.read(buffer);
					while (read >= 1) {
						outputStream.write(buffer, 0, read);
						read = inputStream.read(buffer);
					}
				} finally {
					inputStream.close();
					outputStream.close();
				}
				return true;
			}
			if (tries == 0) {
				return false;
			}
			return grabLauncher(md5, file, tries - 1);
		} catch (Exception e) {
		}
		return false;
	}

	public static String getMd5(File file) {
		DigestInputStream stream = null;
		try {
			stream = new DigestInputStream(new java.io.FileInputStream(file),
					MessageDigest.getInstance("MD5"));
			byte[] buffer = new byte[' '];
			while (stream.read(buffer) != -1) {
			}
			stream.close();
		} catch (Exception ignored) {
			return null;
		}

		return String.format("%1$032x", new Object[] { new BigInteger(1, stream
				.getMessageDigest().digest()) });
	}

	public static String getMd5(String v) {
		try {
			MessageDigest hash = MessageDigest.getInstance("MD5");
			hash.update(v.getBytes());
			return String.format("%1$032x", new Object[] { new BigInteger(1,
					hash.digest()) });
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
		}
		return null;
	}

	public static float getCurrentBootstrapVersion() {
		try {
			byte[] verdata = SimpleRequest.get(new URL(
					"http://mineshafter.info/bootver"));

			String verstring;
			if (verdata == null)
				verstring = "0";
			else
				verstring = new String(verdata);
			if (verstring.isEmpty())
				verstring = "0";
			float version;
			try {
				version = Float.parseFloat(verstring);
			} catch (Exception e) {
				version = 0.0F;
			}
			return version;
		} catch (Exception e) {
			System.out.println("Error while checking version:");
			e.printStackTrace();
		}
		return 0.0F;
	}
}

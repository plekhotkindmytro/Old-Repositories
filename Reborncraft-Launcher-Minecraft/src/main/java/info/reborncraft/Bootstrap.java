package info.reborncraft;

import info.reborncraft.proxy.ModularProxy;
import info.reborncraft.proxy.SocksProxyConnection;
import info.reborncraft.proxy.handlers.YggdrasilProxyHandler;
import info.reborncraft.util.Resources;
import info.reborncraft.util.Streams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import SevenZip.Compression.LZMA.Decoder;

public class Bootstrap extends JFrame {
	public static Thread mainThread;

	private static final long serialVersionUID = 1L;
	private static int bootstrapVersion = 4;
	private static int mineshafterBootstrapVersion = 7;

	private final File workDir;
	private final File launcherJar;
	private final File packedLauncherJar;
	private final File packedLauncherJarNew;
	private final File patchedLauncherJar;

	public Bootstrap() {
		super("Minecraft Launcher");
		this.workDir = Util.getWorkingDirectory();
		this.launcherJar = new File(workDir, "launcher.jar");
		this.packedLauncherJar = new File(workDir, "launcher.pack.lzma");
		this.packedLauncherJarNew = new File(workDir, "launcher.pack.lzma.new");
		this.patchedLauncherJar = new File(workDir, "launcher_mcpatched.jar");
	}

	public void run() {
		if (!workDir.isDirectory())
			workDir.mkdir();
		if (packedLauncherJarNew.isFile()) {
			renameNew();
		}
		String md5 = null;
		if (this.packedLauncherJar.exists())
			md5 = Util.getMd5(this.packedLauncherJar);
		if (!Util.grabLauncher(md5, this.packedLauncherJarNew))
			System.out.println("New launcher not downloaded");
		renameNew();
		unpack();
		patchLauncher();
		startLauncher();
	}

	public void renameNew() {
		if (this.packedLauncherJarNew.isFile()) {
			this.packedLauncherJar.delete();
			this.packedLauncherJarNew.renameTo(this.packedLauncherJar);
		}
	}

	public void unpack() {
		if (!this.packedLauncherJar.exists()) {
			return;
		}
		String path = this.packedLauncherJar.getAbsolutePath();
		File unpacked = new File(path.substring(0, path.lastIndexOf('.')));
		try {
			BufferedInputStream inStream = new BufferedInputStream(
					new FileInputStream(this.packedLauncherJar));
			BufferedOutputStream outStream = new BufferedOutputStream(
					new FileOutputStream(unpacked));

			byte[] properties = new byte[5];
			inStream.read(properties, 0, 5);
			Decoder decoder = new Decoder();
			decoder.SetDecoderProperties(properties);
			long outSize = 0L;
			for (int i = 0; i < 8; i++) {
				int v = inStream.read();
				outSize |= v << 8 * i;
			}

			decoder.Code(inStream, outStream, outSize);

			inStream.close();
			outStream.flush();
			outStream.close();

			JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(
					this.launcherJar));
			Pack200.newUnpacker().unpack(unpacked, jarOut);
			jarOut.close();
			unpacked.delete();
		} catch (IOException e) {
			System.out.println("Exception while unpacking:");
			e.printStackTrace();
		}
	}

	public void patchLauncher() {
		if (!this.launcherJar.exists())
			return;
		if (this.patchedLauncherJar.exists())
			this.patchedLauncherJar.delete();
		try {
			ZipInputStream inStream = new ZipInputStream(new FileInputStream(
					this.launcherJar));
			ZipOutputStream outStream = new ZipOutputStream(
					new FileOutputStream(this.patchedLauncherJar));

			ZipEntry entry;

			while ((entry = inStream.getNextEntry()) != null) {

				String n = entry.getName();
				if ((!n.startsWith("META-INF/"))
						|| ((!n.endsWith(".DSA")) && (!n.endsWith(".RSA")) && (!n
								.endsWith(".SF")))) {
					outStream.putNextEntry(entry);

					InputStream dataSource;
					if (n.equals("META-INF/MANIFEST.MF")) {
						dataSource = new ByteArrayInputStream(
								"Manifest-Version: 1.0\n".getBytes());
					} else {
						if (n.equals("com/mojang/authlib/HttpAuthenticationService.class")) {
							dataSource = Resources
									.load("resources/HttpAuthenticationService.class");
						} else {
							if (n.equals("com/mojang/launcher/Http.class")) {
								dataSource = Resources
										.load("resources/Http.class");
							} else {
								if (n.equals("net/minecraft/launcher/updater/RemoteVersionList.class"))
									dataSource = Resources
											.load("resources/RemoteVersionList.class");
								else
									dataSource = inStream;
							}
						}
					}
					Streams.pipeStreams(dataSource, outStream);
					outStream.flush();
				}
			}
			inStream.close();
			outStream.close();
		} catch (Exception e) {
			System.out.println("Error while patching launcher:");
			e.printStackTrace();
		}
	}

	public void startLauncher() {
		ModularProxy proxy = new ModularProxy(SocksProxyConnection.class,
				new YggdrasilProxyHandler());
		proxy.start();
		int proxyPort = proxy.getListeningPort();

		System.setErr(System.out);
		System.setProperty("java.net.preferIPv4Stack", "true");

		Proxy proxyInfo = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(
				"127.0.0.1", proxyPort));
		try {
			Class<?> launcher = new URLClassLoader(
					new URL[] { this.patchedLauncherJar.toURI().toURL() })
					.loadClass("net.minecraft.launcher.Launcher");
			Constructor<?> ctor = launcher
					.getConstructor(new Class[] { JFrame.class, File.class,
							Proxy.class, PasswordAuthentication.class,
							String[].class, Integer.class });
			ctor.newInstance(new Object[] { this, this.workDir, proxyInfo,
					null, new String[0], Integer.valueOf(bootstrapVersion) });
		} catch (Exception e) {
			System.out.println("Error while starting launcher:");
			e.printStackTrace();
		}

		setSize(854, 480);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private static String getImage(String imagePath) {
		File file = new File(imagePath);

		try {
			/*
			 * Reading a Image file from file system
			 */
			FileInputStream imageInFile = new FileInputStream(file);
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			imageInFile.close();
			/*
			 * Converting Image byte array into Base64 String
			 */
			String imageDataString = Base64
					.encodeBase64URLSafeString(imageData);
			return imageDataString;
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return null;
	}

	private static void insertServer(String targetPath) {
		try {

			File target = new File(targetPath);
			if (!target.exists()) {
				target.createNewFile();
			}
			String targetFileContent = new String(Files.readAllBytes(target
					.toPath()), "UTF-8");

			int lastIndex = 0;
			int serverCount = 0;

			while (lastIndex != -1) {

				lastIndex = targetFileContent.indexOf("\u0002ip\u0000",
						lastIndex);

				if (lastIndex != -1) {
					serverCount++;
					lastIndex += "\u0002ip\u0000".length();
				}
			}

			String myServerIp = "factions.reborncraft.biz:25565";
			String myServerName = "reborncraft.biz";
			// String myServerImage = getImage("server-icon.png");
			StringBuilder myServerBuilder = new StringBuilder();
			myServerBuilder.append("\u0008\u0000\u0004name\u0000");
			myServerBuilder.append((char) (myServerName.length() << 0x0000));
			myServerBuilder.append(myServerName);
			myServerBuilder.append("\u0008\u0000\u0002ip\u0000");
			myServerBuilder.append((char) (myServerIp.length() << 0x0000));
			myServerBuilder.append(myServerIp);
			myServerBuilder.append("\u0000");

			StringBuilder serverDatBuilder = new StringBuilder();
			serverDatBuilder.append("\n");
			serverDatBuilder.append("\u0000");
			serverDatBuilder.append("\u0000");
			serverDatBuilder.append("\t");
			serverDatBuilder.append("\u0000");
			serverDatBuilder.append("\u0007");
			serverDatBuilder.append("servers");
			serverDatBuilder.append("\n");
			serverDatBuilder.append("\u0000").append("\u0000").append("\u0000");
			char serverCountChar = (char) ((serverCount + 1) << 0x0000);
			// char serverCountChar = (char) ((1) << 0x0000);
			serverDatBuilder.append(serverCountChar);
			serverDatBuilder.append(myServerBuilder);
			File tempTarget = new File(targetPath + ".tmp");
			if (serverCount != 0) {

				String serverEntryStart = "\u0008\u0000\u0004";
				int indent = targetFileContent.indexOf(serverEntryStart);
				copyFromSourceToTarget(target, tempTarget, indent);
				// serverDatBuilder.append(targetFileContent.substring(indent));
			} else {
				serverDatBuilder.append("\u0000");
			}

			if (!targetFileContent.contains(myServerIp)) {
				Files.write(Paths.get(targetPath), serverDatBuilder.toString()
						.getBytes("UTF-8"));
				appendFromSourceToTarget(tempTarget, target);

			}
			tempTarget.delete();
		} catch (IOException e) {
			System.out.println("Cannot copy our server to server.dat");
			e.printStackTrace();
		}

	}

	private static void copyFromSourceToTarget(File source, File target,
			int indent) throws IOException {
		if (!target.exists()) {
			target.createNewFile();
		}

		FileChannel sourceChannel = null;
		FileChannel targetChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			targetChannel = new FileOutputStream(target).getChannel();

			sourceChannel.transferTo(indent, sourceChannel.size() - indent,
					targetChannel);
		} finally {
			if (sourceChannel != null) {
				sourceChannel.close();
			}

			if (targetChannel != null) {
				targetChannel.close();
			}
		}
	}

	public static void appendFromSourceToTarget(File source, File target)
			throws IOException {
		if (!target.exists()) {
			target.createNewFile();
		}

		FileChannel sourceChannel = null;
		FileChannel targetChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			targetChannel = new FileOutputStream(target, true).getChannel();
			targetChannel.position(targetChannel.size());
			targetChannel.transferFrom(sourceChannel, targetChannel.size(),
					sourceChannel.size());
		} finally {
			if (sourceChannel != null) {
				sourceChannel.close();
			}

			if (targetChannel != null) {
				targetChannel.close();
			}
		}
	}

	public static void main(String[] args) {
		mainThread = Thread.currentThread();

		float v = Util.getCurrentBootstrapVersion();
		System.out.println("Current proxy version: "
				+ mineshafterBootstrapVersion);
		System.out.println("Gotten proxy version: " + v);
		if (mineshafterBootstrapVersion < v) {
			JOptionPane
					.showMessageDialog(
							null,
							"A new version of Mineshafter is available at http://mineshafter.info/\nGo get it.",
							"Update Available", -1);
			System.exit(0);
		}

		Bootstrap frame = new Bootstrap();
		frame.run();

		insertServer(frame.workDir + "/servers.dat");
	}
}

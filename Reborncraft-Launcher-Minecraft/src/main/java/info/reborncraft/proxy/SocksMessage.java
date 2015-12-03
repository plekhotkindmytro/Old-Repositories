package info.reborncraft.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class SocksMessage {
	public InetAddress ip = null;

	public int version;

	public int port;

	public int command;

	public String host = null;

	public String user = null;

	SocksMessage() {
	}

	SocksMessage(int command, InetAddress ip, int port) {
		this.command = command;
		this.ip = ip;
		this.port = port;
	}

	public abstract void read(InputStream paramInputStream) throws IOException;

	public abstract void write(OutputStream paramOutputStream)
			throws IOException;

	public InetAddress getInetAddress() throws UnknownHostException {
		return ip;
	}

	public String toString() {
		return "Proxy Message:\nVersion:" + this.version + "\n" + "Command:"
				+ this.command + "\n" + "IP:     " + this.ip + "\n"
				+ "Port:   " + this.port + "\n" + "User:   " + this.user + "\n";
	}

	static final String bytes2IPV4(byte[] addr, int offset) {
		String hostName = "" + (addr[offset] & 0xFF);
		for (int i = offset + 1; i < offset + 4; i++)
			hostName = hostName + "." + (addr[i] & 0xFF);
		return hostName;
	}

	static final String bytes2IPV6(byte[] addr, int offset) {
		return null;
	}
}

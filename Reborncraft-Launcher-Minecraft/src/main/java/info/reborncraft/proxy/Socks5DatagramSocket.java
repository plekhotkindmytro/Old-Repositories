package info.reborncraft.proxy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Socks5DatagramSocket extends java.net.DatagramSocket {
	InetAddress relayIP;
	int relayPort;

	Socks5DatagramSocket(InetAddress relayIP, int relayPort)
			throws java.io.IOException {
		this.relayIP = relayIP;
		this.relayPort = relayPort;
	}

	public void send(DatagramPacket dp) throws IOException {
		byte[] head = formHeader(dp.getAddress(), dp.getPort());
		byte[] buf = new byte[head.length + dp.getLength()];
		byte[] data = dp.getData();

		System.arraycopy(head, 0, buf, 0, head.length);

		System.arraycopy(data, 0, buf, head.length, dp.getLength());

		super.send(new DatagramPacket(buf, buf.length, this.relayIP,
				this.relayPort));
	}

	public void receive(DatagramPacket dp) throws IOException {
		super.receive(dp);
		int init_length = dp.getLength();
		while ((!this.relayIP.equals(dp.getAddress()))
				|| (this.relayPort != dp.getPort())) {
			dp.setLength(init_length);
			super.receive(dp);
		}

		byte[] data = dp.getData();
		ByteArrayInputStream bIn = new ByteArrayInputStream(
				data, 0, dp.getLength());
		SocksMessage msg = new Socks5Message(bIn);
		dp.setPort(msg.port);
		dp.setAddress(msg.getInetAddress());

		int data_length = bIn.available();
		System.arraycopy(data, dp.getLength() - data_length, data, 0,
				data_length);
		dp.setLength(data_length);
	}

	private byte[] formHeader(InetAddress ip, int port) {
		Socks5Message request = new Socks5Message(0, ip, port);
		request.data[0] = 0;
		return request.data;
	}
}

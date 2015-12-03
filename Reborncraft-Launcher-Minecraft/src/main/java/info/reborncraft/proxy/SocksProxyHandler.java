package info.reborncraft.proxy;

import java.io.InputStream;
import java.io.OutputStream;

public abstract interface SocksProxyHandler {
	public abstract boolean onConnect(InputStream paramInputStream,
			OutputStream paramOutputStream, SocksMessage paramSocksMessage);

	public abstract boolean onBind();
}

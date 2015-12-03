package info.reborncraft.proxy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public abstract interface HttpProxyHandler {
	public abstract boolean onGET(String paramString,
			Map<String, String> paramMap, InputStream paramInputStream,
			OutputStream paramOutputStream);

	public abstract boolean onPOST(String paramString,
			Map<String, String> paramMap, InputStream paramInputStream,
			OutputStream paramOutputStream);

	public abstract boolean onHEAD(String paramString,
			Map<String, String> paramMap, InputStream paramInputStream,
			OutputStream paramOutputStream);

	public abstract boolean onCONNECT(String paramString,
			Map<String, String> paramMap, InputStream paramInputStream,
			OutputStream paramOutputStream);
}

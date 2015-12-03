package info.reborncraft.proxy.handlers;

import java.io.OutputStream;
import java.util.Map;

public abstract interface Handler {
	public abstract void handle(String paramString,
			Map<String, String> paramMap, byte[] paramArrayOfByte,
			OutputStream paramOutputStream);
}
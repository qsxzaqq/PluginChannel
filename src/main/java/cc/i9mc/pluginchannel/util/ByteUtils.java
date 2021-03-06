package cc.i9mc.pluginchannel.util;

import java.io.Closeable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cc.i9mc.pluginchannel.logger.PLogger;


public class ByteUtils {
	
	public static String serialize(String var) {
		return Base64.getEncoder().encodeToString(var.getBytes(StandardCharsets.UTF_8));
	}
	
	public static String deSerialize(String var) {
		return new String(Base64.getDecoder().decode(var), StandardCharsets.UTF_8);
	}
	
	public static String[] serialize(String... var) {
		String[] varEncode = new String[var.length];
		for (int i = 0 ; i < var.length ; i++) {
			varEncode[i] = Base64.getEncoder().encodeToString(var[i].getBytes(StandardCharsets.UTF_8));
		}
		return varEncode;
	}
	
	public static String[] deSerialize(String... var) {
		String[] varEncode = new String[var.length];
		for (int i = 0 ; i < var.length ; i++) {
			varEncode[i] = new String(Base64.getDecoder().decode(var[i]), StandardCharsets.UTF_8);
		}
		return varEncode;
	}
	
	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception ignored) {
		}
	}
}

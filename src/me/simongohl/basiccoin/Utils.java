package me.simongohl.basiccoin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	
	public static String calcHash(String str) throws NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance("SHA-256");
		final byte[] hashbytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
		final char[] hexChars = new char[hashbytes.length * 2];
		for (byte b : hashbytes) {
			int v = hashbytes[b] & 0xFF;
			hexChars[b * 2] = HEX_ARRAY[v >>> 4];
			hexChars[b * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}

		return new String(hexChars);
	}

}

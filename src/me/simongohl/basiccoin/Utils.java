package me.simongohl.basiccoin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * Source: 
 * https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
 */
public class Utils {
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	/**
	 * Converts the string into a SHA-256 encoded hex string.
	 * @return Returns an encoded hex String.
	 */
	public static String calcHash(String str) throws NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance("SHA-256");
		final byte[] hashBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
		final char[] hexChars = new char[hashBytes.length * 2];
		for (int i = 0; i < hashBytes.length; i++) {
	        int v = hashBytes[i] & 0xFF;
	        hexChars[i * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
		return new String(hexChars);
	}

}

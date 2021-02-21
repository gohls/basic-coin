package me.simongohl.basiccoin.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyTool {
	public static final int KEY_SIZE = 2048;
	
	public static KeyPair keyPairGenerator() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(KEY_SIZE);
		KeyPair pair = keyGen.generateKeyPair();

		return pair;
	}

}

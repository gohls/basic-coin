package me.simongohl.basiccoin.wallet;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {
	private String name; 
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public Wallet(String name, KeyPair pair){
		this.name = name;
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

}
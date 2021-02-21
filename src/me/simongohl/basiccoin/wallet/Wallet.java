package me.simongohl.basiccoin.wallet;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {
	private String name; 
	private int coinAmount;
	final private PrivateKey privateKey;
	final private PublicKey publicKey;
	
	public Wallet(String name, KeyPair pair){
		this.name = name;
		this.coinAmount = 100;
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getCoinAmount() {
		return coinAmount;
	}

	public void setCoinAmount(int coinAmount) {
		this.coinAmount = coinAmount;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

}
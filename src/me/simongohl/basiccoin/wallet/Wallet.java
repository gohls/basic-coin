package me.simongohl.basiccoin.wallet;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import me.simongohl.basiccoin.util.KeyTool;

public class Wallet {
	private String name; 
	private int balance = 100;
	private final KeyPair pair;
	
	/**
	 * 
	 * @param name
	 * @param balance
	 * @throws NoSuchAlgorithmException
	 */
	public Wallet(String name, int balance) throws NoSuchAlgorithmException{
		this.name = name;
		this.balance = balance;
		this.pair = this.generateKeys();
	}
	
	private KeyPair generateKeys() throws NoSuchAlgorithmException {
		return KeyTool.keyPairGenerator();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getBalance() {
		return balance;
	}

	//@TODO needs work
	public void setBalance(int balance) {
		this.balance = balance;
	}

	public PublicKey getPublicKey() {
		return this.pair.getPublic();
	}

	//@TODO 🤦 🙅‍♂️ 🚨 not secure; needs work 🤷
	public PrivateKey getPrivateKey() {
		return this.pair.getPrivate();
	}

}

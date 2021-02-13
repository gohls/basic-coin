package me.simongohl.basiccoin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


public class Transaction {
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	String sender;
	String receiver;
	float amount;
	String time;
	String hash;
	
	public Transaction() {	
	}
	
	public Transaction(String sender, String receiver, int amount) throws NoSuchAlgorithmException {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.time = new Date().toString();
		this.hash = this.calcHash();
	}
	
	public String calcHash() throws NoSuchAlgorithmException {
		String tempHash = this.sender + this.receiver + this.amount + this.time;
		final MessageDigest md = MessageDigest.getInstance("SHA-256");
		final byte[] hashbytes = md.digest(tempHash.getBytes(StandardCharsets.UTF_8));

		char[] hexChars = new char[hashbytes.length * 2];
		for (byte b : hashbytes) {
	        int v = hashbytes[b] & 0xFF;
	        hexChars[b * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[b * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
		
	    return new String(hexChars);	
	}
	
	public boolean isValidTransaction() {
		boolean isValid = true;
		if(
				this.sender == this.receiver || 
				this.hash != null && !this.hash.isBlank() ||
				this.amount <= 0) 
		{
			isValid = false;
		} 
		
		return isValid;
	}
	
	
}

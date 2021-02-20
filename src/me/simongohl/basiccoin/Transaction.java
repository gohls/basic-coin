package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import me.simongohl.basiccoin.util.HashTool;

public class Transaction {
	String senderName;
	String receiverName;
	float coinAmount;
	String memo;
	String time; // dow mon dd hh:mm:ss zzz yyyy
	String hash;

	public Transaction() {
		this.memo = "";
	}

	public Transaction(
			String senderName, 
			String receiverName, 
			float coinAmount, 
			String memo) 
					throws NoSuchAlgorithmException {
		super();
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.coinAmount = coinAmount;
		this.memo = memo;
		this.time = new Date().toString();
		this.hash = this.calcTransactionHash();
	}

	public String calcTransactionHash() throws NoSuchAlgorithmException {
		String tempHash = this.senderName + 
						  this.receiverName + 
						  this.coinAmount + 
						  this.memo + 
						  this.time;
		String encodedHash = HashTool.calcHash(tempHash);
		
		return encodedHash;
	}

	public boolean isValidTransaction() throws NoSuchAlgorithmException {
		boolean isValid = true;
		if (
				this.senderName == this.receiverName || 
				this.hash != this.calcTransactionHash() || 
				this.coinAmount <= 0) {
			isValid = false;
		}
		
		//@TODO check for signature
		
		return isValid;
	}
	
	public boolean signTransaction(String key, String senderKey) throws NoSuchAlgorithmException {
		boolean isSigned = true;
		if(this.hash != this.calcTransactionHash()) {
			isSigned = false;
		}

		
		//@TODO missing impl
		// - Verify wallet 
		// - Sign transaction with private key
		
		return isSigned;
	}
	
}

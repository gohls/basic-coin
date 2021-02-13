package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Transaction {

	String senderName;
	String receiverName;
	float coinAmount;
	String memo;
	String transactionTime; // dow mon dd hh:mm:ss zzz yyyy
	String hash;

	public Transaction() {
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
		this.transactionTime = new Date().toString();
		this.hash = this.calcHash();
	}

	public String calcHash() throws NoSuchAlgorithmException {
		String tempHash = this.senderName + 
						  this.receiverName + 
						  this.coinAmount + 
						  this.memo + 
						  this.transactionTime;
		String hashStr = Utils.calcHash(tempHash);
		return hashStr;
	}

	public boolean isValidTransaction() throws NoSuchAlgorithmException {
		boolean isValid = true;
		if (
				this.senderName == this.receiverName || 
				this.hash != this.calcHash() || 
				this.coinAmount <= 0) {
			isValid = false;
		}

		return isValid;
	}

}

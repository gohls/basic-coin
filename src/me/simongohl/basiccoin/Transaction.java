package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Transaction {
	// Used for bytes array to hex conversion
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	String senderName;
	String receiverName;
	float coinAmount;
	String transactionTime; // dow mon dd hh:mm:ss zzz yyyy
	String transactionHash;

	public Transaction() {
	}

	public Transaction(String senderName, String receiverName, int coinAmount) throws NoSuchAlgorithmException {
		super();
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.coinAmount = coinAmount;
		this.transactionTime = new Date().toString();
		this.transactionHash = this.calcHash();
	}

	public String calcHash() throws NoSuchAlgorithmException {
		String tempHash = this.senderName + this.receiverName + this.coinAmount + this.transactionTime;
		String hashStr = Utils.calcHash(tempHash);
		return hashStr;
	}

	public boolean isValidTransaction() throws NoSuchAlgorithmException {
		boolean isValid = true;
		if (this.senderName == this.receiverName || this.transactionHash != this.calcHash() || this.coinAmount <= 0) {
			isValid = false;
		}

		return isValid;
	}

}

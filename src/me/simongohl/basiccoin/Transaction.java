package me.simongohl.basiccoin;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Base64;
import java.util.Date;
import java.util.Hashtable;

import me.simongohl.basiccoin.util.HashTool;
import me.simongohl.basiccoin.wallet.Wallet;


public class Transaction {
	String senderName;
	String receiverName;
	int coinAmount;
	String memo = "";
	String time; // dow mon dd hh:mm:ss zzz yyyy
	String hash;
	private String signature;

	public Transaction(
			String senderName, 
			String receiverName, 
			int coinAmount, 
			String memo) 
					throws NoSuchAlgorithmException {
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.coinAmount = coinAmount;
		this.memo = memo;
		this.time = new Date().toString();
		this.hash = this.computeTransactionHash(); 
	}

	public String computeTransactionHash() throws NoSuchAlgorithmException {
		String tempHash = this.senderName + 
						  this.receiverName + 
						  this.coinAmount + 
						  this.memo + 
						  this.time;
		String encodedHash = HashTool.calcHash(tempHash);
		
		return encodedHash;
	}
	
	//@TODO is passing in all wallets a good way of doing this?
	public boolean signTransaction(Hashtable<String, Wallet> wallets) throws NoSuchAlgorithmException {
		boolean isSigned = true;
		if(this.hash != this.computeTransactionHash()) {
			isSigned = false;
		}
		if(!wallets.containsKey(this.senderName) || !wallets.containsKey(this.receiverName)) {
			isSigned = false;
		}
		
		try {
			byte[] transactionBytes = this.hash.getBytes(StandardCharsets.UTF_8);
			Signature sig = Signature.getInstance("SHA256withRSA");
			Wallet senderWallet = (Wallet) wallets.get(senderName);
			sig.initSign(senderWallet.getPrivateKey());
			sig.update(transactionBytes);
			byte[] signatureBytes = sig.sign();
			this.signature = Base64.getEncoder().encodeToString(signatureBytes);
			System.out.println("Transaction signed!");
		} catch (Exception e){
			isSigned = false;
			System.out.println("Error signing...");
		}
		
		return isSigned;
	}
	
	//@TODO is passing in all wallets a good way of doing this?
	public boolean isValidTransaction(Hashtable<String, Wallet> wallets) throws NoSuchAlgorithmException {
		boolean isValid = true;
		if (
				this.senderName == this.receiverName || 
				this.hash != this.computeTransactionHash() || 
				this.coinAmount <= 0) {
			isValid = false;
		}
		
		try {
			byte[] transactionBytes = this.hash.getBytes(StandardCharsets.UTF_8);
			Signature sig = Signature.getInstance("SHA256withRSA");
			Wallet senderWallet =  (Wallet) wallets.get(senderName);
			sig.initVerify(senderWallet.getPublicKey());
			sig.update(transactionBytes);
			byte[] signatureBytes = Base64.getDecoder().decode(this.signature);
			
			if(!sig.verify(signatureBytes)){
				isValid = false;
			}
		} catch (Exception e){
			isValid = false;
		}
		
		return isValid;
	}
	
}

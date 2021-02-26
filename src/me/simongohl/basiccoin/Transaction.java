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

	/**
	 * 
	 * @param String senderName
	 * @param String receiverName
	 * @param String memo
	 * @param int coinAmount
	 * @throws NoSuchAlgorithmException
	 */
	public Transaction(
			String senderName, 
			String receiverName, 
			String memo,
			int coinAmount) 
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
		System.out.println("\n this.hash: " + this.hash);
		System.out.println("\n compute: " + this.computeTransactionHash());
		if(!this.hash.equals(this.computeTransactionHash())) {
			
			isSigned = false;
		}
		System.out.print("\nCheck isSigned 1: " + isSigned);
		System.out.println("\n sender: " + wallets.containsKey(this.senderName));
		System.out.println("\n receiver: " + wallets.containsKey(this.receiverName));
		if(!wallets.containsKey(this.senderName) || !wallets.containsKey(this.receiverName)) {
			System.out.println("\n Goes in here");
			isSigned = false;
		}
		System.out.print("\nCheck isSigned 2: " + isSigned);
		try {
			byte[] transactionBytes = this.hash.getBytes(StandardCharsets.UTF_8);
			Signature sig = Signature.getInstance("SHA256withRSA");
			Wallet senderWallet = (Wallet) wallets.get(senderName);
			sig.initSign(senderWallet.getPrivateKey());
			sig.update(transactionBytes);
			byte[] signatureBytes = sig.sign();
			this.signature = Base64.getEncoder().encodeToString(signatureBytes);
			System.out.println("\nTransaction signed!");
		} catch (Exception e){
			isSigned = false;
			System.out.println("\nError signing...");
		}
		System.out.print("\nCheck isSigned 3: " + isSigned);
		
		return isSigned;
	}
	
	//@TODO is passing in all wallets a good way of doing this?
	public boolean isValidTransaction(Hashtable<String, Wallet> wallets) throws NoSuchAlgorithmException {
		boolean isValid = true;
		if (
				this.senderName.equals(this.receiverName) || 
				!this.hash.equals(this.computeTransactionHash()) || 
				this.coinAmount <= 0) {
			isValid = false;
		}
		System.out.print("\n isValid 1: " + isValid);
		try {
			byte[] transactionBytes = this.hash.getBytes(StandardCharsets.UTF_8);
			Signature sig = Signature.getInstance("SHA256withRSA");
			Wallet senderWallet =  (Wallet) wallets.get(senderName);
			sig.initVerify(senderWallet.getPublicKey());
			sig.update(transactionBytes);
			byte[] signatureBytes = Base64.getDecoder().decode(this.signature);
			
			if(!sig.verify(signatureBytes)){
				System.out.println("if verify");
				isValid = false;
			} else {
				System.out.println("\nTransaction validated!");
			}
		} catch (Exception e){
			isValid = false;
			System.out.println("\nError: " + e);
		}
		System.out.print("\n isValid 2: " + isValid);
		return isValid;
	}

	@Override
	public String toString() {
		return "\nPending Transaction: " + this.senderName + " is added " + this.coinAmount + " BasicCoin to " + this.receiverName + " wallet.";
	}
	
}

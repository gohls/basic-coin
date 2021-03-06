package me.simongohl.basiccoin.blockchain;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Base64;
import java.util.Date;
import java.util.Hashtable;

import me.simongohl.basiccoin.util.HashTool;
import me.simongohl.basiccoin.wallet.Wallet;


public class Transaction {
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static final String HASH_ENCRYPTION_ALGORITHM = "SHA256withRSA";
			
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
		if (!this.hash.equals(this.computeTransactionHash())) {
			isSigned = false;
		}

		if (!wallets.containsKey(this.senderName) || !wallets.containsKey(this.receiverName)) {
			isSigned = false;
		}

		try {
			Wallet senderWallet = wallets.get(senderName);
	
			Signature sig = Signature.getInstance(HASH_ENCRYPTION_ALGORITHM);
			sig.initSign(senderWallet.getPrivateKey());
			
			final byte[] transactionBytes = this.hash.getBytes(CHARSET);
			sig.update(transactionBytes);
			final byte[] signatureBytes = sig.sign();
			this.signature = Base64.getEncoder().encodeToString(signatureBytes);
						
			System.out.println("\nTransaction signed!");
		} catch (Exception e){
			isSigned = false;
			System.out.println("\nError: signing failed.");
			e.printStackTrace();
		}
		
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

		try {
			byte[] transactionBytes = this.hash.getBytes(CHARSET);
			
			Wallet senderWallet =  wallets.get(senderName);
			
			Signature sig = Signature.getInstance(HASH_ENCRYPTION_ALGORITHM);

			sig.initVerify(senderWallet.getPublicKey());
			sig.update(transactionBytes);

			byte[] signatureBytes = Base64.getDecoder().decode(this.signature);
			
			if (!sig.verify(signatureBytes)){
				isValid = false;
			} else {
				System.out.println("\nTransaction verified!");
			}
		} catch (Exception e){
			isValid = false;
			System.out.println("\nError: " + e);
		}
		
		return isValid;
	}

	@Override
	public String toString() {
		return "\nTransaction: " + this.senderName + " added " + 
				this.coinAmount + " BasicCoin(s) to " + this.receiverName + 
				"'s wallet with " + this.memo + " as memo.";
	}
	
}

package me.simongohl.basiccoin;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;


/**
* BasicCoin is a simple implementation of blockchain technology,
* applying Proof-of-Work (PoW) as its consensus algorithm.
*
* Right now, it's just a terminal application. 
* 
* @author  Simon Gohl
* @version 0.0.0
*/
public class BasicCoin {
	ArrayList<Block> blockchain;
	ArrayList<Transaction> pendingTransactions;
	int miningDifficulty;
	int blockSize;
	
	public BasicCoin() {
		super();
		this.blockchain = new ArrayList<Block>();
		this.pendingTransactions = new ArrayList<Transaction>();
		this.miningDifficulty = 2;
		this.blockSize = 5;
		
	 }
	
	public void addGenesisBlock() throws NoSuchAlgorithmException {
		Transaction transaction = new Transaction("me", "you", 10, "genesis");
		this.pendingTransactions.add(transaction);
		Block gensis = new Block("0", "null", this.pendingTransactions, new Date().toString());
		this.blockchain.add(gensis);
	};
	
	public boolean addTransaction(
			String senderName, 
			String receiverName, 
			float coinAmount, 
			String memo, 
			String keyStr, 
			String senderKey) 
					throws NoSuchAlgorithmException {
		
		final byte[] keyByte = keyStr.getBytes(StandardCharsets.US_ASCII);
		final byte[] senderKeyByte = senderKey.getBytes(StandardCharsets.US_ASCII);
		
		//@TODO Check stuff
		
		Transaction transaction = new Transaction(senderName, receiverName, coinAmount, memo);
		
		//@TODO Sign transaction here
		
		if (!transaction.isValidTransaction()) {
			return false;
		}
		return true;
	};
	
	public void minePendingTransactions() {
		//@TODO missing impl
	};
	
	@Override
	public String toString() {
		String temp = "";
		for (Block b : this.blockchain) {
			temp += "Block ID: \t" + b.blockID + "\n" +
					"Prev Block ID: \t" + b.prevBlockID + "\n" +
					"Block Time: \t" + b.time + "\n" +
					"Block Hash: \t" + b.hash + "\n";
		}
		return temp;
	}
	
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		BasicCoin basicCoin = new BasicCoin();
		basicCoin.addGenesisBlock();
		System.out.println(basicCoin);	
	}

}

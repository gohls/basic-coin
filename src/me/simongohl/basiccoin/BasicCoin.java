package me.simongohl.basiccoin;

//import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;

import me.simongohl.basiccoin.util.KeyTool;
import me.simongohl.basiccoin.wallet.Wallet;


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
	LinkedList<Block> blockchain;
	ArrayList<Transaction> pendingTransactions;
	Hashtable<String, Wallet> wallets;
	final int miningDifficulty;
	final int blockSize;
	final int miningReward;
	
	public BasicCoin() {
		this.blockchain = new LinkedList<Block>();
		this.pendingTransactions = new ArrayList<Transaction>();
		this.wallets = new Hashtable<String, Wallet>();
		this.miningDifficulty = 3;
		this.blockSize = 10;
		this.miningReward = 10;
	 }
	
	public void addGenesisBlock() throws NoSuchAlgorithmException {
		Transaction transaction = new Transaction("me", "you", 1, "genesis");
		this.pendingTransactions.add(transaction);
		Block genesis = new Block("0", "null", this.pendingTransactions, new Date().toString());
		this.blockchain.add(genesis);
	};
	
	// @TODO public addWallet() {}
	
	public boolean addTransaction(
			String senderName, 
			String receiverName, 
			int coinAmount, 
			String memo) 
					throws NoSuchAlgorithmException {
		
		Transaction transaction = new Transaction(senderName, receiverName, coinAmount, memo);
		transaction.signTransaction(this.wallets);
		
		if (!transaction.isValidTransaction(this.wallets)) {
			return false;
		}
		
		return true;
	};
	
	public void minePendingTransactions(String minerName) {
//		for (Transaction t : this.pendingTransactions) {
//			
//		}
		
//		@TODO give mining reward 
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
		Block block = basicCoin.blockchain.get(0);
		block.mineBlock(5);
		KeyTool.keyPairGenerator();
	}

}

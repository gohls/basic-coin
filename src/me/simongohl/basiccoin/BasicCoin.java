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
	public static final int MAX_BLOCK_SIZE = 10;
	
	LinkedList<Block> blockchain;
	ArrayList<Transaction> pendingTransactions;
	//@TODO not sure if I want wallets in here 🤷..not secure!!!
	Hashtable<String, Wallet> wallets; 
	int miningDifficulty;
	final int miningReward;
	
	public BasicCoin() {
		//@TODO Hmm should I make this a hashtable or my own linked list Node impl ??
		this.blockchain = new LinkedList<Block>();
		this.pendingTransactions = new ArrayList<Transaction>();
		this.wallets = new Hashtable<String, Wallet>();
		this.miningDifficulty = 3;
		this.miningReward = 10;
	 }
	
	public void addGenesisBlock() throws NoSuchAlgorithmException {
		Transaction transaction = new Transaction("Basic", "Coin", 1, "genesis");
		this.pendingTransactions.add(transaction);
		Block genesis = new Block("0", "null", this.pendingTransactions, new Date().toString());
		this.blockchain.add(genesis);
	}
	
	public Block getLastBlock() {
		return this.blockchain.getLast();
	}
	
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
	}
	
	public void minePendingTransactions(String minerName) throws NoSuchAlgorithmException {
//		ArrayList<Transaction> tempPendingTransactions = new ArrayList<Transaction>();
//		for (int i = 0; i < this.pendingTransactions.size(); ++i) {
//			tempPendingTransactions.add(this.pendingTransactions.get(i - 1));
//			if(i % MAX_BLOCK_SIZE == 0) {
//				Block lastBlock = this.getLastBlock();
//				Block newBlock = new Block("here", lastBlock.blockID, tempPendingTransactions, new Date().toString());
//				this.blockchain.add(newBlock);
//				tempPendingTransactions.removeAll(tempPendingTransactions);
//			}
//		}
		
//		@TODO give mining reward 
	}
	
	public Wallet addWallet(String name, int balance) throws NoSuchAlgorithmException {
		Wallet wallet = new Wallet(name, balance);
		this.wallets.put(name, wallet);
		System.out.println("Wallet added!");
		return wallet;
	}
	
	public void removeWallet(String name) {
		this.wallets.remove(name);
		System.out.println("Wallet removed!");
	}
	
	@Override
	public String toString() {
		String tempStr = "";
		for (Block b : this.blockchain) {
			tempStr += "Block ID: \t" + b.blockID + "\n" +
					"Prev Block ID: \t" + b.prevBlockID + "\n" +
					"Block Time: \t" + b.time + "\n" +
					"Block Hash: \t" + b.hash + "\n";
		}
		
		return tempStr;
	}
	
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		BasicCoin basicCoin = new BasicCoin();
		basicCoin.addGenesisBlock();
		System.out.println(basicCoin);	
		Block block = basicCoin.blockchain.get(0);
		block.mineBlock(5);
		KeyTool.keyPairGenerator();
		Block lastBlock = basicCoin.getLastBlock();
		System.out.println(lastBlock);
	}

}

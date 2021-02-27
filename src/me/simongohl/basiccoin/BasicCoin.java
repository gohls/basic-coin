package me.simongohl.basiccoin;

//import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;

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
	public static final int MAX_BLOCK_SIZE = 5;
	public static final int MINING_DIFFICULTY = 3;
	public static final int MINING_REWARD = 10;
	
	LinkedList<String> blockchain;
	ArrayList<Transaction> pendingTransactions;
	//@TODO Not sure if I want wallets in here 🤷..not secure!!!
	Hashtable<String, Wallet> wallets; 
	
	public BasicCoin() {
		this.blockchain = new LinkedList<String>();
		this.pendingTransactions = new ArrayList<Transaction>();
		this.wallets = new Hashtable<String, Wallet>();
	 }

	public void addGenesisBlock() throws NoSuchAlgorithmException {
		Transaction transaction = new Transaction("Basic", "Coin", "genesis", 1);
		this.pendingTransactions.add(transaction);
		Block genesis = new Block(0, null, this.pendingTransactions, new Date().toString());
		String genesisHash = genesis.computeBlockHash();
		this.blockchain.add(genesisHash);
	}
	
	public String getLastBlock() {
		return this.blockchain.getLast();
	}
	
	/**
	 * 
	 * @param String senderName
	 * @param String receiverName
	 * @param int coinAmount
	 * @param String memo
	 * @return boolean if transaction was added
	 * @throws NoSuchAlgorithmException
	 */
	public boolean addTransaction(
			String senderName, 
			String receiverName, 
			String memo,
			int coinAmount) 
					throws NoSuchAlgorithmException {
		boolean isAdded = true;
		Transaction transaction = new Transaction(senderName, receiverName, memo, coinAmount);
		transaction.signTransaction(this.wallets);
		if (!transaction.isValidTransaction(this.wallets)) {
			isAdded = false;
			System.out.println("\n Error: Transaction validation failed!");
		} else {
			this.pendingTransactions.add(transaction);
			System.out.println("Transaction added!");
		}
		
		return isAdded;
	}
	
	public void minePendingTransactions(String minerName) throws NoSuchAlgorithmException {
		ArrayList<Transaction> tempPendingTransactions = new ArrayList<Transaction>();
		for (int i = 0; i < this.pendingTransactions.size(); ++i) {
			tempPendingTransactions.add(this.pendingTransactions.get(i - 1));
			if(i % MAX_BLOCK_SIZE == 0) {
				int index = this.blockchain.size();
				String prevBlockHash = this.getLastBlock();
				String time = new Date().toString();
				Block newBlock = new Block(index, prevBlockHash, tempPendingTransactions, time);
				String newBlockHash = newBlock.computeBlockHash();
				this.blockchain.add(newBlockHash);
				tempPendingTransactions.removeAll(tempPendingTransactions);
				// Reward miner 👷 ⛏ 💰️
			}
		}
		

	}
	
	/**
	 * 
	 * @param String name
	 * @param int balance
	 * @return new generated Wallet 
	 * @throws NoSuchAlgorithmException
	 */
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
		for (String block: this.blockchain) {
			tempStr += "\nBlock hash: \t" + block;
		}
		for (Transaction t : this.pendingTransactions) {
			tempStr += "\nPending Transaction: " + t.senderName + " is added " + t.coinAmount + " BasicCoin to " + t.receiverName + " wallet.";
		}
		
		return tempStr;
	}
	
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		BasicCoin basicCoin = new BasicCoin();
		basicCoin.addGenesisBlock();
		System.out.println(basicCoin);	
		basicCoin.addWallet("Simon", 100);
		basicCoin.addWallet("Bill", 100);
//		basicCoin.addWallet("Elon", 100);
//		basicCoin.addWallet("Steve", 100);
//		basicCoin.addWallet("Jeff", 100);
//		basicCoin.addWallet("Warren", 100);
		basicCoin.addTransaction("Simon", "Bill", "💻", 10);
//		basicCoin.addTransaction("Simon", "Elon", "🚀", 10);
//		basicCoin.addTransaction("Simon", "Steve", "🍎", 10);
//		basicCoin.addTransaction("Simon", "Jeff", "📚", 10);
//		basicCoin.addTransaction("Simon", "Warren", "📈", 10);
		System.out.println(basicCoin);
		
	}

}

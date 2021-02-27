package me.simongohl.basiccoin;

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
	
	LinkedList<Block> blockchain;
	ArrayList<Transaction> pendingTransactions;
	Hashtable<String, Wallet> wallets; 
	
	public BasicCoin() {
		this.blockchain = new LinkedList<Block>();
		this.pendingTransactions = new ArrayList<Transaction>();
		//@TODO Not sure if I want wallets in here 🤷..not secure 🤦 🙅‍♂️ 🚨!!!
		this.wallets = new Hashtable<String, Wallet>();
	 }

	public void addGenesisBlock() throws NoSuchAlgorithmException {
		Transaction transaction = new Transaction("Basic", "Coin", "genesis", 1);
		this.pendingTransactions.add(transaction);
		Block genesis = new Block(0, null, this.pendingTransactions, new Date().toString());
		this.blockchain.add(genesis);
		this.pendingTransactions.removeAll(pendingTransactions);
	}
	
	public Block getLastBlock() {
		return this.blockchain.getLast();
	}
	
	public boolean addTransaction(
			String senderName, 
			String receiverName, 
			String memo,
			int coinAmount) 
					throws NoSuchAlgorithmException {
		boolean isAdded = true;
		
		//@TODO Check sender balance before adding transaction (including pending transactions)
		Transaction transaction = new Transaction(senderName, receiverName, memo, coinAmount);
		
		//@TODO Only pass sender and receiver wallets
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
		for (int i = 1; i <= this.pendingTransactions.size(); i++) {
			
			tempPendingTransactions.add(this.pendingTransactions.get(i - 1));
			
			// Add new block to blockchain
			if (i % MAX_BLOCK_SIZE == 0) {
				Block newBlock = new Block(
						this.blockchain.size(), 
						this.getLastBlock().hash, 
						tempPendingTransactions, 
						new Date().toString());
				newBlock.hash = newBlock.mineBlock();;
				this.blockchain.add(newBlock);
				
				//@TODO Bookkeeping: add/subtract from wallet balance per transaction
				
				// Reward miner 👷 ⛏ 💰
				int balance = this.wallets.get(minerName).getBalance();
				this.wallets.get(minerName).setBalance(balance + MINING_REWARD);
				
				tempPendingTransactions.removeAll(tempPendingTransactions);
			}
			if (i == this.pendingTransactions.size()) {
				this.pendingTransactions = tempPendingTransactions;
				break;
			}
		}
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
		for (Block block: this.blockchain) {
			tempStr += block;
			for (Transaction transaction : block.transactions) {
				tempStr += transaction;
			}
		}
//		for (Transaction t : this.pendingTransactions) {
//			tempStr += "\nPending Transaction: " + t.senderName + " added " + 
//					t.coinAmount + " BasicCoin(s) to " + t.receiverName + "'s wallet.";
//		}
		
		return tempStr;
	}
	
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		BasicCoin basicCoin = new BasicCoin();
		basicCoin.addGenesisBlock();
		System.out.println("\ngenesis: " + basicCoin);	
		basicCoin.addWallet("Simon", 100);
		basicCoin.addWallet("Bill", 100);
		basicCoin.addWallet("Elon", 100);
		basicCoin.addWallet("Steve", 100);
		basicCoin.addWallet("Jeff", 100);
		basicCoin.addWallet("Warren", 100);
		basicCoin.addTransaction("Simon", "Bill", "💻", 10);
		basicCoin.addTransaction("Simon", "Elon", "🚀", 10);
		basicCoin.addTransaction("Simon", "Steve", "🍎", 10);
		basicCoin.addTransaction("Simon", "Jeff", "📚", 10);
		basicCoin.addTransaction("Simon", "Warren", "📈", 10);
		basicCoin.minePendingTransactions("Simon");

		basicCoin.addTransaction("Jeff", "Simon", "📚", 10);
		basicCoin.addTransaction("Warren", "Simon", "📈", 10);
		basicCoin.addTransaction("Bill", "Simon", "💻", 10);
		basicCoin.addTransaction("Elon", "Simon", "🚀", 10);
		basicCoin.addTransaction("Steve", "Simon", "🍎", 10);
		
		basicCoin.minePendingTransactions("Simon");
		System.out.println("\n" + basicCoin);
	}

}

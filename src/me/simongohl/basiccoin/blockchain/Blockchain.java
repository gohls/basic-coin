package me.simongohl.basiccoin.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;

import me.simongohl.basiccoin.wallet.Wallet;

public class Blockchain {
	public static final int MAX_BLOCK_SIZE = 5;
	public static final int MINING_DIFFICULTY = 3;
	public static final int MINING_REWARD = 10;
	
	LinkedList<Block> chain;
	ArrayList<Transaction> pendingTransactions;
	Hashtable<String, Wallet> wallets; 
	
	public Blockchain() {
		this.chain = new LinkedList<Block>();
		this.pendingTransactions = new ArrayList<Transaction>();
		//@TODO Not sure if I want wallets in here 🤷..not secure 🤦 🙅‍♂️ 🚨!!!
		this.wallets = new Hashtable<String, Wallet>();
	 }

	public void addGenesisBlock() throws NoSuchAlgorithmException {
		Transaction transaction = new Transaction("Basic", "Coin", "genesis", 1);
		this.pendingTransactions.add(transaction);
		Block genesis = new Block(0, null, this.pendingTransactions, new Date().toString());
		this.chain.add(genesis);
		this.pendingTransactions.removeAll(pendingTransactions);
	}
	
	public Block getLastBlock() {
		return this.chain.getLast();
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
			
			// Add new block to chain
			if (i % MAX_BLOCK_SIZE == 0) {
				Block newBlock = new Block(
						this.chain.size(), 
						this.getLastBlock().hash, 
						tempPendingTransactions, 
						new Date().toString());
				newBlock.hash = newBlock.mineBlock();;
				this.chain.add(newBlock);
				
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
	
	//@TODO Get Transaction history of Wallet 
	
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
		for (Block block: this.chain) {
			tempStr += block;
			for (Transaction transaction : block.transactions) {
				tempStr += transaction;
			}
		}
		
		return tempStr;
	}
}


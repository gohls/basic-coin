package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;

import me.simongohl.basiccoin.util.HashTool;
import me.simongohl.basiccoin.wallet.Wallet;

public class Block {
	int index;
	String prevBlockHash;
	ArrayList<Transaction> transactions;
	String time;
	private int nonce = 0;
	String hash;
	
	/**
	 * 
	 * @param String index
	 * @param String prevBlockHash
	 * @param ArrayList<Transaction> transactions
	 * @param String time
	 * @throws NoSuchAlgorithmException
	 */
	public Block(int index, String prevBlockHash, ArrayList<Transaction> transactions, String time) 
			throws NoSuchAlgorithmException {
		this.index = index;
		this.prevBlockHash = prevBlockHash;
		this.transactions = transactions;
		this.time = time;
		this.hash = this.computeBlockHash();
	}
	
	public String computeBlockHash() throws NoSuchAlgorithmException {
		String hashTransactions = "";		
		for (Transaction t : this.transactions) {
			hashTransactions += t.hash;
		}
		
		String tempHash = this.time + hashTransactions + this.prevBlockHash + Integer.toString(nonce); 
		String encodedHash = HashTool.calcHash(tempHash);
		
		return encodedHash;
	}
	
	public void mineBlock() throws NoSuchAlgorithmException {
		int miningDifficulty = BasicCoin.MINING_DIFFICULTY;
		String hashStartsWith = "";
		for (int i = 0; i < miningDifficulty; i++) {
			hashStartsWith += i;
		}

		System.out.println("Mining start...");
		do {
			this.nonce++;
			this.hash = this.computeBlockHash();
		} while (!hashStartsWith.equals((this.hash.substring(0, miningDifficulty))));
		
		System.out.println("Mining done!");
		System.out.println("Block hash:" + this.hash);
	}
	
	public boolean hasValidTransactions(Hashtable<String, Wallet> wallets) throws NoSuchAlgorithmException {
		boolean hasValidTransactions = true;
		for(Transaction t : this.transactions) {
			if(!t.isValidTransaction(wallets)) {
				hasValidTransactions = false;
				break;
			}
		}
		
		return hasValidTransactions;
	}
	
	@Override
	public String toString() {
		String tempStr = "";
		tempStr += "Block ID: \t" + this.index + "\n" +
					"Prev Block ID: \t" + this.prevBlockHash + "\n" +
					"Block Time: \t" + this.time + "\n" +
					"Block Hash: \t" + this.hash + "\n";

		return tempStr;
	}
	
		
}

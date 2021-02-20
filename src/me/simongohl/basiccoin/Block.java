package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import me.simongohl.basiccoin.util.HashTool;

public class Block {
	String blockID;
	String prevBlockID;
	ArrayList<Transaction> transactions;
	String time;
	int nonce;
	String hash;
	
	public Block() {
		this.nonce = 0;	
	}
	
	public Block(String blockID, String prevBlockID, ArrayList<Transaction> transactions, String time) 
			throws NoSuchAlgorithmException {
		super();
		this.blockID = blockID;
		this.prevBlockID = prevBlockID;
		this.transactions = transactions;
		this.time = time;
		this.hash = this.calcBlockHash();
	}
	
	public String calcBlockHash() throws NoSuchAlgorithmException {
		String hashTransactions = "";		
		for (Transaction t : this.transactions) {
			hashTransactions += t.hash;
		}
		
		String tempHash = this.time + hashTransactions + this.prevBlockID + Integer.toString(nonce); 
		String encodedHash = HashTool.calcHash(tempHash);
		
		return encodedHash;
	}
	
	public void mineBlock(int miningDifficulty) throws NoSuchAlgorithmException {
		String hashStartsWith = "";
		for (int i = 0; i < miningDifficulty; i++) {
			hashStartsWith += i;
		}

		System.out.println("Mining start...");
		do {
			this.nonce++;
			this.hash = this.calcBlockHash();
		} while (!hashStartsWith.equals((this.hash.substring(0, miningDifficulty))));
		
		System.out.print("Mining done!");
	}
	
	public boolean hasValidTransactions() throws NoSuchAlgorithmException {
		boolean hasValidTransactions = true;
		for(Transaction t : this.transactions) {
			if(!t.isValidTransaction()) {
				hasValidTransactions = false;
				break;
			}
		}
		
		return hasValidTransactions;
	}
		
}

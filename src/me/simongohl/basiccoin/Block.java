package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import me.simongohl.basiccoin.util.HashTool;

public class Block {
	String blockID;
	String prevBlockID;
	ArrayList<Transaction> transactions;
	String time;
	int nonse;
	String hash;
	
	public Block() {
		this.nonse = 0;	
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
		String tempHash = this.time + hashTransactions + this.prevBlockID + Integer.toString(nonse); 
		String encodedHash = HashTool.calcHash(tempHash);
		return encodedHash;
	}
	
	public boolean mineBlock(int miningDifficulty) throws NoSuchAlgorithmException {
		String hashStartsWith = "";
		for (int i = 0; i < miningDifficulty; i++) {
			hashStartsWith += i;
		}

		System.out.println("Mining start...");
		do {
			this.nonse++;
			this.hash = this.calcBlockHash();
		} while (!hashStartsWith.equals((this.hash.substring(0, miningDifficulty))));
		// System.out.println("nonse: " + this.nonse);
		// System.out.println("Block hash: " + this.hash);
		System.out.print("Mining done!");
		
		return true;
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

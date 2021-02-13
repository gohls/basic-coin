package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class Block {
	
	String blockID;
	String prevBlockID;
	ArrayList<Transaction> transactions;
	String time;
	int nonse;
	String hash;
	
	public Block(
			String blockID, 
			String prevBlockID, 
			ArrayList<Transaction> transactions, 
			String time, 
			int nonse, 
			String hash) 
					throws NoSuchAlgorithmException {
		super();
		this.blockID = blockID;
		this.prevBlockID = prevBlockID;
		this.transactions = transactions;
		this.time = time;
		this.nonse = nonse;
		this.hash = this.calcBlockHash();
	}
	
	public String calcBlockHash() throws NoSuchAlgorithmException {
		String hashTransactions = "";
		for (Transaction t : this.transactions) {
			hashTransactions += t.hash;
		}
		String hashStr = Utils.calcHash(hashTransactions);
		return hashStr;
	}
	
	public boolean mineBlock(int miningDifficulty) throws NoSuchAlgorithmException {
		int[] arr = new int[miningDifficulty];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		String str = Arrays.toString(arr);
		do {
			this.nonse++;
			this.hash = this.calcBlockHash();
		} while (!str.substring(0, miningDifficulty).equals(
				(this.hash.substring(0, miningDifficulty))));
		
		return true;
	}
	
	
	
}

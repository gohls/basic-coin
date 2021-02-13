package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


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
	
	public void blockchain() {
		ArrayList<Block> blockchain = new ArrayList<Block>();
		ArrayList<Transaction> pendingTransactions = new ArrayList<Transaction>();
		int miningDifficulty = 2;
		int blockSize = 5;
		
	 }
	
	public void addGenesisBlock() {};
	
	public void addTransaction() {};
	
	public void minePendingTransactions() {};
	
	
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		System.out.println("Hello BasicCoin!");	
		
		Transaction transaction = new Transaction("me", "you", 510, "test");
		System.out.println(transaction.hash);
		
	}

}

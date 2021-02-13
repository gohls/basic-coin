package me.simongohl.basiccoin;

public class Block {
	
	String blockID;
	String prevBlockID;
	Transaction transaction;
	String time;
	int nonse;
	String hash;
	
	public Block(String blockID, String prevBlockID, Transaction transaction, String time, int nonse, String hash) {
		super();
		this.blockID = blockID;
		this.prevBlockID = prevBlockID;
		this.transaction = transaction;
		this.time = time;
		this.nonse = nonse;
		this.hash = hash;
	}
	
}

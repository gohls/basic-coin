package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import me.simongohl.basiccoin.blockchain.Blockchain;


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
	
	public static void makeWallet() {}
	
	public static void sendBasicCoin() {}
	
	public static void mineBlock() {}
	
	public static void getBalance() {}
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		
		Blockchain basicCoin = new Blockchain();
		basicCoin.addGenesisBlock();
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		int selectedOpt;
		do {
			System.out.println("\nBasicCoin Menu: \n" +
								"1.\t Make a Wallet \n" + 
								"2 \t Send BasicCoin \n" +
								"3 \t Mine Block \n" +
								"4 \t Get Balance \n" +
								"5 \t Exit \n\n");
			System.out.print("Enter selection: ");
			selectedOpt = scan.nextInt();
			
			switch (selectedOpt) {
				case 1:
					 makeWallet();
				case 2:
					 sendBasicCoin();
				case 3: 
					 mineBlock();
				case 4: 
					 getBalance();
				default:
					break;
			}
		}
		while (selectedOpt != 5);
		
		
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

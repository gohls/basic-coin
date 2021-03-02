package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import me.simongohl.basiccoin.blockchain.Blockchain;
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
	private final Wallet myWallet;

	public BasicCoin(String name) throws NoSuchAlgorithmException {
		this.myWallet = new Wallet(name, 100);
	}
	
	public static void generateWallets() {}
	
	public static void printNames() {}
	
	public static void sendBasicCoin() {}
	
	public static void requestBasicCoin() {}
	
	public static void mineBlock() {}
	
	public static void printBalance() {}
	
	public static void printBalanceOf() {}
	
	public static void printBalanceOfAll() {}
	
	public static void printTransactionHistory() {}
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		Blockchain basicCoin = new Blockchain();
		basicCoin.addGenesisBlock();
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int selectedOpt;
		do {
			System.out.println("\nBasicCoin Menu: \n" +
								"1.\t Make a Wallet \n" + 
								"2 \t Send BasicCoin \n" +
								"3 \t Mine Block \n" +
								"4 \t Get Balance \n" +
								"5 \t Exit \n\n");
			System.out.print("Enter selection: ");
			selectedOpt = scanner.nextInt();
			
			switch (selectedOpt) {
				case 1:
					 generateWallets();
				case 2:
					 sendBasicCoin();
				case 3: 
					 mineBlock();
				case 4: 
					 printBalance();
				default:
					break;
			}
		}
		while (selectedOpt != 5);
		
		System.out.println("\n" + basicCoin);
	}

}

package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import me.simongohl.basiccoin.blockchain.Blockchain;
import me.simongohl.basiccoin.util.FileTool;
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
	Blockchain basicCoin;
	
	public BasicCoin() {
		this.basicCoin = new Blockchain();
	}

	public void generateWallets() throws NumberFormatException, NoSuchAlgorithmException {
		ArrayList<String> data = new ArrayList<String>(FileTool.readFile());
		String[] walletDataArray = new String[2];
		for (String d : data) {
			// @TODO some error handling would be good here
			// - Make sure no name is used twice (not sure what happens when there is a collision..error?)
			walletDataArray = d.split(",");
			this.basicCoin.addWallet(walletDataArray[0], Integer.parseInt(walletDataArray[1]));
		}
	}
	
	public void printWalletNames() {
		Set<String> wallets = this.basicCoin.getWallets().keySet();
		for (String name : wallets) {
			System.out.println(name);
		}
	}
	
	public void generateTransactions() {
		System.out.println("");
	}
	
	public static void makeATransaction() {
		System.out.println("");
	}
	
	public static void requestBasicCoin() {
		
	}
	
	public static void mineBlock() {
		
	}
	
	public static void getBalance() {
		
	}
	
	public int getBalanceOf(String name) {
		Wallet wallet = this.basicCoin.getWallet(name);
		return wallet.getBalance();
	}
	
	public static void getBalanceOfAll() {}
	
	public static void printTransactionHistory() {}
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {
		BasicCoin basicCoin = new BasicCoin();
		
		// Not the best ascii art 
		System.out.println("  ##===\\       //===\\    ");
		System.out.println("  ##   ||      ##          ");
		System.out.println("  ##===//      ##          ");
		System.out.println("  ##   \\      ##          ");
		System.out.println("  ##   || asic ##      oin ");
		System.out.println("  ##===//       \\===//    ");
		System.out.println("");
		System.out.println("Intializing!");
		System.out.print("Generating Wallets...");
		basicCoin.generateWallets();
		System.out.print("Completed!");
		basicCoin.generateTransactions();
		System.out.print("Generating Wallets...");
		System.out.print("Completed!");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int selectedOpt;
		do {
			System.out.println("\nBasicCoin Menu: \n" +
								"1.\t Generate Wallets \n" + 
								"2. \t Print Wallet Names \n" +
								"3. \t Mine Block \n" +
								"4. \t Get Balance \n" +
								"5. \t Exit \n\n");
			System.out.print("Enter selection: ");
			selectedOpt = scanner.nextInt();
			
			switch (selectedOpt) {
				case 1:
					basicCoin.generateWallets();
				case 2: 
					basicCoin.printWalletNames();
				case 3:
					makeATransaction();
				case 4: 
					 mineBlock();
				case 5: 
					 getBalance();
				default:
					break;
			}
		}
		while (selectedOpt != 5);
		
		System.out.println("\n" + basicCoin);
	}

}

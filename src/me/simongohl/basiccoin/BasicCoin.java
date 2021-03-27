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
	String name;
	Wallet wallet;
	
	public BasicCoin(String name) throws NoSuchAlgorithmException {
		this.basicCoin = new Blockchain();
		this.name = name;
		this.wallet = this.basicCoin.addWallet(this.name, 100);
	}
	

	final void generateWallets() throws NumberFormatException, NoSuchAlgorithmException {
		ArrayList<String> data = new ArrayList<String>(FileTool.readFile());
		String[] walletDataArray = new String[2];
		for (String d : data) {
			// @TODO some error handling would be good here
			// - Make sure no name is used twice (not sure what happens when there is a collision..error?)
			walletDataArray = d.split(",");
			this.basicCoin.addWallet(walletDataArray[0], Integer.parseInt(walletDataArray[1]));
		}
	}
	
	final void generateTransactions() {
//		System.out.println("");
	}
	
	public void printWalletNames() {
		Set<String> wallets = this.basicCoin.getWallets().keySet();
		for (String name : wallets) {
			System.out.println(name);
		}
	}
	
	public static void makeATransaction() {
		System.out.println("");
	}
	
	public static void requestBasicCoin() {
		
	}
	
	public static void mineBlock() {
		
	}
	
	public int getBalance() {
		return this.wallet.getBalance();
	}
	
	public int getBalanceOf(String name) {
		Wallet wallet = this.basicCoin.getWallet(name);
		return wallet.getBalance();
	}
	
	public static void getBalanceOfAll() {
		
	}
	
	public static void printTransactionHistory() {}
	
	public static void main(String[] agrs) throws NoSuchAlgorithmException {	
		// 1. Print BasicCoin Logo
		// Not the best ascii text art 
		System.out.println("  ||===\\\\       //===\\\\    ");
		System.out.println("  ||   ||      ||          ");
		System.out.println("  ||===//      ||          ");
		System.out.println("  ||   \\\\      ||          ");
		System.out.println("  ||   || asic ||      oin ");
		System.out.println("  ||===//       \\\\===//    ");
		
		// 2. Get name to create Wallet
		System.out.println("");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("To get started, Enter your first name: ");
		String name = scanner.nextLine();
		
		System.out.println("");
		System.out.println("Intializing!");
		
		System.out.print("- Creating your wallet... ");
		BasicCoin basicCoin = new BasicCoin(name);
		System.out.print("Completed!");
		System.out.println("");
		
		System.out.print("- Generating wallets (this may take a minute)... ");
		basicCoin.generateWallets();
		System.out.print("Completed!");
		System.out.println("");
		
		System.out.print("- Generating some transactions... ");
		basicCoin.generateTransactions();
		System.out.print("Completed!");
		System.out.println("");
		
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
//					 getBalance();
				default:
					break;
			}
		}
		while (selectedOpt != 5);
		
		System.out.println("\n" + basicCoin);
	}

}

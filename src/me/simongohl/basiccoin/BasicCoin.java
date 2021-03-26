package me.simongohl.basiccoin;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

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
		String[] walletArray = new String[2];
		for (String d : data) {
			// @TODO parse data between name and amount
			// Basiccoin.addWallet(name);
			System.out.println(d);
			walletArray = d.split(",");
			this.basicCoin.addWallet(walletArray[0], Integer.parseInt(walletArray[1]));
		}
	}
	
	public static void generateTransactions() {}
	
	public static void printWalletNames() {
//		for (Wallet wallet : Basiccoin.wallets) {
//			System.out.println(wallet.name)
//		}
	}
	
	public static void makeATransaction() {}
	
	public static void requestBasicCoin() {}
	
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
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int selectedOpt;
		do {
			System.out.println("\nBasicCoin Menu: \n" +
								"1.\t Generate Wallets \n" + 
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
					makeATransaction();
				case 3: 
					 mineBlock();
				case 4: 
					 getBalance();
				default:
					break;
			}
		}
		while (selectedOpt != 5);
		
		System.out.println("\n" + basicCoin);
	}

}

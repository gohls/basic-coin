package me.simongohl.basiccoin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileTool {
	private static final String FILE_READER_PATH = "./BasicCoin/sample_data.csv";
	// private static final String FILE_WRITER_PATH = "./BasicCoin/transaction_history.csv";
	
	public static Set<String> readFile() {
		// Type Set to avoid name clash with Wallets
		Set<String> names = new HashSet<String>();
		
		try {
		  	File fileObj = new File(FILE_READER_PATH);
		  	Scanner scanner = new Scanner(fileObj);
		  	boolean header = true;
		  	while (scanner.hasNextLine()) {
		  		// might need this for parsing later..?
		  		if (header) {
		  			scanner.nextLine();
		  			header = false;
		  		}
				String name = scanner.nextLine();
				names.add(name);
				}
		  	scanner.close();
		    } catch (FileNotFoundException e) {
		    	System.out.println("Error: could not find file.");
		    	e.printStackTrace();
		    }
		
		return names;
	}
	
	public static void writeFile() {
		//@TODO print transaction history (receipts)
	}

}


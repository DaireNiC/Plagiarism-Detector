package ie.gmit.sw;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Menu class is the command line UI provided to allow the
 * user to enter files for the similarity computation.
 * 
 * @author Daire Ní Chathain
 *
 */
public class Menu {

	private Scanner console = new Scanner(System.in);
	private DocumentSimilarityLauncher launcher;

	public Menu() {
		this.launcher = new DocumentSimilarityLauncher( );
	}
	
	// ===Menu UI===//
	public void showMenu() {
		printMenu();
		int choice = console.nextInt();
		while (choice != 3) {
			switch (choice) {
			case 1:// Enter files for comparision
				System.out.println("How many documents would you like to compare (max 2): ");
				choice = console.nextInt();
				try {
					getFilesForParsing(choice);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case 0:
				// exit application
				System.exit(0);
				break;
			case 4:
			default:
				System.out.println("Please enter a valid option");
				break;
			}
			printMenu();
			choice = console.nextInt();
		}
	}

	/**
	 * Prints Main Menu
	 */
	public  void printMenu() {
		System.out.println("\n -----Document Similarity Checker----");
		System.out.println("(1): To enter files for comparision: ");
		System.out.println("(0): Exit: ");
		System.out.print("=============================\nPlease Enter your option here: ");

	}

	/**
	 * @param numFiles 	Number of files the user wishes to parse 
	 * @throws FileNotFoundException	Exception
	 * @throws InterruptedException	Exception
	 */
	public  void getFilesForParsing(int numFiles) throws FileNotFoundException, InterruptedException {
		List<String> files = new ArrayList<String>();

		for (int i = 0; i < numFiles; i++) {
			// input files
			System.out.println("Please enter name(s) of input files (include .txt)");
			String inputFile = console.next();
			// ensuring input file is valid
			if (!(inputFile.endsWith(".txt"))) {
				System.out.println("invalid input file");
			} else {
				files.add(inputFile);
			}
		}
		//Enter details for similarity computation 
		System.out.println("Please enter the shingle size you wish to use");
		System.out.println("shingle size: ");
		int shingleSize = console.nextInt();
		
		System.out.println("Please enter the shingle size you wish to use\n"
				+ "Note: A larger size will result in longer run times & in some cases improved accuracy \n Size (200 - 300) recommended");
		System.out.println("sample size: ");
		int k = console.nextInt();
		System.out.println("Processing..please wait...");
		// launch similarity checker with given valid input
		launcher.launch(files, shingleSize, k);
	}

}

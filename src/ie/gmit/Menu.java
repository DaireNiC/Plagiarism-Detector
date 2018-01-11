package ie.gmit;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

	public static Scanner console = new Scanner(System.in);
	private DocumentSimilarityLauncher launcher;

	public Menu() {
	}
	
	// ===Menu UI===//
	public static void showMenu() {
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

	// ===Prints Main Menu===//
	public static void printMenu() {
		System.out.println("/n-----Document Similarity Checker----");
		System.out.println("(1): To enter files for comparision: ");
		System.out.println("(0): Exit: ");
		System.out.print("=============================\nPlease Enter your option here: ");

	}

	// ===Takes User input for parsing & parses URL or txt===//
	public static void getFilesForParsing(int numFiles) throws FileNotFoundException, InterruptedException {
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
		System.out.println("Please enter the shingle size you wish to use");
		System.out.println("shingle size: ");
		int shingleSize = console.nextInt();
		System.out.println("Please enter the shingle size you wish to use\n"
				+ "Note: A larger size will result in longer run times & in some cases improved accuracy \n Size (200 - 300) recommended");
		System.out.println("sample size: ");
		int k = console.nextInt();
		
		// launch similarity checker with given valid input
		startDoccumentSimilarityCheck(files, shingleSize, k);
		System.out.println(" Recheck? ");
		int a = console.nextInt();
	}

	public static void startDoccumentSimilarityCheck(List<String> files, int shingleSize, int k) throws FileNotFoundException, InterruptedException {
		// Begin the application
		DocumentSimilarityLauncher launcher = new DocumentSimilarityLauncher( );
		launcher.launch(files, shingleSize, k);
	}

}

package ie.gmit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	public static Scanner console = new Scanner (System.in);
	private List<String> files = new ArrayList<String>();
	
//===Menu UI===//
public static void showMenu() {  
	printMenu();
    int choice = console.nextInt();
        while (choice!= 3) {
        	switch (choice) {
	            case 1://Enter files for comparision
	            	System.out.println("How many documents would you like to compare: ");
	            	choice = console.nextInt();
	            	getFilesForParsing(choice);
	            	break;
	            case 0:  	
	            	//exit application
	            	System.exit(0);
	            	 break;
	            case 4: 
	            default: 
	            	System.out.println("Please enter a valid option");
	            	break;
        	}  printMenu();
    	choice = console.nextInt();
    }       	
}			
//===Prints Main Menu===//
private static void printMenu() {
	System.out.println("/n-----Document Similarity Checker----");
	System.out.println("(1): To enter files for comparision: ");
	System.out.println("(0): Exit: ");
	System.out.print("=============================\nPlease Enter your option here: ");
	
}
	
	//===Takes User input for parsing & parses URL or txt===//
	private static List<String> getFilesForParsing(int numFiles) {
		
		List<String> files = new ArrayList<String>();
		
		for(int i = 0; i < numFiles; i++){
	    	//input files
	    	System.out.println("Please enter name(s) of input files (include .txt)");
			String inputFile = console.next();
			//ensuring input file is valid
			if(!(inputFile.endsWith(".txt")) ){
				System.out.println("invalid input file");
			}
			else{
				files.add(inputFile);
			}
		}
		return files;
		
	}


	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
}

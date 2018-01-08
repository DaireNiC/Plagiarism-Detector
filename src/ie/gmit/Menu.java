package ie.gmit;

import java.util.Scanner;

import ie.gmit.sw.FileParser;
import ie.gmit.sw.Parser;
import ie.gmit.sw.URLParser;

public class Menu {
	public static Scanner console = new Scanner (System.in);
	
	

//===Menu UI===//
private static void showMenu() {  
	printMenu();
    int choice = console.nextInt();
        while (choice!= 3) {
        	switch (choice) {
	            case 1://Encrypt case
	            case 2://Decrypt case
	            	//calls the parser method which determine if input is url or .txt
	            	parser();
	            	break;
	            case 3:  	
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
	private static void parser() {
		
		//input for url or txt
		System.out.print("\n====Option (1)====\nEnter a URL(begining with http) or  a file (.txt) that you wish to parse: ");
    	String parseMe  = console.next();

    	//input for keyword
    	System.out.println("Please enter Keyword: ");
		String keyword = console.next();
		 		
    	//input for outputfile
    	System.out.println("Please enter name of output file (include .txt)");
		String outputfile = console.next();
		//ensuring output file is valid
		if(!(outputfile.endsWith(".txt")) ){
			System.out.println("invalid name for output file");
		}

    	
		//FileParse
		if(parseMe.endsWith(".txt")){
			Parser parseobject = new FileParser();
		//	parseobject.parse(parseMe, keyword, isEncrypt);
			parseobject.parse(parseMe, keyword, outputfile);
			System.out.println("File Parse Succesful");			
		}
		//URL Parse
		else if(parseMe.startsWith("http")){
			Parser parseobject = new URLParser();
		//	parseobject.parse(parseMe,keyword);
			parseobject.parse(parseMe, keyword, outputfile);
			System.out.println("URL Parse Succesful");
		}
		//Handle Exception of invalid input
		else{
			System.out.println("Please enter a valid URL or .txt");			
		}				
	}
}
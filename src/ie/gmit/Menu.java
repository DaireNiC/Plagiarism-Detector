package ie.gmit;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	public static Scanner console = new Scanner (System.in);
	private List<String> files = new ArrayList<String>();

public	static void main(String []  args){
	showMenu();
}
	
	
//===Menu UI===//
public static void showMenu() {  
	printMenu();
    int choice = console.nextInt();
        while (choice!= 3) {
        	switch (choice) {
	            case 1://Enter files for comparision
	            	System.out.println("How many documents would you like to compare: ");
	            	choice = console.nextInt();
				try {
					getFilesForParsing(choice);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	private static void getFilesForParsing(int numFiles) throws FileNotFoundException, InterruptedException {
		
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
		System.out.println("Please enter the shingle size you wish to use");
		System.out.println("shingle size: ");
		int shingleSize = console.nextInt();
    	System.out.println("Please enter the shingle size you wish to use\n"
    			+ "Note: A larger size will result in longer run times & in some cases improved accuracy \n Size (200 - 300) recommended");
    	System.out.println("sample size: ");
    	int k = console.nextInt();
		
		//launch similarity checker with given valid input
		Launcher l = new Launcher(files, shingleSize, k);
		l.launch();
		
	}


	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
}

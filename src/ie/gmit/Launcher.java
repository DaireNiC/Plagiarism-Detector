package ie.gmit;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Launcher {

	public static void main(String [] args) throws InterruptedException, FileNotFoundException{
		
		String filePath = "text.txt";
		int id = 1;
		String filePath2 = "text2.txt";
		int id2 = 2;
		
		int shingleSize = 3;
		// Holds all the shingles that have been created from documents
		// minhash threads take from this queue
		BlockingQueue <Shingle> q = new LinkedBlockingQueue<Shingle>(100);
	
		Thread t1 = new Thread(new FileToShingleParser(id, filePath, q, shingleSize));
		Thread t2 = new Thread(new FileToShingleParser(id2, filePath2, q, shingleSize));
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		for (Shingle s : q){
			System.out.println(s.hashCode() + "\t" + s.getFileID());
		}//pass in string list , for each create a thread and docparser
	}
	
}

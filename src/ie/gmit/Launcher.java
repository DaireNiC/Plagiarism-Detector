package ie.gmit;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Launcher {

	private static Menu menu ;
	
	public Launcher(){
		this.menu = new Menu();
	}
	
	public static void main(String [] args) throws InterruptedException, FileNotFoundException{
		
	//	menu = new Menu();
	//	menu.showMenu();
		
		String filePath = "text2.txt";
		int id = 1;
		String filePath2 = "text.txt";
		int id2 = 2;
		
		int k = 30;
		int poolSize = 8;
		System.out.println();
		int shingleSize = 3;
		// Holds all the shingles that have been created from documents
		// minhash threads take from this queue
		BlockingQueue <Shingle> q = new LinkedBlockingQueue<Shingle>(150);
		Consumer consumer = new Consumer(q,k, poolSize);
		
		Thread t1 = new Thread(new FileToShingleParser(id, filePath, q, shingleSize));
		Thread t2 = new Thread(new FileToShingleParser(id2, filePath2, q, shingleSize));
		Thread t3 = new Thread(consumer);
		
		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		
		t3.join();
		
		Map <Integer,List<Integer>> map = new HashMap<Integer, List<Integer>>();
		map = consumer.getMap();

		for (Integer key : map.keySet()) {
		    System.out.println(key + "keyo");
		}
		
		System.out.println(map.get(1).size());
		List<Integer> list = map.get(1);

		System.out.println(map.get(2).size());
		
		List<Integer> intersection = map.get(1);
		intersection.retainAll(map.get(2));
		System.out.println("same" + intersection.size());
		float jaccard = ((float)intersection.size() / (((map.get(1).size() + map.get(2).size()) - intersection.size() )) );
		System.out.println(jaccard);
		
		System.out.println("all finished");
		
	}
	
}

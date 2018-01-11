package ie.gmit;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import net.sourceforge.plantuml.api.MyRunnable;

public class Launcher {
	private BlockingQueue <Shingle> q;
	private List<String> files;
	private int shingleSize;
	private int k;
	private int threadPoolSize;
	
	public Launcher(List<String> files, int shingleSize, int k){
		this.files = files;
		this.shingleSize = shingleSize;
		this.k = k;
		this.threadPoolSize = 8;
		this.q = new LinkedBlockingQueue<Shingle>(150);
	}
	
	public  void launch() throws InterruptedException, FileNotFoundException{

		
		Consumer consumer = new Consumer(q,k, threadPoolSize);
		Thread tConsumer = new Thread(consumer);
		
		
		ExecutorService executor = Executors.newFixedThreadPool(files.size());

		for(int i =0; i < files.size(); i ++)
		{
		    System.out.println("starting stread for doc " + (i+1));
		    Thread t = new Thread(new FileToShingleParser(i+1, files.get(i), q, shingleSize));
		    executor.submit(t);
		}
		tConsumer.start();

		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		System.out.println("thread shutdown..");
		tConsumer.join();
		/*
		 * 
		 * 
		
		String filePath = "text2.txt";
		int id = 1;
		String filePath2 = "text.txt";
		int id2 = 2;
		
		int k = 300;
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
		
		
		tConsu
	*/	
		
		
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

package ie.gmit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Consumer implements Minhasher, Runnable {
	
	private BlockingQueue<Shingle> q;
	private int k;
	private int [] minHashes;
	private Map<Integer, List<Integer>> map = new HashMap<>();
	private ExecutorService pool;
	
	private Consumer(BlockingQueue<Shingle> q, int k, int poolSize){
		this.q = q;
		this.k = k;
		this.pool = Executors.newFixedThreadPool(poolSize);
		this.minHashes = new int [k];
		initHashes(minHashes);
	}
	
	public void run(){
		
		int docCount = 2;//change
		while(docCount > 0){
			try {
				Shingle s = q.take();
			//do poison check
				
			pool.execute(new Runnable(){
				public void run(){
					for(int i = 0; i < minHashes.length; i ++){
						//get the hashcode for the shingle --> make it random by using random num and XOR operator
						int value = s.getHashCode()^minHashes[i];
						//get a handle on the linked list for doc shingle belongs to 
						List<Integer> list = map.get(s.getFileID());
						if(list == null){
							//initialise the list with max value 
							for(int j = 0; i < k; i++){
								list.set(i, Integer.MAX_VALUE);
							}
							map.put(s.getFileID(), list);
						}
						else{
							if(list.get(i) > value){
								list.set(i, value);
							}
						}
					}
				}
			});
			
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	@Override
	public int[] initHashes(int[] minHashes) {
		Random r = new Random();
		for(int i = 0; i < minHashes.length ; i ++){
			minHashes[i] = r.nextInt();
			System.out.println("radnom: "  + minHashes[i]);
		}
		
		return minHashes;
	}

}

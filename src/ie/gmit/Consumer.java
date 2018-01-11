package ie.gmit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Consumer implements Minhashator, Runnable {

	private BlockingQueue<Shingle> q;
	private int k;
	private int[] minHashes;
	private Map<Integer, List<Integer>> map;
	private ExecutorService pool;

	public Consumer(BlockingQueue<Shingle> q, int k, int poolSize) {
		this.q = q;
		this.k = k;
		this.pool = Executors.newFixedThreadPool(poolSize);
		this.minHashes = new int[k];
		this.map = new ConcurrentHashMap<Integer, List<Integer>>(2, 0.9f, poolSize);
		initHashes(minHashes);
	}

	public void run() {

		int docCount = 2;// change
		while (docCount > 0) {
			try {
				Shingle s = q.take();
				// do poison check
				if (s instanceof Poison) {
					System.out.println("finished with doc" + s.getFileID());
					docCount--;
				} else {
					pool.execute(new Runnable() {
						public void run() {
							for (int i = 0; i < minHashes.length; i++) {
								// get the hashcode for the shingle --> make it
								// random by using random num and XOR operator
								int value = s.hashCode() ^ minHashes[i];
								List<Integer> list = map.get(s.getFileID());
								if (list == null) {
									list = new ArrayList<Integer>(Collections.nCopies(k, Integer.MAX_VALUE));
									map.put(s.getFileID(), list);
									System.out.println("inserting new  doc" + s.getFileID());
								} else {
									if (list.get(i) > value) {
										list.set(i, value);
									}
								}
							}
						}
					});// runnable
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		// No more threads are submitted to poolservice
		pool.shutdown();
		
		// Blocks until all threads in the pool  have finished!
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			System.out.println("fail");
			e.printStackTrace();
			
		}
		
		System.out.println("Done");
	}

	@Override
	public int[] initHashes(int[] minHashes) {
		Random r = new Random();
		for (int i = 0; i < minHashes.length; i++) {
			minHashes[i] = r.nextInt();
		}

		return minHashes;
	}

	public Map<Integer, List<Integer>> getMap() {
		return this.map;
	}

}

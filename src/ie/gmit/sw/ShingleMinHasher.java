package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The Shingle MinHasher generates minhashes from a shingles provided in the for of a 
 * blocking queue. An ExecutorService is used to spawn worker threads to compute the 
 * minhashes efficiently. 
 * @author Daire Ni Chathain
 *
 */
public class ShingleMinHasher implements Minhashator, Runnable {

	private BlockingQueue<Shingle> q;
	private int k;
	private int numFiles;
	private int[] minHashes;
	private Map<Integer, List<Integer>> map;
	private ExecutorService pool;
	private final int poolSize;

	/**
	 * @param q		The blocking queue of shingles to create minhashes from
	 * @param k		The number of minhashes to be generated
	 * @param numFiles	The number of file for comparision
	 */
	public ShingleMinHasher(BlockingQueue<Shingle> q, int k, int numFiles) {
		this.q = q;
		this.poolSize = 8;
		this.numFiles = numFiles;
		this.k = k;
		this.pool = Executors.newFixedThreadPool(poolSize);
		this.minHashes = new int[k];
		this.map = new ConcurrentHashMap<Integer, List<Integer>>(numFiles, 0.9f, poolSize);
	}

	public void run() {
		while (numFiles > 0) {
			try {
				// take a shingle from the queue
				Shingle s = q.take();
				// do poison check
				if (s instanceof Poison) {
					numFiles--;
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
		//No more threads are submitted to poolservice
		pool.shutdown();
		// Blocks until all threads in the pool have finished!
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			System.out.println("failure shutting down threa pool");
			e.printStackTrace();
		}
	}

	/** 
	 * @see ie.gmit.sw.Minhashator#initHashes()
	 */
	@Override
	public void initHashes() {
		Random r = new Random();
		for (int i = 0; i < minHashes.length; i++) {
			this.minHashes[i] = r.nextInt();
		}
	}


	/**	
	 * compute the jaccard index using the minhashes generated
	 * @return	float Return the computed jaccard distance of the files compared
	 */
	public float getJaccardIndex() {
		int i = 1;
		List<Integer> intersection = map.get(i);
		intersection.retainAll(map.get(i +1));
		float jaccard = (float) intersection.size() / (k * map.size() - (float) intersection.size());
		return jaccard;
	}
}

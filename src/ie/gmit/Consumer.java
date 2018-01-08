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

public class Consumer implements Minhasher, Runnable {

	private BlockingQueue<Shingle> q;
	private int k;
	private int[] minHashes;
	private Map<Integer, List<Integer>> map = new ConcurrentHashMap<>();
	private ExecutorService pool;

	public Consumer(BlockingQueue<Shingle> q, int k, int poolSize) {
		this.q = q;
		this.k = k;
		this.pool = Executors.newFixedThreadPool(poolSize);
		this.minHashes = new int[k];
		initHashes(minHashes);
	}

	public void run() {

		int docCount = 2;// change
		while (docCount > 0) {
			try {
				Shingle s = q.take();
				// do poison check
				if (s instanceof Poison) {
					docCount--;
				} else {
					pool.execute(new Runnable() {
						public void run() {
							List<Integer> list = map.get(s.getFileID());
							for (int i = 0; i < minHashes.length; i++) {
								// get the hashcode for the shingle --> make it
								// random by using random num and XOR operator
								int value = s.getHashCode() ^ minHashes[i];
								if (list == null) {
									list = new ArrayList<Integer>(Collections.nCopies(k, Integer.MAX_VALUE));
									System.out.println("inserting  new doc into map***********************");
									map.put(s.getFileID(), list);
								} else {
									if (list.get(i) > value) {
										list.set(i, value);
									}
								}
							//	map.put(s.getFileID(), list);
							}
						}
					});// runnable
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public int[] initHashes(int[] minHashes) {
		Random r = new Random();
		for (int i = 0; i < minHashes.length; i++) {
			minHashes[i] = r.nextInt();
			System.out.println("radnom: " + minHashes[i]);
		}

		return minHashes;
	}

	public Map<Integer, List<Integer>> getMap() {
		return this.map;
	}

}

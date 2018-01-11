package ie.gmit;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DocumentSimilarityLauncher {
	/**
	 * The DocumentSimilarityLauncher is generated
	 * from the menu class upon user selection.
	 *
	 * The Blocking queue holds shingles generated by the FileToShingleParser.
	 * Shingles are taken from the queue and processed by the ShingleMinHasher.
	 * The Jaccard index is computed when all FileToShingleParser and ShingleMinHasher
	 * threads have terminated.
	 * 
	 * @author Daire N� Chath�in
	 */
	
	private BlockingQueue <Shingle> q;
	
	public DocumentSimilarityLauncher(){
		this.q = new LinkedBlockingQueue<Shingle>(150); //program to abstraction
	}
	
	/**
	 * Begins similarity check by first launching FileToShingleParser
	 * threads for each user specified file. The shingleMinHahser which
	 * acts as a consumer of the FiletoShingleParser result is then 
	 * 
	 * @param files 				Files specified by the user from menu
	 * @param shingleSize			The number of words used to create a shingle
	 * @param k						The sample size of the smallest minhashes generated from FiletoShingleParser result
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	public void launch(List<String> files, int shingleSize, int k) throws InterruptedException, FileNotFoundException{
		
		//start a thread for each of the files entered
		ExecutorService executor = Executors.newFixedThreadPool(files.size());
		// Max of (2) files now but scalable code for more development in future
		for(int i =0; i < files.size(); i ++)
		{
		    System.out.println("starting thread for doc " + (i+1));
		    Thread t = new Thread(new FileToShingleParser(i+1, files.get(i), q, shingleSize));
		    executor.submit(t);
		}
		
		// Consumer thread is a ShingleMinHasher to read parsed shingles and generate k minhashes
		ShingleMinHasher consumer = new ShingleMinHasher(q,k, files.size());
		consumer.initHashes();
		Thread tConsumer = new Thread(consumer);
		tConsumer.start();
		
		//wait for file parser threads to complete
		executor.shutdown(); //no new file threads will be added at this point on
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		
		//wait for consumer thread to complete
		tConsumer.join();
		
		//once the consumer thread and parser threads have finished, compute jaccard distance
		float jaccard  = consumer.getJaccardIndex();
		System.out.println("The jaccard index of the two files entered:" + jaccard );
	}
	
}

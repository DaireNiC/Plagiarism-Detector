package ie.gmit.sw;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;

/**
 * FileToShingleParser possesses the implementation of the parse method provided by
 * FileParser. It also contains the implementation of the shinglize method
 * outlined in ShingleCreator interface. 	
 * 
 * @author Daire Ní Chatháin
 *
 */
public class FileToShingleParser extends FileParser implements Runnable, ShingleCreator {
	
	private BlockingQueue<Shingle> q;
	private String file;
	private int fileID;
	private int shingleSize;
	
	/**
	 * @param fileID 		ID of file to be shinglized
	 * @param file			file name
	 * @param q				Shingles, once created are added to this queue
	 * @param shingleSize	The size of the shingles to be created from the file
	 * @throws FileNotFoundException	Exception
	 */
	public FileToShingleParser(int fileID, String file, BlockingQueue<Shingle> q, int shingleSize) throws FileNotFoundException {
		//let super handle the parse --> delegation
		super(file);
		this.q = q;
		this.shingleSize = shingleSize;
		this.fileID = fileID;
	}

	/**
	 * Run method parses the file into shingles and adds to queue
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		//Parse the file into words
		parse();
		try {
			//generate shingles parsed file
			extractShingles();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Take words from the buffer and generate shingles
	 * @see ie.gmit.sw.ShingleCreator#shinglize(int)
	 */
	public Shingle shinglize(int shingleSize) {
		StringBuilder sb = new StringBuilder();
		for(int i =0; i < shingleSize;i++){
			if(getBuffer().peek() != null){
				sb.append(getBuffer().poll());
			}
			else{
				return null;
			}
		}
		return new Shingle(fileID, sb.toString().hashCode());
	}
	
	/**
	 * Extract shingles from the buffer and put onto
	 *  the blocking queue for ShingleMinhasher
	 * @throws InterruptedException	Exception
	 */
	public void extractShingles() throws InterruptedException{
		while(getBuffer().size() > 0){
			Shingle s = shinglize(shingleSize);
			if(s != null){
				q.put(s);
			}
			//covers case of returning from putting final shingle on the q & buffer == 0
			if( s == null || getBuffer().size() == 0 ){
				q.put(new Poison(fileID, 0));
			}
			
		}
	}
}

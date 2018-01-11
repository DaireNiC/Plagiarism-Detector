package ie.gmit;


import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;

public class FileToShingleParser extends FileParser implements Runnable, ShingleCreator {
	
	private BlockingQueue<Shingle> q;
	private String file;
	private int fileID;
	private int shingleSize;
	
	public FileToShingleParser(int fileID, String file, BlockingQueue<Shingle> q, int shingleSize) throws FileNotFoundException {
		//let super handle the parse --> delegation
		super(file);
		this.q = q;
		this.shingleSize = shingleSize;
		this.fileID = fileID;
	}

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

	//Take words from the buffer and generate shingles
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
	//Extract shingles from the buffer and put onto the blocking queue for ShingleMinhasher
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

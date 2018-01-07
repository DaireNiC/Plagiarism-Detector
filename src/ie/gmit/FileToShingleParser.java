package ie.gmit;


import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;

public class FileToShingleParser extends FileParser implements Runnable, ShingleCreator {

	private BlockingQueue<Shingle> q;
	private String file;
	private String fileID;
	int shingleSize;
	
	public FileToShingleParser(String fileID, String file, BlockingQueue<Shingle> q, int shingleSize) throws FileNotFoundException {
		super(file);
		this.q = q;
		this.shingleSize = shingleSize;
		this.fileID = fileID;
	}

	public void run() {
		parse();
		try {
			flushBuffer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Shingle getNextShingle(int shingleSize) {
		
		StringBuilder sb = new StringBuilder();
		
		for(int i =0; i < shingleSize;i++){
			if(getBuffer().peek() != null){
				sb.append(getBuffer().poll());
			}
		}

		System.out.println(sb.toString());
		return new Shingle(fileID, sb.toString().hashCode());
	}
	
	private void flushBuffer() throws InterruptedException{
		while(getBuffer().size() > 0){
			Shingle s = getNextShingle(shingleSize);
			if(s != null){
				q.put(s);
			}else{
				System.out.println("throwing poison************");
				q.put(new Poison(fileID, 0));
			}
		}
		
	}
}

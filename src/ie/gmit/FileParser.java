package ie.gmit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class FileParser extends Parser{

	private BufferedReader br;
	private Deque<String> buffer = new LinkedList<String>();
	private String file;
	
	public FileParser(String file) throws FileNotFoundException {
		super();
		this.file = file;
		this.br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	}

	public void parse() {
		//pass file to buffered reader & begin parsing doc into shingles
		try {
			String line;

			
			while((line = br.readLine()) != null){
				//remove spaces & punctuation & make  all lowercase
				String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
				addWordsToBuffer(words);
				//create shingles from words & put in queue for minhasher
		//		Shingle s = getNextShingle(shingleSize);
			//	q.put(s);
				}
	//		flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void addWordsToBuffer(String[] words) {
		for (String s : words){
			buffer.add(s);
		}
		
	}

	public Deque<String> getBuffer() {
		return buffer;
	}

	public void setBuffer(Deque<String> buffer) {
		this.buffer = buffer;
	}

}
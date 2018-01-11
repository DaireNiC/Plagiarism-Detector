package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

/**
 * FileParser class extends from the abstract class Parser 
 * implementing its parse method. Provides the functionality of
 * the buffered reading of an input stream. Input is stripped of punctuation
 * and converted to lowercase for processing. 
 * 
 * 
 * @author Daire Ní Chatháin
 *
 */
public class FileParser extends Parser{

	private BufferedReader br;
	private Deque<String> buffer = new LinkedList<String>();
	private String file;
	
	/**
	 * @param file File in form of a string
	 * @throws FileNotFoundException Exception
	 * 	*/
	public FileParser(String file) throws FileNotFoundException {
		super(); //call to super parser class
		this.file = file;
		this.br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	}

	/* 
	 * @see ie.gmit.sw.Parser#parse()
	 */
	public void parse() {
		//pass file to buffered reader & begin parsing doc into shingles
		try {
			String line;
			while((line = br.readLine()) != null){
				//remove spaces & punctuation & make  all lowercase
				String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
				addWordsToBuffer(words);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * This method takes in an array of words and each word to the buffer
	 * @param words taken from parser buffer
	 */
	public void addWordsToBuffer(String[] words) {
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
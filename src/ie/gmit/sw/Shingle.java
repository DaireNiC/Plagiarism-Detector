package ie.gmit.sw;

/**
 * Shingle Type, (may be K-Shingle)
 * Used as the basic unit (element) to indicate a fixed-size group of words.
 * This has the advantage of retaining sthe semantics of the document
 * 
 * @author Daire Ní Chatháin
 *
 */
public class Shingle {

	//ID of the doccument shingle belongs to
	private int fileID;
	//hashCode of the words making up the shingle
	private int hashCode;
	
	/**
	 * @param fileID 	File ID of the shingle (used for similarity comparision)
	 * @param hashCode	Hashcode of the words that make up the shingle
	 */
	public Shingle(int fileID, int hashCode) {
		this.fileID = fileID;
		this.hashCode = hashCode; 
	}

	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashCode;
		return result;
	}
}

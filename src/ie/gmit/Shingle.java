package ie.gmit;

/**
 * @author User
 *
 */
public class Shingle {

	//ID of the doccument shingle belongs to
	private int fileID;
	//hashCode of the words making up the shingle
	private int hashCode;
	
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

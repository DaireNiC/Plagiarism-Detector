package ie.gmit;

public class Shingle {

	//ID of the doccument shingle belongs to
	private int fileID;
	private int hashCode;
	
	public Shingle(int fileID2, int hashCode) {
		this.setFileID(fileID2);
		this.setHashCode(hashCode); 
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
}

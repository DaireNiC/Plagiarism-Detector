package ie.gmit;

public class Shingle {

	//ID of the doccument shingle belongs to
	private String fileID;
	private int hashCode;
	
	public Shingle(String fileID, int hashCode) {
		this.setFileID(fileID);
		this.setHashCode(hashCode); 
	}

	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
}

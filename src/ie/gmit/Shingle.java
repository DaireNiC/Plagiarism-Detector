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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shingle other = (Shingle) obj;
		if (hashCode != other.hashCode)
			return false;
		return true;
	}
}

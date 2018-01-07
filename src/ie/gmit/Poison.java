package ie.gmit;

public class Poison extends Shingle {

	public Poison(String fileID, int hashCode) {
		super(fileID, hashCode);
		this.setHashCode(-99);
	}

}

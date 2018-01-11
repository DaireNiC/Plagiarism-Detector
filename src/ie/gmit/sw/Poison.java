package ie.gmit.sw;

/**
 * Specialised type of Shingle that indicates the final shingle 
 * has been extracted from a file. 
 * @author Daire N� Chath�in
 *
 */
public class Poison extends Shingle {

	public Poison(int fileID, int hashCode) {
		super(fileID, hashCode);
		this.setHashCode(-99);
	}

}

package ie.gmit.sw;

/**
 * Specialised type of Shingle that indicates the final shingle 
 * has been extracted from a file. 
 * @author Daire Ní Chatháin
 *
 */
public class Poison extends Shingle {

	public Poison(int fileID, int hashCode) {
		super(fileID, hashCode);
		this.setHashCode(-99);
	}

}

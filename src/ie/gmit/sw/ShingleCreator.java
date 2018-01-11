package ie.gmit.sw;

/**
 * 
 * The interface ShingleCreator outlines the method for
 * the creation of a shingle.
 *  
 * @author Daire Ní Chatháin
 * 
 */
public abstract interface ShingleCreator {
	/**
	 * @param shingleSize	The size of the shingle to be created
	 * @return	Shingle		return a shingle type
	 */
	Shingle shinglize(int shingleSize);
	
}
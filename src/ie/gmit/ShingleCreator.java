package ie.gmit;

public abstract interface ShingleCreator {
	Shingle getNextShingle(int shingleSize);
	
}
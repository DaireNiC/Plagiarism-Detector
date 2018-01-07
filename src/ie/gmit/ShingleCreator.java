package ie.gmit;

public abstract interface ShingleCreator {
	int shingleSize = 0;
	Shingle getNextShingle(int shingleSize);
	
}
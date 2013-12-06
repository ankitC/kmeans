package interfaces4KMeans;

import java.util.ArrayList;

/* Interface for defining the testing interface for KMeans */
public interface KMeansTestInterface {
	int sampleLength = 0;
	public abstract ArrayList<DataPoint> generateData(int sampleLength);
	public abstract void test() throws Throwable;
}

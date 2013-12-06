package interfaces4KMeans;

import java.io.Serializable;

/* Interface for Average Class used for KMeans */
public interface Average extends Serializable {

	public void addDataPoint(DataPoint dataPoint);
	public DataPoint getAverage();
	public void combineAverages(Average average);

}

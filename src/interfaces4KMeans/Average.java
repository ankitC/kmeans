package interfaces4KMeans;

import java.io.Serializable;

public interface Average extends Serializable {

	public void addDataPoint(DataPoint dataPoint);
	public DataPoint getAverage();
	public void combineAverages(Average average);

}

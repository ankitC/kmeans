package interfaces4KMeans;

import java.io.Serializable;

public interface Average extends Serializable {

	public void addDataPoint(DataPoint dataPt);
	public DataPoint getAverage();
}

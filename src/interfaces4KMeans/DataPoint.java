package interfaces4KMeans;

import java.io.Serializable;

/* Interface for defining the DataPoint for KMeans */
public interface DataPoint extends Serializable {

	public double distanceTo(DataPoint d);
}

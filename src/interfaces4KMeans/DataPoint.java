package interfaces4KMeans;

import java.io.Serializable;

public interface DataPoint extends Serializable {

	public double distanceTo(DataPoint d);

}

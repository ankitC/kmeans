package interfaces4KMeans;

import java.io.Serializable;
import java.util.ArrayList;

/* interface for cluster used for KMeans */
public interface Cluster extends Serializable {
	
	ArrayList<DataPoint> dataset = null;
	 DataPoint centroid = null;
	 Average runningAvg = null;
	 
	 public String toString();
}

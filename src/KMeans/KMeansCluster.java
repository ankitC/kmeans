package KMeans;

import interfaces4KMeans.Average;
import interfaces4KMeans.Cluster;
import interfaces4KMeans.DataPoint;

import java.util.ArrayList;

public class KMeansCluster implements Cluster {

	private static final long serialVersionUID = 2518689765641386585L;
	private ArrayList<DataPoint> dataset = new ArrayList<DataPoint>();
	private DataPoint centroid;
	private Average clusterAvg;
	private int size = 0;

	public KMeansCluster(DataPoint centroid,Average avgerage){
		this.centroid = centroid;
		this.clusterAvg = avgerage;
	}

	public KMeansCluster(ArrayList<DataPoint> dataset, DataPoint centroid, 	Average runningAvg) {
		this.dataset = dataset;
		this.centroid = centroid;
		this.clusterAvg = runningAvg;
		this.size = dataset.size();
	}

	public void addDataPoint(DataPoint dataPt) {
		dataset.add(dataPt);
		clusterAvg.addDataPoint(dataPt);
		this.size++;
	}

	public DataPoint  getCentroid() {
		return centroid;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public DataPoint getAverage() {
		return (DataPoint) clusterAvg.getAverage();
	}
	
	public Average getClusterAverage(){
		return this.clusterAvg;
	}

	public String toString() {
		String result = "Centered around: " + centroid.toString() + "\n";
		result+="Cluster Size:"+size+"\n";
		DataPoint  avg =(DataPoint) clusterAvg.getAverage();
		String avgString = "null";
		if(avg != null) {avgString = avg.toString();}

		result += "Avg: " + avgString + "\n";
		/*
		for(int d = 0; d < dataset.size(); d++) {
			result += d + ": " + dataset.get(d).toString() + "\n";
		}
		 */
		return result;
	}
}

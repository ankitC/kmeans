package oneDimensionalSampleSpace;

import interfaces4KMeans.Cluster;

import java.util.ArrayList;



public class Cluster1D implements Cluster {
	private ArrayList<DataPoint1D> dataset = new ArrayList<DataPoint1D>();
	private DataPoint1D centroid;
	private Average1D clusterAvg;
	
	public Cluster1D(DataPoint1D centroid,Average1D avgerage){
		this.centroid = centroid;
		this.clusterAvg = avgerage;
	}

	public Cluster1D(ArrayList<DataPoint1D> dataset, DataPoint1D centroid, 	Average1D runningAvg) {
		this.dataset = dataset;
		this.centroid = centroid;
		this.clusterAvg = runningAvg;
	}

	public void addDataPoint(DataPoint1D dataPt) {
		dataset.add(dataPt);
		clusterAvg.addDataPoint(dataPt);
	}

	public DataPoint1D getCentroid() {
		return centroid;
	}

	public DataPoint1D getAverage() {
		return (DataPoint1D) clusterAvg.getAverage();
	}
	
	public String toString() {
		String result = "Centered around: " + centroid.toString() + "\n";
		
		DataPoint1D  avg =(DataPoint1D) clusterAvg.getAverage();
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

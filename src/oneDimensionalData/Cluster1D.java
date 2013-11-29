package oneDimensionalData;

import java.util.ArrayList;


public class Cluster1D {
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

	public void addDataPt(DataPoint1D dataPt) {
		dataset.add(dataPt);
		clusterAvg.addDataPt(dataPt);
	}

	public DataPoint1D getCentroid() {
		return centroid;
	}

	public DataPoint1D getAverage() {
		return clusterAvg.getAverage();
	}
	
	public String toString() {
		String result = "Centered around: " + centroid.toString() + "\n";
		
		DataPoint1D  avg =clusterAvg.getAverage();
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

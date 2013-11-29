import java.util.ArrayList;


public class Cluster {
	private ArrayList<DataPoint> dataset = new ArrayList<DataPoint>();
	private DataPoint centroid;
	private Average clusterAvg;
	
	public Cluster(DataPoint centroid,Average avgerage){
		this.centroid = centroid;
		this.clusterAvg = avgerage;
	}

	public Cluster(ArrayList<DataPoint> dataset, DataPoint centroid, 	Average runningAvg) {
		this.dataset = dataset;
		this.centroid = centroid;
		this.clusterAvg = runningAvg;
	}

	public void addDataPt(DataPoint dataPt) {
		dataset.add(dataPt);
		clusterAvg.addDataPt(dataPt);
	}

	public DataPoint getCentroid() {
		return centroid;
	}

	public DataPoint getAverage() {
		return clusterAvg.getAverage();
	}
	
	public String toString() {
		String result = "Centered around: " + centroid.toString() + "\n";
		
		DataPoint  avg =clusterAvg.getAverage();
		String avgString = "null";
		if(avg != null) {avgString = avg.toString();}
		
		result += "Avg: " + avgString + "\n";
		
		for(int d = 0; d < dataset.size(); d++) {
			result += d + ": " + dataset.get(d).toString() + "\n";
		}
		
		return result;
	}
	
}

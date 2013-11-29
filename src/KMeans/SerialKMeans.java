package KMeans;

import interfaces4KMeans.Average;
import interfaces4KMeans.DataPoint;
import java.util.ArrayList;
import java.util.Random;

public class SerialKMeans {

	private ArrayList<DataPoint> dataset;
	private ArrayList<KMeansCluster> clusters;
	private ArrayList<Double> meanCentroidDeviation;	
	private Class<?> avgerageImplementation;
	private int iterations;

	public SerialKMeans(ArrayList<DataPoint> dataset,	  Class<?> sampleAvgerageClass, int numberOfClasses, double tolerance) throws Exception {
		super();
		this.dataset = dataset;
		this.avgerageImplementation = sampleAvgerageClass;

		if(numberOfClasses<= 0) {
			System.out.println("Cannot have " +numberOfClasses +" classes, min : 1");
		}

		this.avgerageImplementation = sampleAvgerageClass;

		this.clusters = new ArrayList<KMeansCluster>();
		this.meanCentroidDeviation = new ArrayList<Double>();

		Random rgenerator = new Random();

		for(int i = 0; i < numberOfClasses; i++) {
			DataPoint centroid = dataset.get(rgenerator.nextInt(dataset.size()));
			KMeansCluster cluster = new KMeansCluster((DataPoint) centroid,  (Average)sampleAvgerageClass.getConstructor().newInstance());
			this.clusters.add(cluster);
		}

		/* Initial Clustering */
		this.clusterDataset();

		this.iterations = 0;

		while(!this.isInTolerenceLimits(tolerance)) {
			this.findNewClusters();
			this.clusterDataset();
			this.iterations++;
		}

	}

	/* Iterate through the points and find the clusters */
	private void clusterDataset() {
		for(int d = 0; d < dataset.size(); d++) {
			DataPoint dataPt = dataset.get(d);
			KMeansCluster closestCluster = findClosestCluster(dataPt);
			closestCluster.addDataPoint(dataPt);
		}
	}

	/* Finding the cluster which is closest to the given point */
	private KMeansCluster findClosestCluster(DataPoint dataPt) {

		KMeansCluster closestCluster = clusters.get(0);
		double minDist = dataPt.distanceTo(closestCluster.getCentroid());

		for(int i = 0; i < clusters.size(); i++) {
			KMeansCluster cluster = clusters.get(i);
			double distance = dataPt.distanceTo(cluster.getCentroid());
			if(minDist > distance) {
				minDist = distance;
				closestCluster = cluster;
			}
		}
		return closestCluster;
	}


	public void findNewClusters() throws Exception{
		ArrayList<KMeansCluster> newClusters = new ArrayList<KMeansCluster>();
		meanCentroidDeviation = new ArrayList<Double>();

		for(int c = 0; c < clusters.size(); c++) {

			KMeansCluster cluster = clusters.get(c);
			DataPoint avg = cluster.getAverage();

			if(avg != null) {
				meanCentroidDeviation.add(avg.distanceTo(cluster.getCentroid()));
				Average newAverage = (Average) avgerageImplementation.getConstructor().newInstance();
				newClusters.add(new KMeansCluster(avg, newAverage));
			}
		}
		clusters = newClusters;
	}

	private boolean isInTolerenceLimits(double tolerance) {

		for(int i = 0; i < meanCentroidDeviation.size(); i++) {
			if(meanCentroidDeviation.get(i) > tolerance) {
				return false;
			}
		}
		
		return meanCentroidDeviation.size() == clusters.size();
	}

	public String toString() {
		String result = "Created " + clusters.size() + " clusters in " + iterations + " iterations...\n";
		for(int c = 0; c < clusters.size(); c++) {
			result += "Cluster: " + c + "\n" + clusters.get(c).toString();
			result += "------------------\n";
		}

		return result;
	}
}


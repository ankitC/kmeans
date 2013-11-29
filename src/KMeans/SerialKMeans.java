package KMeans;

import interfaces4KMeans.Average;
import interfaces4KMeans.DataPoint;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class SerialKMeans {

	private ArrayList<DataPoint> dataset;
	private ArrayList<KMeansCluster> clusters;
	private ArrayList<Double> centroidEpsilons;
	private Class<?> kAvgClass;
	private int ctr;

	public SerialKMeans(ArrayList<DataPoint> dataset,	  Class<?> kAvgClass, int numborOfClasses, double tolerance) throws Exception {
		super();

		this.dataset = dataset;
		this.kAvgClass = kAvgClass;

		if(numborOfClasses<= 0) {
			System.out.println("KMeansMaster: Number of classes  must be greater than 0.");
		}

		this.kAvgClass = kAvgClass;

		this.clusters = new ArrayList<KMeansCluster>();
		this.centroidEpsilons = new ArrayList<Double>();

		Random rgenerator = new Random();

		for(int i = 0; i < numborOfClasses; i++) {
			DataPoint centroid = dataset.get(rgenerator.nextInt(dataset.size()));
			KMeansCluster cluster = new KMeansCluster((DataPoint) centroid,  (Average)kAvgClass.getConstructor().newInstance());
			this.clusters.add(cluster);
		}

		/* Initial Clustering */
		this.clusterDataset();

		this.ctr = 0;
		while(!this.withinRange(tolerance)) {
			this.findNewClusters();
			this.clusterDataset();
			this.ctr++;
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
		for(int c = 0; c < clusters.size(); c++) {
			KMeansCluster cluster = clusters.get(c);
			double distance = dataPt.distanceTo(cluster.getCentroid());
			if(minDist > distance) {
				minDist = distance;
				closestCluster = cluster;
			}
		}
		return closestCluster;
	}


	public void findNewClusters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<KMeansCluster> newClusters = new ArrayList<KMeansCluster>();
		centroidEpsilons = new ArrayList<Double>();

		for(int c = 0; c < clusters.size(); c++) {
			// TODO: catch reflections errors and throw a better exception
			KMeansCluster cluster = clusters.get(c);
			DataPoint avg = cluster.getAverage();

			// empty sets will have a null avg

			if(avg != null) {
				centroidEpsilons.add(avg.distanceTo(cluster.getCentroid()));
				Average newAverager = (Average) kAvgClass.getConstructor().newInstance();
				newClusters.add(new KMeansCluster(avg, newAverager));
			}
		}
		clusters = newClusters;
	}

	private boolean withinRange(double tolerance) {

		for(int i = 0; i < centroidEpsilons.size(); i++) {
			if(centroidEpsilons.get(i) > tolerance) {
				return false;
			}
		}
		return centroidEpsilons.size() == clusters.size();
	}

	public String toString() {
		String result = "Created " + clusters.size() + " clusters in " + ctr + " iterations...\n";
		for(int c = 0; c < clusters.size(); c++) {
			result += "Cluster: " + c + "\n" + clusters.get(c).toString();
			result += "------------------\n";
		}

		return result;
	}
}


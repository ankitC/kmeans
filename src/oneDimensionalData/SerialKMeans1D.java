package oneDimensionalData;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;


public class SerialKMeans1D {

	private ArrayList<DataPoint1D> dataset;
	private ArrayList<Cluster1D> clusters;
	private ArrayList<Double> centroidEpsilons;
	private Class<?> kAvgClass;
	private int ctr;

	public SerialKMeans1D(ArrayList<DataPoint1D> dataset,	  Class<?> kAvgClass, int numborOfClasses, double tolerance) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super();
		
		this.dataset = dataset;
		this.kAvgClass = kAvgClass;

		if(numborOfClasses<= 0) {
			System.out.println("KMeansMaster: Number of classes  must be greater than 0.");
		}

		this.kAvgClass = kAvgClass;

		this.clusters = new ArrayList<Cluster1D>();
		this.centroidEpsilons = new ArrayList<Double>();

		Random rgenerator = new Random();

		for(int i = 0; i < numborOfClasses; i++) {
			DataPoint1D centroid = dataset.get(rgenerator.nextInt(dataset.size()));
			Cluster1D cluster = new Cluster1D(centroid,  (Average1D)kAvgClass.getConstructor().newInstance());
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
			DataPoint1D dataPt = dataset.get(d);
			Cluster1D closestCluster = findClosestCluster(dataPt);
			closestCluster.addDataPt(dataPt);
		}
	}

	/* Finding the cluster which is closest to the given point */
	private Cluster1D findClosestCluster(DataPoint1D dataPt) {
		Cluster1D closestCluster = clusters.get(0);
		double minDist = dataPt.distanceTo(closestCluster.getCentroid());
		for(int c = 0; c < clusters.size(); c++) {
			Cluster1D cluster = clusters.get(c);
			double distance = dataPt.distanceTo(cluster.getCentroid());
			if(minDist > distance) {
				minDist = distance;
				closestCluster = cluster;
			}
		}
		return closestCluster;
	}


	public void findNewClusters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<Cluster1D> newClusters = new ArrayList<Cluster1D>();
		centroidEpsilons = new ArrayList<Double>();

		for(int c = 0; c < clusters.size(); c++) {
			// TODO: catch reflections errors and throw a better exception
			Cluster1D cluster = clusters.get(c);
			DataPoint1D avg = cluster.getAverage();

			// empty sets will have a null avg

			if(avg != null) {
				centroidEpsilons.add(avg.distanceTo(cluster.getCentroid()));
				Average1D newAverager = (Average1D) kAvgClass.getConstructor().newInstance();
				newClusters.add(new Cluster1D(avg, newAverager));
			}
		}
		clusters = newClusters;
	}


	/**
	 * Check to see if KMeansMaster is complete based off the distance between the old centroids and the current centroids.
	 * @param centroidEpsilon
	 * @return
	 */
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


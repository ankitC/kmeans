import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;


public class SerialKMeans {

	private ArrayList<DataPoint> dataset;
	private ArrayList<Cluster> clusters;
	private ArrayList<Double> centroidEpsilons;
	private Class<?> kAvgClass;
	private int ctr;

	public SerialKMeans(ArrayList<DataPoint> dataset,	  Class<?> kAvgClass, int numborOfClasses, double tolerance) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super();
		
		this.dataset = dataset;
		this.kAvgClass = kAvgClass;

		if(numborOfClasses<= 0) {
			System.out.println("KMeansMaster: Number of classes  must be greater than 0.");
		}

		this.kAvgClass = kAvgClass;

		this.clusters = new ArrayList<Cluster>();
		this.centroidEpsilons = new ArrayList<Double>();

		Random rgenerator = new Random();

		for(int i = 0; i < numborOfClasses; i++) {
			DataPoint centroid = dataset.get(rgenerator.nextInt(dataset.size()));
			Cluster cluster = new Cluster(centroid,  (Average)kAvgClass.getConstructor().newInstance());
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
			Cluster closestCluster = findClosestCluster(dataPt);
			closestCluster.addDataPt(dataPt);
		}
	}

	/* Finding the cluster which is closest to the given point */
	private Cluster findClosestCluster(DataPoint dataPt) {
		Cluster closestCluster = clusters.get(0);
		double minDist = dataPt.distanceTo(closestCluster.getCentroid());
		for(int c = 0; c < clusters.size(); c++) {
			Cluster cluster = clusters.get(c);
			double distance = dataPt.distanceTo(cluster.getCentroid());
			if(minDist > distance) {
				minDist = distance;
				closestCluster = cluster;
			}
		}
		return closestCluster;
	}


	public void findNewClusters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		ArrayList<Cluster> newClusters = new ArrayList<Cluster>();
		centroidEpsilons = new ArrayList<Double>();

		for(int c = 0; c < clusters.size(); c++) {
			// TODO: catch reflections errors and throw a better exception
			Cluster cluster = clusters.get(c);
			DataPoint avg = cluster.getAverage();

			// empty sets will have a null avg

			if(avg != null) {
				centroidEpsilons.add(avg.distanceTo(cluster.getCentroid()));
				Average newAverager = (Average) kAvgClass.getConstructor().newInstance();
				newClusters.add(new Cluster(avg, newAverager));
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


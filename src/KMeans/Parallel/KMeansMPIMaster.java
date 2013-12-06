package KMeans.Parallel;

import mpi.*;
import interfaces4KMeans.Average;
import interfaces4KMeans.DataPoint;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;



import java.util.Random;

import KMeans.KMeansCluster;

public class KMeansMPIMaster {
	//private ArrayList<DataPoint> dataset;
	private ArrayList<ArrayList<DataPoint>> dataPartitions;
	private ArrayList<KMeansCluster> clusters;
	private ArrayList<Double> tolerance;
	private Class<?> averageClass;
	private int iterations;
	private int numProcs;
	private int masterRank;
	private KMeansMPISlave masterSlave;

	public KMeansMPIMaster(ArrayList<DataPoint> dataset, Class<?> AvgerageClass, int numberOfClusters, double tolerance, int masterRank, int numSlaves) throws Throwable {
		if(numberOfClusters <= 0) {
			throw new Throwable("KMeansMaster: k must be greater than 0.");
		}

		//this.dataset = dataset;
		this.dataPartitions = partitionDataset(dataset, numSlaves);

		this.averageClass = AvgerageClass;

		// empty clusters
		this.clusters = new ArrayList<KMeansCluster>();
		this.tolerance = new ArrayList<Double>();

		this.iterations = 0;
		this.numProcs = numSlaves;
		this.masterRank = masterRank;
		this.masterSlave = new KMeansMPISlave(this.masterRank, this.masterRank, this.numProcs);

		Random rgenerator = new Random();
		for(int i = 0; i < numberOfClusters; i++) {
			DataPoint centroid = dataset.get(rgenerator.nextInt(dataset.size()));
			KMeansCluster cluster = new KMeansCluster(centroid, (Average) AvgerageClass.getConstructor().newInstance());
			this.clusters.add(cluster);
		}

		this.clusterDataset();

		while(!this.withinRange(tolerance)) {
			this.iterations++;
			//	System.out.println("Running iteration " + this.iterations + "...");
			this.findNewClusters();
			this.clusterDataset();
		}

		finishCommunication();
		//System.out.println("Printing results...");
		//System.out.println(this.toString());
	}

	/* To stop communication, we send an empty message to the workers */
	private void finishCommunication() throws Exception {
		KMeansMPIMessage[] messages = new KMeansMPIMessage[numProcs];
		communicateWithSlaves(messages);
	}

	/**
	 * Communicate with the slaves.
	 */
	private void communicateWithSlaves(KMeansMPIMessage[] messages) throws Exception {
		MPI.COMM_WORLD.Scatter(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, masterRank);
		masterSlave.handleMessage(messages[0]);
		MPI.COMM_WORLD.Gather(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, masterRank);
	}

	/*
	 * Check if the centroids are within the range.
	 */
	private boolean withinRange(double centroidEpsilon) throws Throwable {
		if(clusters.size() <= 0) {
			throw new Throwable("KMeansMaster: there was a fatal error, for somereason all the clusters died.");
		}

		for(int i = 0; i < tolerance.size(); i++) {
			if(tolerance.get(i) > centroidEpsilon) {
				return false;
			}
		}
		return tolerance.size() == clusters.size();
	}

	/* Find the new centroids for new clusters based on averages*/
	private void findNewClusters() throws Exception{
		ArrayList<KMeansCluster> newClusters = new ArrayList<KMeansCluster>();
		tolerance = new ArrayList<Double>();

		for(int c = 0; c < clusters.size(); c++) {
			KMeansCluster cluster = clusters.get(c);
			DataPoint avg = cluster.getAverage();

			if(avg != null) {
				tolerance.add(avg.distanceTo(cluster.getCentroid()));
				Average newAverager = (Average) averageClass.getConstructor().newInstance();
				newClusters.add(new KMeansCluster(avg, newAverager));
			}
		}
		clusters = newClusters;
	}

	/* Assing the data to the cluster based on distances */
	private void clusterDataset() throws Exception {
		
		/* Pack the DataPoint partition and the clusters in a message and send it to the workers */
		KMeansMPIMessage[] clusterWork = generateClusterWork();
		communicateWithSlaves(clusterWork);

		/* Combine the clusters received from all workers */
		ArrayList<KMeansCluster> baseClusters = clusterWork[0].getClusters();
		for(int m = 1; m < clusterWork.length; m++) {
			ArrayList<KMeansCluster> toMergeClusters = clusterWork[m].getClusters();
			for(int c = 0; c < baseClusters.size(); c++) {
				KMeansCluster baseCluster = baseClusters.get(c);
				baseCluster.setSize(baseCluster.getSize()+toMergeClusters.get(c).getSize());
				baseCluster.getClusterAverage().combineAverages(toMergeClusters.get(c).getClusterAverage());
			}
		}
	}

	private KMeansMPIMessage[] generateClusterWork() {
		KMeansMPIMessage[] work = new KMeansMPIMessage[numProcs];

		for(int i = 0; i < dataPartitions.size(); i++) {
			work[i] = new KMeansMPIMessage(clusters, dataPartitions.get(i));
		}

		return work;
	}

	private ArrayList<ArrayList<DataPoint>> partitionDataset(ArrayList<DataPoint> dataset, int numPartitions) {
		int datasetSize = dataset.size();
		int partitionSize = (datasetSize + numPartitions - 1)/numPartitions;
		ArrayList<ArrayList<DataPoint>> partitions = new ArrayList<ArrayList<DataPoint>>();

		for(int p = 0; p < datasetSize; p += partitionSize) {
			int endPt = p + partitionSize;
			if(endPt >= datasetSize) {endPt = datasetSize - 1;}
			partitions.add((new ArrayList<DataPoint>(dataset.subList(p, endPt))));
		}

		return partitions;
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


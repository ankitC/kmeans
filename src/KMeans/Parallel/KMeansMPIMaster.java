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
	private ArrayList<Double> centroidEpsilons;
	private Class<?> KAvgClass;
	private int ctr;
	private int numProcs;
	private int masterRank;
	private KMeansMPISlave masterSlave;
	
	public KMeansMPIMaster(ArrayList<DataPoint> dataset, Class<?> AvgerageClass, int numberOfClusters, double centroidEpsilon, int masterRank, int numSlaves) throws Throwable {
		if(numberOfClusters <= 0) {
			throw new Throwable("KMeansMaster: k must be greater than 0.");
		}
		
		//this.dataset = dataset;
    this.dataPartitions = partitionDataset(dataset, numSlaves);

		this.KAvgClass = AvgerageClass;
		
		// empty clusters
		this.clusters = new ArrayList<KMeansCluster>();
		this.centroidEpsilons = new ArrayList<Double>();
		

		this.ctr = 0;
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
				
		while(!this.withinRange(centroidEpsilon)) {
			this.ctr++;
      System.out.println("Running iteration " + this.ctr + "...");
			this.findNewClusters();
			this.clusterDataset();
		}
		
		killSlaves();
		System.out.println("Printing results...");
		this.toString();
	}
	
	private void killSlaves() throws Exception {
		KMeansMPIMessage[] messages = new KMeansMPIMessage[numProcs];
		sendMessages(messages);
	}
	
	/**
	 * Send messages to the slaves using gather and scatter.
	 * @param messages
	 */
	private void sendMessages(KMeansMPIMessage[] messages) throws Exception {
		MPI.COMM_WORLD.Scatter(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, masterRank);
		masterSlave.handleMessage(messages[0]);
		MPI.COMM_WORLD.Gather(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, masterRank);
	}
	
	/**
	 * Check to see if KMeansMaster is complete based off the distance between the old centroids and the current centroids.
	 * @param centroidEpsilon
	 * @return
	 * @throws Throwable 
	 */
	private boolean withinRange(double centroidEpsilon) throws Throwable {
		if(clusters.size() <= 0) {
			throw new Throwable("KMeansMaster: there was a fatal error, for somereason all the clusters died.");
		}
		
		//System.out.print("[");
		for(int i = 0; i < centroidEpsilons.size(); i++) {
			//System.out.print(centroidEpsilons.get(i) + ", ");
			if(centroidEpsilons.get(i) > centroidEpsilon) {
				//System.out.println("] term early");
				return false;
			}
		}
		//System.out.println("]");
		return centroidEpsilons.size() == clusters.size();
	}
	
	/**
	 * Create the new clusters (based off the average of the old clusters).
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private void findNewClusters() throws Exception{
		ArrayList<KMeansCluster> newClusters = new ArrayList<KMeansCluster>();
		centroidEpsilons = new ArrayList<Double>();
		
		for(int c = 0; c < clusters.size(); c++) {
			KMeansCluster cluster = clusters.get(c);
			DataPoint avg = cluster.getAverage();
			
			if(avg != null) {
				centroidEpsilons.add(avg.distanceTo(cluster.getCentroid()));
				Average newAverager = (Average) KAvgClass.getConstructor().newInstance();
				newClusters.add(new KMeansCluster(avg, newAverager));
			}
		}
		clusters = newClusters;
	}
	
	/**
	 * Add the data to the closest cluster.
	 */
	private void clusterDataset() throws Exception {
		KMeansMPIMessage[] clusterWork = generateClusterWork();
		sendMessages(clusterWork);
		
		// merge clusters
		ArrayList<KMeansCluster> baseClusters = clusterWork[0].getClusters();
		
		for(int m = 1; m < clusterWork.length; m++) {
			ArrayList<KMeansCluster> toMergeClusters = clusterWork[m].getClusters();
			for(int c = 0; c < baseClusters.size(); c++) {
				KMeansCluster baseCluster = baseClusters.get(c);
				//baseCluster.mergeWith(toMergeClusters.get(c));
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

	/**
	 * For debugging.
	 */
	public String toString() {
		String result = "Created " + clusters.size() + " clusters in " + ctr + " iterations...\n";
		for(int c = 0; c < clusters.size(); c++) {
			result += "Cluster: " + c + "\n" + clusters.get(c).toString();
			result += "------------------\n";
		}
		
		return result;
	}
}


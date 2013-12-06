package KMeans.Parallel;

import interfaces4KMeans.DataPoint;
import mpi.*;
import java.util.ArrayList;

import KMeans.KMeansCluster;

public class KMeansMPISlave {

	private int rank;
	private int masterRank;
	private int numProcs;
	private boolean finalMessageReceived;

	public KMeansMPISlave(int rank, int masterRank, int numProcs) {
		this.rank = rank;
		this.masterRank = masterRank;
		this.numProcs = numProcs;
		this.finalMessageReceived = false;
	}

	public void startSlave() throws Exception {
		finalMessageReceived = false;
		while(!finalMessageReceived) {
			listenFromMaster();
		}
	}

	public void setFinalMessageReceived() {
		finalMessageReceived = true;
	}

	public void listenFromMaster() throws Exception {
		KMeansMPIMessage[] messages = new KMeansMPIMessage[1];
		MPI.COMM_WORLD.Scatter(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, masterRank);
		handleMessage(messages[0]);
		MPI.COMM_WORLD.Gather(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, masterRank);

	}

	public void handleMessage(KMeansMPIMessage message) {
		/* Exit if we receive an empty message */
		if(message == null) {
			setFinalMessageReceived();
		} else {
			ArrayList<KMeansCluster> clusters = message.getClusters();
			ArrayList<DataPoint> dataset = message.getData();

			clusterDataset(clusters, dataset);
			message.setClusters(clusters);
		}
	}

	/* Cluster the incoming dataset to the predefined clusters */
	private void clusterDataset(ArrayList<KMeansCluster> clusters, ArrayList<DataPoint> dataset) {
		for(int i = 0; i < dataset.size(); i++) {
			DataPoint dataPt = dataset.get(i);
			KMeansCluster closestCluster = findClosestCluster(clusters, dataPt);
			closestCluster.addDataPoint(dataPt);
		}
	}

	/* Find the closest cluster based on the the distance from the centroid */
	private KMeansCluster findClosestCluster(ArrayList<KMeansCluster> clusters, DataPoint dataPoint) {
		KMeansCluster closestCluster = clusters.get(0);
		double minDist = dataPoint.distanceTo(closestCluster.getCentroid());
		for(int c = 0; c < clusters.size(); c++) {
			KMeansCluster cluster = clusters.get(c);
			double distance = dataPoint.distanceTo(cluster.getCentroid());
			if(minDist > distance) {
				minDist = distance;
				closestCluster = cluster;
			}
		}
		return closestCluster;
	}
}

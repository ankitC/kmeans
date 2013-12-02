package KMeans.Parallel;

import interfaces4KMeans.DataPoint;

import java.util.ArrayList;

import KMeans.KMeansCluster;

public class KMeansMPISlave {

	private int rank;
	private int masterRank;
	private int numProcs;
	private boolean loop;

	public KMeansMPISlave(int rank, int masterRank, int numProcs) {
		this.rank = rank;
		this.masterRank = masterRank;
		this.numProcs = numProcs;
		this.loop = false;
	}

	public void startListening() throws Exception {
		loop = true;
		while(loop) {
			listenForWork();
		}
	}

	public void stopListening() {
		loop = false;
	}

	public void listenForWork() throws Exception {
		KMeansMPIMessage[] messages = new KMeansMPIMessage[1];
		MPI.COMM_WORLD.Scatter(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, masterRank);
		handleMessage(messages[0]);
		MPI.COMM_WORLD.Gather(messages, 0, 1, MPI.OBJECT, messages, 0, 1, MPI.OBJECT, masterRank);

	}

	public void handleMessage(KMeansMPIMessage message) {
		//System.out.println("Slave " + rank + " recieved a message.");
		// TODO: change to a better stop message
		if(message == null) {
			stopListening();
		} else {
			ArrayList<KMeansCluster> clusters = message.getClusters();
			ArrayList<DataPoint> dataset = message.getData();

			clusterDataset(clusters, dataset);
			message.setClusters(clusters);
		}
	}

	private void clusterDataset(ArrayList<KMeansCluster> clusters, ArrayList<DataPoint> dataset) {
		for(int d = 0; d < dataset.size(); d++) {
			DataPoint dataPt = dataset.get(d);
			KMeansCluster closestCluster = findClosestCluster(clusters, dataPt);
			closestCluster.addDataPoint(dataPt);
		}
	}

	/**
	 * Find the closest cluster based off the data point.
	 * @param dataPt
	 * @return
	 */
	private KMeansCluster findClosestCluster(ArrayList<KMeansCluster> clusters, DataPoint dataPt) {
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

}

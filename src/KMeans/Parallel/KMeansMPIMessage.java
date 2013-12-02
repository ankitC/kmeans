package KMeans.Parallel;

import interfaces4KMeans.DataPoint;

import java.io.Serializable;
import java.util.ArrayList;

import KMeans.KMeansCluster;

public class KMeansMPIMessage implements Serializable {

	private static final long serialVersionUID = -3323000008501613199L;
	private ArrayList<DataPoint> dataset;
	private ArrayList<KMeansCluster> clusters;

	public KMeansMPIMessage(ArrayList<KMeansCluster> clusters, ArrayList<DataPoint> dataset) {
		this.setClusters(clusters);
		this.setDataSet(dataset);
	}

	public void setDataSet(ArrayList<DataPoint> newDataset) {
		dataset = newDataset;
	}
	public void setClusters(ArrayList<KMeansCluster> newClusters) {
		clusters = newClusters;
	}

	public ArrayList<DataPoint> getData() {
		return dataset;
	}
	public ArrayList<KMeansCluster> getClusters() {
		return clusters;
	}
}

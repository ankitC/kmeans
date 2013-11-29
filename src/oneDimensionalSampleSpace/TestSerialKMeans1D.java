package oneDimensionalSampleSpace;

import interfaces4KMeans.DataPoint;
import interfaces4KMeans.KMeansTestInterface;

import java.util.ArrayList;

import dataGenerators.OneDimensionalDataGenerator;

import KMeans.SerialKMeans;

public class TestSerialKMeans1D implements KMeansTestInterface {

	private int sampleLength = 1000;

	public ArrayList<DataPoint> generateData(int sampleLength) {
		return OneDimensionalDataGenerator.generateInt(sampleLength);
	}

	public void test() throws Throwable {

		ArrayList <DataPoint>data =generateData(this.sampleLength);
		System.out.println("Finished Generating Data");
		SerialKMeans kMeansResults = new SerialKMeans(data, Average1D.class, 5, 0.00001);
		System.out.println(kMeansResults.toString());
	
	}

	public static void main(String[] args) throws Throwable {
		TestSerialKMeans1D oneDimenstionaTest = new TestSerialKMeans1D();
		oneDimenstionaTest.test();
	}
}

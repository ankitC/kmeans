package oneDimensionalSampleSpace;

import interfaces4KMeans.DataPoint;

import java.util.ArrayList;

import dataGenerators.OneDimensionalDataGenerator;

import KMeans.SerialKMeans;

public class TestSerialKMeans1D {

	public static void testSerial() throws Throwable {
		ArrayList<DataPoint> data = new ArrayList<DataPoint>();
	
		data = OneDimensionalDataGenerator.generateInt(10000);
		System.out.println("Finished Generating Data");
		SerialKMeans k = new SerialKMeans(data, Average1D.class, 5, 0.00001);
		System.out.println(k.toString());
	}

	public static void main(String[] args) throws Throwable {
		testSerial();
	}

}

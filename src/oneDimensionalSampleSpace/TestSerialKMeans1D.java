package oneDimensionalSampleSpace;

import java.util.ArrayList;

import dataGenerators.DataGenerator1D;

import KMeans.SerialKMeans;

public class TestSerialKMeans1D {

	public static void testSerial() throws Throwable {
		ArrayList<DataPoint1D> data = new ArrayList<DataPoint1D>();
		
//		data.add(new DataPoint(1));
//		data.add(new DataPoint(12));
//		data.add(new DataPoint(1243));
//		data.add(new DataPoint(11));
//		data.add(new DataPoint(1242));
//		data.add(new DataPoint(23212));
//		data.add(new DataPoint(31111111));
	
		data = DataGenerator1D.generateInt(10000);
		System.out.println("Finished Generating Data");
		SerialKMeans k = new SerialKMeans(data, Average1D.class, 5, 0.00001);
		System.out.println(k.toString());
	}

	public static void main(String[] args) throws Throwable {
		testSerial();
	}

}

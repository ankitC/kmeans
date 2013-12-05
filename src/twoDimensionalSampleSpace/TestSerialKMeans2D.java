package twoDimensionalSampleSpace;

import interfaces4KMeans.DataPoint;
import interfaces4KMeans.KMeansTestInterface;
import java.util.ArrayList;

import KMeans.Serial.SerialKMeans;
import dataGenerators.TwoDimensionalDataGenerator;

public class TestSerialKMeans2D implements KMeansTestInterface {

	private int sampleLength = 1000000;

	public ArrayList<DataPoint> generateData(int sampleLength) {
		return TwoDimensionalDataGenerator.generateInt(sampleLength);
	}

	public void test() throws Throwable {

		ArrayList <DataPoint>data =generateData(this.sampleLength);
		SerialKMeans kMeansResults = new SerialKMeans(data, Average2D.class, 10, 0);
		//	System.out.println(kMeansResults.toString());

	}

	public static void main(String[] args) throws Throwable {
		long time1 = System.currentTimeMillis();
		TestSerialKMeans2D oneDimenstionaTest = new TestSerialKMeans2D();
		oneDimenstionaTest.test();
		long time2 = System.currentTimeMillis();
		long time = time2 - time1;
		System.out.println("Time:"+ time);
	}

}


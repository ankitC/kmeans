package twoDimensionalSampleSpace;

import interfaces4KMeans.DataPoint;
import interfaces4KMeans.KMeansTestInterface;
import java.util.ArrayList;
import KMeans.SerialKMeans;
import dataGenerators.TwoDimensionalDataGenerator;

public class TestSerialKMeans2D implements KMeansTestInterface {

	private int sampleLength = 100000;

	public ArrayList<DataPoint> generateData(int sampleLength) {
		return TwoDimensionalDataGenerator.generateInt(sampleLength);
	}

	public void test() throws Throwable {

		ArrayList <DataPoint>data =generateData(this.sampleLength);
		SerialKMeans kMeansResults = new SerialKMeans(data, Average2D.class, 12, 0.001);
		System.out.println(kMeansResults.toString());

	}

	public static void main(String[] args) throws Throwable {
		TestSerialKMeans2D oneDimenstionaTest = new TestSerialKMeans2D();
		oneDimenstionaTest.test();
	}

}


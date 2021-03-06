package DNASampleSpace;

import java.util.ArrayList;

import KMeans.Serial.SerialKMeans;

import dataGenerators.DNADataGenerator;

import interfaces4KMeans.DataPoint;
import interfaces4KMeans.KMeansTestInterface;

/* Simple Test program to test the Time for Serial Execution of the program */
public class TestSerialKMeansDNA implements KMeansTestInterface{

	/* Dataset parameters */
	private int sampleLength = 1000000;
	private int elementsPerStrand = 20;

	public ArrayList<DataPoint> generateData(int numberOfStrands) {
		return DNADataGenerator.generateDNA(numberOfStrands, this.elementsPerStrand);
	}

	public void test() throws Throwable {

		ArrayList <DataPoint>data =generateData(this.sampleLength);
		SerialKMeans kMeansResults = new SerialKMeans(data, AverageDNA.class, 100, 0);

		/* Output Commented out */
		//	System.out.println(kMeansResults.toString());

	}

	public static void main(String[] args) throws Throwable {
		long time1 = System.currentTimeMillis();
		TestSerialKMeansDNA DNATest = new TestSerialKMeansDNA();
		DNATest.test();
		long time2 = System.currentTimeMillis();
		long time = time2 - time1;
		System.out.println("Time:"+ time);

	}

}

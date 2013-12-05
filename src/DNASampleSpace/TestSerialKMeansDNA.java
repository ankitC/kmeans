package DNASampleSpace;

import java.util.ArrayList;

import KMeans.Serial.SerialKMeans;

import dataGenerators.DNADataGenerator;

import interfaces4KMeans.DataPoint;
import interfaces4KMeans.KMeansTestInterface;

public class TestSerialKMeansDNA implements KMeansTestInterface{

	private int sampleLength = 1000;
	private int elementsPerStrand = 30;

	public ArrayList<DataPoint> generateData(int numberOfStrands) {
		return DNADataGenerator.generateDNA(numberOfStrands, this.elementsPerStrand);
	}

	public void test() throws Throwable {

		ArrayList <DataPoint>data =generateData(this.sampleLength);
		System.out.println("Finished Generating Data");
		//	System.out.println(data.size());

		//for(int i = 0; i< data.size(); i++)
		//	System.out.println(data.get(i).toString());

		SerialKMeans kMeansResults = new SerialKMeans(data, AverageDNA.class, 5, 0);
	
		/* Output Commented out */
		//	System.out.println(kMeansResults.toString());

	}

	public static void main(String[] args) throws Throwable {
		TestSerialKMeansDNA DNATest = new TestSerialKMeansDNA();
		DNATest.test();
	}

}

package dataGenerators;

import interfaces4KMeans.DataPoint;

import java.util.ArrayList;
import java.util.Random;

import oneDimensionalSampleSpace.DataPoint1D;


public class OneDimensionalDataGenerator {

	public static ArrayList<DataPoint> generateInt(int length){
		
		ArrayList<DataPoint> sampleData = new ArrayList<DataPoint>();
		Random intGenerator = new Random();
	
		for(int i = 0; i< length; i++){
			sampleData.add(new DataPoint1D(intGenerator.nextInt()%1000));						
		}
		
		return sampleData;
	}
}

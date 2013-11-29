package dataGenerators;

import java.util.ArrayList;
import java.util.Random;

import oneDimensionalSampleSpace.DataPoint1D;


public class DataGenerator1D {

	public static ArrayList<DataPoint1D> generateInt(int length){
		ArrayList<DataPoint1D> sampleData = new ArrayList<DataPoint1D>();
		Random intGenerator = new Random();
		for(int i = 0; i< length; i++){
			sampleData.add(new DataPoint1D(intGenerator.nextInt()%1000));						
		}
		return sampleData;
	}
}

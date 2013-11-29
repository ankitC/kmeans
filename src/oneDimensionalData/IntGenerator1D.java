package oneDimensionalData;

import java.util.ArrayList;
import java.util.Random;


public class IntGenerator1D {

	static ArrayList<DataPoint1D> generateInt(int length){
		ArrayList<DataPoint1D> sampleData = new ArrayList<DataPoint1D>();
		Random intGenerator = new Random();
		for(int i = 0; i< length; i++){
			sampleData.add(new DataPoint1D(intGenerator.nextInt()%1000));						
		}
		return sampleData;
	}
}

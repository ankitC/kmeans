package dataGenerators;

import interfaces4KMeans.DataPoint;
import java.util.ArrayList;
import java.util.Random;
import twoDimensionalSampleSpace.DataPoint2D;

public class TwoDimensionalDataGenerator {

	public static ArrayList<DataPoint> generateInt(int length) {

		ArrayList<DataPoint> sampleData = new ArrayList<DataPoint>();
		Random intGenerator = new Random();

		for (int i = 0; i < length; i++) {
			sampleData.add( new DataPoint2D(intGenerator.nextInt() % length, intGenerator.nextInt()%length));
		}

		return sampleData;
	}

}

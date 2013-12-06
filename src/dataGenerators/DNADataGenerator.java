package dataGenerators;

import interfaces4KMeans.DataPoint;

import java.util.ArrayList;
import java.util.Random;

import DNASampleSpace.DataPointDNA;

/* Generate DNAs as per specifications */
public class DNADataGenerator {

	private static String element[] = {"A", "C", "G", "T"};
	private static Random random  = new Random();

	public static ArrayList<DataPoint> generateDNA(int numberOfStrands, int elementsPerStrand){

		ArrayList<DataPoint> returnCollection = new ArrayList<DataPoint>();

		for(int i=0; i< numberOfStrands; i++){
			String[] dnaStrand = new String[elementsPerStrand];
			for(int j=0; j<elementsPerStrand; j++){
				String prop = element[Math.abs(random.nextInt())%4];
				dnaStrand[j] = prop;
			}

			DataPointDNA temp = new DataPointDNA(dnaStrand);
			/* Return the outputs to the program */
			//	System.out.println(temp.toString());
			returnCollection.add(temp);
		}
		return returnCollection;
	}
}

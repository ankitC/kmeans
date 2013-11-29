package dataGenerators;

import java.util.Random;


public class DataGenerator1D {
	
    private static String element[] = {"A", "B", "C", "D"};
    private static Random random  = new Random();

    public static String[] generateDNA(int length){
        String[] dnaStrand = new String[length];

        for(int i=0; i<length; i++){
            String prop = element[random.nextInt()%4];
            dnaStrand[i] = prop;
        }
        return dnaStrand;
    }

}

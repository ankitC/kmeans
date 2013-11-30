package dataGenerators;

import java.util.Random;


public class DNADataGenerator {
	
    private static String element[] = {"A", "C", "G", "T"};
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

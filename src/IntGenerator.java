import java.util.ArrayList;
import java.util.Random;


public class IntGenerator {

	static ArrayList<DataPoint> generateInt(int length){
		ArrayList<DataPoint> sampleData = new ArrayList<DataPoint>();
		Random intGenerator = new Random();
		for(int i = 0; i< length; i++){
			sampleData.add(new DataPoint(intGenerator.nextInt()%1000));						
		}
		return sampleData;
	}
}

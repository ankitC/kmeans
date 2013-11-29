import java.util.ArrayList;

public class TestSerialKMeans {

	public static void testSerial() throws Throwable {
		ArrayList<DataPoint> data = new ArrayList<DataPoint>();
		
//		data.add(new DataPoint(1));
//		data.add(new DataPoint(12));
//		data.add(new DataPoint(1243));
//		data.add(new DataPoint(11));
//		data.add(new DataPoint(1242));
//		data.add(new DataPoint(23212));
//		data.add(new DataPoint(31111111));
	
		data = IntGenerator.generateInt(10000);
		System.out.println("Finished Generating Data");
		SerialKMeans k = new SerialKMeans(data, Average.class, 5, 0.00001);
		System.out.println(k.toString());
	}

	public static void main(String[] args) throws Throwable {
		testSerial();
	}

}

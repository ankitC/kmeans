package DNASampleSpace;

import interfaces4KMeans.DataPoint;

public class DataPointDNA implements DataPoint {

	private static final long serialVersionUID = -3366332743322347128L;
	private String[] datapoint;

	public DataPointDNA(String[] datapoint) {
		this.datapoint = datapoint;
	}

	public String[] getDatapoint() {
		return datapoint;
	}
	public void setDatapoint(String[] datapoint) {
		this.datapoint = datapoint;
	}

	/* Find the number of differences in sequences of letters in the DNA Strand */
	public double distanceTo(DataPoint dataPoint) {
		String[] stream1 = datapoint;
		String[] stream2 = ((DataPointDNA) dataPoint).getDatapoint();
		float difference = 0;

		if(stream1.length != stream2.length){
			System.out.println("Entered DataPoints do not have the same length. Please check the input data.");
			System.exit(1);
		}

		for(int i = 0; i < stream1.length; i++){
			if(!stream1[i].equalsIgnoreCase(stream2[i]))
				difference=(float) (difference +1.0);
		}
	//	System.out.println("A:"+this.toString());
	//	System.out.println("B:"+dataPoint.toString());
	//	System.out.println("Difference:"+difference);
		return difference;
	}

	public String toString(){
		String result = "[";

		for(int i = 0; i < datapoint.length; i++){
			result += (datapoint[i] + ", ");
		}
		result +="]";
		return result;
	}


}

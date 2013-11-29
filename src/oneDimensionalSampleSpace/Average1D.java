package oneDimensionalSampleSpace;

import interfaces4KMeans.Average;
import interfaces4KMeans.DataPoint;


public class Average1D implements Average{

	private static final long serialVersionUID = 8646046901376884325L;
	private int total;
	private int size;

	public Average1D() {
		this.total = 0;
		this.size = 0;
	}
	
	public DataPoint getAverage() {
		if(size <= 0) {
			return null;
		}
		return (DataPoint) new DataPoint1D(total/size);
	}

	public void addDataPoint(DataPoint dataPt) {
		// TODO Auto-generated method stub
		total +=   ((DataPoint1D) dataPt).getData();
		size++;
	}
}

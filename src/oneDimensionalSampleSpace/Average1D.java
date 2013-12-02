package oneDimensionalSampleSpace;

import interfaces4KMeans.Average;
import interfaces4KMeans.DataPoint;


public class Average1D implements Average{

	private static final long serialVersionUID = 8646046901376884325L;
	private long total;
	private int size;

	public Average1D() {
		this.total = 0;
		this.size = 0;
	}

	public DataPoint getAverage() {
		if(size <= 0) {
			return null;
		}
		return (DataPoint) new DataPoint1D((int)(total/size));
	}

	public void addDataPoint(DataPoint dataPt) {
		total +=   ((DataPoint1D) dataPt).getData();
		size++;
	}

	public void combineAverages(Average average) {
		this.total+= ((Average1D) average).getTotal();
		this.size+=((Average1D)average).getSize();
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}

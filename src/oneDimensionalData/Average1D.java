package oneDimensionalData;


public class Average1D {

	int total;
	int size;

	public Average1D() {
		this.total = 0;
		this.size = 0;
	}
	
	public DataPoint1D getAverage() {
		if(size <= 0) {
			return null;
		}
		return new DataPoint1D(total/size);
	}

	public void addDataPt(DataPoint1D dataPt) {
		total +=   dataPt.getData();
		size++;
	}
}

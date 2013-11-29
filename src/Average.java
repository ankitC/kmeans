
public class Average {
	
	int total;
	int size;
	
	public Average() {
		this.total = 0;
		this.size = 0;
	}
	public DataPoint getAverage() {
		if(size <= 0) {
			return null;
		}
		
		return new DataPoint(total/size);
	}
	public void addDataPt(DataPoint dataPt) {
		total +=   dataPt.getData();
		size++;
		
	}


}

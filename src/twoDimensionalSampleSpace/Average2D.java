package twoDimensionalSampleSpace;

import interfaces4KMeans.Average;
import interfaces4KMeans.DataPoint;

public class Average2D implements Average{

	private static final long serialVersionUID = 5560469128309610569L;
	private int xTotal;
	private int yTotal;
	private int size;

	public  Average2D() {
		this.xTotal = 0;
		this.yTotal = 0;
		this.size = 0;
	}

	/* Find average of all points within the cluster */
	public DataPoint2D  getAverage() {
		if(size == 0) {
			return null;
		}
		return new DataPoint2D(xTotal/size, yTotal/size);
	}

	public void addDataPoint(DataPoint data) {
		xTotal += ((DataPoint2D) data).getX();
		yTotal += ((DataPoint2D) data).getY();
		size++;
	}

	public int getSize(){
		return size;
	}

	public int getTotalX(){
		return xTotal;
	}

	public int getTotalY(){
		return yTotal;
	}
}

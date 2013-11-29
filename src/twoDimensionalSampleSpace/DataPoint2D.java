package twoDimensionalSampleSpace;

import interfaces4KMeans.DataPoint;

public class DataPoint2D implements DataPoint {

	private static final long serialVersionUID = 4998490492971266389L;
	private int x;
	private int y;

	public DataPoint2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String toString() {
		return "("+this.x + ", " +this. y+")";
	}

	public double distanceTo(DataPoint dataPoint) {
		double distance = (double) Math.sqrt(Math.pow(this.x -  ((DataPoint2D) dataPoint).getX(), 2) + Math.pow(this.y - ((DataPoint2D) dataPoint).getY(), 2));
		return  distance;
	}
	
}

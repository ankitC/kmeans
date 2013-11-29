package twoDimensionalSampleSpace;

public class DataPoint2D {
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
	
	public double distanceTo(DataPoint2D d) {
		double distance = (double) Math.sqrt(Math.pow(this.x -  d.getX(), 2) + Math.pow(this.y - d.getY(), 2));
		return  distance;
	}
	
	public String toString() {
		return "("+this.x + ", " +this. y+")";
	}
	
}

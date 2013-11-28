
/* Representation of a datapoint */
public class dataPoint {
		private int data;

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}
		
		public double distanceTo(dataPoint  d) {
			return Math.abs(data - ((dataPoint) d).getData());
		}
	
		public String toString() {
			return String.valueOf(this.getData());
		}

}

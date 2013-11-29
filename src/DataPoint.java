
/* Representation of a datapoint */
public class DataPoint {
		private int data;

		public DataPoint(int i) {
			this.data = i;
		}

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}
		
		public double distanceTo(DataPoint  d) {
			return Math.abs(data - ((DataPoint) d).getData());
		}
	
		public String toString() {
			return String.valueOf(this.getData());
		}

}

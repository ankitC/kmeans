package oneDimensionalData;


/* Representation of a datapoint */
public class DataPoint1D {
		private int data;

		public DataPoint1D(int i) {
			this.data = i;
		}

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}
		
		public double distanceTo(DataPoint1D  d) {
			return Math.abs(data - ((DataPoint1D) d).getData());
		}
	
		public String toString() {
			return String.valueOf(this.getData());
		}

}

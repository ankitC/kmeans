package oneDimensionalSampleSpace;

import interfaces4KMeans.DataPoint;


/* Representation of a datapoint */
public class DataPoint1D implements DataPoint{
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
		
		public String toString() {
			return String.valueOf(this.getData());
		}

		public double distanceTo(DataPoint d) {
			return Math.abs(data - ((DataPoint1D) d).getData());
		}

}

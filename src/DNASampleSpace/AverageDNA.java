package DNASampleSpace;

import interfaces4KMeans.Average;
import interfaces4KMeans.DataPoint;

public class AverageDNA implements Average {

	private static final long serialVersionUID = 757454833475406133L;
	private int datasize = -1; 
	private int[] ACount;
	private int[] CCount;
	private int[] GCount;
	private int[] TCount;

	public  AverageDNA(){
		this.datasize = -1;
		this.ACount = null;
		this.CCount = null;
		this.GCount = null;
		this.TCount = null;
	}

	public void addDataPoint(DataPoint dataPt) {

		String[] datapoint = ((DataPointDNA)dataPt).getDatapoint();

		if(datasize < 0){		
		//	System.out.println("DataPoint Length:"+ datapoint.length);
			this.datasize = datapoint.length;
			
			ACount = new int[datasize];
			CCount = new int[datasize];
			GCount = new int[datasize];
			TCount = new int[datasize];
		}

		for(int i = 0; i < datapoint.length; i++){
			String base = datapoint[i];

			if(base.equals("A")){
				ACount[i] = ACount[i] + 1;
			} else if(base.equals("C")){
				CCount[i] = CCount[i] + 1;
			} else if(base.equals("G")){
				GCount[i] = GCount[i] + 1;
			} else if(base.equals("T")){
				TCount[i] = TCount[i] + 1;
			} else {
				System.out.println("Data Corrupted. Strand Contains unknown Element.");
				System.exit(1);
			}
		}
	//	System.out.println("Datapoint:" +dataPt.toString());
	//	System.out.println("ACount:"+ACount.length+"CCount:"+CCount.length+"GCount:"+GCount.length+"TCount:"+TCount.length);
		
	}

	public DataPoint getAverage() {

		if(datasize <= 0){
			return null;
		}

		String[] result = new String[datasize];
		for(int i = 0; i < datasize; i++){
			int A = ACount[i];
			int C = CCount[i];
			int G = GCount[i];
			int T = TCount[i];

			int max1 = Math.max(A, C);
			int max2 = Math.max(G, T);
			int max  = Math.max(max1, max2);

			if(max == A){
				result[i] = "A";
			} else if(max == C){
				result[i] = "C";
			} else if(max == G){
				result[i] = "G";
			} else if(max == T){
				result[i] = "T";
			} else {
				result[i] = "?";
			}
		}
		DataPointDNA returnResult = new DataPointDNA(result);
		return returnResult;
	}
}

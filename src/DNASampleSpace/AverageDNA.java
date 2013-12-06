package DNASampleSpace;

import interfaces4KMeans.Average;
import interfaces4KMeans.DataPoint;

/* Class defining the Average of the DNA dataset in the cluster */
public class AverageDNA implements Average {

	private static final long serialVersionUID = 757454833475406133L;
	private int strandLength = -1; 
	private int[] ACount;
	private int[] CCount;
	private int[] GCount;
	private int[] TCount;

	public  AverageDNA(){
		this.strandLength = -1;
		this.ACount = null;
		this.CCount = null;
		this.GCount = null;
		this.TCount = null;
	}
	/* The average is defined as the element that occurs at a position for the max number of times */
	public void addDataPoint(DataPoint dataPt) {

		String[] datapoint = ((DataPointDNA)dataPt).getDatapoint();

		/* Set the strandLength when the first datapoint is added to the cluster */
		if(strandLength < 0){		
			//	System.out.println("DataPoint Length:"+ datapoint.length);
			this.strandLength = datapoint.length;

			ACount = new int[strandLength];
			CCount = new int[strandLength];
			GCount = new int[strandLength];
			TCount = new int[strandLength];
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
		/* Debug statements */
		//	System.out.println("Datapoint:" +dataPt.toString());
		//	System.out.println("ACount:"+ACount.length+"CCount:"+CCount.length+"GCount:"+GCount.length+"TCount:"+TCount.length);

	}

	/* Average is defined as the mean of the cluster. 
	 * It gives back a strand with each element at a position being the element 
	 * which occurs most number of times at that position */
	public DataPoint getAverage() {
		if(strandLength <= 0){
			return null;
		}

		String[] result = new String[strandLength];
		for(int i = 0; i < strandLength; i++){
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

	/* Used only when using Parallel KMeans. It combines the 2 clusters */
	public void combineAverages(Average average) {

		AverageDNA childAverage = (AverageDNA)average;
		for(int i = 0; i < strandLength; i++){
			ACount[i] += childAverage.getNumberOfOccurances("A")[i];
			CCount[i] += childAverage.getNumberOfOccurances("C")[i];
			GCount[i] += childAverage.getNumberOfOccurances("G")[i];
			TCount[i] += childAverage.getNumberOfOccurances("T")[i];
		}
	}

	public int getStrandLength() {
		return strandLength;
	}

	public void setStrandLength(int strandlength) {
		this.strandLength = strandlength;
	}

	public int[] getNumberOfOccurances(String element){
		if(element.equals("A")){
			return ACount;
		} else if(element.equals("C")){
			return CCount;
		} else if(element.equals("G")){
			return GCount;
		} else if(element.equals("T")){
			return TCount; 
		} else {
			return null;
		}
	}

}
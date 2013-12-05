package twoDimensionalSampleSpace;
import interfaces4KMeans.DataPoint;

import java.util.ArrayList;
import mpi.*;
import KMeans.Parallel.KMeansMPIMaster;
import KMeans.Parallel.KMeansMPISlave;

import dataGenerators.DNADataGenerator;


public class TestParallelKMeans2D {
	
	static int sampleLength = 10000;

	public static void main(String[] args) throws Throwable {
		try {
			MPI.Init(args);
			int rank = MPI.COMM_WORLD.Rank();
			int procs = MPI.COMM_WORLD.Size();
			int masterRank = 0;

			if(rank == masterRank) {
				System.out.println("Initializing Master");
				ArrayList<DataPoint> data = new ArrayList<DataPoint>();

				data = dataGenerators.TwoDimensionalDataGenerator.generateInt(sampleLength);
				KMeansMPIMaster mpiNode = new KMeansMPIMaster(data,  twoDimensionalSampleSpace.Average2D.class, 10, 0, masterRank, procs);
				System.out.println(mpiNode.toString());
			} else {
				System.out.println("Started slave " + rank);
				KMeansMPISlave mpiNode= new KMeansMPISlave(rank, masterRank, procs);
				mpiNode.startListening();
			//	System.out.println("Results on Slave");
			//	mpiNode.toString();
			}

			MPI.Finalize();
		} catch(MPIException e) {
			System.out.println("MPI Exception");
			e.printStackTrace();
		}
	}
}
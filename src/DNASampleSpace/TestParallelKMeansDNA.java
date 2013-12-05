package DNASampleSpace;
import interfaces4KMeans.DataPoint;

import java.util.ArrayList;
import mpi.*;
import KMeans.Parallel.KMeansMPIMaster;
import KMeans.Parallel.KMeansMPISlave;

import dataGenerators.DNADataGenerator;


public class TestParallelKMeansDNA {

	public static void main(String[] args) throws Throwable {
		long time1 = System.currentTimeMillis();
		try {
			MPI.Init(args);
			int rank = MPI.COMM_WORLD.Rank();
			int procs = MPI.COMM_WORLD.Size();
			int masterRank = 0;

			if(rank == masterRank) {
			//	System.out.println("Initializing Master");
				ArrayList<DataPoint> data = new ArrayList<DataPoint>();

				data = DNADataGenerator.generateDNA(1000000, 10);
				KMeansMPIMaster mpiNode = new KMeansMPIMaster(data,  DNASampleSpace.AverageDNA.class, 10, 0, masterRank, procs);
	//			System.out.println(mpiNode.toString());
			} else {
	//			System.out.println("Started slave " + rank);
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
		long time2 = System.currentTimeMillis();
		long time = time2 - time1;
		System.out.println("Time:"+ time);
	}
}

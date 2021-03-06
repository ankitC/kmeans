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
			/* Starting and setting up the MPI env */
			MPI.Init(args);
			int rank = MPI.COMM_WORLD.Rank();
			int procs = MPI.COMM_WORLD.Size();
			int masterRank = 0;

			if(rank == masterRank) {
				//	System.out.println("Initializing Master");
				ArrayList<DataPoint> data = new ArrayList<DataPoint>();
				/* Generate the data */
				data = DNADataGenerator.generateDNA(1000000, 20);
				KMeansMPIMaster mpiNode = new KMeansMPIMaster(data,  DNASampleSpace.AverageDNA.class, 100, 0, masterRank, procs);
			
				long time2 = System.currentTimeMillis();
				long time = time2 - time1;
				System.out.println("Time:"+ time);
			} else {

				KMeansMPISlave mpiNode= new KMeansMPISlave(rank, masterRank, procs);
				mpiNode.startSlave();
			}

			/* Finish the execution and we are done */
			MPI.Finalize();
		} catch(MPIException e) {
			System.out.println("MPI Exception");
			e.printStackTrace();
		}

	}
}

rm -r bin/

mkdir bin

mpijavac src/KMeansParallel.java src/dataGenerators/DNADataGenerator.java src/DNASampleSpace/AverageDNA.java src/DNASampleSpace/DataPointDNA.java src/interfaces4KMeans/*.java src/KMeans/KMeansCluster.java src/KMeans/Parallel/*.java -d ./bin/

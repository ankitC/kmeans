MAIN=$(find src -name $2".java" | sed {s/src\\///g} | sed {s/\.java//g})
#MAIN="$2"
#cd bin

pwd

mpirun -np $1 -hostfile hostfile java -cp /afs/andrew.cmu.edu/usr21/vbalaji/private/15440/kmeans/kmeans.jar $MAIN "{*:3}"

#mpirun -np $1 java -cp kmeans.jar $2


MAIN=$(find src -name $2".java" | sed {s/src\\///g} | sed {s/\.java//g})

cd bin

mpirun -np $1 java $MAIN "${*:3}}"

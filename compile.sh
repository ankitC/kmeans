rm -r bin
rm kmeans.jar

mkdir bin

find ./src -name *.java > sources_list.txt
mpijavac -classpath "${CLASSPATH}" @sources_list.txt -d ./bin/

cd bin

jar cf kmeans.jar *
mv kmeans.jar ../
cd ../

rm sources_list.txt


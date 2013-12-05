rm -r bin
mkdir bin

find ./src -name *.java > sources_list.txt
mpijavac -classpath "${CLASSPATH}" @sources_list.txt -d ./bin/
rm sources_list.txt


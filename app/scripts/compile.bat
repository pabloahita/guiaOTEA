cd ../src/main/java/cli
dir /s/B *.java>tmp.txt
javac @tmp.txt -d ../../../../class
del tmp.txt
cd ../../../../scripts
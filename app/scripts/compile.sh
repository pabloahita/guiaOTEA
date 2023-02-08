#!/bin/bash
cd ../src/main/java/cli
find -name *.java>tmp.txt
javac @tmp.txt -d ../../../../class
rm tmp.txt
cd ../../../../scripts
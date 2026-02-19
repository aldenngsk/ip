#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/sixseven/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# ensure clean data state for reproducible test (backup and restore after)
if [ -f "./data/duke.txt" ]; then
    cp ./data/duke.txt ./data/duke.txt.bak
    > ./data/duke.txt
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin sixseven.SixSeven < input.txt > ACTUAL.TXT

# restore data file
if [ -f "./data/duke.txt.bak" ]; then
    mv ./data/duke.txt.bak ./data/duke.txt
fi

# convert to UNIX format (works on macOS and Linux, no dos2unix needed)
sed 's/\r$//' ACTUAL.TXT > ACTUAL.tmp && mv ACTUAL.tmp ACTUAL.TXT
sed 's/\r$//' EXPECTED.TXT > EXPECTED-UNIX.TXT

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
# Scrambled Strings

This is a code challenge is based on this problem from [Google Code Competitions](https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edf/0000000000051004).

Count how many of the words from a dictionary appear as substrings in a long string of
characters either in their original form or in their scrambled form. The scrambled form of the
dictionary word must adhere to the following rule: the first and last letter must be maintained
while the middle characters can be reorganised.

## Getting Started

### input

Is made up of two files 

1. A dictionary file, where each line comprises one dictionary word.

```
axpaj
apxaj
dnrbt
pjxdn
abd
```

2. An input file that contains a list of long strings, each on a newline.

```
aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt
dsadsadasdsaddsadsadasdsaddsadsadasdsaddsadsadasds
aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt
```

### Program arguments

* -d | --dictionary : The file path of the dictionary file.
* -i | --input : the file path of the input file.

There files must exist for the program to work.

### Output

The output will be a console log for each input line displaying the line number and the count of the words found.

```
Case #1: 2
```

### Building via SBT

Ensure that [sbt](https://www.scala-sbt.org/release/docs/Setup.html) is installed
and then run the following commands to build and run the program.

```
sbt assembly
./scrmabled-strings --dictionary example/dictionary.txt --input example/input.txt
```

### Building via docker

Ensure that [docker](https://docs.docker.com/install/) is installed
and then run the following commands to build and run the program.

```
docker build \
    --build-arg OPENJDK_TAG=8u212 \
    --build-arg SBT_VERSION=1.3.3 \
    -t scrmabled .
```

Use docker -v command to mount the directoriy where you input files are to the docker container and then pass those arguments to the container.

```
docker run -v $(pwd)/example:/mnt/example scrmabled -d /mnt/example/dictionary.txt -i /mnt/example/input.txt
```

## Running the tests

The test are written in [ScalaTest](https://docs.scala-lang.org/getting-started/intellij-track/testing-scala-in-intellij-with-scalatest.html) which can be run via SBT

```sbt test```
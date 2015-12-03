#!/bin/bash
URL=$1
PARAMS=$@
NAME=`cat pom.xml | grep artifactId | head -1 | sed 's/^[ \t]*//;s/[ \t]*$//;s/<[^>]*>//g' | tr -d '\r'` 
VERSION=`cat pom.xml | grep version | head -1 | sed 's/^[ \t]*//;s/[ \t]*$//;s/<[^>]*>//g' | tr -d '\r'|tr -d '\t'|tr -d '\f'|tr -d '\n'`

JAR_FILE=$NAME-$VERSION-jar-with-dependencies.jar

TARGET="./target"

# bash check if target directory exists
if [ ! -d $TARGET ]; then
	# if not exist than compile the project
	mvn clean install
fi

if [ ! -z $URL ]; then
	cd target || exit 1
	java -jar $JAR_FILE $PARAMS
else 
	echo "Please enter URL"
fi
exit 1
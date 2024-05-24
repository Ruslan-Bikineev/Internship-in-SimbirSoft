#!/bin/bash

SELENIUM_DIR=selenium-server
SELENIUM_JAR=selenium-server-4.21.0.jar
cd $SELENIUM_DIR
java -jar $SELENIUM_JAR hub
sleep 5
java -jar $SELENIUM_JAR node --port 4445
java -jar $SELENIUM_JAR node --port 4446
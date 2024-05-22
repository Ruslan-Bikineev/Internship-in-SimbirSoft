#!/bin/bash
SELENIUM_DIR=Selenium-server-4.21.0
SELENIUM_JAR=selenium-server-4.21.0.jar

cd $SELENIUM_DIR
java -jar $SELENIUM_JAR hub
java -jar $SELENIUM_JAR node --port 4445
java -jar $SELENIUM_JAR node --port 4446
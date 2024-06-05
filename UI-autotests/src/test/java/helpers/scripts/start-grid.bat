@echo off
SET SELENIUM_DIR=selenium-server
SET SELENIUM_JAR=selenium-server-4.21.0.jar

cd %SELENIUM_DIR%

start java -jar %SELENIUM_JAR% hub
TIMEOUT /t 5 /nobreak

start java -jar %SELENIUM_JAR% node --port 4445
start java -jar %SELENIUM_JAR% node --port 4446
start java -jar %SELENIUM_JAR% node --port 4447
start java -jar %SELENIUM_JAR% node --port 4448
start java -jar %SELENIUM_JAR% node --port 4449
@echo off
SET SELENIUM_DIR=C:\Program Files\Selenium-server-4.21.0
SET SELENIUM_JAR=selenium-server-4.21.0.jar
cd /
cd %SELENIUM_DIR%
start java -jar %SELENIUM_JAR% hub
TIMEOUT /t 5 /nobreak
start java -jar %SELENIUM_JAR% node --port 4445
start java -jar selenium-server-4.21.0.jar node --port 4446

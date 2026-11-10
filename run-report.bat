@echo off
cd /d "%~dp0"
REM 1) Cucumber suite -> allure results
call mvn clean test
REM 2) JUnit unit tests -> append allure results (no clean)
call mvn test -Punit
REM 3) build + open the interactive Allure report
call mvn allure:serve

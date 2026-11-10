@echo off
cd /d "%~dp0"
mvn clean test -Dcucumber.filter.tags="@demo"
pause

#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

# 1) Cucumber suite -> allure results
mvn clean test

# 2) JUnit unit tests -> append allure results (no clean)
mvn test -Punit

# 3) build + open the interactive Allure report
mvn allure:serve

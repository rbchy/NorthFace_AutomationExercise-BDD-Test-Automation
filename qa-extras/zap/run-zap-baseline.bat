@echo off
REM OWASP ZAP baseline (passive) security scan of the demo shop. Requires Docker.
set TARGET=%1
if "%TARGET%"=="" set TARGET=https://automationexercise.com
docker run --rm -v "%cd%:/zap/wrk:rw" -t ghcr.io/zaproxy/zaproxy zap-baseline.py -t %TARGET% -r zap-baseline-report.html
pause

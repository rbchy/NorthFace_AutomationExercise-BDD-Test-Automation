#!/usr/bin/env bash
# OWASP ZAP baseline (passive) security scan of the demo shop.
# Requires Docker. Produces zap-baseline-report.html in this folder.
set -e
TARGET="${1:-https://automationexercise.com}"
docker run --rm -v "$(pwd):/zap/wrk:rw" -t ghcr.io/zaproxy/zaproxy \
  zap-baseline.py -t "$TARGET" -r zap-baseline-report.html

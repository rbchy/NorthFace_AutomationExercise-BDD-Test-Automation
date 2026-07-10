# OWASP ZAP — baseline security scan

Runs an OWASP ZAP **baseline** (passive) scan against the demo shop and produces an
HTML report of security findings (missing headers, cookie flags, information leaks, etc.).
A baseline scan is safe and non-intrusive — it does not attack the target.

## Prerequisites
- Docker Desktop installed and running.

## Run
Windows: double-click `run-zap-baseline.bat`
macOS/Linux:
```bash
chmod +x run-zap-baseline.sh
./run-zap-baseline.sh
```
Optionally pass a different target:
```bash
./run-zap-baseline.sh https://your-staging-site.example
```

Output: `zap-baseline-report.html` in this folder.

## Notes
- Exit code is non-zero if warnings/failures are found — useful as a CI gate.
- For an active (attack) scan use `zap-full-scan.py` instead — only run that against
  systems you own or are authorised to test.

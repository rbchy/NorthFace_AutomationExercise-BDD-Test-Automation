# QA Extras — test types beyond the Selenium UI suite

The main Maven project covers UI, E2E, API, accessibility and performance-smoke tests.
Some test types need dedicated tools and live here as ready-to-run scaffolds.

| Folder      | Test type            | Tool             | How to run                          |
|-------------|----------------------|------------------|-------------------------------------|
| (main pom)  | Unit testing         | JUnit 5          | `mvn test -Punit` / `run-unit.bat`  |
| `jmeter/`   | Load / performance   | Apache JMeter    | `jmeter -n -t demo-api-load-test.jmx ...` |
| `zap/`      | Security / DAST      | OWASP ZAP (Docker) | `run-zap-baseline.bat`            |

Each subfolder has its own README with prerequisites and commands.

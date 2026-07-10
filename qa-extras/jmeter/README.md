# JMeter load test — Demo Shop API

Load-tests the demo shop's public API (`GET https://automationexercise.com/api/productsList`).

**Plan:** 10 virtual users x 5 loops (= 50 requests), 5-second ramp-up.
Each request asserts the response body contains `products` and completes under 3000 ms.

## Prerequisites
- Apache JMeter 5.6+ (`https://jmeter.apache.org/download_jmeter.cgi`)

## Run (headless / CLI — recommended for CI)
```bash
jmeter -n -t demo-api-load-test.jmx -l results.jtl -e -o html-report
```
- `-n` non-GUI, `-t` test plan, `-l` raw results, `-e -o` generate an HTML dashboard.
- Open `html-report/index.html` for throughput, latency percentiles, and error rate.

## Run (GUI — for editing)
```bash
jmeter -t demo-api-load-test.jmx
```

## Tuning
Edit the Thread Group in the plan: `num_threads` (users), `LoopController.loops`,
`ramp_time`. To stress-test, raise users; to endurance-test, switch the Thread Group
to the scheduler with a long duration.

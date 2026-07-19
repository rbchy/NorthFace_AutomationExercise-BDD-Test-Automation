# NorthFace_Automation – BDD Test Automation Framework (Selenium · Cucumber · TestNG · RestAssured · JUnit)

![Java](https://img.shields.io/badge/Java-17-red)
![Selenium](https://img.shields.io/badge/Selenium-4.21-43B02A)
![Cucumber](https://img.shields.io/badge/Cucumber-7.18-23D96C)
![TestNG](https://img.shields.io/badge/TestNG-7.10-orange)
![Maven](https://img.shields.io/badge/Build-Maven-blue)
![Tests](https://img.shields.io/badge/tests-69%20passing-brightgreen)
![Pass rate](https://img.shields.io/badge/pass%20rate-100%25-brightgreen)
![Report](https://img.shields.io/badge/report-Allure-orange)
![Platform](https://img.shields.io/badge/platform-macOS%20%7C%20Windows%20%7C%20Linux-blue)

A robust, multi-layer test automation framework demonstrating modern QA/SDET practice — **UI, End-to-End,
API, Unit, Accessibility, Performance, and Security testing** in one project, with **69 automated tests**
and real, documented engineering decisions at its core.

> Note: the CI badge was removed from this fork since it pointed at the original repo's GitHub Actions
> run. Re-add `![CI](https://github.com/<your-username>/<your-repo>/actions/workflows/ci.yml/badge.svg)`
> once this project is pushed to your own GitHub repo with the workflow enabled.

---

## 🌐 Applications Under Test

| Application | URL | Used for |
|-------------|-----|----------|
| The North Face | https://www.thenorthface.com/ | Bot-safe smoke / navigation (production, PerimeterX-protected) |
| Automation Exercise | https://automationexercise.com/ ( [login](https://automationexercise.com/login) ) | Search, cart, checkout, auth & API E2E |

---

## 📊 Latest Test Run

**Allure report — 69 test cases, 100% passed.**

| Metric | Result |
|--------|--------|
| ✅ Total test cases (Allure, unified) | **69** |
| ✅ Pass rate | **100%** |
| ❌ Failed | **0** |
| 🧩 Breakdown | **21** Cucumber scenarios + **48** JUnit 5 unit tests |
| 🖥️ Verified on | Java 17 · Maven · macOS (also runs on Windows/Linux) |

> Numbers come from the generated **Allure** report (which unifies the Cucumber and JUnit
> results into a single dashboard) and the Cucumber HTML report
> (`target/cucumber-reports/report.html`).

---

## 🚀 Overview

**NorthFace_Automation** was built against a **real, bot-protected production retail site**
(https://www.thenorthface.com/) rather than a friendly demo app — and the standout part is how it
handled the site fighting back, plus how the build itself was hardened.

- The site (protected by **PerimeterX**) served a *"Press & Hold to confirm you are a human"* bot
  challenge to the automated browser. This was diagnosed from a **live DOM dump** (`px-captcha-modal`
  overlay intercepting clicks) — a real anti-bot system, not a locator bug.
- The correct engineering call: keep North Face limited to **bot-safe smoke checks**, and run
  the transactional **search → cart → checkout** flow against an automation-friendly target
  (automationexercise.com) — exactly how a QA team points automation at a staging environment.
- Real flakiness was removed at the source: the demo site's **Google Vignette interstitial ads** were
  hijacking navigation, so ad/analytics domains are blackholed at the Chrome layer and navigation
  happens by direct URL.
- **Build stability:** `allure-cucumber7-jvm` and `allure-junit5` were previously pinned to
  hardcoded versions, which pulled in an `io.cucumber:gherkin` transitively requiring
  `io.cucumber:messages` in a version range that never overlapped with what `cucumber-java:7.18.0`
  needed — a hard Maven dependency-resolution failure, not a code bug. Fixed by importing
  `allure-bom` in `dependencyManagement` (see `pom.xml`) so the Allure artifact versions are resolved
  consistently instead of hand-pinned; `allure-commandline` is kept on its own
  `allure.report.version` property since it ships from a separate release train than the Allure
  Java libraries.

*Diagnose → decide → stabilise* — the difference between following a tutorial and automating a messy,
real application (and keeping its build green).

---

## 🛠️ Tech Stack

* **Language:** Java 17
* **Automation Tool:** Selenium WebDriver 4.21
* **BDD Framework:** Cucumber 7.18 (Gherkin)
* **Test Runner:** TestNG
* **API Testing:** RestAssured
* **Unit Testing:** JUnit 5
* **Load/Performance:** Apache JMeter
* **Security (DAST):** OWASP ZAP
* **Build Tool:** Maven (dependency versions managed via `junit-bom` + `allure-bom`)
* **Driver Management:** WebDriverManager
* **Reporting:** Allure 2.29.1 (libraries) / Allure Commandline 2.27.0 (report renderer)
* **IDE:** Eclipse / IntelliJ

---

## 📁 Project Structure

```bash
NorthFace_Automation/
│
├── src/test/java/com/northface/automation/
│   ├── pages/            # Page Object Model (North Face)
│   │   └── demo/         # Page Objects for the demo shop (E2E)
│   ├── stepdefinitions/  # Cucumber glue: Common, DemoShop, Auth, Api, Accessibility, Performance, Hooks
│   ├── runners/          # TestRunner (TestNG + Cucumber)
│   ├── utils/            # ConfigReader, DriverManager (ad-domain blocking, DOM dumps)
│   ├── lib/              # Pure logic: PriceUtils, CartCalculator, EmailValidator, PasswordValidator
│   └── unit/             # JUnit 5 unit tests
│
├── src/test/resources/
│   ├── features/         # Gherkin: smoke, navigation, search, cart, negative, auth, api, accessibility, performance
│   └── config.properties # URLs, browser, headless, timeouts, perf budget
│
├── qa-extras/
│   ├── jmeter/           # API load-test plan (.jmx)
│   └── zap/              # OWASP ZAP baseline scan
│
├── .github/workflows/ci.yml   # CI: runs unit + API tests on every push
├── run-all.bat / run-unit.bat / run-demo.bat / run-report.bat   # Windows
├── run-report.sh                                                # macOS / Linux
├── testng.xml            # TestNG suite
├── pom.xml               # Maven dependencies (junit-bom + allure-bom) + `unit` profile
└── README.md
```

---

## 🎯 Test Coverage (69 tests · 100% passing)

| Layer | Type | Tool | Positive / Negative | Count |
|-------|------|------|:--:|:--:|
| 🔥 UI | Smoke & navigation (North Face, bot-safe) | Selenium + Cucumber | + | 2 |
| 🛒 E2E | Search → add-to-cart → checkout (demo shop) | Selenium + Cucumber | + | 8 |
| 🔐 Functional | Auth: signup / invalid login | Selenium | +/− | 2 |
| 🔌 API | productsList, brandsList, searchProduct, verifyLogin | RestAssured | +/− | 6 |
| ♿ Accessibility | lang / title / accessible-name | Selenium + JS | +/− | 2 |
| ⚡ Performance | page-load budget smoke | Selenium | +/− | 2 |
| 🧪 Unit | price / cart-math / email / password validators | JUnit 5 | +/− | 48 |
| 📈 Load | API load-test plan | Apache JMeter | — | scaffold |
| 🛡️ Security | passive baseline scan | OWASP ZAP | — | scaffold |

Every functional area has **positive and negative** cases; search is **data-driven** (`Scenario Outline` + `Examples`: dress, top, jeans, tshirt).

---

## ⚙️ Framework Highlights

* ✔️ **BDD (Cucumber + Gherkin)** — human-readable scenarios
* ✔️ **Page Object Model (POM)** — every locator lives in one place
* ✔️ **Layered design** — pages · steps · runners · utils · lib · unit
* ✔️ **Dependency Injection (PicoContainer)** — one shared `TestContext` per scenario, no static state
* ✔️ **Config-driven** — URLs / browser / headless / timeouts / perf budget, overridable with `-Dkey=value`
* ✔️ **Self-documenting failures** — screenshot **and** live-DOM dump to `target/dom-dumps/`
* ✔️ **Flakiness controls** — ad-domain blocking + direct-URL navigation
* ✔️ **Reproducible dependency resolution** — `allure-bom` import prevents Allure/Cucumber transitive
  version conflicts (see Overview)
* ✔️ **CI on every push** — GitHub Actions runs the deterministic unit + API suite (once pushed to your repo)

---

## ▶️ Running the Tests

```bash
# Full Cucumber suite (UI + E2E + API + a11y + perf) → 21 scenarios
mvn clean test                                    # or run-all.bat (Windows)

# JUnit unit tests (fast, deterministic, no browser) → 48 tests
mvn clean test -Punit                             # or run-unit.bat (Windows)

# Slice by tag
mvn clean test -Dcucumber.filter.tags="@smoke"
mvn clean test -Dcucumber.filter.tags="@api"
mvn clean test -Dcucumber.filter.tags="@negative"

# Headless (CI)
mvn clean test -Dheadless=true
```

Report: `target/cucumber-reports/report.html` (screenshots auto-attached on failure).

---

## 📈 Reporting

Three report layers are available:

| Report | What it shows | How to generate |
|--------|---------------|-----------------|
| **Allure** (recommended) | Unified interactive dashboard for **Cucumber + JUnit** — overview, pass/fail charts, suites, timeline | `./run-report.sh` (Mac/Linux) or `run-report.bat` (Windows) • or `mvn clean test` → `mvn test -Punit` → `mvn allure:serve` |
| **Cucumber HTML** | BDD scenario report with steps + failure screenshots | auto-generated at `target/cucumber-reports/report.html` |
| **Surefire HTML** | Plain JUnit/TestNG results table | `mvn surefire-report:report-only` |

> `run-report.sh` / `run-report.bat` run the full Cucumber suite, then the JUnit tests (without
> cleaning, so both sets land in `target/allure-results`), then open the interactive Allure report.
>
> Opening `target/site/allure-maven-plugin/index.html` directly (double-click / `file://`) shows a
> blank page — Allure's report loads its data via JavaScript `fetch()` calls that browsers block over
> `file://`. Always view it through `mvn allure:serve`, or serve the folder yourself:
> `cd target/site/allure-maven-plugin && python3 -m http.server 8000`.

---

## 📌 Prerequisites

* Java JDK **17+**
* Maven 3.8+
* Google Chrome (ChromeDriver auto-managed by WebDriverManager)
* Internet connection (tests hit live sites)
* macOS/Linux: `chmod +x run-report.sh` once, if it isn't already executable
* *(Optional)* Docker for OWASP ZAP, Apache JMeter for load tests

---

## 📈 Future Enhancements

* Enable **parallel execution** (TestNG data-provider)
* Add **cross-browser** runs (Firefox, Edge) + **BrowserStack / Sauce Labs**
* Data-driven from **Excel / JSON**
* Expand JMeter (stress / endurance) and ZAP (active scan) coverage
* Re-enable the CI badge once pushed to a personal GitHub repo with Actions configured

---

## 👨‍💻 Author

**Ranajit B Chowdhury**
**QA Automation Engineer & SDET
📧 Cell Phone 2673425565
📧 chyranajit@gmail.com

---

## 📄 Summary

NorthFace_Automation is a real-world, industry-aligned framework that goes beyond happy-path UI testing:
it spans **UI, E2E, API, unit, accessibility, performance, and security**, and backs every claim with a
**verified 100% pass rate — 21/21 Cucumber scenarios plus 48 JUnit tests, 69 total**. It documents two
kinds of genuine engineering decisions: working around a live PerimeterX bot wall, and resolving a real
Maven dependency-range conflict in the Allure/Cucumber reporting stack. Built to be readable,
maintainable, and interview-ready.

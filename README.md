# 🖥️ Selenium POM Framework

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Selenium](https://img.shields.io/badge/Selenium-4.18-green?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.9-red)
![Allure](https://img.shields.io/badge/Allure-2.25-blue)
![CI](https://github.com/gnisharahmed-ui/selenium-pom-framework/actions/workflows/ui-tests.yml/badge.svg)

A senior-level Selenium UI automation framework built with Java, TestNG, and Allure Reports — featuring Page Object Model, parallel execution, thread-safe WebDriver management, and full CI/CD integration.

---

## 📐 Architecture

```
selenium-pom-framework/
├── src/
│   ├── main/java/com/sdet/
│   │   ├── config/        # ConfigReader — config.properties + system property overrides
│   │   ├── pages/         # Page Objects (BasePage, LoginPage, InventoryPage, ...)
│   │   └── utils/         # DriverFactory (ThreadLocal), ScreenshotUtil, RetryAnalyzer
│   └── test/java/com/sdet/
│       └── tests/         # BaseTest + LoginTest, CheckoutTest
├── src/test/resources/
│   ├── config.properties  # Browser, URL, timeout config
│   └── testng.xml         # Suite definition with parallel execution
├── .github/workflows/     # GitHub Actions CI
└── docs/                  # Design decisions
```

## 🔑 Key Design Decisions

| Concern | Solution |
|---|---|
| Thread Safety | `ThreadLocal<WebDriver>` in `DriverFactory` |
| Flaky Tests | `RetryAnalyzer` — configurable max retries |
| Reporting | Allure with step annotations + auto screenshot on failure |
| Config | `config.properties` overridable via `-Dproperty=value` |
| Parallelism | TestNG `parallel="classes"` with configurable thread count |
| Browser Support | Chrome, Firefox, headless mode |

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Chrome or Firefox browser

### Run Tests

```bash
# Clone and run with defaults (Chrome, headed)
git clone https://github.com/gnisharahmed-ui/selenium-pom-framework.git
cd selenium-pom-framework
mvn clean test

# Headless Chrome (CI mode)
mvn clean test -Dheadless=true

# Firefox
mvn clean test -Dbrowser=firefox -Dheadless=true

# Run specific test class
mvn clean test -Dtest=LoginTest
```

### Generate Allure Report

```bash
mvn allure:serve
```

---

## ✅ Test Coverage

| Test Class | Scenarios |
|---|---|
| `LoginTest` | Valid login, invalid creds, locked-out user, empty fields, data-driven login |
| `CheckoutTest` | Full E2E checkout, field validation, product sorting |

---

## 🔧 Configuration

Edit `src/test/resources/config.properties` or pass as `-D` system properties:

```properties
base.url=https://www.saucedemo.com
browser=chrome          # chrome | firefox
headless=false          # true for CI
implicit.wait=10
explicit.wait=15
```

---

## 📊 Sample Allure Report

![Allure Report](docs/allure-report-sample.png)

> Run `mvn allure:serve` to generate and open the report in your browser.

---

## 🏗️ CI/CD

GitHub Actions runs tests on every PR and push, across Chrome and Firefox in parallel. Allure reports are uploaded as artifacts.

See [`.github/workflows/ui-tests.yml`](.github/workflows/ui-tests.yml)

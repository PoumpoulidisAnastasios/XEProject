# XEProject

## ✅ Prerequisites

Make sure you have the following installed and configured:

### 🔧 Tools

| Tool        | Description                    | Link |
|-------------|--------------------------------|------|
| Java JDK    | Java 17 recommended            | [Download](https://www.oracle.com/java/technologies/javase-downloads.html) |
| Maven       | Project and dependency manager | [Download](https://maven.apache.org/download.cgi) |
| Git         | Version control system         | [Download](https://git-scm.com/downloads) |
| IntelliJ or Eclipse | Java IDE                       | [IntelliJ](https://www.jetbrains.com/idea/) / [Eclipse](https://www.eclipse.org/downloads/) |


> ✅ Verify Java and Maven are installed:
> 
> java -version
> 
> mvn -version


## 📥  Clone the Project from GitHub

Open your terminal or Git Bash and run:

```bash
git clone https://github.com/PoumpoulidisAnastasios/XEProject.git
cd your-selenium-project
```
## ⚙️ 3. Install Dependencies with Maven

Once the project is opened in your IDE, install all dependencies using Maven.

### ✅ Run the following command in your terminal (in the root directory of the project):

```bash
mvn clean install -DskipTests
```

## Overview

This project automates the functional testing of the XE Property website ([https://www.xe.gr/property/](https://www.xe.gr/property/)) using **Selenium WebDriver**, **Cucumber (BDD)**, and **TestNG**. The tests cover property searches, filtering by price and size, image carousel validation, sorting verification, and phone contact popup checks.

## Architecture & Design

### Page Object Model (POM)

- The project implements the **Page Object Model** design pattern to enhance maintainability and readability.
- Each web page is represented by a dedicated Java class encapsulating UI elements and behaviors.
- This decouples test logic from page internals, enabling easy updates when UI changes.

### PageFactory

- Selenium’s `PageFactory` is used for element initialization, leveraging annotations like `@FindBy` to locate elements.
- This provides efficient element caching and cleaner code.

# XE Property Automation - Method Descriptions

This document provides a brief explanation of each method implemented in the `HomePage` and `SearchPage` classes.

# Test Runner Explanation

This project includes two main runner files to execute the automated test scenarios:

---

## 1. Runner File
- Allows you to run a scenario or scenarios using their tags or all the feature file (e.g. SmokeTests_01).
  Example snippet in runner:
```java
@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"com.xe.testautomation.bdd","com.xe.testautomation.bdd.steps"},
        tags = "@SmokeTests_01",       // Run only scenarios tagged
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)

public class Runner extends AbstractTestNGCucumberTests {
}
```

## 2. TestRunner File
- Executes all the scenarios defined under the SmokeTests feature.
```java
@CucumberOptions(
        features = "src/main/resources/features", // Path to your .feature files
        glue = {"com.xe.testautomation.bdd",
                "com.xe.testautomation.bdd.steps",    // step defs in src/main/java
                "com.xe.testautomation.bdd.setup"
        },     // Package with step defs & hooks
        plugin = {
                "pretty",
                "summary",
                "html:target/cucumber-report.html"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
```
---

## ⚠️ Possible Reasons for Test Failures

### 1. CAPTCHA Interference
Some tests may fail due to the presence of a CAPTCHA on the XE Property website. CAPTCHA is designed to prevent automated access and may block the automation flow.

- **Cause**: CAPTCHA appears during navigation or page load.
- **Effect**: Tests may time out, fail to interact with elements, or stop execution entirely.

---

### 2. Missing Phone Button or Phone Number (Test: `@SmokeTests_05`)
The last test (`@SmokeTests_05`) often fails due to the absence of the expected phone-related UI elements.

- **Cause**: The ad may not include a phone number or may not render the phone button.
- **Effect**: The assertion checking visibility or interaction with the phone button fails.
- **Note**: This behavior can vary between ads depending on listing content.

---

## 📊 Test Report

After running the test suite, a detailed report is generated.

> **ℹ️ Note:** To view the test report, open the generated file `index.html` located inside the `cucumber-html-reports` folder using any web browser.
> My Test Run Report will be uploaded(TestRun.html).

## HomePage Methods

| Method Name             | Description                                                                                      |
|------------------------|--------------------------------------------------------------------------------------------------|
| `clickConsentButton()`  | Clicks the cookie consent or GDPR consent popup button to proceed on the site.                   |
| `setLocation(String)`   | Enters the specified location into the search location input field and selects the suggested option. |
| `setPropertySearch(String)` | Selects the property transaction type (e.g., Rent, Sale) from the available options.             |
| `clickAllSearchResults()` | Expands or selects all available search result filters or categories (e.g., property types).      |
| `clickSearchButton()`   | Clicks the search button to initiate the property search based on selected filters.              |

---

## SearchPage Methods

| Method Name                          | Description                                                                                                           |
|------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| `setPriceRange(String min, String max)` | Opens the price filter, sets minimum and maximum price, and applies the filter.                                        |
| `setSquareMeters(String min, String max)` | Opens the size filter, sets minimum and maximum square meters, and applies the filter.                                 |
| `carouselPhotosCounterSinglePage(int number)` | Checks each ad on the current page to verify no ad carousel contains more than the specified number of photos.         |
| `carouselPhotosCounterAllPages(int number)` | Verifies across all pages that no ad carousel exceeds the given number of photos.                                     |
| `adsPriceSinglePage(int min, int max)` | Checks if all ads on the current page have prices within the given range.                                             |
| `adsPriceAllPages(int min, int max)` | Checks if all ads across all pages have prices within the given range.                                                |
| `adsSquareMetersSinglePage(int min, int max)` | Checks if all ads on the current page have square meters within the given range.                                       |
| `adsSquareMetersAllPages(int min, int max)` | Checks if all ads across all pages have square meters within the given range.                                          |
| `pressSortButton(String sortMethod)` | Opens the sorting dropdown and selects the specified sorting method (e.g., price descending).                         |
| `sortResultsByDescendingPrice()`    | Validates that the ads on all pages are sorted by price in descending order.                                          |
| `checkPhone()`                      | For each ad on all pages, verifies that the phone number is hidden when clicking the ad but visible after clicking the phone button in the popup. |

---



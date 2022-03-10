## Description
Test Automation project for Flink Ecommerce Product.

## Required Tools
* Maven 3.6.3
* Java 1.8
* chrome browser 96.0.4664.110 or less
* firefox browser 83.0 or less

## Supported OS (in current scope)
* windows
* Mac

## Steps to change the supported Driver Executable
If User is facing issue with chrome/firefox browser initialization,
Please follow below step.

1. identify current chrome/firefox version & running OS.
2. [ChromeDriver][3]
3. [GeckonDriver][4]
4. For chrome replace the binary from main > Java > com > flink > resources > chromeDriverExecutable
5. For firefox replace the binary from main > Java > com > flink > resources > firefoxDriverExecutable

## Enhancement areas for robust framework [Need to do more work]

1. Retry mechanism for failed test cases
2. Handling other failure scenarios like network error

## Project Structure

### 1.  flink

Flink is test automation project used for e-commerce website Weather shopper
</br>
This is framework is implemented in page object model along with testNG and seleniumWebDriver.

#### 1. java

enumeration

    All pages object are stored here in Enum.
    This can be consumed by test methods.

extentReport

    Extent report manager

listeners

    TestNg listeners.

pages

    All the application pages are mapped for Page Object model.

singleton

    singleton class.

utils

    utility classes.

#### 2. resources

- chromeDriverExecutable

      This is chromeDriverExecutable compatible
      with application supported browser.

- application.conf

      application config file used to store environment wide
      config.

### 2. test
#### 1. Java
- annotations.

        custom annotations used for test methods.

- dataprovider.

        custom dataprovider for test methods.

- test

        test files for different features.
        important for test execution.

- testRunner

        Test Initializer file responsible to set environment variable.

#### 2. resources

- TestData

      Test data stored in properties file.
      for each test files.

## Example Procedure

#### Execution from command line

        cd <project_root>
        mvn clean test

#### Import Project into IntelliJ
- Start from project's root directory.

        cd <project_root>

- Import `<project_root>` in IntelliJ, when prompted to include additional Unknown child projects, click yes.

#### Plugin require for Intellij

1. [TestNg][2]

#### Running Java Application

`mvn clean test`

#### Execution report

`target > surefire-reports > emailable-report.html`

## Referenced articles

* [TestNg Document][1]

[1]: https://testng.org/doc/documentation-main.html
[2]: https://blog.jetbrains.com/idea/2006/06/testng-plugin/
[3]: https://chromedriver.chromium.org/downloads
[4]: https://github.com/mozilla/geckodriver/releases
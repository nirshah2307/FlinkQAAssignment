## Description
Test Automation project for Mercari Ecommerce Website.

## Required Tools
* Maven 3.6.3
* Java 1.8

## Project Structure

### 1.  mercari

Mercari is test automation project used for e-commerce website
test automation suite.</br>
This is framework is implemented in page object model along with testNG and seleniumWebDriver.

#### 1. java

contracts

    Contracts for all page element activity

enumeration

    All pages object are stored here in Enum.
    This can be consumed by test methods.

exceptionHandling

    custom exception handling

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

#### Import Project into IntelliJ
- Start from project's root directory.

        cd <project_root>

- Import `<project_root>` in IntelliJ, when prompted to include additional Unknown child projects, click yes.

#### Plugin require for Intellij

1. [TestNg][2]

#### Running Java Application

`mvn clean test`

#### Execution report

`TestReport > MercariUIAutomationTestReport.html`

## Referenced articles

* [TestNg Document][1]

[1]: https://testng.org/doc/documentation-main.html
[2]: https://blog.jetbrains.com/idea/2006/06/testng-plugin/
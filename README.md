# CareHiresSCM
This is Selenium Java TestNG project for CareHires SCM

# CareHires-TestNG Automation Framework

## Overview
This is an automated testing framework for CareHires web application using Selenium WebDriver and TestNG. The framework is designed to perform automated UI testing of the jobs functionality.

## Project Structure


## Tech Stack
- Java
- Selenium WebDriver
- TestNG
- Maven (assumed based on standard project structure)

## Prerequisites
- Java JDK (version X.X or higher)
- Maven (version X.X or higher)
- Chrome/Firefox browser
- IDE (IntelliJ IDEA recommended)

## Setup Instructions
1. Clone the repository
```bash
git clone https://github.com/carehires/scm-automation-circle.git
```
2. Install dependencies
```bash
mvn clean install
```

3. Configure test environment
   * Update config.properties with required test environment details
   * Set up browser drivers (ChromeDriver/GeckoDriver)

## Running Tests
### Via Maven
```bash
mvn test
```

### Via TestNG XML
* Right-click on the TestNG XML file
* Select "Run As" > "TestNG Suite"

## Project Features
* Page Object Model (POM) design pattern
* TestNG test framework
* Reusable action classes for all functionalities
* List view tersting capabilities

## Test Categories

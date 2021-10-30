
This project includes two test automation scenarios for magazine.trivago.com/ home page and by using Selenium and Cucumber.

Test scenarios are described in Gherkin format in the feature files located here ./src\test\resources\features

## Installation ##

You need to have [Java 8 JDK]

To run the tests locally with Chrome, install ChromeDriver 

To install all dependencies, run

```console
$ mvn clean install
```


## Running tests ##

```console
$ mvn test
```
Tests will run on Chrome. 



## Dependencies
* *[selenium](https://www.selenium.dev/)*
* *[testng](https://testng.org/)*
* *[webdrivermanager](https://github.com/bonigarcia/webdrivermanager)*

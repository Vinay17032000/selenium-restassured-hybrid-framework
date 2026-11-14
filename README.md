# Selenium + RestAssured Test Framework

Java 17, Maven, Selenium WebDriver 4, TestNG, RestAssured.

- UI tests run against [saucedemo.com](https://www.saucedemo.com) using the Page Object Model, explicit waits and a ThreadLocal driver.
- API tests run against [restful-booker](https://restful-booker.herokuapp.com), using POJO serialization and request chaining.

## Structure

```
src
├── main/java/com/framework
│   ├── config        ConfigReader (properties file with -D overrides)
│   ├── driver        ThreadLocal WebDriver setup and teardown
│   ├── pages         Page objects (BasePage holds the explicit waits)
│   └── api/models    Request/response POJOs
└── test
    ├── java/com/framework
    │   ├── base      BaseUiTest and BaseApiTest
    │   ├── listeners TestListener (Extent logging, failure screenshots)
    │   ├── reports   Thread-safe ExtentManager
    │   ├── ui        Login, cart and checkout tests
    │   └── api       Token, create booking and booking flow tests
    └── resources
        ├── config.properties   URLs, browser, headless flag, demo credentials
        └── testng.xml          Parallel suite (classes, 4 threads) and listener
Jenkinsfile               Runs mvn clean test
```

## Running

```
mvn clean test
mvn clean test -Dheadless=true
mvn clean test -Dbrowser=firefox -Dui.base.url=https://staging.example.com
mvn clean test -Dsuite.file=path/to/other-suite.xml
```

Chrome must be installed; Selenium Manager downloads the matching driver. Any key in `config.properties` can be overridden with `-D`. The Extent report is written to `target/extent-report.html`, with a screenshot attached to failed UI tests.

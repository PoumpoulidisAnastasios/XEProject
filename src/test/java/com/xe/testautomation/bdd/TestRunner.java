package com.xe.testautomation.bdd;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

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


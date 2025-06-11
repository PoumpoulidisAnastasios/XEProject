package com.xe.testautomation.bdd;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"com.xe.testautomation.bdd","com.xe.testautomation.bdd.steps"},
        tags = "@SmokeTests_01",       // Run only scenarios tagged
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)

public class Runner extends AbstractTestNGCucumberTests {

}


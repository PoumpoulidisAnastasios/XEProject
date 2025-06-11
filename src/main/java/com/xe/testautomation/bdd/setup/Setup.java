package com.xe.testautomation.bdd.setup;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Setup {
    private static WebDriver driver;

    @Before
    public void setUp() {

        if (driver == null) {
            System.out.println("Creating new ChromeDriver instance");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Setup @After called for scenario: " + scenario.getName());

        if (scenario.getSourceTagNames().contains("@browserTearDown")) {
            if (driver != null) {
                driver.quit();
                driver = null;
                System.out.println("Browser closed (@browserTearDown tag)");
            }
        } else {
            System.out.println("Browser kept open (no @browserTearDown tag)");
        }
    }
}
package com.xe.testautomation.bdd.pages;

import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public  HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(name = "geo_place_id")
    private WebElement locationInput;

    @FindBy(css = "input[data-testid='submit-input']")
    private WebElement searchButton;

    @FindBy(className = "qc-cmp2-consent-info")
    private WebElement consentInfo;

    @FindBy(id = "accept-btn" )
    private WebElement consentButton;

    @FindBy(className = "dropdown-container")
    private WebElement dropdownContainer;

    @FindBy(css = "button[data-testid='open-property-transaction-dropdown']")
    private WebElement clickPropertyTransactionButton;


    public void setLocation(String location){
        WebElement locationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("geo_place_id")));
       locationInput.sendKeys(location);
    }

    public void clickSearchButton(){
        searchButton.click();
    }

    public void clickConsentButton(){
      //  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        WebElement consentInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("qc-cmp2-consent-info")));
        boolean consentExists = !driver.findElements(By.className("qc-cmp2-consent-info")).isEmpty();
        if(consentExists) {
            consentButton.click();
        }
    }

    public void setPorpertySearch(String propertyTransaction){
        clickPropertyTransactionButton.click();
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("ul.dropdown-panel[data-testid='dropdown-panel-property-transaction']")));

        // Get all the <li> elements
        List<WebElement> listItems = dropdown.findElements(By.tagName("li"));
        for(WebElement item  : listItems) {
            WebElement button = item.findElement(By.tagName("button"));
            if (button.getText().equals(propertyTransaction)) {
                button.click();
                break;
            }
        }
    }

    public void clickAllSearchResults(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dropdown-container")));

        // Find and click all options
        List<WebElement> allOptions = driver.findElements(By.cssSelector("button.dropdown-panel-option"));
        List<String> optionTexts = new ArrayList<>();
        for (WebElement option : allOptions) {
            optionTexts.add(option.getText());
        }

        for (String optionText : optionTexts) {
            setLocation(optionText);
            List<WebElement> options = driver.findElements(By.cssSelector("button.dropdown-panel-option"));
            for (WebElement option : options) {
                if (option.getText().contains(optionText)) {
                    option.click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                    break;
                }
            }
        }
    }
}

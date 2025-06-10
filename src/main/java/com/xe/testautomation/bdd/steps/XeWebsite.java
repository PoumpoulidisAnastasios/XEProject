package com.xe.testautomation.bdd.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.xe.testautomation.bdd.pages.HomePage;
import com.xe.testautomation.bdd.pages.SearchPage;
import com.xe.testautomation.bdd.setup.Setup;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class XeWebsite {

    private WebDriver driver;
    private HomePage homePage;
    private SearchPage searchPage;
    public XeWebsite() {
        this.driver = Setup.getDriver();
        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
    }

    @Given("I open the XE Property website")
    public void OpenXePropertyWebsite() {
        // Open the site directly here
      //  WebDriver driver = Setup.getDriver();
        driver.get("https://www.xe.gr/property/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        assertTrue(driver.getTitle().toLowerCase().contains("xe"));

        homePage.clickConsentButton();
    }

    @Then("I set the location as {string}")
    public void setLocationForSearch(String location){

        homePage.setLocation(location);
    }

    @Given("I search properties for {string}")
    public void setPropertyTransaction(String transaction){
        homePage.setPorpertySearch(transaction);
    }

    @Then("I click all search results")
    public void setAllResults(){
        homePage.clickAllSearchResults();
    }

    @When("I click search button")
    public void clickSearchButton(){
        homePage.clickSearchButton();
    }

    @When("I set the price from {string} to {string}")
    public void setPriceRange(String minPrice, String maxPrice){
        searchPage.setPriceRange(minPrice,maxPrice);
    }

    @When("I set the square meters from {string} to {string}")
    public void setSquareMetersRange(String minSize, String maxSize){
        searchPage.setSquareMeters(minSize,maxSize);
    }

    @Then("I verify that the ads images are not greater than {int}")
    public void checkNumberOfImages(int number){
       // searchPage.sortResults("Πρόσφατη ενημέρωση");
       assertTrue(searchPage.carouselPhotosCounterAllPages(number),"Fail: There are ads with more than" + number + "images in carousel");

    }

    @Then("I verify that the price is in the range of {int} to {int}")
    public void checkPriceIsInTheRange(int minPrice, int maxPrice){
        assertTrue(searchPage.adsPriceAllPages(minPrice,maxPrice), "Fail: There are ads with price out of range ");
    }

    @Then("I verify that the square meters are in the range of {int} to {int}")
    public void checkSquareMetersAreInTheRange(int minMeters, int maxMeters){
       assertTrue(searchPage.adsSquareMetersAllPages(minMeters,maxMeters),"Fail: There are ads with square meters out of range ");
    }

    @When("I click the sorting method {string}")
    public void selectSortingMethod(String sortMethod){
        searchPage.pressSortButton(sortMethod);
    }

    @Then("I verify that price sorting method is descending")
    public void verifySortMethod(){
        assertTrue(searchPage.sortResultsByDescendingPrice(),"Fail: There ads are not following the descending sorting method" );
    }

    @Then("I verify phone is not visible when clicking the ad but visible when clicking the phone button")
    public void verifyPhoneDisplay(){
        assertTrue(searchPage.checkPhone());
    }
}

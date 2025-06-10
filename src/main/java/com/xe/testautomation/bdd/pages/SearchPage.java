package com.xe.testautomation.bdd.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public SearchPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @FindBy(css = "div[data-testid='price-filter']")
    private WebElement priceFilter;

    @FindBy(css = "div[data-testid='size-filter']")
    private WebElement sizeFilter;

    @FindBy(name = "maximum_price")
    private WebElement maxPriceInput;

    @FindBy(name = "minimum_price")
    private WebElement minPriceInput;

    @FindBy(name = "maximum_size")
    private WebElement maxSizeInput;

    @FindBy(name = "minimum_size")
    private WebElement minSizeInput;

    @FindBy(css = "div[data-testid='no-results']")
    private WebElement noResults;

    @FindBy(css = "a[aria-label='Next page']")
    private WebElement nextPageArrow;

    @FindBy(css = "button[data-testid='open-property-sorting-dropdown']")
    private WebElement sortingButton;

    @FindBy(css = "button[data-testid='call-action-button']")
    private WebElement phoneButton;

    @FindBy(className = "xe-modal-close")
    private WebElement clossAdPopUp;


    // ****
    // Method that set the price in HOmePage
    // ****
    public void setPriceRange(String minPrice,String maxPrice){
        WebElement priceFilterRange = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-testid='price-filter']")));
        WebElement priceRangeButton= priceFilterRange.findElement(By.tagName("button"));
        priceRangeButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        minPriceInput.sendKeys(minPrice);
        maxPriceInput.sendKeys(maxPrice);
        priceRangeButton.click();
    }

    // ****
    // Method that set the square meters in HomePage
    // ****
    public void setSquareMeters(String minMeters, String maxMeters){
        WebElement sizeFilterRange = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-testid='size-filter']")));
        WebElement sizeRangeButton= sizeFilterRange.findElement(By.tagName("button"));
        sizeRangeButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        minSizeInput.sendKeys(minMeters);
        maxSizeInput.sendKeys(maxMeters);
        sizeRangeButton.click();
    }

    public boolean carouselPhotosCounterSignlePage(int number){
        int counter=0 ;
        Actions actions = new Actions(driver);
            List<WebElement> ads = driver.findElements(By.cssSelector("div.lazyload-wrapper.scroll"));
            System.out.println("-----------------");
            for (WebElement ad : ads) {
                actions.moveToElement(ad).perform();
                List<WebElement> images = ad.findElements(By.cssSelector("div[class='slick-slide']"));
                counter = images.size() + 1;
                if (counter > number) {
                    return false;
                }
                System.out.println(counter);
            }
        return true;
    }

    public boolean carouselPhotosCounterAllPages(int number){
        Actions actions = new Actions(driver);
        List<WebElement> nextPageArrowDisabledList = driver.findElements(By.xpath("//a[@aria-label='Next page']/parent::li[contains(@class, 'disabled')]"));
        List<WebElement> noResultElement = driver.findElements(By.cssSelector("div[data-testid='no-results']"));
        if(noResultElement.isEmpty()){
            while(true){
                nextPageArrowDisabledList = driver.findElements(By.xpath("//a[@aria-label='Next page']/parent::li[contains(@class, 'disabled')]"));
                if(!carouselPhotosCounterSignlePage(number)){
                    return false;
                }
                actions.moveToElement(nextPageArrow).perform();
                if(nextPageArrowDisabledList.isEmpty()){
                    nextPageArrow.click();
                }else{
                    break;
                }
            }
        }
        return true;
    }

    public boolean adsPriceSinglePage(int minPrice, int maxPrice){
        Actions actions = new Actions(driver);
        List<WebElement> ads = driver.findElements(By.cssSelector("div.lazyload-wrapper.scroll"));
        System.out.println("-----------------");
        for (WebElement ad : ads) {
            actions.moveToElement(ad).perform();
            WebElement adPrice = ad.findElement(By.cssSelector("span[data-testid='property-ad-price']"));
            String numberPart = adPrice.getText().replaceAll("[^\\d.,]", "");
            System.out.println(numberPart);
            if(!(Integer.parseInt(numberPart)>= minPrice && Integer.parseInt(numberPart)<=maxPrice)){
                return false;
            }
        }
        return true;
    }

    public boolean adsPriceAllPages(int minPrice, int maxPrice) {
        Actions actions = new Actions(driver);
        List<WebElement> nextPageArrowDisabledList = driver.findElements(By.xpath("//a[@aria-label='Next page']/parent::li[contains(@class, 'disabled')]"));
        List<WebElement> noResultElement = driver.findElements(By.cssSelector("div[data-testid='no-results']"));
        if (noResultElement.isEmpty()) {
            while (true) {
                nextPageArrowDisabledList = driver.findElements(By.xpath("//a[@aria-label='Next page']/parent::li[contains(@class, 'disabled')]"));
                if(!adsPriceSinglePage(minPrice,maxPrice)){
                    return false;
                }
                actions.moveToElement(nextPageArrow).perform();
                if(nextPageArrowDisabledList.isEmpty()){
                    nextPageArrow.click();
                }else{
                    break;
                }
            }
        }
        return true;
    }

    public boolean adsSquareMetersSinglePage(int minMeters, int maxMeters){
        Actions actions = new Actions(driver);
        List<WebElement> ads = driver.findElements(By.cssSelector("div.lazyload-wrapper.scroll"));
        System.out.println("-----------------");
        for (WebElement ad : ads) {
            actions.moveToElement(ad).perform();
            WebElement adSquareMeters = ad.findElement(By.cssSelector("h3[data-testid='property-ad-title']"));
            String numberPart = adSquareMeters.getText().replaceAll("[^\\d]", "");
            System.out.println(numberPart);
            if(!(Integer.parseInt(numberPart)>= minMeters && Integer.parseInt(numberPart)<=maxMeters)){
                return false;
            }
        }
        return true;
    }

    public boolean adsSquareMetersAllPages(int minMeters, int maxMeters){
        Actions actions = new Actions(driver);
        List<WebElement> nextPageArrowDisabledList = driver.findElements(By.xpath("//a[@aria-label='Next page']/parent::li[contains(@class, 'disabled')]"));
        List<WebElement> noResultElement = driver.findElements(By.cssSelector("div[data-testid='no-results']"));
        if (noResultElement.isEmpty()) {
            while (true) {
                nextPageArrowDisabledList = driver.findElements(By.xpath("//a[@aria-label='Next page']/parent::li[contains(@class, 'disabled')]"));
                if(!adsSquareMetersSinglePage(minMeters,maxMeters)){
                    return false;
                }
                actions.moveToElement(nextPageArrow).perform();
                if(nextPageArrowDisabledList.isEmpty()){
                    nextPageArrow.click();
                }else{
                    break;
                }
            }
        }
        return true;
    }

    public void pressSortButton(String sortMethod){
        sortingButton.click();

        List<WebElement> sortingMethods = driver.findElements(By.cssSelector("ul.dropdown-panel li"));
        for(WebElement method : sortingMethods){
            if (method.findElement(By.tagName("button")).getText().equals(sortMethod)) {
                method.click();
            }
        }
    }

    public boolean sortResultsByDescendingPrice(){
        Actions actions = new Actions(driver);
       // pressSortButton(sortMethod);
        List<WebElement> ads = driver.findElements(By.cssSelector("div.lazyload-wrapper.scroll"));
        int flagPrice= Integer.parseInt(ads.get(0).findElement(By.cssSelector("span[data-testid='property-ad-price']")).getText().replaceAll("[^\\d.,]", ""));
        for (WebElement ad : ads) {
            System.out.println("################");
            System.out.println(flagPrice);
            actions.moveToElement(ad).perform();
            WebElement adPrice = ad.findElement(By.cssSelector("span[data-testid='property-ad-price']"));
            String numberPart = adPrice.getText().replaceAll("[^\\d.,]", "");
             if(Integer.parseInt(numberPart)>flagPrice){
                  return false;
            }
           flagPrice=Integer.parseInt(numberPart);
        }
        List<WebElement> nextPageArrowDisabledList = driver.findElements(By.xpath("//a[@aria-label='Next page']/parent::li[contains(@class, 'disabled')]"));
        if(nextPageArrowDisabledList.isEmpty()) {
            nextPageArrow.click();
            sortResultsByDescendingPrice();
        }
        return true;
    }

    public boolean checkPhone(){
        Actions actions = new Actions(driver);
        List<WebElement> ads = driver.findElements(By.cssSelector("div.lazyload-wrapper.scroll"));
        List<WebElement> nextPageArrowDisabledList = driver.findElements(By.xpath("//a[@aria-label='Next page']/parent::li[contains(@class, 'disabled')]"));
        List<WebElement> noResultElement = driver.findElements(By.cssSelector("div[data-testid='no-results']"));
        if (noResultElement.isEmpty()) {
            for (WebElement ad : ads) {
                boolean multipleAdsFlag=false;
                actions.moveToElement(ad).perform();
                if(!ad.findElements(By.cssSelector("span[data-testid='property-ads-group']")).isEmpty()) {
                    if (ad.findElement(By.cssSelector("span[data-testid='property-ads-group']")).getText().equals("Πολλαπλές αγγελίες")) {
                        ad.click();
                        WebElement uniqueAd = driver.findElement(By.cssSelector("[data-testid='unique-ad-url']"));
                        uniqueAd.click();
                        multipleAdsFlag =true ;
                    }
                }else{
                    ad.click();

                }
                WebElement adPopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='modal-content grid-x']")));
               // List<WebElement> phoneList = driver.findElements(By.cssSelector("a[href^='tel:']"));
                List<WebElement> phoneList  = driver.findElements(By.cssSelector("a[href^='tel:'] span"));
                List<WebElement> phoneButtons  = driver.findElements(By.cssSelector("button[data-testid='call-action-button']"));

                if(!phoneList.isEmpty()){
                    System.out.println("--------------------------");
                    System.out.println("Fail: Phone is visible in ad pop up");
                    return false;
                }
                if(phoneButtons.isEmpty()){
                    System.out.println("--------------------------");
                    System.out.println("Fail: Phone Button is not visible in ad pop up");
                    return false;
                }
                phoneButton.click();
                WebElement phonePopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-testid='call-action-container']")));
                List<WebElement> phoneList2  = driver.findElements(By.cssSelector("a[href^='tel:'] span"));
                if(phoneList2.isEmpty()){
                    return false;
                }
                List<WebElement> closeButtons = driver.findElements(By.cssSelector("div.xe-modal-title button"));
                // Click the last close button in the list (lowest in DOM)
                for (int i = closeButtons.size() - 1; i >= 0; i--) {
                    WebElement btn = closeButtons.get(i);
                        btn.click();
                }
                if(multipleAdsFlag){
                    WebElement closeButton = driver.findElement(By.cssSelector("div.xe-modal-title button"));
                    closeButton.click();
                }

            }

        }
        if(nextPageArrowDisabledList.isEmpty()) {
            nextPageArrow.click();
            checkPhone();
        }
        return true;
    }

}

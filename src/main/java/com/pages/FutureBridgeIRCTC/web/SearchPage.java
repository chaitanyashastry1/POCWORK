package com.pages.FutureBridgeIRCTC.web;

import com.commonFunctions.CommonFunctionsWeb;
import com.pages.FutureBridgeIRCTC.generic.CommonSearchPage;
import com.utils.GlobalVars;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage extends CommonSearchPage {
    private static WebDriver driver;
    private static GlobalVars globalVars;
    private static CommonFunctionsWeb commonFunctions;

    @FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
    private static WebElement OK;
    @FindBy(xpath = "(//input[@role='searchbox'])[1]")
    private static WebElement from;
    @FindBy(xpath = "(//input[@role='searchbox'])[2]")
    private static WebElement to;
    @FindBy(xpath = "//li[@role='option']/span[contains(text(),'C SHIVAJI MAH T - CSMT')]")
    private static WebElement from1;
    @FindBy(xpath = "//li[@role='option']/span[contains(text(),'HOWRAH JN - HWH')]")
    private static WebElement to1;
    @FindBy(xpath = "//p-dropdown[@id='journeyQuota']/div")
    private static WebElement dropdownTSelectTatKal;
    @FindBy(xpath = "(//li[@role='option']/span)[5]")
    private static WebElement tatKal;
    @FindBy(xpath = "//span[@class='ng-tns-c59-10 ui-calendar']")
    private static WebElement clickCalendar;
    @FindBy(xpath = "(//td[@class='ng-tns-c59-10 ng-star-inserted']/a)[1]")
    private static WebElement selectDate;
    @FindBy(xpath = "//p-dropdown[@id='journeyClass']/div")
    private static WebElement dropdownTSelectJourneyClass;
    @FindBy(xpath = "//li[@aria-label='AC 3 Tier (3A)']/span")
    private static WebElement selectClass;
    @FindBy(xpath = "(//label[@class='css-label_c t_c'])[3]")
    private static WebElement checkBoxWithAvailableBerth;
    @FindBy(xpath = "//button[@class='search_btn train_Search']")
    private static WebElement searchButton;
    @FindBy(xpath = "(//button[@class='active btnDefault ng-star-inserted']/span)[1]")
    private static WebElement availableTrains;

    public SearchPage() {
        globalVars = GlobalVars.getInstance();
        driver = globalVars.getWebDriver();
        PageFactory.initElements(driver, this);
        commonFunctions = CommonFunctionsWeb.getInstance();
        long t1=System.currentTimeMillis();
        long t2=System.currentTimeMillis();
        System.out.println("Cookie time for "+globalVars.getProjectName()+" >> "+(t2-t1));
        System.out.println("****************** Test started for :"+globalVars.getProjectName());

    }

    @Override
    public boolean getSearchResults() {
        boolean isSearchSuccessful = false;
        boolean isSearchSuccessfulStatus;
        try {
            commonFunctions.waitForPageToLoad();
            commonFunctions.clickElementIfDisplayed(from, 10, "From");
            commonFunctions.clickElementIfDisplayed(from1, 10, "C SHIVAJI MAH T - CSTM");
            commonFunctions.clickElementIfDisplayed(to, 10, "To");
            commonFunctions.clickElementIfDisplayed(to1, 10, "HOWRAH JN - HWH");
            commonFunctions.clickElementIfDisplayed(dropdownTSelectTatKal, 10, "dropdown To Select TatKal");
            commonFunctions.clickElementIfDisplayed(tatKal, 10, "tatKal");
            commonFunctions.clickElementIfDisplayed(clickCalendar, 10, "click Calendar");
            commonFunctions.clickElementIfDisplayed(selectDate, 10, "select Date");
            commonFunctions.clickElementIfDisplayed(dropdownTSelectJourneyClass, 10, "dropdown To Select JourneyClass");
            commonFunctions.clickElementIfDisplayed(selectClass, 10, "select Class of journey");
            commonFunctions.isElementDisplayedAndPresent(checkBoxWithAvailableBerth,10,"checkBox With Available Berth");
            commonFunctions.clickElementIfDisplayed(checkBoxWithAvailableBerth, 10, "checkBox With Available Berth");
            commonFunctions.clickElementIfDisplayed(searchButton, 10, "search Button");
            commonFunctions.waitForPageLoading();
            commonFunctions.getElementText(availableTrains,30);
            isSearchSuccessful=  commonFunctions.checkPageURL(30);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (isSearchSuccessful) {
            isSearchSuccessfulStatus = true;
        } else {
            isSearchSuccessfulStatus = false;
        }
        return isSearchSuccessfulStatus;
    }
}
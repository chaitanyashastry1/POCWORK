package com.pages.FutureBridgeDataDriven.web;

import com.commonFunctions.CommonFunctionsWeb;
import com.pages.FutureBridgeDataDriven.generic.CommonCustomerSignupPage;
import com.utils.GlobalVars;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomerSignUpPage extends CommonCustomerSignupPage {
    private static WebDriver driver;
    private static GlobalVars globalVars;
    private static CommonFunctionsWeb commonFunctions;



    @FindBy(css = "input#name")
    private static WebElement uname;
    @FindBy(id = "SelectSegment")
    private static WebElement segmentDropDown;
    @FindBy(xpath = "(//select[@class='custom-select']/option)[8]")
    private static WebElement SegmentEducation;
    @FindBy(xpath = "//select[@id='serviceTypeId']")
    private static WebElement serviceTypeIdDropDown;
    @FindBy(xpath = "(//select[@id='serviceTypeId']/option)[2]")
    private static WebElement selectServiceType;
    @FindBy(xpath = "//input[@id='mobile']")
    private static WebElement umobile;
    @FindBy(xpath = "//input[@id='primaryemail']")
    private static WebElement uemail;
    @FindBy(id = "Communicationpincode")
    private static WebElement upincode;
    @FindBy(id = "Communicationarea")
    private static WebElement uarea;
    @FindBy(id = "Communicationbuilding")
    private static WebElement uhouse;
    @FindBy(id = "Communicationstate")
    private static WebElement ustate;
    @FindBy(id = "Communicationcity")
    private static WebElement ucity;
    @FindBy(xpath = "//label[@class='checkbox-label']/span")
    private static WebElement checkbox;
    @FindBy(id = "Orgnaization")
    private static WebElement uorgnaization;
    @FindBy(id = "alternatemobile")
    private static WebElement ualternatemobile;
    @FindBy(id = "LandLine")
    private static WebElement ulandLine;
    @FindBy(id = "AssociationName")
    private static WebElement uassociationName;
    @FindBy(id = "AssociationContact")
    private static WebElement uassociationContact;
    @FindBy(id = "AssociationEmail")
    private static WebElement uassociationEmail;
    @FindBy(xpath = "//button[@type='submit']")
    private static WebElement submitButton;


    public CustomerSignUpPage(){
        globalVars= GlobalVars.getInstance();
        driver =globalVars.getWebDriver();
        PageFactory.initElements(driver, this);
        commonFunctions= CommonFunctionsWeb.getInstance();
        long t1=System.currentTimeMillis();
        //commonFunctions.clickElementIfDisplayed(allow, 10,"Allow Notifications");
        long t2=System.currentTimeMillis();
        System.out.println("Cookie time for "+globalVars.getProjectName()+" >> "+(t2-t1));
        System.out.println("****************** Test started for :"+globalVars.getProjectName());

    }



    @Override
    public boolean signupCustomer(String name, String mobile, String email, String pinCode, String area, String house, String State, String city, String organization, String alternatePhone, String landLineNo, String societyName, String societyPhone, String societyEmail) {
        boolean isSignUpSuccessful;
        commonFunctions.waitForPageLoading();
        commonFunctions.checkPageURL(30);
        commonFunctions.sendKey(uname,name,30);
        commonFunctions.clickElementIfDisplayed(segmentDropDown,20,"segmentDropDown");
        commonFunctions.clickElementIfDisplayed(SegmentEducation,20,"SegmentEducation");
        commonFunctions.clickElementIfDisplayed(serviceTypeIdDropDown,20,"serviceTypeIdDropDown");
        commonFunctions.clickElementIfDisplayed(selectServiceType,20,"selectServiceType");
        commonFunctions.sendKey(umobile,mobile,20);
        commonFunctions.sendKey(uemail,email,20);
        commonFunctions.sendKey(upincode,pinCode,20);
        commonFunctions.sendKey(uarea,area,30);
        commonFunctions.sendKey(uhouse,house,30);
        commonFunctions.sendKey(ustate,State,30);
        commonFunctions.sendKey(ucity,city,30);
        commonFunctions.isElementDisplayedAndPresent(checkbox,20,"checkBox");
        commonFunctions.clickElementWithJS(checkbox,20,"checkbox");
        commonFunctions.sendKey(uorgnaization,organization,30);
        commonFunctions.sendKey(ualternatemobile,alternatePhone,30);
        commonFunctions.sendKey(ulandLine,landLineNo,30);
        commonFunctions.sendKey(uassociationName,societyName,30);
        commonFunctions.sendKey(uassociationContact,societyPhone,30);
        commonFunctions.sendKey(uassociationEmail,societyEmail,30);
        isSignUpSuccessful= commonFunctions.clickElementIfDisplayed(submitButton,20,"submitButton");
        return isSignUpSuccessful;
    }
}

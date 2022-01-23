package com.pages.FutureBridgeGoogle.web;

import com.commonFunctions.CommonFunctionsWeb;
import com.pages.FutureBridgeGoogle.generic.CommonLoginPage;
import com.utils.GlobalVars;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

public class LoginPage extends CommonLoginPage {
    private static WebDriver driver;
    private static GlobalVars globalVars;
    private static CommonFunctionsWeb commonFunctions;

    @FindBy(id = "signInBtn")
    private static WebElement singInButton;
    @FindBy(xpath = "//input[@id='phoneEmail']")
    private static WebElement emailTextBoxMainSignInPage;
    @FindBy(id = "btSubmit")
    private static WebElement continueButtonSignInPage;
    @FindBy(id = "upass")
    private static WebElement passwordTextBoxMainSignInPage;
    @FindBy(id = "btnLogin")
    private static WebElement signInButtonMainSignInPage;
    @FindBy(id = "userName")
    private static WebElement userNameLabelAfterLogin;
    @FindBy(xpath = "//ul[@class='userProfile']//ul//li[2]/a")
    private static WebElement signOutButton;

    private static WebElement allow;
//=================================================================================================

    @FindBy(xpath = "//input[@type='email']")
    private static WebElement gmailUser;
    @FindBy(xpath = "(//button[@type='button'])[3]")
    private static WebElement gmailUserNext;
    @FindBy(xpath = "//input[@type='password']")
    private static WebElement gmailUserPass;
    @FindBy(xpath = "(//button[@type='button'])[2]")
    private static WebElement gmailUserNext2;

    public LoginPage(){
        globalVars= GlobalVars.getInstance();
        driver =globalVars.getWebDriver();
        PageFactory.initElements(driver, this);
        commonFunctions= CommonFunctionsWeb.getInstance();
        long t1=System.currentTimeMillis();
        commonFunctions.clickElementIfDisplayed(allow, 10,"Allow Notifications");
        long t2=System.currentTimeMillis();
        System.out.println("Cookie time for "+globalVars.getProjectName()+" >> "+(t2-t1));
        System.out.println("****************** Test started for :"+globalVars.getProjectName());

    }




    @Override
    public boolean loginWithEmailAndPassword(String email, String password, String expectedUserName) {
        boolean isLoginSuccessful;
        commonFunctions.clickElement(gmailUser, 60, "Sign In Button");
        commonFunctions.sendKey(gmailUser, email, 30);
        commonFunctions.clickElement(gmailUserNext, 10, "next Button");
        commonFunctions.clickElement(gmailUserPass, 60, "Sign In Button");
        commonFunctions.sendKey(gmailUserPass, email, 30);
        commonFunctions.clickElement(gmailUserNext2, 10, "next2 Button");
        isLoginSuccessful = commonFunctions.checkElementText(userNameLabelAfterLogin, expectedUserName, 60, "UserName");
        return isLoginSuccessful;
    }

/*    Automating of Gmail is not recommended better if you would give some another scenerio,
    but how ever i can write this script but it will not work.

    Google somehow is marking those accounts and blocks them if they are launched by automation frameworks/extensions.
    Temporary Solutions: Create a fresh GMail account using a different mobile number from another device (Not recommended).*/

    @Override
    public boolean signOut() {
        boolean signOutStatus;
        commonFunctions.waitForPageToLoad();
        commonFunctions.isElementDisplayedAndPresent(userNameLabelAfterLogin,20,"Username");
        commonFunctions.clickElement(userNameLabelAfterLogin, 45, "userName");
        commonFunctions.isElementDisplayedAndPresent(signOutButton,10,"SignOut button");
        commonFunctions.clickElement(signOutButton, 60, "SignOut button");
        signOutStatus = commonFunctions.isElementDisplayedAndPresent(singInButton, 70, "Sign In");
        return signOutStatus;
    }
}

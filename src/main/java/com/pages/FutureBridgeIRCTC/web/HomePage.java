package com.pages.FutureBridgeIRCTC.web;

import com.commonFunctions.CommonFunctionsWeb;
import com.pages.FutureBridgeIRCTC.generic.CommonHomePage;
import com.utils.GlobalVars;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;


public class HomePage extends CommonHomePage {
	private static WebDriver driver;
	private static GlobalVars globalVars;
	private static CommonFunctionsWeb commonFunctions;



	@FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
	private static WebElement OK;
	@FindBy(xpath="(//div/img[@class='h_logo'])[1]")
	private static WebElement IRCTCLogo;

	public HomePage(){
        globalVars= GlobalVars.getInstance();
        driver =globalVars.getWebDriver();
        PageFactory.initElements(driver, this);
        commonFunctions= CommonFunctionsWeb.getInstance();
		long t1=System.currentTimeMillis();
		long t2=System.currentTimeMillis();
		System.out.println("Cookie time for "+globalVars.getProjectName()+" >> "+(t2-t1));
		System.out.println("****************** Test started for :"+globalVars.getProjectName());

    }

	@Override
	public String getIRCTCTitle() {
		 return commonFunctions.getTitle();
	}

	@Override
	public boolean clickIRCTCtLogo() {
		return commonFunctions.clickElementIfDisplayed(IRCTCLogo ,5, "IRCTC logo");
	}

	@Override
	public boolean handleAlerts() {
		boolean isAlertHandled;
		ArrayList<String> windowsList = new ArrayList<>(commonFunctions.getWindowHandlesWithExpectedNumberOfWindows(1));
		commonFunctions.switchToWindow(windowsList.get(windowsList.size() - 1));
		commonFunctions.isElementDisplayedIgnoringStaleElement(OK,30,"OK Alert");
		isAlertHandled = commonFunctions.clickElementWithJS(OK, 30,"OK Alert");

		return isAlertHandled;
	}
}


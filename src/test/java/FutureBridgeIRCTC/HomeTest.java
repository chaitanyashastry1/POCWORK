package FutureBridgeIRCTC;

import com.annotation.Author;
import com.annotation.Description;
import com.base.TestBase;
import com.pages.FutureBridgeIRCTC.generic.CommonHomePage;
import com.utils.Constants;
import com.utils.GlobalVars;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class HomeTest {
	private static CommonHomePage homePage;
	GlobalVars globalVars;

	@BeforeSuite
	public void initialization() {
		globalVars = TestBase.setup(Constants.FUTURE_BRIDGE_IRCTC);
	}

	@BeforeMethod
	public void beforeMethod() {
		homePage = CommonHomePage.getInstance();
	}

	@Author(name = "Chaitanya Shastry")
	@Description(": To verify Page Title of  IRCTC")
	@Test
	public void verifyGetHomePageTitleTest() {
		String[] params = globalVars.getParamsData("verifyGetHomePageTitleTest");
		String expectedTitle = params[0];
		String title = homePage.getIRCTCTitle();
		Assert.assertEquals(title, expectedTitle, "Actual and Expected Title is not matched");
	}
	
	@Author(name = "Chaitanya Shastry")
	@Description(": To verify Logo of IRCTC")
	@Test
	public void verifyClickIRCTCLogoImageTest() {
		boolean isDisplayed = homePage.clickIRCTCtLogo();
		Assert.assertTrue(isDisplayed , "Logo is not Clickable");
	}

	@Author(name = "Chaitanya Shastry")
	@Description(": To verify Alert Handle of IRCTC")
	@Test
	public void verifyHandleAlertsTest() {
		boolean isDisplayed = homePage.handleAlerts();
		Assert.assertTrue(isDisplayed , "Alert is not Clickable");
	}


	@AfterSuite
	public void closeDriver(ITestContext context) { TestBase.tearDownSuite(context); }
}

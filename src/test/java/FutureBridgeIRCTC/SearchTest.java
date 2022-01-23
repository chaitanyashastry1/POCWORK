package FutureBridgeIRCTC;

import com.annotation.Author;
import com.annotation.Description;
import com.base.TestBase;
import com.pages.FutureBridgeIRCTC.generic.CommonHomePage;
import com.pages.FutureBridgeIRCTC.generic.CommonSearchPage;
import com.utils.Constants;
import com.utils.GlobalVars;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class SearchTest {
	private static CommonSearchPage SearchPage;
	private static CommonHomePage homePage;
	GlobalVars globalVars;

	@BeforeSuite
	public void initialization() {
		globalVars = TestBase.setup(Constants.FUTURE_BRIDGE_IRCTC);
	}

	@BeforeMethod
	public void beforeMethod() {
		SearchPage = CommonSearchPage.getInstance();
		homePage = CommonHomePage.getInstance();
	}

	@Author(name = "Chaitanya Shastry")
	@Description(" To verify Search functionality of IRCTC")
	@Test
	public void verifySearchResults(){
		boolean isStepTrue;
		String[] params = globalVars.getParamsData("verifySearchResults");
		isStepTrue = homePage.handleAlerts();
		Assert.assertTrue(isStepTrue,"Alert Handled Successfully");
		isStepTrue = SearchPage.getSearchResults();
		Assert.assertTrue(isStepTrue,"Search results failed");
	}

	@AfterSuite
	public void closeDriver(ITestContext context) { TestBase.tearDownSuite(context); }

}

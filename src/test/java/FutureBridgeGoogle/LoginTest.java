package FutureBridgeGoogle;

import com.annotation.Author;
import com.annotation.Description;
import com.base.TestBase;
import com.pages.FutureBridgeGoogle.generic.CommonLoginPage;
import com.utils.Constants;
import com.utils.GlobalVars;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LoginTest {
    private static CommonLoginPage loginPage;
    GlobalVars globalVars;

    @BeforeSuite
    public void initialization(){
        globalVars = TestBase.setup(Constants.FUTURE_BRIDGE_GOOGLE);
    }

    @BeforeMethod
    public void beforeMethod(){
        loginPage=CommonLoginPage.getInstance();
    }



    @Author(name = "Chaitanya Shastry")
    @Description(": To verify Login with Email functionality ")
    @Test
    public void verifyLoginWithEmailAndPassword(){
        boolean isStepTrue;
        String[] params = globalVars.getParamsData("verifyLoginWithEmailAndPassword");
        String email = params[0];
        String password = params[1];
        String expectedUserName=params[2];
        isStepTrue = loginPage.loginWithEmailAndPassword(email, password, expectedUserName);
        Assert.assertTrue(isStepTrue, "Login with Email and Password Failed");
    }


    @AfterSuite
    public void closeDriver(ITestContext context){
        TestBase.tearDownSuite(context);
    }
}

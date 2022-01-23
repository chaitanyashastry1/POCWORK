package FutureBridgeDataDriven;

import com.annotation.Author;
import com.annotation.Description;
import com.base.TestBase;
import com.pages.FutureBridgeDataDriven.generic.CommonCustomerSignupPage;
import com.utils.Constants;
import com.utils.GlobalVars;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class CustomerSignUpTest {
    private static CommonCustomerSignupPage customerSignupPagePage;
    GlobalVars globalVars;

    @BeforeSuite
    public void initialization(){
        globalVars = TestBase.setup(Constants.FUTURE_BRIDGE_DATADRIVEN);
    }

    @BeforeMethod
    public void beforeMethod(){
        customerSignupPagePage=CommonCustomerSignupPage.getInstance();
    }



    @Author(name = "Chaitanya Shastry")
    @Description(": To verify SignUp of Customer for new connection functionality ")
    @Test
    public void verifySignUpOfCustomer(){
        boolean isStepTrue;
        String[] params = globalVars.getParamsData("verifySignUpOfCustomer");
        String name = params[0];
        String mobile = params[1];
        String email= params[2];
        String pinCode=params[3];
        String area=params[4];
        String house=params[5];
        String State=params[6];
        String city=params[7];
        String organization=params[8];
        String alternatePhone=params[9];
        String landLineNo=params[10];
        String societyName=params[11];
        String societyPhone=params[12];
        String societyEmail=params[13];
        isStepTrue = customerSignupPagePage.signupCustomer( name,mobile,email,pinCode,area,house,State,city,organization,alternatePhone,landLineNo,societyName,societyPhone,societyEmail);
        Assert.assertTrue(isStepTrue, "Signup of the Customer Failed");
    }


    @AfterSuite
    public void closeDriver(ITestContext context){
        TestBase.tearDownSuite(context);
    }
}

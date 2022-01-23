package com.pages.FutureBridgeDataDriven.generic;
import com.pages.FutureBridgeDataDriven.web.CustomerSignUpPage;
import com.utils.Constants;
import com.utils.GlobalVars;

public abstract class CommonCustomerSignupPage {
    private static CommonCustomerSignupPage commonCustomerSignupPage;
    private static GlobalVars globalVars;

    public static CommonCustomerSignupPage getInstance(){
        System.out.println("******************* beforeMethod starts here *******************");

        globalVars= GlobalVars.getInstance();
        if(commonCustomerSignupPage==null || !globalVars.getIsLastTestCasePass()){
            switch (globalVars.getPlatformName()){
                case Constants.AMP:
                    break;
                case Constants.DESKTOP_WEB:
                    commonCustomerSignupPage= new CustomerSignUpPage();
                    break;
            }
        }
        System.out.println("******************* beforeMethod ends here *******************");
        return commonCustomerSignupPage;
    }

    public abstract boolean signupCustomer(String name, String mobile, String email, String pinCode, String area, String house, String State, String city, String organization, String alternatePhone, String landLineNo, String societyName, String societyPhone, String societyEmail );

}

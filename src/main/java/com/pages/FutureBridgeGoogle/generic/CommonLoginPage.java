package com.pages.FutureBridgeGoogle.generic;
import com.pages.FutureBridgeGoogle.web.LoginPage;
import com.utils.Constants;
import com.utils.GlobalVars;

public abstract class CommonLoginPage {
    private static CommonLoginPage commonLoginPage;
    private static GlobalVars globalVars;

    public static CommonLoginPage getInstance(){
        System.out.println("******************* beforeMethod starts here *******************");

        globalVars= GlobalVars.getInstance();
        if(commonLoginPage==null || !globalVars.getIsLastTestCasePass()){
            switch (globalVars.getPlatformName()){
                case Constants.AMP:
                    break;
                case Constants.DESKTOP_WEB:
                    commonLoginPage= new LoginPage();
                    break;
            }
        }
        System.out.println("******************* beforeMethod ends here *******************");
        return commonLoginPage;
    }

    public abstract boolean loginWithEmailAndPassword(String email, String password, String expectedUserName);
    public abstract boolean signOut();

/*    Automating of Gmail is not recommended better if you would give some another scenerio,
    but how ever i can write this script but it will not work.

    Google somehow is marking those accounts and blocks them if they are launched by automation frameworks/extensions.
    Temporary Solutions: Create a fresh GMail account using a different mobile number from another device (Not recommended).*/
}

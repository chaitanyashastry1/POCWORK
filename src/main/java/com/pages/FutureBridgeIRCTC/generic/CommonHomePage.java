package com.pages.FutureBridgeIRCTC.generic;
import com.pages.FutureBridgeIRCTC.web.HomePage;
import com.utils.Constants;
import com.utils.GlobalVars;

public abstract class CommonHomePage {
    private static CommonHomePage commonHomePage;
    private static GlobalVars globalVars;

    public static CommonHomePage getInstance(){
        System.out.println("******************* beforeMethod starts here *******************");

        globalVars= GlobalVars.getInstance();
        if(commonHomePage==null || !globalVars.getIsLastTestCasePass()){
            switch (globalVars.getPlatformName()){
                case Constants.AMP:
                    break;
                case Constants.DESKTOP_WEB:
                	commonHomePage= new HomePage();
                    break;
            }
        }
        System.out.println("******************* beforeMethod ends here *******************");
        return commonHomePage;
    }

    public abstract String  getIRCTCTitle();
    public abstract boolean clickIRCTCtLogo();
    public abstract boolean handleAlerts();

}

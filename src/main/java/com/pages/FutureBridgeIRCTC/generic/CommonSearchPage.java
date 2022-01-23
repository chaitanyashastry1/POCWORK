package com.pages.FutureBridgeIRCTC.generic;

import com.pages.FutureBridgeIRCTC.web.SearchPage;
import com.utils.Constants;
import com.utils.GlobalVars;

public abstract class CommonSearchPage {
    private static CommonSearchPage commonSearchPage;
    private static GlobalVars globalVars;

    public static CommonSearchPage getInstance(){
        System.out.println("******************* beforeMethod starts here *******************");

        globalVars= GlobalVars.getInstance();
        if(commonSearchPage==null || !globalVars.getIsLastTestCasePass()){
            switch (globalVars.getPlatformName()){
                case Constants.AMP:
                    break;
                case Constants.DESKTOP_WEB:
                    commonSearchPage= new SearchPage();
                    break;

            }
        }
        System.out.println("******************* beforeMethod ends here *******************");
        return commonSearchPage;
    }


    public abstract boolean getSearchResults() ;
}

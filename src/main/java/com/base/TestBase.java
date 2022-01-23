package com.base;

import com.utils.*;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public abstract class TestBase {
    static InputStream input = null;
    public static DataReader dataReader = null;
    private static TestBase testBase=null;
    private static GlobalVars globalVars;
    private static Properties prop;

    public static GlobalVars setup(String project){
        System.out.println("******************* setup starts here *******************");
        instantiateClasses();

        globalVars.setProjectName(project);

        if(!globalVars.getTestCaseListRunModeTrue().isEmpty()){
            testBase.initializeGlobalVariables();
            testBase.initializeDriver();
            initializeLoggingAndReporting();
        }
        System.out.println("******************* setup ends here *******************");
        return globalVars;
    }

    public static void instantiateClasses(){
        globalVars=GlobalVars.getInstance();
        testBase= getInstance();
        dataReader = DataReader.getInstance();
    }

    public abstract void initializeDriver();

    public static TestBase getInstance(){
        if(testBase==null) {
            switch (globalVars.getPlatformName()){
                case Constants.ANDROID_NATIVE:
                case Constants.ANDROID_AMP:
                case Constants.ANDROID_WEB:
                   // testBase=new AndroidBase();
                    break;
                case Constants.IOS_NATIVE:
                case Constants.IOS_AMP:
                case Constants.IOS_WEB:
                   // testBase=new IOSBase();
                    break;
                case Constants.DESKTOP_WEB:
                    testBase=new DesktopBase();
                    break;
                default:
                    System.out.println("********** Incorrect Platform Name **********");
            }
        }
        return testBase;
    }

    private static void initializeConfigPropertiesData(){
        boolean isPlatformAndroidNative=globalVars.getPlatformName().contains(Constants.ANDROID);
        boolean isPlatformIOSNative=globalVars.getPlatformName().contains(Constants.IOS);

        globalVars.setUatUrl(prop.getProperty(Constants.UAT_URL));
        globalVars.setStgUrl(prop.getProperty(Constants.STG_URL));
        globalVars.setPreProdUrl(prop.getProperty(Constants.PRE_PROD_URL));
        globalVars.setProdUrl(prop.getProperty(Constants.PROD_URL));
        globalVars.setIsCloudRun(Boolean.parseBoolean(prop.getProperty(Constants.IS_CLOUD_RUN)));
        globalVars.setIsAutoStartAppium(Boolean.parseBoolean(prop.getProperty(Constants.IS_AUTO_START_APPIUM)));
        globalVars.setJiraUrl(prop.getProperty(Constants.JIRA_URL));
        globalVars.setJiraUserName(prop.getProperty(Constants.JIRA_USERNAME));
        globalVars.setJiraPassword(prop.getProperty(Constants.JIRA_PASSWORD));
        globalVars.setJiraDefectAssignee(prop.getProperty(Constants.JIRA_DEFECT_ASSIGNEE));
        globalVars.setMaxRetry(Integer.parseInt(prop.getProperty(Constants.MAX_RETRY)));
        globalVars.setImplicitWait(Integer.parseInt(prop.getProperty(Constants.IMPLICIT_WAIT)));
        globalVars.setSenderEmailId(prop.getProperty(Constants.SENDER_EMAIL_ID));
        globalVars.setSenderEmailPassword(prop.getProperty(Constants.SENDER_EMAIL_PASSWORD));
        globalVars.setJiraProjectName(prop.getProperty(Constants.JIRA_PROJECT_NAME));
        globalVars.setJiraDefectType(prop.getProperty(Constants.JIRA_DEFECT_TYPE));
        globalVars.setAppiumServerIp(prop.getProperty(Constants.APPIUM_SERVER_IP));
        globalVars.setAppiumServerPort(prop.getProperty(Constants.APPIUM_SERVER_PORT));

        if(isPlatformAndroidNative){
            initializeAndroidSpecificConfigData();
        }
        else if(isPlatformIOSNative){
            initializeIOSSpecificConfigData();
        }
    }
    private static void initializeAndroidSpecificConfigData(){
       // globalVars.setAndroidAPKFileName(prop.getProperty(Constants.ANDROID_APK_FILE_NAME));
        globalVars.setAppPackage(prop.getProperty(Constants.APP_PACKAGE));
        globalVars.setAppActivity(prop.getProperty(Constants.APP_ACTIVITY));
        globalVars.setAppWaitPackage(prop.getProperty(Constants.APP_WAIT_PACKAGE));
        globalVars.setDeviceNameAndroid(prop.getProperty(Constants.DEVICE_NAME_ANDROID));
        globalVars.setPlatformVersionAndroid(prop.getProperty(Constants.PLATFORM_VERSION_ANDROID));
    }
    private static void initializeIOSSpecificConfigData(){
        globalVars.setJiraDefectType(prop.getProperty(Constants.IPA_FILE_NAME));
        globalVars.setJiraDefectType(prop.getProperty(Constants.UDID));
        globalVars.setJiraDefectType(prop.getProperty(Constants.XCODE_ORG_ID));
        globalVars.setJiraDefectType(prop.getProperty(Constants.UPDATE_WDA_BUNDLE_ID));
        globalVars.setJiraDefectType(prop.getProperty(Constants.XCODE_SIGNING_ID));
        globalVars.setDeviceNameIOS(prop.getProperty(Constants.DEVICE_NAME_IOS));
        globalVars.setPlatformVersionIOS(prop.getProperty(Constants.PLATFORM_VERSION_IOS));
    }


    public static void initializeGlobalVariables(){
        try {
            String projectName=globalVars.getProjectName();
            globalVars.setClassDataElementMap(dataReader.getTestCaseMapping()); //dataReader.getClassData(className)
            globalVars.setProp(new Properties());
            prop=globalVars.getProp();
            globalVars.setWorkingDir(System.getProperty("user.dir"));
            input = TestBase.class.getClassLoader().getResourceAsStream(projectName+"_config.properties");
            prop.load(input);
            initializeConfigPropertiesData();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void initializeLoggingAndReporting()
    {
        //********** Auto Start Appium Server ***************
        if(!globalVars.getIsCloudRun()){
            if(globalVars.getIsAutoStartAppium())
                AppiumServer.startServer();

            Utils.initializeExtentReport();
            //DOMConfigurator.configure(globalVars.getLog4jPath());
            //Log.initializeLogProperties();
        }

    }

    public static void tearDownSuite(ITestContext context) {
        if(!globalVars.getTestCaseListRunModeTrue().isEmpty()){
            //if(!globalVars.getIsBrowserStack()){
            if(!globalVars.getIsCloudRun()){
                // ***** calling the mail send method only if mail flag is true**********
                if(globalVars.getIsMailFlag())
                    //SendMailSSLWithAttachmentUtil.sendEmail(context);
                    //************ Auto Stop Appium Server based on boolean flag ************
                if(globalVars.getIsAutoStartAppium())
                    AppiumServer.stopServer();
            }
/*
            else{
                markTestResultOnBrowserStack(context);
            }
*/
            testBase.closeDriver();
        }
    }

    public abstract void closeDriver();

    public static void markTestResultOnBrowserStack(ITestContext context){
        String resultMessageFailTests="";

        boolean failTestExists=!context.getFailedTests().getAllResults().isEmpty();
        if(failTestExists){
            Set<ITestResult> testResults= context.getFailedTests().getAllResults();
            for(ITestResult result: testResults){
                String msg=result.getThrowable().getMessage();
                if(msg.contains("Build info:")) {
                    msg = msg.substring(0, msg.indexOf("Build info:"));
                }
                String exceptionName= result.getThrowable().toString();
                exceptionName=exceptionName.substring(0, exceptionName.indexOf(":"));
                String exceptionMessage= "Exception: "+exceptionName+" | Message: "+ msg;

                resultMessageFailTests=resultMessageFailTests+" || Test: "+result.getName()+"| "+exceptionMessage;
            }
            resultMessageFailTests=resultMessageFailTests.replaceFirst("||","");

            System.out.println("Master message: "+resultMessageFailTests);
        }
       // Utils.markTestResultOnBrowserStack(resultMessageFailTests, failTestExists);
    }
    
}

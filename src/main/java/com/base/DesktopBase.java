package com.base;

import com.utils.Constants;
import com.utils.GlobalVars;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.Toolkit;

import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

public class DesktopBase extends TestBase {
    static WebDriver driver;
    static GlobalVars globalVars;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String browser;
    static DesiredCapabilities capabilities;

    @Override
    public void initializeDriver() {
        System.out.println("*******************initializeDriver start*******************");
        globalVars=GlobalVars.getInstance();
        browser=globalVars.getBrowser();
        if(driver ==null || globalVars.getWebDriver()==null){
            if(globalVars.getIsBrowserStack()){
                initializeDriverWithBrowserStackCapabilities();
            }
            else {
                initializeWebDriver();
            }
        }
        else{
            driver=globalVars.getWebDriver();
            driver.manage().deleteAllCookies();
            driver.get(globalVars.getProdUrl());
        }
        System.out.println("*******************initializeDriver ends*******************");
    }

    public static void initializeWebDriver(){
        System.out.println("*******************initializeWebDriver start*******************");
        DesiredCapabilities capabilities;
        try{
            switch (browser){
                case Constants.CHROME:
                    WebDriverManager.chromedriver().setup();
                    if(globalVars.getBrowserMode().equalsIgnoreCase("private")){
                        capabilities = DesiredCapabilities.chrome();
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--incognito");
                        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                        driver = new ChromeDriver(options);
                    }else {
                        driver = new ChromeDriver();
                    }
                    break;
                case Constants.FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions=new FirefoxOptions();
                    firefoxOptions.setHeadless(false);
                    if(globalVars.getBrowserMode().equalsIgnoreCase("private")){
                        firefoxOptions.addArguments("-private-window");
                    }
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                default:
                    System.out.println("Invalid browser name !!!!!!");
            }

            driver.manage().window().maximize();

            //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            globalVars.setWebDriver(driver);

            //To enable debug info level log of Selenium, we need to uncomment below line
            //((RemoteWebDriver) driver).setLogLevel(Level.INFO);
            navigateToUrl();
            System.out.println("*******************initializeWebDriver ends*******************");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private static void navigateToUrl(){
        System.out.println("*******************navigateToUrl start*******************");
        String urlVariable=globalVars.getEnvironment()+"Url";
        String URLToNavigate=globalVars.getProp().getProperty(urlVariable);

        driver.get(URLToNavigate);
        System.out.println("******* Navigated to URL: "+URLToNavigate);
        System.out.println("*******************navigateToUrl ends*******************");
    }

    private static void setSystemPropertyForChromeDriver(){
        if(isWindows()){
            System.setProperty(Constants.WEB_DRIVER_CHROME, globalVars.getWebDriverPath()+"chromedriver.exe");
        }
        else if(isUnix()) {
            System.setProperty(Constants.WEB_DRIVER_CHROME, globalVars.getWebDriverPath() + "chromedriver_linux");
        }
        else if(isMac()){
            System.setProperty(Constants.WEB_DRIVER_CHROME, globalVars.getWebDriverPath()+"chromedriver");
        }

    }

    private static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    private static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    private static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }

    private static void maximizeBrowserOnMac(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        int width = (int) toolkit.getScreenSize().getWidth();

        int height = (int) toolkit.getScreenSize().getHeight();

        driver.manage().window().setSize(new Dimension(width, height));
    }

    @Override
    public void closeDriver(){
        driver=globalVars.getWebDriver();
        if(driver !=null) {
            try {
                driver.quit();
            } catch (Exception exception) {
                System.out.println("******** Exception in closeDriver() method! *****");
                System.out.println(exception.getMessage());
            }
            driver=null;
            globalVars.setWebDriver(null);
        }
    }

    public void initializeDriverWithBrowserStackCapabilities() {
        try{

            JSONParser parser = new JSONParser();
            String jsonConfig="src/main/resources/Capabilities/"+globalVars.getProjectName()+"/"+globalVars.getCapabilitiesJsonName()+".conf.json";
            JSONObject config = (JSONObject) parser.parse(new FileReader(jsonConfig));

            capabilities = new DesiredCapabilities();

            Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
            Iterator it = commonCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if(capabilities.getCapability(pair.getKey().toString()) == null){
                    if(pair.getKey().toString().equalsIgnoreCase("name")){
                        capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString()+"_"+globalVars.getBuildNumber());
                    }
                    else {
                        capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
                    }

                }
            }
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-plugins");
            options.addArguments("--disable-notifications");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            //capabilities.setCapability(Constants.NEW_COMMAND_TIMEOUT, 20000);
            String username = System.getenv("BROWSERSTACK_USERNAME");
            if(username == null) {
                username = (String) config.get("username");
            }

            String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            if(accessKey == null) {
                accessKey = (String) config.get("access_key");
            }

            //globalVars.setWebDriver(driver = new RemoteWebDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities));
            driver = new RemoteWebDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
            System.out.println("Initialized driver successfully");
            driver.manage().window().maximize();
            globalVars.setWebDriver(driver);
            navigateToUrl();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

package com.commonFunctions;

import com.base.TestBase;
import com.utils.Constants;
import com.utils.GlobalVars;
import com.utils.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.collections.Lists;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CommonFunctionsWeb {
    private static int tryCount = 0;
    private static WebDriver driver;
    private static GlobalVars globalVars;
    private static CommonFunctionsWeb commonFunctionsWeb;

    public static CommonFunctionsWeb getInstance() {
        if (commonFunctionsWeb == null) {
            commonFunctionsWeb = new CommonFunctionsWeb();
        }
        return commonFunctionsWeb;
    }

    private CommonFunctionsWeb() {
        globalVars = GlobalVars.getInstance();
        driver = globalVars.getWebDriver();
    }

    public boolean clickElement(WebElement element) {
        boolean isElementClicked = false;
        try {
            element.click();
            isElementClicked = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isElementClicked;
    }

    public boolean clickElementIfDisplayed(WebElement element, int timeOutInSecond, String elementName) {
        boolean isElementClicked = false;

        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            isElementClicked = true;
            Utils.logStepInfo(true, "Clicked on " + elementName + " successfully");
        } catch (Exception e) {
            System.out.println("****** Unable to find " + elementName + " within " + timeOutInSecond + " seconds *****");
        }
        return isElementClicked;
    }
/*
    public void isElementClickable(WebElement element, int timeOutInSecond,String elementName){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        try{

            Thread.sleep(5000);
        }
        catch (Exception e) {
            System.out.println("****** Unable to find "+elementName +" within "+timeOutInSecond+" seconds *****");
        }
    }
*/


    public boolean isElementClickable(WebElement element, int timeOutInSecond, String elementName) {
        boolean isElementClickableStatus = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.visibilityOf(element));
            isElementClickableStatus = element.isEnabled();
            Utils.logStepInfo(true, elementName + " clickable");
            isElementClickableStatus = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isElementClickableStatus;
    }

    public boolean clickElementIfDisplayed(WebElement element) {
        boolean isElementClicked = false;
        long t1 = System.currentTimeMillis();
        if (element.isDisplayed()) {
            element.click();
            isElementClicked = true;
        }
        long t2 = System.currentTimeMillis();
        System.out.println("clickElementIfDisplayed for : " + globalVars.getProjectName() + " | Time: " + (t2 - t1) + " | Result: " + isElementClicked);
        return isElementClicked;
    }

    public boolean clickElement(WebElement element, int timeOutInSecond) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            Utils.logStepInfo(true, "Clicked on element successfully");
        } catch (Exception e) {
            Utils.logStepInfo(false, "Unable to Click on Element");
        }
        return true;
    }

    public boolean clickElement(WebElement element, int timeOutInSecond, String elementName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            Utils.logStepInfo(true, "Clicked on " + elementName + " successfully");
        } catch (Exception e) {
            Utils.logStepInfo(false, "Unable to Click on " + elementName);
        }
        return true;
    }

    public boolean clickElementReload(WebElement element, int timeOutInSecond, String elementName) {

        try {
            Thread.sleep(5000);
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            Utils.logStepInfo(true, "Clicked on " + elementName + " successfully");
        } catch (Exception e) {
            Utils.logStepInfo(false, "Unable to Click on " + elementName);
        }
        return true;
    }

    public boolean clickElementWithActions(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
        return true;
    }

    public boolean clickElementWithJS(WebElement element) {
        boolean isElementClicked = false;
        try {

            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].click();", element);
            isElementClicked = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isElementClicked;
    }


    public boolean clickElementWithJS(WebElement element, int timeOutInSecond) {

        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click();", element);

        return true;
    }


    public boolean clickElementWithJS(WebElement element, int timeOutInSecond, String elementName) {
        boolean isElementClicked = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].click();", element);
            Utils.logStepInfo(true, "Clicked on " + elementName + " successfully");
            isElementClicked = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isElementClicked;
    }


    public boolean clickElementWithActions(WebElement element, int timeOutInSecond) {
        Actions actions;

        WebDriverWait wait;
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, timeOutInSecond);
        wait.until(ExpectedConditions.elementToBeClickable(element));

        actions.moveToElement(element).click().build().perform();

        return true;
    }


    public void sendKey(WebElement element, String key) {
        element.sendKeys(key);
    }


    public boolean sendKeyBoolean(WebElement element, String key) {
        element.sendKeys(key);
        return true;
    }


    public boolean sendKeyBoolean(WebElement element, String key, int timeOutInSecond) {

        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);

        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(key);

        return true;
    }

    public boolean sendKeyBoolean(WebElement element, String key, int timeOutInSecond, String elementName) {

        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(key);

        Utils.logStepInfo(true, "SendKeys '" + key + "' to " + elementName + " successfully");

        return true;
    }

    public void sendKey(WebElement element, String key, int timeOutInSecond) {
        WebDriverWait wait;
        try {
            wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.sendKeys(key);
            System.out.println("Sent Keys :: "+key+" successfully");
        } catch (StaleElementReferenceException staleElementReferenceException) {
            System.out.println("****** Found StaleElementReferenceException *******");
            if (tryCount < 1) {
                tryCount++;
                Utils.logStepInfo(false, "Found StaleElementReferenceException Retrying");
                sendKey(element, key, timeOutInSecond);
                tryCount = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendKeyWithActions(WebElement element, String key) {
        Actions actions;
        try {
            actions = new Actions(driver);
            actions.moveToElement(element).sendKeys(key).build().perform();
        } catch (StaleElementReferenceException staleElementReferenceException) {
            System.out.println("****** Found StaleElementReferenceException *******");
            tryCount++;
            if (tryCount < 1) {
                tryCount++;
                Utils.logStepInfo(false, "Found StaleElementReferenceException Retrying");
                sendKeyWithActions(element, key);
                tryCount = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isElementDisplayed(WebElement element) {
        boolean isElementDisplayed = false;
        try {
            isElementDisplayed = element.isDisplayed();

        } catch (StaleElementReferenceException staleElementReferenceException) {
            System.out.println("****** Found StaleElementReferenceException *******");
            if (tryCount < 1) {
                tryCount++;
                Utils.logStepInfo(false, "Found StaleElementReferenceException Retrying");
                isElementDisplayed = isElementDisplayed(element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tryCount = 0;
        return isElementDisplayed;
    }

    public boolean isElementDisplayed(WebElement element, int timeOutInSecond) {
        boolean isElementDisplayed;

        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        isElementDisplayed = wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();

        return isElementDisplayed;
    }

    public void pageRefresh() {
        driver.navigate().refresh();
    }

    public boolean isElementDisplayed(WebElement element, int timeOutInSecond, String elementName) {
        boolean isElementDisplayed = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.visibilityOf(element));
            isElementDisplayed = element.isDisplayed();
            Utils.logStepInfo(true, elementName + " available");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isElementDisplayed;
    }

    public boolean isElementDisplayedReload(WebElement element, int timeOutInSecond, String elementName) {
        boolean isElementDisplayed;

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        isElementDisplayed = element.isDisplayed();
        Utils.logStepInfo(true, elementName + " available");

        return isElementDisplayed;
    }


    public String getElementText(WebElement element) {
        return element.getText();
    }

    public String getElementText(WebElement element, int timeOutInSecond) {
        String text = "";
        WebDriverWait wait;

        wait = new WebDriverWait(driver, timeOutInSecond);
        wait.until(ExpectedConditions.visibilityOf(element));
        //wait.until(ExpectedConditions.textToBePresentInElement(element, "Hello, Tester11"));

        text = element.getText().trim();
        Utils.logStepInfo(true, "Get text from element successfully");
        Utils.logStepInfo(true, "Text found: " + text);

        return text;
    }

    public void waitForPageToLoad() {
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
    }

    public String getElementText(WebElement element, String expectedText, int timeOutInSecond) {
        String text = "";
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));


        text = element.getText().trim();
        Utils.logStepInfo(true, "Get text from element successfully");
        Utils.logStepInfo(true, "Text found: " + text);

        return text;
    }

    public boolean checkElementText(WebElement element, String expectedText, int timeOutInSecond, String elementName) {
        boolean isElementWithTextExist;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        isElementWithTextExist = wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));
        if (isElementWithTextExist) {
            Utils.logStepInfo(true, elementName + " verified successfully");
        } else {
            Utils.logStepInfo(false, elementName + " verification failed");
        }
        Utils.logStepInfo(isElementWithTextExist, elementName + " found: " + element.getText().trim());

        return isElementWithTextExist;
    }


    public void switchToWindow(String window) {
        try {
            System.out.println("************ Windows: " + window);
            driver.switchTo().window(window);
            //driver.getTitle();
        } catch (Exception e) {
            System.out.println("******* No such window found *******");
            //e.printStackTrace();
        }
    }

    public Set<String> getWindowHandlesWithExpectedNumberOfWindows(int expectedNumberOfWindows) {
        Set<String> handles = new HashSet<>();
        try {

            handles = driver.getWindowHandles();
            new WebDriverWait(driver, 20).until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));

            handles = driver.getWindowHandles();

        } catch (Exception e) {
            System.out.println("********* Exception in getWindowHandlesWithExpectedNumberOfWindows() method *****");
            System.out.println("******* No. of windows available is: " + handles.size() + " ***********");
            //e.printStackTrace();
        }

        return handles;
    }

//    public Set<String> getWindowHandlesWithExpectedNumberOfWindows(int expectedNoOfWindows) {
//        Set<String> handles=new HashSet<>();
//        try {
//            for(int i=0; i<5; i++){
//                handles =driver.getWindowHandles();
//                System.out.println("****** Inside getWindowHandles method with expected ******"+handles.size());
//                if(handles.size()>=expectedNoOfWindows){
//                    break;
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return handles;
//    }

    public void waitForWindow(int expectedNoOfWindows) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.numberOfWindowsToBe(expectedNoOfWindows));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWindowHandle() {
        String handle = "";
        try {
            handle = driver.getWindowHandle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return handle;
    }

    public Set<String> getWindowHandlesAll() {
        Set<String> handles = new LinkedHashSet<>();
        try {
            handles = driver.getWindowHandles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handles;
    }

    public void deleteCookiesAndNavigateToHomePage() {
        System.out.println("*******************deleteCookiesAndNavigateToHomePage start*******************");
        try {
            closeAllWindowsExceptMasterTab();
            driver = globalVars.getWebDriver();
            String urlVariable = globalVars.getEnvironment() + "Url";
            String url = globalVars.getProp().getProperty(urlVariable);
            driver.get(url);

            driver.manage().deleteAllCookies();
            globalVars.setWebDriver(driver);

            driver.get(url);

            System.out.println("*******************deleteCookiesAndNavigateToHomePage ends*******************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navigateToHomePage() {
        System.out.println("*******************NavigateToHomePage start*******************");
        try {
            driver = globalVars.getWebDriver();
            String urlVariable = globalVars.getEnvironment() + "Url";
            String url = globalVars.getProp().getProperty(urlVariable);
            driver.get(url);
            //globalVars.setWebDriver(driver);
            // driver.get(url);
            System.out.println("*******************NavigateToHomePage ends*******************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFacebookCookies() {
        try {
            driver = globalVars.getWebDriver();
            driver.get("https://www.facebook.com/");
            driver.manage().deleteAllCookies();
            globalVars.setWebDriver(driver);
            String urlVariable = globalVars.getEnvironment() + "Url";
            String url = globalVars.getProp().getProperty(urlVariable);
            driver.get(url);
            //**** reinitializing this class object so that the new driver can be attached to this class object
            commonFunctionsWeb = new CommonFunctionsWeb();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteGoogleCookies() {
        try {
            driver = globalVars.getWebDriver();
            driver.get("https://myaccount.google.com/");
            driver.manage().deleteAllCookies();
            globalVars.setWebDriver(driver);
            String urlVariable = globalVars.getEnvironment() + "Url";
            String url = globalVars.getProp().getProperty(urlVariable);
            driver.get(url);

            //**** reinitializing this class object so that the new driver can be attached to this class object
            commonFunctionsWeb = new CommonFunctionsWeb();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeAllWindowsExceptMasterTab() {
        try {

           /* String title=driver.getTitle();
            if(title.contains("Facebook") && driver.getWindowHandles().size()>1){
                driver.close();
            }*/

            ArrayList<String> handleList = new ArrayList<>(driver.getWindowHandles());

            if (handleList.size() > 1) {

                for (int i = 1; i < handleList.size(); i++) {
                    driver.switchTo().window(handleList.get(i));
                    driver.close();
                }

            }
            driver.switchTo().window(handleList.get(0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean switchToFrame(WebElement frameElement, int timeOutInSec) {
        boolean isSwitchSuccessful;
        try {
            driver.switchTo().parentFrame();
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSec);
            wait.until(ExpectedConditions.visibilityOf(frameElement));
            driver.switchTo().frame(frameElement);
            isSwitchSuccessful = true;
        } catch (Exception e) {
            isSwitchSuccessful = false;
            System.out.println("***** Exception in switchToFrame() *****: " + e.getMessage());
        }

        return isSwitchSuccessful;
    }

    public boolean switchToFrame(String frame) {
        boolean isSwitchSuccessful;
        try {
            driver.switchTo().frame(frame);
            isSwitchSuccessful = true;
        } catch (Exception e) {
            isSwitchSuccessful = false;
            System.out.println("***** Exception in switchToFrame() *****: " + e.getMessage());
        }

        return isSwitchSuccessful;
    }

    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("***** Exception in switchToDefaultContent() *****: " + e.getMessage());
        }

    }

    public boolean isElementDisplayedIgnoringStaleElement(WebElement element, int timeOutInSecond, String elementName) {
        boolean isElementDisplayed;
        Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOutInSecond)).ignoring(StaleElementReferenceException.class).pollingEvery(Duration.ofSeconds(1));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
        isElementDisplayed = element.isDisplayed();
        //Utils.logStepInfo(true, elementName + " available");
        return isElementDisplayed;
    }

    public boolean clickElementWithActions(WebElement element, int timeOutInSecond, String elementName) {
        Actions actions;
        WebDriverWait wait;
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, timeOutInSecond);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        actions.moveToElement(element).click().build().perform();
        Utils.logStepInfo(true, "clicked on" + elementName + " successfully");
        return true;
    }

    public boolean clickElementWithFluentWait(WebElement element, int timeOutInSecond, String elementName) {
        Wait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(timeOutInSecond)).ignoring(NoSuchElementException.class).pollingEvery(Duration.ofSeconds(3));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
        Utils.logStepInfo(true, "clicked on" + elementName + " successfully");
        return true;
    }

    public boolean checkPageURL(String expectedText, int timeOutInSecond, String elementName) {
        boolean isElementPageURLExist;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        String getPageUrl = driver.getCurrentUrl();
        isElementPageURLExist = getPageUrl.contains(expectedText);
        if (isElementPageURLExist) {
            Utils.logStepInfo(true, elementName + " verified successfully");
        } else {
            Utils.logStepInfo(false, elementName + " verification failed");
        }
        Utils.logStepInfo(isElementPageURLExist, elementName + " found: " + getPageUrl);

        return isElementPageURLExist;
    }

    public boolean checkPageURL(int timeOutInSecond) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            String getPageUrl = driver.getCurrentUrl();
            Utils.logStepInfo(true, " PageUrl found: " + getPageUrl);
        } catch (Exception e) {
            System.out.println("******* PageUrl Not Found ******* " + e);
            e.printStackTrace();
        }
        return true;
    }

    public String getTitle() {
        String title = "";
        try {
            title = driver.getTitle();
            System.out.println("Title Found : " + title);
        } catch (Exception e) {
            System.out.println("******* Title Not Found ******* " + e);
            e.printStackTrace();
        }
        return title;
    }

    public boolean checkElementNotPresent(WebElement element) {
        boolean isElementStatus;
        try {
            if (!element.isDisplayed()) {
                isElementStatus = true;
                Utils.logStepInfo("Page not broken");
            } else {
                isElementStatus = false;
                Utils.logStepInfo("Page broken");
            }
        } catch (Exception e) {
            isElementStatus = true;
            Utils.logStepInfo("Page not broken");
        }
        return isElementStatus;
    }

    public boolean doubleClickElementWithActions(WebElement element, int timeOutInSecond, String elementName) {
        Actions actions;
        WebDriverWait wait;
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, timeOutInSecond);
        wait.until(ExpectedConditions.elementToBeClickable(element));

        actions.moveToElement(element).doubleClick().build().perform();
        Utils.logStepInfo(true, " doubleClicked on " + elementName + " successfully");

        return true;
    }

    public String getProperty(WebElement element, String propertyName) {
        String value = null;
        try {
            value = element.getAttribute(propertyName);
            Utils.logStepInfo(true, "found: " + value);
        } catch (Exception e) {
        }
        return value;
    }

    public boolean isElementDisplayedAndPresent(WebElement elements, int timeOutInSecond, String elementName) {
        boolean isElementDisplayed = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
            Utils.logStepInfo(true, elementName + " available");
            isElementDisplayed = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isElementDisplayed;
    }

    public void navigateToWhatsapp() {
        try {
            driver = globalVars.getWebDriver();
            driver.get("https://web.whatsapp.com/");
            globalVars.setWebDriver(driver);
            String urlVariable = globalVars.getEnvironment() + "Url";
            String url = globalVars.getProp().getProperty(urlVariable);
            driver.get(url);
            //**** reinitializing this class object so that the new driver can be attached to this class object
            commonFunctionsWeb = new CommonFunctionsWeb();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendKeysEnter(WebElement element, int timeOutInSecond, String elementName, String expectedText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.visibilityOfAllElements(element));
            element.sendKeys(expectedText);
            element.sendKeys(Keys.ENTER);
            Utils.logStepInfo(true, "Sent " + expectedText + " to " + elementName + " Successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkAllHamburgerElements(List<WebElement> eleList, WebElement ele) {
        boolean checkAllHamburgerElementsStatus;
        boolean isLinkClickable = false;
        boolean isLinkDisplayed = false;
        boolean isURLCorrectStatus = false;
        boolean isPageBrokenStatus = false;
        String hrefValue;
        String propValue;

        try {
            for (int i = 0; i < eleList.size(); i++) {
                hrefValue = eleList.get(i).getAttribute("href");
                propValue = eleList.get(i).getText().toLowerCase();
                isLinkDisplayed = isElementDisplayed(eleList.get(i), 60, propValue);
                isLinkClickable = isElementClickable(eleList.get(i), 60, propValue);
                eleList.get(i).click();
                isURLCorrectStatus = checkPageURL(hrefValue, 60, "");
                isPageBrokenStatus = checkElementNotPresent(ele);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (isLinkClickable && isLinkDisplayed && isURLCorrectStatus && isPageBrokenStatus) {
            checkAllHamburgerElementsStatus = true;
        } else {
            checkAllHamburgerElementsStatus = false;
        }
        return checkAllHamburgerElementsStatus;
    }

    public boolean checkHamburgerAllListsNotBlank(List<WebElement> eleList) {
        boolean checkHamburgerAllListsNotBlankStatus = false;
        try {
            if (eleList.size() != 0) {
                checkHamburgerAllListsNotBlankStatus = true;
                Utils.logStepInfo("Hamburger List Not Blank");
            } else {
                checkHamburgerAllListsNotBlankStatus = false;
                Utils.logStepInfo("Hamburger List is Blank");
            }
        } catch (Exception e) {
            System.out.println("Hamburger List Not found in page");
        }
        return checkHamburgerAllListsNotBlankStatus;
    }

    public boolean checkHamburgerOpensAndClose(WebElement ele1, WebElement ele2) {
        boolean isHamburgerOpensAndCloseStatus;
        boolean isLinkClickable = false;
        boolean isLinkDisplayed = false;
        boolean isHamburgerOpen = false;
        boolean isHamburgerClosed = false;
        try {
            isLinkDisplayed = isElementDisplayed(ele1, 60, "HamburgerLines");
            isLinkClickable = isElementClickable(ele1, 60, "HamburgerLines");
            isHamburgerOpen = isElementClickable(ele1, 60, "Hamburger is Open");
            clickElementIfDisplayed(ele1, 60, "Hamburger is Closed");
            isLinkDisplayed = isElementDisplayed(ele2, 60, "HamburgerLines");
            isLinkClickable = isElementClickable(ele2, 60, "HamburgerLines");
            isHamburgerClosed = isElementClickable(ele2, 60, "Hamburger is Closed");
            clickElementIfDisplayed(ele2, 60, "Hamburger is Open");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (isLinkClickable && isLinkDisplayed && isHamburgerOpen && isHamburgerClosed) {
            isHamburgerOpensAndCloseStatus = true;
        } else {
            isHamburgerOpensAndCloseStatus = false;
        }
        return isHamburgerOpensAndCloseStatus;
    }

    public boolean checkPodcastButtonsFunctionality(WebElement ele, String elementName) {
        boolean checkPodcastButtonsFunctionalityStatus;
        boolean isElementClickable = false;
        boolean isPodcastButtonClicked = false;

        try {
            isElementClickable = isElementClickable(ele, 30, elementName);
            isPodcastButtonClicked = clickElementWithJS(ele, 30, elementName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isElementClickable && isPodcastButtonClicked) {
            checkPodcastButtonsFunctionalityStatus = true;
        } else {
            checkPodcastButtonsFunctionalityStatus = false;
        }
        return checkPodcastButtonsFunctionalityStatus;
    }

    public boolean checkNumberOfTraysInPage(List<WebElement> eleList, WebElement ele) {
        boolean checkNumberOfTraysInPageStatus;
        boolean isTrayDisplayed = false;
        boolean isPageBrokenStatus = false;
        String trays;
        try {
            for (int i = 0; i < eleList.size(); i++) {
                trays = eleList.get(i).getText().toLowerCase();
                isTrayDisplayed = isElementDisplayed(eleList.get(i), 60, trays);
                isPageBrokenStatus = checkElementNotPresent(ele);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (isTrayDisplayed && isPageBrokenStatus) {
            checkNumberOfTraysInPageStatus = true;
        } else {
            checkNumberOfTraysInPageStatus = false;
        }
        return checkNumberOfTraysInPageStatus;
    }

    public boolean getElementListText(List<WebElement> eleList, int timeOutInSecond) {
        boolean getElementListTextStatus;
        boolean isTextDisplayed = false;
        WebDriverWait wait;
        String text;
        try {
            for (int i = 0; i < eleList.size(); i++) {
                wait = new WebDriverWait(driver, timeOutInSecond);
                wait.until(ExpectedConditions.visibilityOf(eleList.get(i)));
                text = eleList.get(i).getText().toLowerCase();
                isTextDisplayed = isElementDisplayed(eleList.get(i), 60, text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (isTextDisplayed) {
            getElementListTextStatus = true;
        } else {
            getElementListTextStatus = false;
        }
        return getElementListTextStatus;
    }

    public void sendKeyWithRandomEmail(WebElement element, int timeOutInSecond) {
        WebDriverWait wait;
        String keys = "testSmart" + System.nanoTime() + "@mailinator.com";
        try {
            wait = new WebDriverWait(driver, timeOutInSecond);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.sendKeys(keys);
            Utils.logStepInfo(true, "SendKeys " + keys + " to Email text box successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean navigateToSpecificPage(WebElement element, int timeOutInSecond, String elementName) {
        boolean navigateToSpecificPageStatus;
        boolean isUserNavigated = false;
        try {
            isElementDisplayed(element, timeOutInSecond, elementName);
            clickElement(element, timeOutInSecond, elementName);
            isUserNavigated = checkPageURL(timeOutInSecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isUserNavigated) {
            navigateToSpecificPageStatus = true;
        } else {
            navigateToSpecificPageStatus = false;
        }
        return navigateToSpecificPageStatus;
    }

    public boolean clickElementsListAndNavigateBack(List<WebElement> eleList1, List<WebElement> eleList2, int timeOutInSecond, String elementName) {
        boolean clickElementListAndNavigateBackStatus;
        boolean isUserNavigated = false;
        WebDriverWait wait;
        try {
            for (int i = 0; i < eleList1.size(); i++) {
                wait = new WebDriverWait(driver, timeOutInSecond);
                wait.until(ExpectedConditions.visibilityOf(eleList1.get(i)));
                wait.until(ExpectedConditions.elementToBeClickable(eleList1.get(i)));
                eleList1.get(i).click();
                Utils.logStepInfo(true, "Clicked on " + elementName + " successfully");
                waitForPageToLoad();
                isUserNavigated = checkPageURL(timeOutInSecond);
                getElementListText(eleList2, timeOutInSecond);
                navigateBack(timeOutInSecond);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (isUserNavigated) {
            clickElementListAndNavigateBackStatus = true;
        } else {
            clickElementListAndNavigateBackStatus = false;
        }
        return clickElementListAndNavigateBackStatus;
    }

    public void navigateBack(int timeOutInSecond) {
        try {
            Utils.logStepInfo(true, "********** Navigate back Starts *************");
            driver.navigate().back();
            checkPageURL(timeOutInSecond);
            Utils.logStepInfo(true, "********** Navigate back ends ***************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean checkAllImagesInElements(List<WebElement> eleList, WebElement ele, int timeOutInSecond) {
        boolean checkAllImagesInElementsStatus;
        boolean isLinkClickable = false;
        boolean isLinkDisplayed = false;
        boolean isPageBrokenStatus = false;
        String hrefValue;
        String propValue;

        try {
            for (int i = 0; i < eleList.size(); i++) {
                hrefValue = eleList.get(i).getAttribute("src");
                Utils.logStepInfo(true, "url is " + hrefValue);
                propValue = eleList.get(i).getAttribute("alt").toLowerCase();
                isLinkDisplayed = isElementDisplayed(eleList.get(i), timeOutInSecond, propValue);
                isLinkClickable = isElementClickable(eleList.get(i), timeOutInSecond, propValue);
                isPageBrokenStatus = checkElementNotPresent(ele);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (isLinkClickable && isLinkDisplayed && isPageBrokenStatus) {
            checkAllImagesInElementsStatus = true;
        } else {
            checkAllImagesInElementsStatus = false;
        }
        return checkAllImagesInElementsStatus;
    }

    public void sendKeysWithEnter(WebElement element, String expectedText, int timeOutInSecond) {
        WebDriverWait wait;
        wait = new WebDriverWait(driver, timeOutInSecond);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(expectedText, Keys.ENTER);
    }

    public void waitForPageLoading() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete"));
        } catch (Exception e) {
        }
    }
}

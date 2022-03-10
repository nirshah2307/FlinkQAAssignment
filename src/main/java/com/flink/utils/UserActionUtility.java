package com.flink.utils;

import org.awaitility.Awaitility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * This class will be responsible for UI Actions utility
 * @author Nirmal.Shah
 */
public class UserActionUtility {
    private static final long WAIT_TIME_ELEMENT_SECS = 5;
    private static final long WAIT_MAX_TIME_ELEMENT_SECS = 30;
    private static final long PAGE_MAX_WAIT_TIME_SECS = 30;

    /**
     * This method will return page title
     * @param driver
     * @return
     */
    public static String getTitle(WebDriver driver){
        return driver.getTitle();
    }

    /**
     * Method to get text of webElement
     * @param element WebElement
     */
    public static String getWebElementText(WebElement element){
        if(element!=null)
            return element.getText();
        else
            throw new NullPointerException("Please set the element value");
    }

    /**
     * Method to click on webElement
     * @param element WebElement
     */
    public static void clickOnWebElement(WebElement element){
        if(element!=null)
            element.click();
        else
            throw new NullPointerException("Please set the element value");
    }

    /**
     * Method to check is element is displayed
     * @param webElement = webElement on which isDisplayed will be called.
     * @return true/false
     */
    public static boolean isElementDisplayed(WebElement webElement) throws NoSuchElementException {
        boolean isVisible;
        try {
            isVisible = webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Element is not loaded");
        }
        return isVisible;
    }

    /**
     * Method to explicit wait for element to be clickable
     * @param driver webdriver object
     * @param webElement webelement object
     */
    public static void waitForElementClickable(WebDriver driver, WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME_ELEMENT_SECS));
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Method to explicit wait for frame to be available
     * @param driver webdriver object
     * @param frameName FrameName object
     */
    public static void waitForFrameToLoadAndSwitch(WebDriver driver, String frameName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME_ELEMENT_SECS));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
    }

    /**
     * Method to explicit wait for element to be clickable
     * @param driver webdriver object
     * @param webElement webelement object
     * @param seconds long wait in seconds
     */
    public static void waitForElementClickable(WebDriver driver, WebElement webElement,long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Method to explicit wait for visibility of element
     * @param driver webdriver object
     * @param webElement webelement object
     */
    public static void waitForElementVisible(WebDriver driver,WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME_ELEMENT_SECS));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Method to explicit wait for visibility of element
     * @param driver webdriver object
     * @param webElement webelement object
     * @param seconds long wait in seconds
     */
    public static void waitForElementVisible(WebDriver driver,WebElement webElement,long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Method to check is element is enabled
     * @param webElement
     * @return true/false
     */
    public static boolean isElementEnabled(WebElement webElement) throws NoSuchElementException {

        boolean isEnabled;
        try {
            isEnabled = webElement.isEnabled();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Element is not loaded/exist");
        }
        return isEnabled;
    }

    /**
     * This method is application only for Select element. max wait 30 secs for condition to be true.
     * <br> condition is to wait till select is loaded with options list </br>
     * @param webElement on which user want to perform async wait
     */
    public static void AsyncOpsWait(WebElement webElement) {
        Select SelectOption = new Select(webElement);
        Awaitility.await()
                .pollInterval(WAIT_TIME_ELEMENT_SECS, TimeUnit.SECONDS)
                .atMost(WAIT_MAX_TIME_ELEMENT_SECS, TimeUnit.SECONDS)
                .until(() -> SelectOption.getOptions().size() > 1);
    }

    /**
     * Method to set text for webelement
     * @param webElement element like textfield
     * @param text text to be set in element
     */
    public static void enterText(WebElement webElement,String text) {
        webElement.sendKeys(text);
    }

    /**
     * Method to clear text
     * @param webElement
     */
    public static void clearText(WebElement webElement) {
        webElement.clear();
    }

    /**
     * Method to capture current screenshots
     * @throws InterruptedException
     */
    public static String captureCurrentScreenshot(WebDriver driver) throws InterruptedException {
        String srcFile = "";
        try {
            srcFile = "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srcFile;
    }

    /**
     * Wait for page load
     * @param driver driver object
     */
    public static void waitForPageLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_MAX_WAIT_TIME_SECS));
        wait.until(pageLoadCondition);
    }

    /**
     * Method to explicit wait for element to be clickable
     * @param driver webdriver object
     * @param webElement webelement object
     */
    public static void waitForInvisibilityOfElement(WebDriver driver, WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME_ELEMENT_SECS));
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    /**
     * Wait for title to be
     * @param driver webdriver object
     * @param title_text webelement object
     */
    public static void waitForTitleToBe(WebDriver driver, String title_text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME_ELEMENT_SECS));
        wait.until(ExpectedConditions.titleContains(title_text));
    }
}

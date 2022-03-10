package com.flink.contracts;

import org.openqa.selenium.NoSuchElementException;

/**
 * contract need to be implemented by all the Pages only.
 *
 */
public interface IPageElementActivity {

    public boolean isElementEnabled(IWebElement webElementEnum) throws NoSuchFieldException, NoSuchElementException, IllegalAccessException;
    public boolean isElementDisplayed(IWebElement webElementEnum) throws NoSuchFieldException, NoSuchElementException, IllegalAccessException;
    public void clickOnElement(IWebElement webElementEnum) throws NoSuchFieldException, NoSuchElementException, IllegalAccessException;
    public String getTitle();
}

package com.flink.test;

import com.flink.annotations.TestMethodParameters;
import com.flink.dataprovider.PropertiesDataProvider;
import com.flink.enumeration.CheckoutPageElementEnum;
import com.flink.enumeration.ConfirmationPageElementEnum;
import com.flink.pages.*;
import com.flink.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;
import testrunner.TestInitializer;

import java.util.HashMap;

public class EndToEndTest extends TestInitializer {
    MoisturizerPage moisturizerPage;
    SunscreensPage sunscreensPage;
    CheckoutPage checkoutPage;

    @TestMethodParameters(propertiesFile = "EndToEndTest.properties")
    @Test(description = "To checkout the product based on temperature",dataProvider = "getPropertiesData",dataProviderClass = PropertiesDataProvider.class)
    public void checkoutProductTest(HashMap<String,String> testData) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        HomePage homePage = new HomePage(driver);
        int temperature = homePage.getTemperature();
        //check the temperature condition
        if(temperature< Constants.MOISTURIZERS_TEMP) {
            moisturizerPage = homePage.buyMoisturizer();
            moisturizerPage.addMoisturizerToCart();
            checkoutPage = moisturizerPage.clickOnCart();
        }
        else {
            sunscreensPage = homePage.buySunscreen();
            sunscreensPage.addSunscreenToCart();
            checkoutPage = sunscreensPage.clickOnCart();
        }
        checkoutPage.clickOnPayWithCard().enterCreditCardDetails(testData.get("email"),testData.get("creditCardNumber"),testData.get("creditCardExpiry"),Integer.parseInt(testData.get("creditCardCVV")),testData.get("zip"));
        checkoutPage.clickOnElement(CheckoutPageElementEnum.submit_button_css);
        checkoutPage.waitForInvisibilityofFrame();
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        confirmationPage.waitForTitle(Constants.CONFIRMATION_PAGE_TITLE);
        Assert.assertTrue(confirmationPage.isElementDisplayed(ConfirmationPageElementEnum.success_input_xpath));
    }
}

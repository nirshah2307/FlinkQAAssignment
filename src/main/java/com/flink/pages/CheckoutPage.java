package com.flink.pages;

import com.flink.contracts.IPageElementActivity;
import com.flink.contracts.IWebElement;
import com.flink.enumeration.CheckoutPageElementEnum;
import com.flink.utils.UserActionUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * @author Nirmal.Shah
 */
public class CheckoutPage implements IPageElementActivity {

    protected final WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using = CheckoutPageElementEnum.Constants.payWithCC_button_xpath)
	private WebElement payWithCC_button_xpath;

	@FindBy(how = How.CSS, using = CheckoutPageElementEnum.Constants.email_input_css)
	private WebElement email_input_css;

	@FindBy(how = How.CSS, using = CheckoutPageElementEnum.Constants.cardNumber_input_css)
	private WebElement cardNumber_input_css;

	@FindBy(how = How.CSS, using = CheckoutPageElementEnum.Constants.zip_input_css)
	private WebElement zip_input_css;

	@FindBy(how = How.CSS, using = CheckoutPageElementEnum.Constants.expiry_input_css)
	private WebElement expiry_input_css;

	@FindBy(how = How.CSS, using = CheckoutPageElementEnum.Constants.cvc_input_css)
	private WebElement cvc_input_css;

	@FindBy(how = How.CSS, using = CheckoutPageElementEnum.Constants.pay_button_css)
	private WebElement submit_button_css;

	public CheckoutPage clickOnPayWithCard(){
		UserActionUtility.clickOnWebElement(payWithCC_button_xpath);
		return new CheckoutPage(driver);
	}

	public void enterCreditCardDetails(String email, String number, String MMYY, int CVC,String zipcode) throws InterruptedException {
		UserActionUtility.waitForFrameToLoadAndSwitch(driver,"stripe_checkout_app");
		UserActionUtility.waitForElementClickable(driver,email_input_css);
		UserActionUtility.enterText(email_input_css, email);
		TimeUnit.SECONDS.sleep(4);
		String[] listOfCardNumber = number.split(" ");
		for(String cardNumber:listOfCardNumber){
			UserActionUtility.enterText(cardNumber_input_css,cardNumber);
			TimeUnit.SECONDS.sleep(2);
		}
		UserActionUtility.enterText(expiry_input_css, MMYY.split(" ")[0]);
		UserActionUtility.enterText(expiry_input_css, MMYY.split(" ")[1]);
		UserActionUtility.enterText(cvc_input_css, String.valueOf(CVC));
		UserActionUtility.enterText(zip_input_css, zipcode);
	}

	public void waitForInvisibilityofFrame(){
		UserActionUtility.waitForInvisibilityOfElement(driver,email_input_css);
		driver.switchTo().defaultContent();
		UserActionUtility.waitForPageLoad(driver);
	}

	@Override
	public boolean isElementDisplayed(IWebElement webElementEnum) throws NoSuchFieldException, IllegalAccessException {
		Class<?> c = this.getClass();
		Field webElement = c.getDeclaredField(webElementEnum.toString());
		if(webElement.getType()!=WebElement.class)
			throw new NoSuchFieldException();
		else
			return UserActionUtility.isElementDisplayed((WebElement) webElement.get(this));
	}

	@Override
	public boolean isElementEnabled(IWebElement webElementEnum) throws NoSuchFieldException, IllegalAccessException {
		Class<?> c = this.getClass();
		Field webElement = c.getDeclaredField(webElementEnum.toString());
		if(webElement.getType()!=WebElement.class)
			throw new NoSuchFieldException();
		else
			return UserActionUtility.isElementEnabled((WebElement) webElement.get(this));
	}

	@Override
	public void clickOnElement(IWebElement webElementEnum) throws NoSuchFieldException, IllegalAccessException {
		Class<?> c = this.getClass();
		Field webElement = c.getDeclaredField(webElementEnum.toString());
		if(webElement.getType()!=WebElement.class)
			throw new NoSuchFieldException();
		else
			UserActionUtility.clickOnWebElement((WebElement) webElement.get(this));
	}

	@Override
	public String getTitle() {
		return UserActionUtility.getTitle(driver);
	}
}

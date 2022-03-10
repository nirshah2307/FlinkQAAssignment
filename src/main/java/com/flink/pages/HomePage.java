package com.flink.pages;

import com.flink.contracts.IPageElementActivity;
import com.flink.contracts.IWebElement;
import com.flink.enumeration.HomePageElementEnum;
import com.flink.utils.UserActionUtility;
import com.flink.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *This is landing page https://weathershopper.pythonanywhere.com/
 * @author Nirmal.Shah
 */
public class HomePage implements IPageElementActivity {

    protected final WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using = HomePageElementEnum.Constants.sunscreens_div_xpath)
	private WebElement sunscreens_div_xpath;

	@FindBy(how = How.XPATH, using = HomePageElementEnum.Constants.moisturizers_div_xpath)
	private WebElement moisturizers_div_xpath;

	@FindBy(how = How.CSS, using = HomePageElementEnum.Constants.temperature_div_css)
	private WebElement temperature_div_css;

	/**
	 * Method to extract temperature from string
	 * @return temperature in integer
	 */
	public int getTemperature(){
		String temperature = UserActionUtility.getWebElementText(temperature_div_css);
		return Utils.getIntegerFromString(temperature);
	};

	/**
	 * Method to buy moisturizer
	 * @return MoisturizerPage
	 */
	public MoisturizerPage buyMoisturizer(){
		UserActionUtility.clickOnWebElement(moisturizers_div_xpath);
		UserActionUtility.waitForPageLoad(driver);
		return new MoisturizerPage(driver);
	}

	/**
	 * Method to buy sunscreen
	 * @return SunscreensPage
	 */
	public SunscreensPage buySunscreen(){
		UserActionUtility.clickOnWebElement(sunscreens_div_xpath);
		UserActionUtility.waitForPageLoad(driver);
		return new SunscreensPage(driver);
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

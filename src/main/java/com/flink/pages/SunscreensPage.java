package com.flink.pages;

import com.flink.contracts.IPageElementActivity;
import com.flink.contracts.IWebElement;
import com.flink.enumeration.SunscreensElementEnum;
import com.flink.utils.UserActionUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Nirmal.Shah
 */
public class SunscreensPage implements IPageElementActivity {

    protected final WebDriver driver;

	public SunscreensPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/*@FindBys({@FindBy(how = How.CSS, using = SunscreensElementEnum.Constants.product_list_div_css)})
	private List<WebElement> product_list_div_css;*/

	@FindBy(how = How.XPATH, using = SunscreensElementEnum.Constants.cart_button_div_xpath)
	private WebElement cart_button_div_xpath;

	public void addSunscreenToCart(){
		List<WebElement> product_list_div_css = driver.findElements(By.xpath("//div[@class='container']/div/div/following-sibling::div | //div[@class='container']/div/div/preceding-sibling::div"));
		for(int item=0;item< product_list_div_css.size();item++){
			String productName = UserActionUtility.getWebElementText(product_list_div_css.get(item).findElement(By.xpath("//p")));
			System.out.println(productName);
			UserActionUtility.clickOnWebElement(driver.findElement(By.xpath("//button[contains(@onclick,'"+productName+"')]")));
		}
	}

	public CheckoutPage clickOnCart(){
		UserActionUtility.clickOnWebElement(cart_button_div_xpath);
		UserActionUtility.waitForPageLoad(driver);
		return new CheckoutPage(driver);
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

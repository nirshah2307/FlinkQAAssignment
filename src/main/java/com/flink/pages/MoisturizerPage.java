package com.flink.pages;

import com.flink.contracts.IPageElementActivity;
import com.flink.contracts.IWebElement;
import com.flink.enumeration.MoisturizersPageElementEnum;
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
 *
 * @author Nirmal.Shah
 */
public class MoisturizerPage implements IPageElementActivity {

    protected final WebDriver driver;

	public MoisturizerPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/*@FindBys({@FindBy(how = How.CSS, using = MoisturizersPageElementEnum.Constants.product_list_div_css)})
	private List<WebElement> product_list_div_css;*/

	@FindBy(how = How.XPATH, using = MoisturizersPageElementEnum.Constants.cart_button_div_xpath)
	private WebElement cart_button_div_xpath;

	public void addMoisturizerToCart(){
		List<WebElement> product_list_div_css = driver.findElements(By.xpath("//div[@class='container']/div/div/following-sibling::div | //div[@class='container']/div/div/preceding-sibling::div"));
		for(int item=0;item< product_list_div_css.size();item++){
			String productName = UserActionUtility.getWebElementText(product_list_div_css.get(item).findElement(By.xpath("//p")));
			UserActionUtility.clickOnWebElement(driver.findElement(By.xpath("//button[contains(@onclick,'"+productName+"')]")));
		}
		//UserActionUtility.clickOnWebElement(product_list_div_css.get(0).findElement(By.xpath("//button")));
		//UserActionUtility.clickOnWebElement(product_list_div_css.get(1).findElement(By.xpath("//button")));
	}

	public CheckoutPage clickOnCart(){
		UserActionUtility.clickOnWebElement(cart_button_div_xpath);
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

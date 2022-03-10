package com.flink.pages;

import com.flink.contracts.IPageElementActivity;
import com.flink.contracts.IWebElement;
import com.flink.enumeration.SunscreensPageElementEnum;
import com.flink.utils.UserActionUtility;
import com.flink.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Nirmal.Shah
 */
public class SunscreensPage implements IPageElementActivity {

    protected final WebDriver driver;

	public SunscreensPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using = SunscreensPageElementEnum.Constants.cart_button_div_xpath)
	private WebElement cart_button_div_xpath;

	/**
	 * add sunscreen products to cart
	 * @return final_cart_products
	 */
	public Map<String,Integer> addSunscreenToCart(){
		Map<String,Integer> SPF50Products = new LinkedHashMap<>();
		Map<String,Integer> SPF30Products = new LinkedHashMap<>();
		Map<String,Integer> final_cart_products = new LinkedHashMap<>();

		List<WebElement> product_rows = driver.findElements(By.xpath("//div[@class='container']/div/following-sibling::div"));

		for(int rows=2;rows<= product_rows.size()+1;rows++){
			List<WebElement> row_items = driver.findElements(By.xpath("//div[@class='container']/div["+rows+"]/div"));
			for(int columns=1;columns < row_items.size()+1;columns++){
				String productName = UserActionUtility.getWebElementText(driver.findElement(By.xpath("//div[@class='container']/div["+rows+"]/div["+columns+"]/p[1]")));
				String price_string = UserActionUtility.getWebElementText(driver.findElement(By.xpath("//div[@class='container']/div["+rows+"]/div["+columns+"]/p[2]")));
				int price = Utils.getIntegerFromString(price_string);
				if(productName.contains("SPF-30"))
					SPF30Products.put(productName,price);
				else if(productName.contains("SPF-50"))
					SPF50Products.put(productName,price);
			}
		}

		//sort SPF30 items in ascending order
		final Map<String, Integer> sorted_Map = new LinkedHashMap<>();
		SPF30Products.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.forEachOrdered(x -> sorted_Map.put(x.getKey(), x.getValue()));
		Optional<String> SPF30_item = sorted_Map.keySet().stream().findFirst();
		SPF30_item.ifPresent(s -> {
			System.out.println("SPF30 to add "+s);
			final_cart_products.put(s,SPF30Products.get(s));
			UserActionUtility.clickOnWebElement(driver.findElement(By.xpath("//button[contains(@onclick,'" + s + "')]")));
		});
		sorted_Map.clear();

		//sort SPF30 items in ascending order
		SPF50Products.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.forEachOrdered(x -> sorted_Map.put(x.getKey(), x.getValue()));
		Optional<String> SPF50_item = sorted_Map.keySet().stream().findFirst();
		SPF50_item.ifPresent(s -> {
			System.out.println("SPF50 to add "+s);
			final_cart_products.put(s,SPF50Products.get(s));
			UserActionUtility.clickOnWebElement(driver.findElement(By.xpath("//button[contains(@onclick,'" + s + "')]")));
		});
		return final_cart_products;
	}

	/**
	 * click on cart button
	 * @return CheckoutPage
	 */
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
package com.flink.pages;

import com.flink.contracts.IPageElementActivity;
import com.flink.contracts.IWebElement;
import com.flink.enumeration.MoisturizersPageElementEnum;
import com.flink.utils.UserActionUtility;
import com.flink.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Nirmal.Shah
 */
public class MoisturizerPage implements IPageElementActivity {

    protected final WebDriver driver;

	public MoisturizerPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using = MoisturizersPageElementEnum.Constants.cart_button_div_xpath)
	private WebElement cart_button_div_xpath;

	/**
	 * Add Moisturizer to cart
	 * @return final_cart_products
	 */
	public Map<String,Integer> addMoisturizerToCart(){
		Map<String,Integer> aloeProducts = new LinkedHashMap<>();
		Map<String,Integer> almondProducts = new LinkedHashMap<>();
		Map<String,Integer> final_cart_products = new LinkedHashMap<>();

		List<WebElement> product_rows = driver.findElements(By.xpath("//div[@class='container']/div/following-sibling::div"));

		for(int rows=2;rows<= product_rows.size()+1;rows++){
			List<WebElement> row_items = driver.findElements(By.xpath("//div[@class='container']/div["+rows+"]/div"));
			for(int columns=1;columns < row_items.size()+1;columns++){
				String productName = UserActionUtility.getWebElementText(driver.findElement(By.xpath("//div[@class='container']/div["+rows+"]/div["+columns+"]/p[1]")));
				String price_string = UserActionUtility.getWebElementText(driver.findElement(By.xpath("//div[@class='container']/div["+rows+"]/div["+columns+"]/p[2]")));
				int price = Utils.getIntegerFromString(price_string);
				if(productName.contains("Aloe") || productName.contains("aloe"))
					aloeProducts.put(productName,price);
				else if(productName.contains("Almond") || productName.contains("almond"))
					almondProducts.put(productName,price);
			}
		}
		final Map<String, Integer> sorted_Map = new LinkedHashMap<>();
		aloeProducts.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.forEachOrdered(x -> sorted_Map.put(x.getKey(), x.getValue()));
		Optional<String> aloe_item = sorted_Map.keySet().stream().findFirst();
		aloe_item.ifPresent(s -> {
			System.out.println("aloe to add "+s);
			final_cart_products.put(s,aloeProducts.get(s));
			UserActionUtility.clickOnWebElement(driver.findElement(By.xpath("//button[contains(@onclick,'" + s + "')]")));
		});
		sorted_Map.clear();
		almondProducts.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.forEachOrdered(x -> sorted_Map.put(x.getKey(), x.getValue()));
		Optional<String> almond_item = sorted_Map.keySet().stream().findFirst();
		almond_item.ifPresent(s -> {
			System.out.println("almond to add "+s);
			final_cart_products.put(s,almondProducts.get(s));
			UserActionUtility.clickOnWebElement(driver.findElement(By.xpath("//button[contains(@onclick,'" + s + "')]")));
		});
		return final_cart_products;
	}

	/**
	 * click on cart
	 * @return CheckoutPage
	 */
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

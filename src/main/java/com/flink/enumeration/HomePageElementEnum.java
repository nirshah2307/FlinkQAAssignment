package com.flink.enumeration;

import com.flink.contracts.IWebElement;

/**
 * Home page objects
 */
public enum HomePageElementEnum implements IWebElement {
        temperature_div(Constants.temperature_div_css),
        moisturizers_div(Constants.moisturizers_div_xpath),
        sunscreens_div(Constants.sunscreens_div_xpath);

        private final String locator_value;

        HomePageElementEnum(String locator_value) {
            this.locator_value = locator_value;
        }

        @Override
        public String getLocator_value(){
            return this.locator_value;
        }

        public static class Constants {
            public static final String temperature_div_css = "div#weather>span";
            public static final String moisturizers_div_xpath = "//button[text()='Buy moisturizers']";
            public static final String sunscreens_div_xpath = "//button[text()='Buy sunscreens']";
        }
    }
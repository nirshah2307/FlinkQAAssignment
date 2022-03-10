package com.flink.enumeration;

import com.flink.contracts.IWebElement;

public enum SunscreensElementEnum implements IWebElement {
        cart_button_div_xpath(Constants.cart_button_div_xpath),
        product_list_div_css(Constants.product_list_div_css);

        private final String locator_value;

        SunscreensElementEnum(String locator_value) {
            this.locator_value = locator_value;
        }

        @Override
        public String getLocator_value(){
            return this.locator_value;
        }

        public static class Constants {
            public static final String product_list_div_css = "div.container>div>div";
            public static final String cart_button_div_xpath = "//button[@onclick='goToCart()']";
        }

    }
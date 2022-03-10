package com.flink.enumeration;

import com.flink.contracts.IWebElement;

/**
 * MoisturizersPage page objects
 */
public enum MoisturizersPageElementEnum implements IWebElement {
        cart_button_div_xpath(Constants.cart_button_div_xpath),
        product_list_div_css(Constants.product_list_div_css);

        private final String locator_value;

        MoisturizersPageElementEnum(String locator_value) {
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
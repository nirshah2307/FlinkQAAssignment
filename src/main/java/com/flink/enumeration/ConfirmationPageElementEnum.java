package com.flink.enumeration;

import com.flink.contracts.IWebElement;

/**
 * Confirmation page objects
 */
public enum ConfirmationPageElementEnum implements IWebElement {
        success_input_xpath(Constants.success_input_xpath);

        private final String locator_value;

        ConfirmationPageElementEnum(String locator_value) {
            this.locator_value = locator_value;
        }

        @Override
        public String getLocator_value(){
            return this.locator_value;
        }

        public static class Constants {
            public static final String success_input_xpath = "//h2[text()='PAYMENT SUCCESS']";
        }

    }
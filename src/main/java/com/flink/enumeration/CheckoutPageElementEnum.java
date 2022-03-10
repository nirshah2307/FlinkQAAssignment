package com.flink.enumeration;

import com.flink.contracts.IWebElement;

public enum CheckoutPageElementEnum implements IWebElement {
        pay_button_xpath(Constants.payWithCC_button_xpath),
        email_input_css(Constants.email_input_css),
        cardNumber_input_css(Constants.cardNumber_input_css),
        expiry_input_css(Constants.expiry_input_css),
        cvc_input_css(Constants.cvc_input_css),
        zip_input_css(Constants.zip_input_css),
        submit_button_css(Constants.pay_button_css);

        private final String locator_value;

        CheckoutPageElementEnum(String locator_value) {
            this.locator_value = locator_value;
        }

        @Override
        public String getLocator_value(){
            return this.locator_value;
        }

        public static class Constants {
            public static final String payWithCC_button_xpath = "//button/span[text()='Pay with Card']";
            public static final String email_input_css = "input#email";
            public static final String cardNumber_input_css = "input#card_number";
            public static final String expiry_input_css = "input#cc-exp";
            public static final String cvc_input_css = "input#cc-csc";
            public static final String pay_button_css = "button#submitButton";
            public static final String zip_input_css = "input#billing-zip";
        }

    }
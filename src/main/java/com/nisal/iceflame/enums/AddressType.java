package com.nisal.iceflame.enums;

import lombok.Getter;

@Getter
public enum AddressType {

    HOME("Home Address"),
    WORK("Work Address"),
    BILLING("Billing Address"),
    SHIPPING("Shipping Address"),
    OTHER("Other");

    private final String title;

    AddressType(String title) {
        this.title = title;
    }

}
package com.heliorodri.challenge.datreader.utils;

public enum Infos {
    INPUT_DIR(System.getProperty("user.dir") + "/data/in/"),
    OUTPUT_DIR(System.getProperty("user.dir") + "/data/out/"),
    SALESMEN_CODE("001"),
    CUSTOMER_CODE("002"),
    SALE_CODE("003");

    private String value;
    Infos(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

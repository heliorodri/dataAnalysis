package com.heliorodri.challenge.datreader.utils;

public enum Infos {
    HOME_PATH(System.clearProperty("user.dir")),
    INPUT_DIR(HOME_PATH.getValue() + "/data/in/"),
    OUTPUT_DIR(HOME_PATH.getValue() + "/data/out/"),
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

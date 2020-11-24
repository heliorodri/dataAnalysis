package com.heliorodri.challenge.datreader.utils;

public enum Infos {
    DATA_PATH(System.clearProperty("user.dir") + "/data"),
    INPUT_DIR(DATA_PATH.getValue()+"/in/"),
    OUTPUT_DIR(DATA_PATH.getValue()+"/out/"),
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

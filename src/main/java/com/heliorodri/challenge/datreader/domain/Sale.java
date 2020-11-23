package com.heliorodri.challenge.datreader.domain;

public class Sale {

    private int saleId;
    private int itemId;
    private int itemQuantity;
    private double itemPrice;
    private String salesmenName;

    public Sale(int saleId, int itemId, int itemQuantity, double itemPrice, String salesmenName) {
        this.saleId = saleId;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.salesmenName = salesmenName;
    }

    public Sale() {}

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getSalesmenName() {
        return salesmenName;
    }

    public void setSalesmenName(String salesmenName) {
        this.salesmenName = salesmenName;
    }
}

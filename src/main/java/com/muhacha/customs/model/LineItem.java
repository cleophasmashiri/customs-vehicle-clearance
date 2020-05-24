package com.muhacha.customs.model;

import java.io.Serializable;

public class LineItem implements Serializable {

    private final String description;
    private final int quantity;
    private final double unitPrice;

    public LineItem(String description, int quantity, double unitPrice) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getAmount() {
        return unitPrice*quantity;
    }
}

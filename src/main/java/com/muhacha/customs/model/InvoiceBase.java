package com.muhacha.customs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class InvoiceBase implements Serializable {

    private LineItem lineItem;
    private List<LineItem> lineItems = new ArrayList<>();

    private String customerName;
    private String addressline1;
    private String addressCity;
    private String addressState;
    private String addressPostalCode;

    public double getTotalAmount() {
        return lineItems.stream().mapToDouble(f -> f.getAmount()).sum();
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public void addLineItem(String description, int quantity, double unitPrice) {
        lineItems.add(new LineItem(description, quantity, unitPrice));
    }
}

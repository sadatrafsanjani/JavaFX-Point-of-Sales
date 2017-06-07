package com.rafsan.inventory.entity;

public class Payment {
    
    private double subTotal;
    private double vat;
    private double discount;
    private double payable;

    public Payment(double subTotal, double vat, double discount, double payable) {
        this.subTotal = subTotal;
        this.vat = vat;
        this.discount = discount;
        this.payable = payable;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPayable() {
        return payable;
    }

    public void setPayable(double payable) {
        this.payable = payable;
    }
    
    
    
}

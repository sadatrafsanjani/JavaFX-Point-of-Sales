package com.rafsan.inventory.entity;

public class Item {

    private String itemName;
    private double unitPrice;
    private double quantity;
    private double total;

    public Item() {
    }

    public Item(String itemName, double unitPrice, double quantity, double total) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Item{" + "itemName=" + itemName + 
                ", unitPrice=" + unitPrice + 
                ", quantity=" + quantity + 
                ", total=" + total + '}';
    }
}

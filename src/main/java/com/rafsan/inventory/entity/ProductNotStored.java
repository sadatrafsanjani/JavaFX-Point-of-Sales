package com.rafsan.inventory.entity;

public class ProductNotStored extends ProductBase {
  public ProductNotStored(String productName, double price, double quantity, String description, Category category, Supplier supplier) {
    super(productName, price, quantity, description, category, supplier);
  }
}

package com.rafsan.inventory.factories;

import com.rafsan.inventory.entity.Category;
import com.rafsan.inventory.entity.ProductNotStored;
import com.rafsan.inventory.entity.Supplier;
import com.rafsan.inventory.interfaces.Product;

public class ProductNotStoredFactory extends ProductFactory {
  public Product createProduct(String productName, double price, double quantity, String description, Category category, Supplier supplier) {
    return new ProductNotStored(productName, price, quantity, description, category, supplier);
  }
}
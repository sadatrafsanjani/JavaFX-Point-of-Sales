package com.rafsan.inventory.factories;

import com.rafsan.inventory.entity.Category;
import com.rafsan.inventory.entity.ProductStored;
import com.rafsan.inventory.entity.Supplier;
import com.rafsan.inventory.interfaces.Product;

public class ProductStoredFactory extends ProductFactory {
  private long id = 0;

  public Product createProduct(String productName, double price, double quantity, String description, Category category, Supplier supplier) {
    return new ProductStored(this.id, productName, price, quantity, description, category, supplier);
  }

  public void setProductId(long id) {
    this.id = id;
  }
}

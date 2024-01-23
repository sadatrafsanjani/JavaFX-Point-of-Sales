package com.rafsan.inventory.factories;

import com.rafsan.inventory.entity.Category;
import com.rafsan.inventory.entity.Supplier;
import com.rafsan.inventory.interfaces.Product;

public abstract class ProductFactory {
  public abstract Product createProduct(String productName, double price, double quantity, String description, Category category, Supplier supplier);
}

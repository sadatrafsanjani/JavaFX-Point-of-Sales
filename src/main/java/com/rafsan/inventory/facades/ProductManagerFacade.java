package com.rafsan.inventory.facades;

import java.util.ArrayList;

import com.rafsan.inventory.interfaces.Product;
import com.rafsan.inventory.interfaces.ProductManager;

public class ProductManagerFacade implements ProductManager {
  private ArrayList<Product> products = new ArrayList<Product>();

  @Override
  public Product getProduct(int i) {
    return products.get(i);
  }

  @Override
  public void updateProduct(int i, Product product) {
    products.add(i, product);
  }

  @Override
  public void addProduct(Product product) {
    products.add(product);
  }
  
}

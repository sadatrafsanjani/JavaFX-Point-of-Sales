package com.rafsan.inventory.interfaces;

public interface ProductManager {

  Product getProduct(int it);
  
  void updateProduct(int i, Product product);

  void addProduct(Product product);
}

package com.rafsan.inventory.decorators;

import com.rafsan.inventory.interfaces.Product;

public abstract class ProductDecorator implements Product {
  protected Product product;

  public ProductDecorator(Product product) {
    this.product = product;
  }
}

package com.rafsan.inventory.decorators;

import com.rafsan.inventory.entity.Category;
import com.rafsan.inventory.entity.Supplier;
import com.rafsan.inventory.interfaces.Product;

public class Discount extends ProductDecorator {
  private double descuento;

  public Discount(Product product, double descuento) {
    super(product);
    this.descuento = descuento;
  }

  @Override
  public String getProductName() {
    return product.getProductName();
  }

  @Override
  public double getPrice() {
    return product.getPrice() - (product.getPrice() * descuento);
  }

  @Override
  public double getQuantity() {
    return product.getQuantity();
  }

  @Override
  public String getDescription() {
    return product.getDescription();
  }

  @Override
  public Category getCategory() {
    return this.product.getCategory();
  }

  @Override
  public Supplier getSupplier() {
    return this.product.getSupplier();
  }

  public double getDescuento() {
    return this.descuento;
  }
}

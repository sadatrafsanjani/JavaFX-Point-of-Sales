package com.rafsan.inventory.entity;

import javax.persistence.Column;

public class ProductStored extends ProductBase {

  @Column(name = "id")
  private long id;

  public ProductStored(long id, String productName, double price, double quantity, String description, Category category, Supplier supplier) {
    super(productName, price, quantity, description, category, supplier);

    this.id = id;
  }

  public long getId() {
    return this.id;
  }
}

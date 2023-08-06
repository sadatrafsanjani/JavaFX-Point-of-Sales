package com.rafsan.inventory.interfaces;

import java.io.Serializable;

import com.rafsan.inventory.entity.Category;
import com.rafsan.inventory.entity.Supplier;

public interface Product extends Serializable {
  public String getProductName();

  public double getPrice();

  public double getQuantity();

  public String getDescription();

  public Category getCategory();

  public Supplier getSupplier();
}

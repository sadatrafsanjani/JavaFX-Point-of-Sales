package com.rafsan.inventory.adapters;

import com.rafsan.inventory.entity.Item;
import com.rafsan.inventory.entity.ProductBase;

public class ItemAdapter extends ProductBase {
  private Item item;

  public ItemAdapter(Item item) {
    this.item = item;
  }

  @Override
  public String getProductName() {
    return item.getItemName();
  }

  @Override
  public double getPrice() {
    return item.getUnitPrice();
  }

  @Override
  public double getQuantity() {
    return this.getQuantity();
  }
}

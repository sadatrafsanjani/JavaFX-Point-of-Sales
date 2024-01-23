package com.rafsan.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.rafsan.inventory.interfaces.Product;

public class ProductBase implements Product {

  @Column(name = "name")
  private String productName;
  @Column(name = "price")
  private double price;
  @Column(name = "quantity")
  private double quantity;
  @Column(name = "description")
  private String description;
  
  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "categoryId")
  private Category category;
  
  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "supplierId")
  private Supplier supplier;

  public ProductBase() {}

  public ProductBase(String productName, double price, 
    double quantity, String description, Category category, Supplier supplier) {
    this.productName = productName;
    this.price = price;
    this.quantity = quantity;
    this.description = description;
    this.category = category;
    this.supplier = supplier;
  }

  public String getProductName() {
      return productName;
  }

  public void setProductName(String productName) {
      this.productName = productName;
  }

  public double getPrice() {
      return price;
  }

  public void setPrice(double price) {
      this.price = price;
  }

  public double getQuantity() {
      return quantity;
  }

  public void setQuantity(double quantity) {
      this.quantity = quantity;
  }

  public String getDescription() {
      return description;
  }

  public void setDescription(String description) {
      this.description = description;
  }

  public Category getCategory() {
      return category;
  }

  public void setCategory(Category category) {
      this.category = category;
  }

  public Supplier getSupplier() {
      return supplier;
  }

  public void setSupplier(Supplier supplier) {
      this.supplier = supplier;
  }
}

package com.rafsan.inventory.builders;

import com.rafsan.inventory.entity.Employee;
import com.rafsan.inventory.entity.Invoice;
import com.rafsan.inventory.interfaces.Builder;

public class InvoiceBuilder implements Builder<Invoice> {

  private String id;
  private Employee employee;
  private double total;
  private double vat;
  private double discount;
  private double payable;
  private double paid;
  private double returned;
  private String date;

  public InvoiceBuilder setId(String id) {
      this.id = id;
      return this;
  }

  public InvoiceBuilder setEmployee(Employee employee) {
      this.employee = employee;
      return this;
  }

  public InvoiceBuilder setTotal(double total) {
      this.total = total;
      return this;
  }

  public InvoiceBuilder setVat(double vat) {
      this.vat = vat;
      return this;
  }

  public InvoiceBuilder setDiscount(double discount) {
      this.discount = discount;
      return this;
  }

  public InvoiceBuilder setPayable(double payable) {
      this.payable = payable;
      return this;
  }

  public InvoiceBuilder setPaid(double paid) {
      this.paid = paid;
      return this;
  }

  public InvoiceBuilder setReturned(double returned) {
      this.returned = returned;
      return this;
  }

  public InvoiceBuilder setDate(String date) {
      this.date = date;
      return this;
  }
  
  @Override
  public Invoice build() {
    return new Invoice(id, employee, total, vat, discount, payable, paid, returned, date);
  } 
}

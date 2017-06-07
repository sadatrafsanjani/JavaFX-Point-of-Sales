package com.rafsan.inventory.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "employeeId")
    private Employee employee;
    
    @Column(name = "total")
    private double total;
    @Column(name = "vat")
    private double vat;
    @Column(name = "discount")
    private double discount;
    @Column(name = "payable")
    private double payable;
    @Column(name = "paid")
    private double paid;
    @Column(name = "returned")
    private double returned;
    @Column(name = "datetime", insertable=false)
    private String date;
    
    public Invoice() {
        
    }

    public Invoice(String id, Employee employee, double total, double vat, 
            double discount, double payable, double paid, double returned, String date) {
        this.id = id;
        this.employee = employee;
        this.total = total;
        this.vat = vat;
        this.discount = discount;
        this.payable = payable;
        this.paid = paid;
        this.returned = returned;
        this.date = date;
    }

    public Invoice(String id, Employee employee, double total, double vat, 
            double discount, double payable, double paid, double returned) {
        this.id = id;
        this.employee = employee;
        this.total = total;
        this.vat = vat;
        this.discount = discount;
        this.payable = payable;
        this.paid = paid;
        this.returned = returned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPayable() {
        return payable;
    }

    public void setPayable(double payable) {
        this.payable = payable;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getReturned() {
        return returned;
    }

    public void setReturned(double returned) {
        this.returned = returned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

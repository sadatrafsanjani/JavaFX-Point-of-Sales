package com.rafsan.inventory;

import com.rafsan.inventory.entity.Employee;

public class AppState {
  private Employee employee;

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Employee getEmployee() {
    return employee;
  }
}

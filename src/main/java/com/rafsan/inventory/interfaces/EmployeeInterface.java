package com.rafsan.inventory.interfaces;

import com.rafsan.inventory.entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface EmployeeInterface {
    
    public ObservableList<Employee> EMPLOYEELIST = FXCollections.observableArrayList();
}

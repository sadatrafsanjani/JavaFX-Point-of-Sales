package com.rafsan.inventory.interfaces;

import com.rafsan.inventory.entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface SupplierInterface {
    
    public ObservableList<Supplier> SUPPLIERLIST = FXCollections.observableArrayList();
}

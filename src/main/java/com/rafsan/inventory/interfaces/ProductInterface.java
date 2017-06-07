package com.rafsan.inventory.interfaces;

import com.rafsan.inventory.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface ProductInterface {
    
    public ObservableList<Product> PRODUCTLIST = FXCollections.observableArrayList();   
}

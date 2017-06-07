package com.rafsan.inventory.interfaces;

import com.rafsan.inventory.entity.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface CategoryInterface {
    
    public ObservableList<Category> CATEGORYLIST = FXCollections.observableArrayList();   
}

package com.rafsan.inventory.interfaces;

import com.rafsan.inventory.entity.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface PurchaseInterface {
    
    public ObservableList<Purchase> PURCHASELIST = FXCollections.observableArrayList();
}

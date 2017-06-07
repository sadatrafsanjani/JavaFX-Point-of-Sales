package com.rafsan.inventory.interfaces;

import com.rafsan.inventory.entity.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface SaleInterface {

    public ObservableList<Sale> SALELIST = FXCollections.observableArrayList();
}

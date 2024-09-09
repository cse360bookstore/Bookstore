package com.example.sellingprocess;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SellingCategoryController2 {

    private String selectedValue = "";


    @FXML
    private Label selectedValueLabel;

    @FXML
    public void handleEnglishPanelClick() {
        selectedValue = "englang";
        selectedValueLabel.setText("Selected Value: " + selectedValue);
    }
}
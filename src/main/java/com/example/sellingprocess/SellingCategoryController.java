package com.example.sellingprocess;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SellingCategoryController {

    private String selectedValue = "";
    private String selectedCategory = "";
    private String selectedCondition = "";
    private double originalPrice = 0.0;

    @FXML
    private TextField originalPriceInput;

    @FXML
    private Label categoryValueLabel;
    @FXML
    private Label conditionValueLabel;
    @FXML
    private Label oPriceValueLabel;
    @FXML
    private Label offeredValueLabel;




    @FXML
    public void handleEnglishPanelClick() {
        selectedValue = "English";
        selectedCategory = "English";
        categoryValueLabel.setText("Category: " + selectedValue);
    }
    @FXML
    public void handleMathPanelClick() {
        selectedValue = "Math";
        selectedCategory = "Math";
        categoryValueLabel.setText("Category: " + selectedValue);
    }
    @FXML
    public void handleSciencePanelClick() {
        selectedValue = "Natural Science";
        selectedCategory = "Natural Science";
        categoryValueLabel.setText("Category: " + selectedValue);
    }
    @FXML
    public void handleComputerPanelClick() {
        selectedValue = "Computer";
        selectedCategory = "Computer";
        categoryValueLabel.setText("Category: " + selectedValue);
    }
    @FXML
    public void handleOtherPanelClick() {
        selectedValue = "Other";
        selectedCategory = "Other";
        categoryValueLabel.setText("Category: " + selectedValue);
    }

    @FXML
    public void handleHeavyClick() {
        selectedValue = "Heavily Used";
        selectedCondition = "Heavily Used";
        conditionValueLabel.setText("Condition: " + selectedValue);
    }

    @FXML
    public void handleModerateClick() {
        selectedValue = "Moderately Used";
        selectedCondition = "Moderately Used";
        conditionValueLabel.setText("Condition: " + selectedValue);
    }

    @FXML
    public void handleNewClick() {
        selectedValue = "Like New";
        selectedCondition = "Like New";
        conditionValueLabel.setText("Condition: " + selectedValue);
    }
    @FXML
    public void handleOriginalPriceSubmit() {
        try {
            originalPrice = Double.parseDouble(originalPriceInput.getText());
            oPriceValueLabel.setText("Original Price: $" + originalPrice);
            calculateOfferedPrice();
        } catch (NumberFormatException e) {
            oPriceValueLabel.setText("Invalid Price");
        }
    }
    private void calculateOfferedPrice() {
        if (originalPrice <= 0) {
            offeredValueLabel.setText("Offered Price: N/A");
            return;
        }

        double reductionFactor = 1.0;

        switch (selectedCategory) {
            case "English":
                reductionFactor -= 0.1;
                break;
            case "Math":
                reductionFactor -= 0.15;
                break;
            case "Natural Science":
                reductionFactor -= 0.2;
                break;
            case "Computer":
                reductionFactor -= 0.25;
                break;
            case "Other":
                reductionFactor -= 0.05;
                break;
        }

        switch (selectedCondition) {
            case "Heavily Used":
                reductionFactor -= 0.3;
                break;
            case "Moderately Used":
                reductionFactor -= 0.2;
                break;
            case "Like New":
                reductionFactor -= 0.1;
                break;
        }


        reductionFactor = Math.max(reductionFactor, 0.1);

        double offeredPrice = originalPrice * reductionFactor;
        offeredValueLabel.setText(String.format("$%.2f", offeredPrice));
    }




}
package Bookstore.components;

import javafx.scene.control.Button;

public class SellerHeaderButton extends Button {

    static String BASE_STYLE ="-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 10; " +
            "-fx-border-radius: 10; " +
            "-fx-border-width: 2; " +
            "-fx-border-color: #FFC627; " +
            "-fx-padding: 10 20; " +
            "-fx-cursor: hand;";

    public SellerHeaderButton() {
        super();
        initializeStyles();
    }


    private void initializeStyles() {

        this.setStyle(
           BASE_STYLE + "-fx-background-color: #8C1D40; "
        );

        // Add hover effect
        this.setOnMouseEntered(event -> this.setStyle(
          BASE_STYLE + "-fx-background-color: #ac3356; "

        ));

        this.setOnMouseExited(event -> initializeStyles());
    }
}
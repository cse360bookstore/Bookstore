package com.example.sellingprocess;

import javafx.animation.FillTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;

public class SellingHeader extends VBox {
    @FXML
    private Rectangle flipper;

    private boolean state = false;

    public SellingHeader() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "SellingHeader.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setupElements();
    }

    private void setupElements() {
        flipper.onMouseClickedProperty().set(event -> switchState());
    }

    private void switchState(){
        FillTransition ft;

        if (state){
            ft = new FillTransition(Duration.millis(1000), flipper, Color.RED, Color.BLUE);
            state = false;
        } else {
            ft = new FillTransition(Duration.millis(1000), flipper, Color.BLUE, Color.RED);
            state = true;
        }

        ft.setCycleCount(1);
        ft.play();
    }
}
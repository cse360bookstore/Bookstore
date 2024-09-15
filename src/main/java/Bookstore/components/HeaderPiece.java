package Bookstore.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class HeaderPiece extends HBox {
    @FXML
    private Text headerText;

    public HeaderPiece() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bookstore/components/HeaderPiece.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public enum HeaderType{
        START,
        MIDDLE,
        END,
    }
    public enum ColorType{
        ACTIVE,
        INACTIVE,
        PASSED,
    }

    private String message;
    private HeaderType type;
    private String Color;
    private ColorType colorType;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        headerText.setText(message);

    }

    public HeaderType getType() {
        return type;
    }

    public void setType(HeaderType type) {
        this.type = type;

    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        this.Color = color;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public void setColorType(ColorType colorType) {
        this.colorType = colorType;

        String css = "-fx-background-color: " + (switch (colorType) {
            case PASSED -> "#8C1D40";
            case INACTIVE -> "#D9D9D9";
            case ACTIVE -> "#FFC627";
        }) + ";";
        this.setStyle(css);

    }

}

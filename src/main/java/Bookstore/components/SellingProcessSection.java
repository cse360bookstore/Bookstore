package Bookstore.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class SellingProcessSection extends VBox {
    @FXML
    private Text sectionTitle;

    @FXML
    private HBox sectionContent;


    public SellingProcessSection() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bookstore/components/SellingProcessSection.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTitle(String title) {
        sectionTitle.setText(title);
    }

    public void setContent(HBox content) {
        sectionContent.getChildren().clear();
        sectionContent.getChildren().addAll(content.getChildren());
        sectionContent.setSpacing(10);
        sectionContent.setAlignment(Pos.CENTER);
    }
}
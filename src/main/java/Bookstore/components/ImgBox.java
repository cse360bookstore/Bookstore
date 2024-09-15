package Bookstore.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ImgBox extends VBox {
    @FXML
    private Text ImgBoxText;
    private ImgBoxClickListener clickListener;
    private String text;
    private String src;

    @FXML
    private ImageView ImgBoxImage;
    public ImgBox()  {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Bookstore/components/ImgBox.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Text getImgBoxText() {
        return ImgBoxText;
    }

    public void setImgBoxText(String imgBoxText) {
        this.text = imgBoxText;
        this.ImgBoxText.setText(imgBoxText);
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
        System.out.println(src);
        Image image = new Image(getClass().getResource(src).toExternalForm());
        this.ImgBoxImage.setImage(image);


    }

    public ImageView getImgBoxImage() {
        return ImgBoxImage;
    }

    public void setImgBoxImage(ImageView imgBoxImage) {
        this.ImgBoxImage = imgBoxImage;
        Image image = new Image(src);
    }
    @FXML
    public void handleClick() {
        if (clickListener != null) {
            clickListener.onImgBoxClick(ImgBoxText.getText());
        }
        System.out.println("Clicked: " + ImgBoxText.getText());
    }

    public void setClickListener(ImgBoxClickListener listener) {
        this.clickListener = listener;
    }

    // Define the interface to be used for click events
    public interface ImgBoxClickListener {
        void onImgBoxClick(String text); // Method to be implemented by the listener
    }
}


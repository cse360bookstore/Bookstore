package Bookstore.scenes.seller;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.AlertHelper;
import Bookstore.components.HeaderPiece;
import Bookstore.components.SellingProcessSection;
import Bookstore.components.ImgBox;
import Bookstore.dataManagers.BookManager;
import Bookstore.models.Book;
import Bookstore.models.UserSession;
import Bookstore.scenes.MainMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SellingProcess implements Initializable {
    DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
    private final BookManager bookManager = new BookManager(dataSource);
    private List<ImgBox> ConditionImgBoxes = new ArrayList<>();
    private List<ImgBox> CategoryImgBoxes = new ArrayList<>();
    private final String IMGBOX_BASE = "-fx-border-radius: 10px; -fx-border-width: 4;  -fx-background-radius: 10;";


    // Make the header maroon as you complete steps
    private List<HeaderPiece> passedHeaderPieces = new ArrayList<>();

    private final String imgPrefix = "/Bookstore/images/";

    @FXML
    private VBox contentArea;

    @FXML
    VBox container;
    @FXML
    HBox container2;
    private HeaderPiece activeHeader;

    private String selectedTitle = "";
    private String selectedDescription = "";
    private String selectedAuthor = "";

    private String selectedCategory = "";
    private String selectedCondition = "";
    private double originalPrice = 0.0;
    private double offeredPrice = 0.0;

    private HeaderPiece CategoryHeader = new HeaderPiece();
    private HeaderPiece ConditionHeader = new HeaderPiece();
    private HeaderPiece PriceHeader = new HeaderPiece();
    private HeaderPiece BookDetailsHeader = new HeaderPiece(); // New Header
    private HeaderPiece ConfirmHeader = new HeaderPiece();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Headers
        CategoryHeader.setColorType(HeaderPiece.ColorType.ACTIVE);
        CategoryHeader.setMessage("Category");

        ConditionHeader.setColorType(HeaderPiece.ColorType.INACTIVE);
        ConditionHeader.setMessage("Condition");

        PriceHeader.setColorType(HeaderPiece.ColorType.INACTIVE);
        PriceHeader.setMessage("Price");

        BookDetailsHeader.setColorType(HeaderPiece.ColorType.INACTIVE);
        BookDetailsHeader.setMessage("Book Details");

        ConfirmHeader.setColorType(HeaderPiece.ColorType.INACTIVE);
        ConfirmHeader.setMessage("Confirm");

        // Set Header Click Actions
        CategoryHeader.setOnMouseClicked(event -> switchPage(CategoryHeader, "CategoryPage"));
        ConditionHeader.setOnMouseClicked(event -> switchPage(ConditionHeader, "ConditionPage"));
        PriceHeader.setOnMouseClicked(event -> switchPage(PriceHeader, "PricePage"));
        BookDetailsHeader.setOnMouseClicked(event -> switchPage(BookDetailsHeader, "BookDetailsPage"));
        ConfirmHeader.setOnMouseClicked(event -> switchPage(ConfirmHeader, "ConfirmPage"));

        container2.getChildren().add(CategoryHeader);
        container2.getChildren().add(ConditionHeader);
        container2.getChildren().add(PriceHeader);
        container2.getChildren().add(BookDetailsHeader);
        container2.getChildren().add(ConfirmHeader);

        switchPage(CategoryHeader, "CategoryPage");
    }

    private SellingProcessSection createCategoryContent() {
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("What is the category of your book?");
        HBox container = new HBox();

        var DarwinBox = new ImgBox();
        DarwinBox.setSrc(imgPrefix + "sparky/Darwin.png");
        DarwinBox.setImgBoxText("Natural Science");
        DarwinBox.setClickListener(category -> updateCategoryField(category, DarwinBox));
        CategoryImgBoxes.add(DarwinBox);

        var TuringBox = new ImgBox();
        TuringBox.setSrc(imgPrefix + "sparky/Turing.png");
        TuringBox.setImgBoxText("Computer Science");
        TuringBox.setClickListener(category -> updateCategoryField(category, TuringBox));
        CategoryImgBoxes.add(TuringBox);

        var ShakespeareBox = new ImgBox();
        ShakespeareBox.setSrc(imgPrefix + "sparky/shakespear.png");
        ShakespeareBox.setImgBoxText("English Language");
        ShakespeareBox.setClickListener(category -> updateCategoryField(category, ShakespeareBox));
        CategoryImgBoxes.add(ShakespeareBox);


        var EinsteinBox = new ImgBox();
        EinsteinBox.setSrc(imgPrefix + "sparky/Einstein.png");
        EinsteinBox.setImgBoxText("Science");
        EinsteinBox.setClickListener(category -> updateCategoryField(category, EinsteinBox));
        CategoryImgBoxes.add(EinsteinBox);

        var OtherBox = new ImgBox();
        OtherBox.setSrc(imgPrefix + "sparky/sparkay.png");
        OtherBox.setImgBoxText("Other");
        OtherBox.setClickListener(category -> updateCategoryField(category, OtherBox));
        CategoryImgBoxes.add(OtherBox);

        // if the selected category is passed in then they are navigating back to the page
        for (ImgBox imgBox : CategoryImgBoxes) {
            String imgBoxText = imgBox.getImgBoxText().getText();
            if (imgBoxText.equals(selectedCategory)) {
                imgBox.setStyle(IMGBOX_BASE + "-fx-background-color: #ac3356; -fx-border-color: #FFC627;");
                imgBox.getImgBoxText().setStyle("-fx-fill: white;");
            } else {
                imgBox.setStyle(IMGBOX_BASE);
                imgBox.getImgBoxText().setStyle("-fx-fill: black;");
            }
        }

        container.getChildren().addAll(DarwinBox, TuringBox, ShakespeareBox, EinsteinBox, OtherBox);

        contentSection.setContent(container);
        return contentSection;
    }

    private SellingProcessSection createConditionContent() {
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("What is the condition of your book?");
        HBox container = new HBox();

        var LikeNewBox = new ImgBox();
        LikeNewBox.setSrc(imgPrefix + "pitchfork/LikeNew.png");
        LikeNewBox.setImgBoxText("Used Like New");
        LikeNewBox.setClickListener(condition -> updateConditionField(condition, LikeNewBox));
        ConditionImgBoxes.add(LikeNewBox);


        var ModerateUseBox = new ImgBox();
        ModerateUseBox.setSrc(imgPrefix + "pitchfork/ModerateUse.png");
        ModerateUseBox.setImgBoxText("Moderately Used");
        ModerateUseBox.setClickListener(condition -> updateConditionField(condition, ModerateUseBox));
        ConditionImgBoxes.add(ModerateUseBox);

        var HeavyUseBox = new ImgBox();
        HeavyUseBox.setSrc(imgPrefix + "pitchfork/HeavilyUsed.png");
        HeavyUseBox.setImgBoxText("Heavily Used");
        HeavyUseBox.setClickListener(condition -> updateConditionField(condition, HeavyUseBox));
        ConditionImgBoxes.add(HeavyUseBox);

        for (ImgBox imgBox : ConditionImgBoxes) {
            String imgBoxText = imgBox.getImgBoxText().getText();
            if (imgBoxText.equals(selectedCondition)) {
                imgBox.setStyle(IMGBOX_BASE + "-fx-background-color: #ac3356; -fx-border-color: #FFC627;");
                imgBox.getImgBoxText().setStyle("-fx-fill: white;");
            } else {
                imgBox.setStyle(IMGBOX_BASE);
                imgBox.getImgBoxText().setStyle("-fx-fill: black;");
            }
        }

        container.getChildren().addAll(LikeNewBox, ModerateUseBox, HeavyUseBox);

        contentSection.setContent(container);
        return contentSection;
    }

    private SellingProcessSection createPriceContent() {
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("What was the price of your book new?");
        HBox container = new HBox();
        container.setSpacing(10);

        Label priceLabel = new Label("New Price: ");
        TextField priceInput = new TextField();
        priceInput.setPromptText("Enter price in USD");

        container.getChildren().addAll(priceLabel, priceInput);

        contentSection.setContent(container);

        priceInput.setOnAction(event -> {
            String price = priceInput.getText();
            try {
                this.originalPrice = Double.parseDouble(price);
                calculateOfferedPrice();

            } catch (NumberFormatException e) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Validation Error:", "Invalid price entered");
            }
        });

        return contentSection;
    }

    private SellingProcessSection createBookDetailsContent() {
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("Enter Book Details");

        GridPane container = new GridPane();
        container.setHgap(10);
        container.setVgap(10);
        container.setPadding(new Insets(10));

        Label titleLabel = new Label("Title:");
        TextField titleInput = new TextField();
        titleInput.setPromptText("Enter book title");

        Label descriptionLabel = new Label("Description:");
        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("Enter book description");

        Label authorLabel = new Label("Author:");
        TextField authorInput = new TextField();
        authorInput.setPromptText("Enter author's name");

        container.add(titleLabel, 0, 0);
        container.add(titleInput, 1, 0);
        container.add(descriptionLabel, 0, 1);
        container.add(descriptionInput, 1, 1);
        container.add(authorLabel, 0, 2);
        container.add(authorInput, 1, 2);

        Button submitButton = new Button("Submit");
        container.add(submitButton, 1, 3);

        submitButton.setOnAction(event -> {
            boolean valid = true;
            StringBuilder errorMessage = new StringBuilder();

            String title = titleInput.getText().trim();
            if (title.isEmpty()) {
                valid = false;
                errorMessage.append("- Title cannot be empty.\n");
            } else {
                selectedTitle = title;
            }

            String description = descriptionInput.getText().trim();
            if (description.isEmpty()) {
                valid = false;
                errorMessage.append("- Description cannot be empty.\n");
            } else {
                selectedDescription = description;
            }

            String author = authorInput.getText().trim();
            if (author.isEmpty()) {
                valid = false;
                errorMessage.append("- Author cannot be empty.\n");
            } else {
                selectedAuthor = author;
            }

            if (!valid) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Validation Error:", errorMessage.toString());
            }
            else {
                if (!passedHeaderPieces.contains(BookDetailsHeader)) {
                    BookDetailsHeader.setColorType(HeaderPiece.ColorType.PASSED);
                    passedHeaderPieces.add(BookDetailsHeader);
                }
            }
        });

        HBox outerContainer = new HBox(container);
        outerContainer.setAlignment(Pos.CENTER);
        contentSection.setContent(outerContainer);

        return contentSection;
    }

    private SellingProcessSection createConfirmContent() {
        calculateOfferedPrice();
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("Based on the category,\ncondition, and original price,\nWe can offer you:");


        HBox container = new HBox();
        container.setSpacing(10);

        Label offeredPriceLabel = new Label(String.format("$%.2f", offeredPrice));
        offeredPriceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");


        Button listBookButton = new Button("List My Book");
        listBookButton.setOnAction(event -> {
            try {
                listMyBook();
            } catch (SQLException e) {
                e.printStackTrace();
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Database Error:", "Error listing book " + e.getMessage());
            }
        });

        container.getChildren().addAll(offeredPriceLabel, listBookButton);

        contentSection.setContent(container);
        return contentSection;
    }

    private void switchPage(HeaderPiece clickedHeader, String page) {
        if (activeHeader != null) {
            activeHeader.setColorType(HeaderPiece.ColorType.INACTIVE);
        }
        for (HeaderPiece header : passedHeaderPieces) {
            header.setColorType(HeaderPiece.ColorType.PASSED);
        }
        clickedHeader.setColorType(HeaderPiece.ColorType.ACTIVE);
        activeHeader = clickedHeader;

        loadPage(page);
    }

    private void loadPage(String pageName) {
        contentArea.getChildren().clear();
        switch (pageName) {
            case "CategoryPage":
                contentArea.getChildren().add(createCategoryContent());
                break;
            case "ConditionPage":
                contentArea.getChildren().add(createConditionContent());
                break;
            case "PricePage":
                contentArea.getChildren().add(createPriceContent());
                break;
            case "ConfirmPage":
                contentArea.getChildren().add(createConfirmContent());
                break;
            case "BookDetailsPage":
                contentArea.getChildren().add(createBookDetailsContent());
                break;
        }
    }

    private void updateCategoryField(String category, ImgBox clickedImgBox) {
        this.selectedCategory = category;
        resetImgBoxColors(CategoryImgBoxes);

        clickedImgBox.setStyle(IMGBOX_BASE + "-fx-background-color: #ac3356; -fx-border-color: #FFC627;");
        clickedImgBox.getImgBoxText().setStyle("-fx-fill: white;");

        if (!passedHeaderPieces.contains(CategoryHeader)) {
            CategoryHeader.setColorType(HeaderPiece.ColorType.PASSED);
            passedHeaderPieces.add(CategoryHeader);
        }
    }

    private void updateConditionField(String condition, ImgBox clickedImgBox) {

        this.selectedCondition = condition;

        resetImgBoxColors(ConditionImgBoxes);

        clickedImgBox.setStyle(IMGBOX_BASE + "-fx-background-color: #ac3356; -fx-border-color: #FFC627;");

        if (!passedHeaderPieces.contains(ConditionHeader)) {
            ConditionHeader.setColorType(HeaderPiece.ColorType.PASSED);
            passedHeaderPieces.add(ConditionHeader);
        }
    }

    private void calculateOfferedPrice() {
        if (originalPrice <= 0) {

            return;
        }
        if (!passedHeaderPieces.contains(PriceHeader)) {
            PriceHeader.setColorType(HeaderPiece.ColorType.PASSED);
            passedHeaderPieces.add(PriceHeader);
        }

        double reductionFactor = 1.0;

        switch (selectedCategory) {
            case "English Language":
                reductionFactor -= 0.1;
                break;
            case "Computer Science":
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
            default:
                reductionFactor -= 0.1;
                break;
        }

        switch (selectedCondition) {
            case "Heavily Used":
                reductionFactor -= 0.3;
                break;
            case "Moderately Used":
                reductionFactor -= 0.2;
                break;
            case "Used Like New":
                reductionFactor -= 0.1;
                break;
            default:
                reductionFactor -= 0.1;
                break;
        }

        reductionFactor = Math.max(reductionFactor, 0.1);


        this.offeredPrice = originalPrice * reductionFactor;
    }


    public void listMyBook() throws SQLException {

        Book myBook = new Book(selectedTitle, selectedAuthor, selectedDescription, selectedCategory, selectedCondition, originalPrice, offeredPrice);

        UserSession session = UserSession.getInstance();
        bookManager.insertBook(myBook, session.getUserId());

        try {
            goToMainMenu();
        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the Main Menu.");
        }
    }
    private void resetImgBoxColors(List<ImgBox> boxes) {
        for (ImgBox imgBox : boxes) {
            imgBox.setStyle(IMGBOX_BASE);
            imgBox.getImgBoxText().setStyle("-fx-fill: black;");
        }
    }

    private void goToMainMenu() throws IOException {
        Stage stage = (Stage) contentArea.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/Bookstore/scenes/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

}


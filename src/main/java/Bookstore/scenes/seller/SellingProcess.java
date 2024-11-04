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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SellingProcess implements Initializable {
    DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
    private final BookManager bookManager = new BookManager(dataSource);


    @FXML
    private Label selectedCategoryLabel;
    @FXML
    private Label selectedConditionLabel;
    @FXML
    private Label selectedPriceLabel;
    @FXML
    private Label offeredPriceLabel;
    @FXML
    private Label bookTitleLabel;
    @FXML
    private Label bookDescriptionLabel;
    @FXML
    private Label bookAuthorLabel;

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
    }

    private SellingProcessSection createCategoryContent() {
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("What is the category of your book?");
        HBox container = new HBox();

        var imgbox = new ImgBox();
        imgbox.setSrc(imgPrefix + "sparky/Darwin.png");
        imgbox.setImgBoxText("Natural Science");
        imgbox.setClickListener(this::updateCategoryField);

        var imgbox2 = new ImgBox();
        imgbox2.setSrc(imgPrefix + "sparky/Turing.png");
        imgbox2.setImgBoxText("Computer Science");
        imgbox2.setClickListener(this::updateCategoryField);

        var imgbox3 = new ImgBox();
        imgbox3.setSrc(imgPrefix + "sparky/shakespear.png");
        imgbox3.setImgBoxText("English Language");
        imgbox3.setClickListener(this::updateCategoryField);

        var imgbox4 = new ImgBox();
        imgbox4.setSrc(imgPrefix + "sparky/Einstein.png");
        imgbox4.setImgBoxText("Science");
        imgbox4.setClickListener(this::updateCategoryField);

        var imgbox5 = new ImgBox();
        imgbox5.setSrc(imgPrefix + "sparky/sparkay.png");
        imgbox5.setImgBoxText("Other");
        imgbox5.setClickListener(this::updateCategoryField);

        container.getChildren().addAll(imgbox, imgbox2, imgbox3, imgbox4, imgbox5);

        contentSection.setContent(container);
        return contentSection;
    }

    private SellingProcessSection createConditionContent() {
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("What is the condition of your book?");
        HBox container = new HBox();

        var imgbox = new ImgBox();
        imgbox.setSrc(imgPrefix + "pitchfork/LikeNew.png");
        imgbox.setImgBoxText("Used Like New");
        imgbox.setClickListener(this::updateConditionField);

        var imgbox2 = new ImgBox();
        imgbox2.setSrc(imgPrefix + "pitchfork/ModerateUse.png");
        imgbox2.setImgBoxText("Moderately Used");
        imgbox2.setClickListener(this::updateConditionField);

        var imgbox3 = new ImgBox();
        imgbox3.setSrc(imgPrefix + "pitchfork/HeavilyUsed.png");
        imgbox3.setImgBoxText("Heavily Used");
        imgbox3.setClickListener(this::updateConditionField);

        container.getChildren().addAll(imgbox, imgbox2, imgbox3);

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
                this.selectedPriceLabel.setText("Inputted Price: $" + price);
                calculateOfferedPrice();

            } catch (NumberFormatException e) {

            }
        });

        return contentSection;
    }

    private SellingProcessSection createBookDetailsContent() {
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("Enter Book Details");

        VBox container = new VBox();
        container.setSpacing(10);

        HBox titleBox = new HBox();
        Label titleLabel = new Label("Title: ");
        TextField titleInput = new TextField();
        titleInput.setPromptText("Enter book title");
        titleBox.getChildren().addAll(titleLabel, titleInput);

        HBox descriptionBox = new HBox();
        Label descriptionLabel = new Label("Description: ");
        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("Enter book description");
        descriptionBox.getChildren().addAll(descriptionLabel, descriptionInput);

        HBox authorBox = new HBox();
        Label authorLabel = new Label("Author: ");
        TextField authorInput = new TextField();
        authorInput.setPromptText("Enter author's name");
        authorBox.getChildren().addAll(authorLabel, authorInput);

        container.getChildren().addAll(titleBox, descriptionBox, authorBox);

        HBox outerContainer = new HBox(container);
        contentSection.setContent(outerContainer);

        titleInput.setOnAction(event -> {
            String title = titleInput.getText().trim();
            selectedTitle =title;
            if (!title.isEmpty()) {
                this.bookTitleLabel.setText("Title: " + title);
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Validation Error:", "Title Cannot be empty.");
            }
        });

        descriptionInput.setOnAction(event -> {
            String description = descriptionInput.getText().trim();
            selectedDescription =description;
            if (!description.isEmpty()) {
                this.bookDescriptionLabel.setText("Description: " + description);
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Validation Error:", "Description cannot be empty.");
            }
        });

        authorInput.setOnAction(event -> {
            String author = authorInput.getText().trim();
            selectedAuthor =author;
            if (!author.isEmpty()) {
                this.bookAuthorLabel.setText("Author: " + author);
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Validation Error:", "Author cannot be empty.");
            }
        });

        return contentSection;
    }

    private SellingProcessSection createConfirmContent() {
        calculateOfferedPrice();
        SellingProcessSection contentSection = new SellingProcessSection();
        contentSection.setTitle("Based on the category,\ncondition, and original price,\nWe can offer you:");

        HBox container = new HBox();
        container.setSpacing(10);

        Label priceLabel = new Label(this.offeredPriceLabel.getText());
        Button listBookButton = new Button("List My Book");
        listBookButton.setOnAction(event -> {
            try {
                listMyBook();
            } catch (SQLException e) {
                e.printStackTrace();
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Database Error:", "Error listing book");
            }
        });

        container.getChildren().addAll(priceLabel,listBookButton);

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
        System.out.println("Loading page: " + pageName);
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

    private void updateCategoryField(String category) {
        selectedCategoryLabel.setText("Selected Category: " + category);
        this.selectedCategory = category;
        if (!passedHeaderPieces.contains(CategoryHeader)) {
            CategoryHeader.setColorType(HeaderPiece.ColorType.PASSED);
            passedHeaderPieces.add(CategoryHeader);
        }
    }

    private void updateConditionField(String condition) {
        selectedConditionLabel.setText("Selected Condition: " + condition);
        this.selectedCondition = condition;
        if (!passedHeaderPieces.contains(ConditionHeader)) {
            ConditionHeader.setColorType(HeaderPiece.ColorType.PASSED);
            passedHeaderPieces.add(ConditionHeader);
        }
    }

    private void calculateOfferedPrice() {
        if (originalPrice <= 0) {
            offeredPriceLabel.setText("Offered Price: N/A");
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

        double offeredPrice2 = originalPrice * reductionFactor;
        offeredPriceLabel.setText(String.format("Offered Price: $%.2f", offeredPrice2));

        this.offeredPrice = offeredPrice2;
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

    private void goToMainMenu() throws IOException {
        Stage stage = (Stage) contentArea.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/Bookstore/scenes/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

}


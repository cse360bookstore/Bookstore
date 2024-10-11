package Bookstore.scenes.seller;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.HeaderPiece;
import Bookstore.components.SellingProcessSection;
import Bookstore.components.ImgBox;
import Bookstore.datamodels.BookDAO;
import Bookstore.models.Book;
import Bookstore.scenes.MainMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
    private final BookDAO bookDAO = new BookDAO(dataSource);

    @FXML
    private Label welcomeText;
    @FXML
    private Label selectedCategoryLabel;
    @FXML
    private Label selectedConditionLabel;
    @FXML
    private Label selectedPriceLabel;
    @FXML
    private Label offeredPriceLabel;

    private List<HeaderPiece> passedHeaderPieces = new ArrayList<>();
    private final String imgPrefix = "/Bookstore/images/";

    @FXML
    private VBox contentArea;

    @FXML
    VBox container;
    @FXML
    HBox container2;
    @FXML
    HBox container3;
    private HeaderPiece activeHeader;
    private String selectedCategory = "";
    private String selectedCondition = "";
    private double originalPrice = 0.0;
    private double offeredPrice = 0.0;

    private HeaderPiece CategoryHeader = new HeaderPiece();
    private HeaderPiece ConditionHeader = new HeaderPiece();
    private HeaderPiece PriceHeader = new HeaderPiece();

    private HeaderPiece ConfirmHeader = new HeaderPiece();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CategoryHeader.setColorType(HeaderPiece.ColorType.ACTIVE);
        CategoryHeader.setMessage("Category");

        ConditionHeader.setColorType(HeaderPiece.ColorType.INACTIVE);
        ConditionHeader.setMessage("Condition");

        PriceHeader.setColorType(HeaderPiece.ColorType.INACTIVE);
        PriceHeader.setMessage("Price");

        ConfirmHeader.setColorType(HeaderPiece.ColorType.INACTIVE);
        ConfirmHeader.setMessage("Confirm");

        CategoryHeader.setOnMouseClicked(event -> switchPage(CategoryHeader, "CategoryPage"));
        ConditionHeader.setOnMouseClicked(event -> switchPage(ConditionHeader, "ConditionPage"));
        PriceHeader.setOnMouseClicked(event -> switchPage(PriceHeader, "PricePage"));
        ConfirmHeader.setOnMouseClicked(event -> switchPage(ConfirmHeader, "ConfirmPage"));

        container2.getChildren().add(CategoryHeader);
        container2.getChildren().add(ConditionHeader);
        container2.getChildren().add(PriceHeader);
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

        container.getChildren().add(imgbox);
        container.getChildren().add(imgbox2);
        container.getChildren().add(imgbox3);
        container.getChildren().add(imgbox4);
        container.getChildren().add(imgbox5);

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

        container.getChildren().add(imgbox);
        container.getChildren().add(imgbox2);
        container.getChildren().add(imgbox3);


        contentSection.setContent(container);
        return contentSection;
    }

    private SellingProcessSection createPriceContent() {
        // Create a new BuyingProcessSection
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
            System.out.println("New price entered: $" + price);
            this.originalPrice = Double.parseDouble(price);
            this.selectedPriceLabel.setText("Inputted Price:" + price);

        });

        return contentSection;
    }
    private SellingProcessSection creatConfirmContent() {
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
            }
        });

        container.getChildren().addAll(priceLabel, listBookButton);

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
                contentArea.getChildren().add(creatConfirmContent());
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

        double offeredPrice2 = originalPrice * reductionFactor;
        offeredPriceLabel.setText(String.format("$%.2f", offeredPrice2));
        this.offeredPrice = offeredPrice2;


    }
    public void listMyBook() throws SQLException {
            Book myBook = new Book("default", "default", 0, 0, "dummy");
            myBook.setCategory(selectedCategory);
            myBook.setCondition(selectedCondition);
            myBook.setOriginalPrice(originalPrice);
            myBook.setNewPrice(offeredPrice);
            myBook.setListedBy(username);
            System.out.println("Listing My Book");
            System.out.println(username);
            List<Book> books =  bookDAO.getAllBooks();
            System.out.println("Before Inserting");
            for (Book book : books) {
                System.out.println(book);
            }
            bookDAO.insertBook(myBook);
            books =  bookDAO.getAllBooks();
            System.out.println("After Inserting");
            for (Book book : books) {
                System.out.println(book);
            }
            try {
                goToMainMenu();
            }
            catch (IOException e) {
                e.printStackTrace();



            }

    }
    private void goToMainMenu() throws IOException {
        // Get the current stage
        Stage stage = (Stage) contentArea.getScene().getWindow();

        // Load the Main Menu FXML
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("/Bookstore/scenes/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Set the scene and show the stage
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

}


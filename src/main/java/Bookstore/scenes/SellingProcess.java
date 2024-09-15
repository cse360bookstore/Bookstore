package Bookstore.scenes;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.components.HeaderPiece;
import Bookstore.components.SellingProcessSection;
import Bookstore.components.ImgBox;
import Bookstore.datamodels.BookDAO;
import Bookstore.models.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SellingProcess implements Initializable {
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


    private String imgPrefix = "/Bookstore/images/";


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        var header = new HeaderPiece();
        header.setColorType(HeaderPiece.ColorType.ACTIVE);
        header.setMessage("Category");
        var header2 = new HeaderPiece();
        header2.setColorType(HeaderPiece.ColorType.INACTIVE);
        header2.setMessage("Condition");

        var header3 = new HeaderPiece();
        header3.setColorType(HeaderPiece.ColorType.INACTIVE);
        header3.setMessage("Price");
        var header4 = new HeaderPiece();
        header4.setColorType(HeaderPiece.ColorType.INACTIVE);
        header4.setMessage("Confirm");


        header.setOnMouseClicked(event -> switchPage(header, "CategoryPage"));
        header2.setOnMouseClicked(event -> switchPage(header2, "ConditionPage"));
        header3.setOnMouseClicked(event -> switchPage(header3, "PricePage"));
        header4.setOnMouseClicked(event -> switchPage(header4, "ConfirmPage"));

        container2.getChildren().add(header);
        container2.getChildren().add(header2);
        container2.getChildren().add(header3);
        container2.getChildren().add(header4);


    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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

    }

    private void updateConditionField(String condition) {
        selectedConditionLabel.setText("Selected Condition: " + condition);
        this.selectedCondition = condition;

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
        Book myBook = new Book("default", "default", 0, 0);
        myBook.setCategory(selectedCategory);
        myBook.setCondition(selectedCondition);
        myBook.setOriginalPrice(originalPrice);
        myBook.setNewPrice(offeredPrice);
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


    }

}


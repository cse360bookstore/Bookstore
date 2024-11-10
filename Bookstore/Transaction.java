package application;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Transaction {
	
	private StringProperty sellerName;
	private StringProperty buyerName;
	private DoubleProperty soldPrice;
	private StringProperty category;
	private StringProperty bookTitle;
	private StringProperty bookCondition;
	private ObjectProperty<LocalDateTime> soldDate;
	
	public Transaction(String sellerName, String buyerName, double soldPrice, String category, String bookTitle, String bookCondition, LocalDateTime soldDate) {
		this.sellerName = new SimpleStringProperty(sellerName);
		this.buyerName = new SimpleStringProperty(buyerName);
		this.soldPrice = new SimpleDoubleProperty(soldPrice);
		this.category = new SimpleStringProperty(category);
		this.bookTitle = new SimpleStringProperty(bookTitle);
		this.bookCondition = new SimpleStringProperty(bookCondition);
		this.soldDate = new SimpleObjectProperty<>(soldDate);
	}
	
	// getters and setters 
	public StringProperty sellerNameProperty() { return sellerName; }
	public void setSellerName(String sellerName) { this.sellerName.set(sellerName); }
	public String getSellerName() { return sellerName.get(); }
	
	public StringProperty buyerNameProperty() { return buyerName; }
	public void setBuyerName(String buyerName) { this.buyerName.set(buyerName); }
	public String getBuyerName() { return buyerName.get(); }
	
	public DoubleProperty soldPriceProperty() { return soldPrice; }
	public void setSoldPrice(double soldPrice) { this.soldPrice.set(soldPrice); }
	public double getSoldPrice() { return soldPrice.get(); }
	
	public StringProperty categoryProperty() { return category; }
	public void setCategory(String category) { this.category.set(category); }
	public String getCategory() { return category.get(); }
	
	public StringProperty bookTitleProperty() { return bookTitle; }
	public void setBookTitle(String bookTitle) { this.bookTitle.get(); }
	public String getBookTitle() { return bookTitle.get(); }
		
	public StringProperty bookConditionProperty() { return bookCondition; }
	public void setBookCondition(String bookCondition) { this.bookCondition.set(bookCondition); }
	public String getBookCondition() { return bookCondition.get(); }
	
	public ObjectProperty<LocalDateTime> soldDateProperty(){ return soldDate; }
	public void setSoldDate(LocalDateTime soldDate) { this.soldDate.set(soldDate); }
	public LocalDateTime getSoldDate() { return soldDate.get(); }
	
	
	
}

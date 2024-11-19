package Bookstore.models;

import java.time.LocalDateTime;

public class Transaction {
    private String sellerName;
    private String buyerName;
    private double soldPrice;
    private String category;
    private String bookTitle;
    private LocalDateTime soldDate;
    private int count;

    // Constructor
    public Transaction(String sellerName, String buyerName, double soldPrice,
                       String category, String bookTitle, LocalDateTime soldDate) {
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.soldPrice = soldPrice;
        this.category = category;
        this.bookTitle = bookTitle;
        this.soldDate = soldDate;
    }
    
    // Constructor for category and count
    public Transaction(String category, int count) {
    	this.category = category;
    	this.count = count;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDateTime getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(LocalDateTime soldDate) {
        this.soldDate = soldDate;
    }
    
    public int getCount() {
    	return count;
    }
    
    public void setCount(int count) {
    	this.count = count;
    }

    // toString Method for Debugging
    @Override
    public String toString() {
        return "Transaction{" +
                ", sellerName='" + sellerName + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", soldPrice=" + soldPrice +
                ", category='" + category + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", soldDate=" + soldDate + " '}'" +
        		", count=" + count +
        		"}";
    }
}
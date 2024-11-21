package Bookstore.models;

import java.time.LocalDateTime;

public class BookForSale {
    private int bookID;
    private String title;
    private String category;
    private String bookCondition;
    private String author;
    private String description;
    private double price;
    private int listedByUserID;
    private LocalDateTime listedAt;
    private Status status;

    public enum Status {
        forSale,
        Sold
    }

    public BookForSale(int bookID, String title, String category, String bookCondition,
                       String author, String description, double price, int listedByUserID,
                       LocalDateTime listedAt, Status status) {
        this.bookID = bookID;
        this.title = title;
        this.category = category;
        this.bookCondition = bookCondition;
        this.author = author;
        this.description = description;
        this.price = price;
        this.listedByUserID = listedByUserID;
        this.listedAt = listedAt;
        this.status = status;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getListedByUserID() {
        return listedByUserID;
    }

    public void setListedByUserID(int listedByUserID) {
        this.listedByUserID = listedByUserID;
    }

    public LocalDateTime getListedAt() {
        return listedAt;
    }

    public void setListedAt(LocalDateTime listedAt) {
        this.listedAt = listedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // toString method
    @Override
    public String toString() {
        return "BookForSale{" +
                "bookID=" + bookID +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", bookCondition='" + bookCondition + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", listedByUserID=" + listedByUserID +
                ", listedAt=" + listedAt +
                ", status=" + status +
                '}';
    }
}
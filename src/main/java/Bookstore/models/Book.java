package Bookstore.models;

import jnr.posix.WString;

public class Book {
    private String Title;
    private String Author;
    private String Description;
    private String category;
    private String condition;
    private double originalPrice;
    private double newPrice;

    public Book() {}
    public Book(String title, String author, String description, String category, String condition, double originalPrice, double newPrice) {
        this.Title = title;
        this.Author = author;
        this.Description = description;
        this.category = category;
        this.condition = condition;
        this.originalPrice = originalPrice;
        this.newPrice = newPrice;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    @Override
    public String toString() {
        return "Book [category=" + category + ", condition=" + condition +
                ", originalPrice=" + originalPrice + ", newPrice=" + newPrice + ", listedBy="  + "]";
    }
}


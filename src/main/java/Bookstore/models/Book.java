package Bookstore.models;

import jnr.posix.WString;

public class Book {
    private String category;
    private String condition;
    private double originalPrice;
    private double newPrice;
    private String listedBy;

    public Book() {}
    public Book(String category, String condition, double originalPrice, double newPrice, String listedBy) {
        this.category = category;
        this.condition = condition;
        this.originalPrice = originalPrice;
        this.newPrice = newPrice;
        this.listedBy = listedBy;
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

    public String getListedBy() {return listedBy;}

    public void setListedBy(String listedBy) {this.listedBy = listedBy;}

    @Override
    public String toString() {
        return "Book [category=" + category + ", condition=" + condition +
                ", originalPrice=" + originalPrice + ", newPrice=" + newPrice + ", listedBy=" + listedBy + "]";
    }
}


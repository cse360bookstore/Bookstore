package Bookstore.models;

public class Transactions {
	private String sellerName;
	private String buyerName;
	private double soldPrice;
	private String category;
	private int transactionID;
	// In case we need to keep track of date.  
//	private Date transactionDate;
	private String itemName;

	// Constructor 
	public Transactions(String sellerName, String buyerName, double soldPrice, String category, int transactionID, String itemName) {
		this.sellerName = sellerName;
		this.buyerName = buyerName;
		this.soldPrice = soldPrice;
		this.category = category;
		this.transactionID = transactionID;
//		this.transactionDate = transactionDate;
		// Not sure if we're going to need to keep track of item name, remove if not
		this.itemName = itemName;
		
	}

	// Default Constructor 
	public Transactions() {}
	
	// Getters and Setters 
	public String getSellerName() {
		return sellerName;
	}
	
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
	public String getBuyName() {
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
	
	public int getTransaction() {
		return transactionID;
	}
	
	public void setTransaction(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public double calculateTotalWithTax(double taxRate) {
		return soldPrice + (soldPrice * taxRate);
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	// Placeholder methods for database functions 
	public void saveToDatabase() {
		
	}
	public static Transactions loadFromDatabase(int transactionID) {
		// Logic to load transaction details by ID 
	}

    @Override 
    public String toString(){
        return "Transaction [sellerName=" + sellerName + ", buyName=" + buyerName + 
        ", soldPrice=" + soldPrice + ", category=" + category + 
        ", transactionID=" + transactionID + ", itemName=" + itemName + "]";
      }
  }
	
	
	

}

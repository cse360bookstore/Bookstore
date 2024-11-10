package application;

import application.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class DatabaseHelper {
	private static final DataSource dataSource = SqlConnectionPoolFactory.createConnectionPool();
	
	// establish database connection
	public static Connection connect() {
		try {
			return dataSource.getConnection();
		}catch (SQLException e) {
			System.err.println("Failed to obtain a database connection.");
			e.printStackTrace();
			return null;
		}
	}
	
	// load transactions from the database 
	public static List<Transaction> loadTransactions(){
		List<Transaction> transactions = new ArrayList<>();
		String query = "SELECT sellerName, buyerName, soldPrice, category, bookTitle, bookCondition, soldDate FROM transactions";
		
		try (Connection conn = connect(); 
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)){
				
				while (rs.next()) {
					String sellerName = rs.getString("sellerName");
					String buyerName = rs.getString("buyerName");
					double soldPrice = rs.getDouble("soldPrice");
					String category = rs.getString("category");
					String bookTitle = rs.getString("bookTitle");
					String bookCondition = rs.getString("bookCondition");
					LocalDateTime soldDate = rs.getTimestamp("soldDate").toLocalDateTime();
					
					Transaction transaction = new Transaction(sellerName, buyerName, soldPrice, category, bookTitle, bookCondition, soldDate);
					transactions.add(transaction);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return transactions;
	}

}

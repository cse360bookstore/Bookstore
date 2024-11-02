package Bookstore.dataManagers;

import Bookstore.models.BookWithUser;
import Bookstore.models.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminManager {
    private final DataSource dataSource;

    public AdminManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ObservableList<Transaction> getAllTransactions() throws SQLException {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        String storedProcedure = "{CALL GetAllTransactions()}";

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {

            boolean hasResultSet = callableStatement.execute();

            if (hasResultSet) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        String sellerName = rs.getString("SellerName");
                        String buyerName = rs.getString("BuyerName");
                        double soldPrice = rs.getDouble("SoldPrice");
                        String category = rs.getString("Category");
                        String bookTitle = rs.getString("BookTitle");
                        Timestamp soldDateTimestamp = rs.getTimestamp("SoldDate");
                        LocalDateTime soldDate = soldDateTimestamp != null ? soldDateTimestamp.toLocalDateTime() : null;

                        Transaction transaction = new Transaction(
                                sellerName,
                                buyerName,
                                soldPrice,
                                category,
                                bookTitle,
                                soldDate
                        );

                        transactions.add(transaction);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
            throw e;
        }

        return transactions;
    }
}
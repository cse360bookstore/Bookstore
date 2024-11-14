package Bookstore.dataManagers;

import Bookstore.models.BookForSale;
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

    public BookForSale updateBook(BookForSale book) throws SQLException {
        String updateBookQuery = "UPDATE BooksForSale SET "
                + "title = ?, "
                + "category = ?, "
                + "BookCondition = ?, "
                + "author = ?, "
                + "description = ?, "
                + "price = ?, "
                + "listedByUserID = ?, "
                + "listedAt = ?, "
                + "Status = ? "
                + "WHERE bookID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(updateBookQuery)) {

            query.setString(1, book.getTitle());
            query.setString(2, book.getCategory());
            query.setString(3, book.getBookCondition());
            query.setString(4, book.getAuthor());
            query.setString(5, book.getDescription());
            query.setDouble(6, book.getPrice());
            query.setInt(7, book.getListedByUserID());
            query.setTimestamp(8, java.sql.Timestamp.valueOf(book.getListedAt())); // Convert LocalDateTime to Timestamp
            query.setString(9, book.getStatus().name()); // Enum to String
            query.setInt(10, book.getBookID());

            int rowsUpdated = query.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No book found with the given ID: " + book.getBookID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to update the book: " + e.getMessage());
        }
        return book;
    }

    public ObservableList<BookForSale> getAllBooksForSale() throws SQLException {
        ObservableList<BookForSale> booksForSale = FXCollections.observableArrayList();
        String query = "SELECT * FROM BooksForSale";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int bookID = resultSet.getInt("bookID");
                String title = resultSet.getString("title");
                String category = resultSet.getString("category");
                String bookCondition = resultSet.getString("BookCondition");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int listedByUserID = resultSet.getInt("listedByUserID");
                Timestamp listedAtTimestamp = resultSet.getTimestamp("listedAt");
                LocalDateTime listedAt = listedAtTimestamp != null ? listedAtTimestamp.toLocalDateTime() : null;
                BookForSale.Status status = BookForSale.Status.valueOf(resultSet.getString("Status"));

                BookForSale book = new BookForSale(
                        bookID,
                        title,
                        category,
                        bookCondition,
                        author,
                        description,
                        price,
                        listedByUserID,
                        listedAt,
                        status
                );

                booksForSale.add(book);
            }
        }

        return booksForSale;
    }
}
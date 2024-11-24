
package Bookstore.dataManagers;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Bookstore.models.Book;
import Bookstore.models.BookForSale;
import Bookstore.models.BookWithUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.DataSource;

public class BookManager {

    private DataSource dataSource;

    public BookManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertBook(Book book, int listedByUserID) throws SQLException {
        int newBookID = -1;
        String sql = "{CALL InsertIntoBooksForSale(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection connection = dataSource.getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getCategory());
            statement.setString(3, book.getCondition());
            statement.setString(4, book.getAuthor());
            statement.setString(5, book.getDescription());
            statement.setDouble(6, book.getNewPrice());
            statement.setInt(7, listedByUserID);

            statement.registerOutParameter(8, Types.INTEGER);
            statement.execute();
            newBookID = statement.getInt(8);
            System.out.println("Book inserted successfully with Book ID: " + newBookID);
        }
    }

    public ObservableList<BookWithUser> getAllBooksForSale() {
        ObservableList<BookWithUser> books = FXCollections.observableArrayList();
        String sql = "{CALL GetAllBooksForSale()}";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            boolean hasResults = stmt.execute();

            if (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        int bookID = rs.getInt("bookID");
                        String title = rs.getString("title");
                        String category = rs.getString("category");
                        String bookCondition = rs.getString("BookCondition");
                        String author = rs.getString("author");
                        int publishYear = rs.getInt("PublishYear");
                        double price = rs.getDouble("price");
                        String listedAt = rs.getString("listedAt");
                        String listedByUsername = rs.getString("listedByUsername");
                        String listedByEmail = rs.getString("listedByEmail");
                        String listedByFirstName = rs.getString("firstName");
                        String listedByLastName = rs.getString("lastName");

                        BookWithUser book = new BookWithUser(bookID, title, category, bookCondition, author,
                                publishYear, price, listedAt, listedByUsername, listedByEmail,
                                listedByFirstName, listedByLastName);
                        books.add(book);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching books for sale: " + e.getMessage());
            e.printStackTrace();
        }

        return books;
    }
    public boolean buyBook(int bookID, int buyerID, double soldPrice) throws SQLException {
        String sql = "{CALL BuyBook(?, ?, ?)}";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, bookID);
            stmt.setInt(2, buyerID);
            stmt.setDouble(3, soldPrice);

            stmt.execute();

            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 45000) {
                //TODO handle errors better
                return false;
            } else {
                throw e;
            }
        }
    }


    public BookWithUser getBookById(int bookID) throws SQLException {
        String sql = "SELECT * FROM BooksForSale WHERE bookID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookID);
            System.out.println(stmt.toString());

            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst() ) {
                return null;
            }

            rs.next();

            int id = rs.getInt("bookID");
            String title = rs.getString("title");
            String category = rs.getString("category");
            String bookCondition = rs.getString("BookCondition");
            String author = rs.getString("author");
            int publishYear = rs.getInt("PublishYear");
            double price = rs.getDouble("price");
            String listedAt = rs.getString("listedAt");
            String listedByUsername = rs.getString("listedByUserID");
            return new BookWithUser(id, title, category, bookCondition, author, publishYear, price,
                    listedAt, listedByUsername, "", "", "");

        } catch (SQLException e) {
            System.err.println("Error fetching book by ID: " + e.getMessage());
            throw e;
        }
    }


    public ObservableList<BookForSale> getBooksBySellerID(int sellerID) {
        ObservableList<BookForSale> books = FXCollections.observableArrayList();
        String sql = "{CALL GetBooksBySellerID(?)}";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, sellerID);

            boolean hasResults = stmt.execute();

            if (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        int bookID = rs.getInt("bookID");
                        String title = rs.getString("title");
                        String category = rs.getString("category");
                        String bookCondition = rs.getString("BookCondition");
                        String author = rs.getString("author");
                        int publishYear = rs.getInt("PublishYear");
                        double price = rs.getDouble("price");
                        BookForSale.Status status = BookForSale.Status.valueOf(rs.getString("Status"));
                        Timestamp listedAtTimestamp = rs.getTimestamp("listedAt");
                        LocalDateTime listedAt = listedAtTimestamp != null ? listedAtTimestamp.toLocalDateTime() : null;




                        BookForSale book = new BookForSale(
                                bookID,
                                title,
                                category,
                                bookCondition,
                                author,
                                publishYear,
                                price,
                                sellerID,
                                listedAt,
                                status
                        );


                        books.add(book);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching books for seller: " + e.getMessage());
            e.printStackTrace();
        }

        return books;
    }

}




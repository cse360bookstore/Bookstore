
package com.example.sellingprocess.datamodels;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.sellingprocess.models.Book;

import javax.sql.DataSource;

public class BookDAO {

    private DataSource dataSource;

    public BookDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM Book";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setCategory(rs.getString("category"));
                book.setCondition(rs.getString("condition"));
                book.setOriginalPrice(rs.getDouble("originalPrice"));
                book.setNewPrice(rs.getDouble("newPrice"));

                books.add(book);
            }
        }

        return books;
    }
    public void insertBook(Book book) throws SQLException {
        String query = "INSERT INTO Book (category, `condition`, originalPrice, newPrice) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getCategory());
            statement.setString(2, book.getCondition());
            statement.setDouble(3, book.getOriginalPrice());
            statement.setDouble(4, book.getNewPrice());

            statement.executeUpdate();
        }
    }
}




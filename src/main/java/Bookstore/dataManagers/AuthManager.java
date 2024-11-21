package Bookstore.dataManagers;
import Bookstore.models.UserRole;
import Bookstore.models.UserSession;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthManager {

    private DataSource dataSource;
    public AuthManager (DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public boolean login(String username, String password) {
        String sql = "{CALL LoginUser(?, ?)}";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, username);
            String hashedPassword = dumbhash(password);
            stmt.setString(2, hashedPassword);

            boolean hasResult = stmt.execute();

            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        UserSession session = UserSession.getInstance();
                        session.setUserId(rs.getInt("userID"));
                        session.setUsername(username);
                        session.setEmail(rs.getString("email"));
                        session.setFirstName(rs.getString("firstName"));
                        session.setLastName(rs.getString("lastName"));
                        String roleString = rs.getString("role");

                        UserRole role = UserRole.fromString(roleString);
                        session.setUserRole(role);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public UserSession register(String username, String password, String email, String firstName, String lastName, UserRole role) {
        String sql = "{CALL RegisterUser(?, ?, ?, ?, ?, ?, ?)}";
        int newUserID = -1;

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, username);
            String hashedPassword = dumbhash(password);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.setString(4, firstName);
            stmt.setString(5, lastName);
            stmt.setString(6, role.name());

            stmt.registerOutParameter(7, java.sql.Types.INTEGER);

            stmt.execute();

            newUserID = stmt.getInt(7);

            System.out.println("User registered successfully with User ID: " + newUserID);
            UserSession session = UserSession.getInstance();
            session.setUserId(newUserID);
            session.setUsername(username);
            session.setEmail(email);
            session.setFirstName(firstName);
            session.setLastName(lastName);
            return session;


        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error during registration: " + e.getMessage());
        }


    }

    public boolean updatePassword(int userID, String newPassword) {
        String sql = "{CALL UpdatePasswordHash(?, ?)}";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, userID);
            String hashedPassword = dumbhash(newPassword);
            stmt.setString(2, hashedPassword);
            stmt.execute();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
    public String getEmailFromUserName(String username) {
        String sql = "{CALL GetEmail(?)}";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, username);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("email");
                } else {
                    return null;
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    // Todo: actually hash password
    private String dumbhash(String password) {
        return password.toLowerCase() + "_hashed";
    }
}
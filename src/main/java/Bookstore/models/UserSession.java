package Bookstore.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Bookstore.dataManagers.AdminManager;

public class UserSession {
    private static UserSession instance;

    private int userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private boolean isActive; // new

    public UserSession() {}

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getters and Setters for user details

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isActive(){
        return isActive;
    }

    public void setActive(boolean active){
        isActive = active;
    }

    public void updateUserRole(UserRole newRole){
        this.userRole = newRole;
    }

    public boolean hasRole(UserRole role){
        return this.userRole == role;
    }

    public boolean isAdmin(){
        return this.userRole == UserRole.ADMIN;
    }

    public boolean hasAnyRole(UserRole... roles){
        for (UserRole role: roles){
            if (this.userRole == role){
                return true;
            }
        }
        return false;
    }

    // refresh session data from the database
    public void refreshSessionFromDatabase(int userId, AdminManager adminManager) throws SQLException{
        String query = "SELECT * FROM Users WHERE userID = ?";
        try (Connection connection = adminManager.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    this.userId = resultSet.getInt("userID");
                    this.username = resultSet.getString("username");
                    this.email = resultSet.getString("email");
                    this.firstName = resultSet.getString("firstName");
                    this.lastName = resultSet.getString("lastName");
                    this.userRole = UserRole.fromString(resultSet.getString("userRole"));
                }
            }
        }
    }

    public void clearSession() {
        this.userId = 0;
        this.username = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        instance = null;
    }
}

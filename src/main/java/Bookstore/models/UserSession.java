package Bookstore.models;

public class UserSession {
    private static UserSession instance;

    private int userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

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


    public void clearSession() {
        this.userId = 0;
        this.username = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        instance = null;
    }
}
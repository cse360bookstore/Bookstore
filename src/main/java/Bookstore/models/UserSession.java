package Bookstore.models;

public class UserSession {
    private static UserSession instance;

    private String userId;
    private String username;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void clearSession() {
        userId = null;
        username = null;
    }
}
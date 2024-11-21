package Bookstore.models;

public enum UserRole {
    BUYER,
    SELLER,
    ADMIN,
    BUYER_SELLER,
    BUYER_ADMIN,
    SELLER_ADMIN,
    BUYER_SELLER_ADMIN;
    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }
}
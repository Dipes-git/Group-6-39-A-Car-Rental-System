package dao;

import model.User;

/**
 * Data Access Object (DAO) interface for User operations.
 * Declares database interaction contracts for authentication, registration, and recovery.
 * 
 * @author dipes
 */
public interface UserDao {
    
    /**
     * Registers a new user into the database.
     * 
     * @param user The User entity containing username, email, and password
     * @return true if registration succeeded, false otherwise
     */
    boolean registerUser(User user);
    
    /**
     * Authenticates a user based on their credentials.
     * 
     * @param username The inputted username
     * @param password The inputted password
     * @return The populated User object if credentials are correct, null otherwise
     */
    User loginUser(String username, String password);
    
    /**
     * Checks if a username already exists in the database.
     * Useful for preventing registration of duplicate accounts.
     * 
     * @param username The username to verify
     * @return true if the username is taken, false otherwise
     */
    boolean checkUserExists(String username);
    
    /**
     * Resets/updates a user's password if their recovery credentials match.
     * 
     * @param username The registered username
     * @param email The registered email
     * @param newPassword The new password to save
     * @return true if password reset succeeded, false otherwise
     */
    boolean updatePassword(String username, String email, String newPassword);
}

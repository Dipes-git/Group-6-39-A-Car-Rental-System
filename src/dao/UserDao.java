package dao;

import model.User;

/**
 * Data Access Object (DAO) interface for User operations.
 * Declares database interaction contracts, supporting security question recovery flows,
 * and extended in Sprint 3 to support administrative customer account management.
 * 
 * @author dipes
 */
public interface UserDao {
    
    /**
     * Registers a new user into the database, including their security question details.
     * 
     * @param user The User entity containing username, email, password, question, and answer
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
     * 
     * @param username The username to verify
     * @return true if the username is taken, false otherwise
     */
    boolean checkUserExists(String username);
    
    /**
     * Retrieves the security question associated with a specific username.
     * 
     * @param username The username of the user
     * @return The security question string if found, null otherwise
     */
    String getSecurityQuestion(String username);
    
    /**
     * Verifies the user's security answer. If correct, updates their password to a new one.
     * 
     * @param username The username of the user
     * @param answer The security answer supplied by the user
     * @param newPassword The new password to save
     * @return true if verification and password update succeeded, false otherwise
     */
    boolean verifyAnswerAndUpdatePassword(String username, String answer, String newPassword);

}
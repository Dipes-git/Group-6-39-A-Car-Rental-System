package model;

/**
 * Represents a User in the Car Rental System.
 * This class serves as the Model layer entity matching the 'users' table in the database,
 * supporting account recovery security questions and role-based access control.
 * 
 * @author dipes
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String role;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructor for creating a new user (e.g., during registration/signup).
     * 
     * @param username The user's screen name
     * @param email The user's registered email address
     * @param password The user's password hash/text
     * @param securityQuestion The user's selected security question
     * @param securityAnswer The user's security answer
     * @param role The user's role ("admin" or "user")
     */
    public User(String username, String email, String password, String securityQuestion, String securityAnswer, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.role = role;
    }

    /**
     * Full constructor (e.g., when retrieving a user from the database with an active ID).
     * 
     * @param id The database primary key ID
     * @param username The user's screen name
     * @param email The user's registered email address
     * @param password The user's password hash/text
     * @param securityQuestion The user's selected security question
     * @param securityAnswer The user's security answer
     * @param role The user's role ("admin" or "user")
     */
    public User(int id, String username, String email, String password, String securityQuestion, String securityAnswer, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.role = role;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

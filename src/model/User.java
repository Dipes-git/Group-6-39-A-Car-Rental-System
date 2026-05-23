package model;

/**
 * Represents a User in the Car Rental System.
 * This class serves as the Model layer entity matching the 'users' table in the database,
 * now supporting account recovery security questions.
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
     */
    public User(String username, String email, String password, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
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
     */
    public User(int id, String username, String email, String password, String securityQuestion, String securityAnswer) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
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
}

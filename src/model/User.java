package model;

/**
 * Represents a User in the Car Rental System.
 * This class serves as the Model layer entity matching the 'users' table in the database,
 * supporting account recovery security questions and role-based access control.
 * Now extended for Sprint 3 to support active/suspended user account status.
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
    private String status = "Active";

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructor for creating a new user (e.g., during registration/signup).
     */
    public User(String username, String email, String password, String securityQuestion, String securityAnswer, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.role = role;
        this.status = "Active";
    }
     * Full constructor (e.g., when retrieving a user from the database with an active ID).
     */
    public User(int id, String username, String email, String password, String securityQuestion, String securityAnswer, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.role = role;
        this.status = "Active";
    }

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
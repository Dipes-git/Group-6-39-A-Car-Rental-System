package controller;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import view.LoginForm;
import view.SignupForm;
import view.ForgotPasswordForm;
import javax.swing.JOptionPane;

/**
 * Controller class to manage all User-related actions (Login, Signup, and Password Recovery),
 * now featuring active verification via Security Questions.
 * 
 * @author dipes
 */
public class UserController {

    private final UserDao userDao;

    public UserController() {
        this.userDao = new UserDaoImpl();
    }

    /**
     * Handles the authentication request from the LoginForm.
     * 
     * @param view The LoginForm instance
     * @param username The entered username
     * @param password The entered password
     * @return true if login was successful, false otherwise
     */
    public boolean handleLogin(LoginForm view, String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter both Username and Password.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        User user = userDao.loginUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(view, "Login Successful!\nWelcome, " + user.getUsername() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Handles the user registration request from the SignupForm.
     * 
     * @param view The SignupForm instance
     * @param username The entered username
     * @param email The entered email
     * @param password The entered password
     * @param confirmPassword The entered confirmation password
     * @param securityQuestion The selected security question
     * @param securityAnswer The entered answer to the security question
     * @return true if registration succeeded, false otherwise
     */
    public boolean handleSignup(SignupForm view, String username, String email, String password, String confirmPassword, String securityQuestion, String securityAnswer) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || securityAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(view, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if username is already taken
        if (userDao.checkUserExists(username)) {
            JOptionPane.showMessageDialog(view, "Username is already taken.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Package into User model and register
        User user = new User(username, email, password, securityQuestion, securityAnswer);
        boolean registered = userDao.registerUser(user);

        if (registered) {
            JOptionPane.showMessageDialog(view, "Registration Successful!\nYou can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Redirect to Login Screen
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            view.dispose();
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Registration failed due to a database error. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Handles the request to fetch the security question associated with a username.
     * 
     * @param view The ForgotPasswordForm instance
     * @param username The entered username
     * @return The retrieved question if successful, null otherwise
     */
    public String handleGetQuestion(ForgotPasswordForm view, String username) {
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter your Username to retrieve your security question.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String question = userDao.getSecurityQuestion(username);
        if (question != null) {
            return question;
        } else {
            JOptionPane.showMessageDialog(view, "Username not found. Please verify spelling.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Verifies the answer to the security question and updates the password.
     * 
     * @param view The ForgotPasswordForm instance
     * @param username The entered username
     * @param answer The entered answer
     * @param newPassword The entered new password
     * @param confirmPassword The entered confirmation password
     * @return true if password reset succeeded, false otherwise
     */
    public boolean handleResetPassword(ForgotPasswordForm view, String username, String answer, String newPassword, String confirmPassword) {
        if (username.isEmpty() || answer.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(view, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean resetSucceeded = userDao.verifyAnswerAndUpdatePassword(username, answer, newPassword);

        if (resetSucceeded) {
            JOptionPane.showMessageDialog(view, "Password reset successfully!\nYou can now log in with your new password.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Redirect back to Login
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            view.dispose();
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Incorrect answer to the security question. Reset failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}

package controller;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import view.LoginForm;
import view.SignupForm;
import view.ForgotPasswordForm;
import javax.swing.JOptionPane;
import java.util.Random;

/**
 * Controller class to manage all User-related actions (Login, Signup, and Password Recovery).
 * Coordinates validation, business rules, and UI transitions.
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
            
            // 🔥 Future Extension: Redirect to Main Dashboard Form here
            // MainDashboard dashboard = new MainDashboard();
            // dashboard.setVisible(true);
            // view.dispose();
            
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
     * @return true if registration succeeded, false otherwise
     */
    public boolean handleSignup(SignupForm view, String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
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

        // Package into a clean User model and attempt database registration
        User user = new User(username, email, password);
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
     * Handles the password recovery request from the ForgotPasswordForm.
     * Generates a secure temporary password and saves it to the database.
     * 
     * @param view The ForgotPasswordForm instance
     * @param username The entered username
     * @param email The entered email
     * @return true if recovery succeeded, false otherwise
     */
    public boolean handlePasswordRecovery(ForgotPasswordForm view, String username, String email) {
        if (username.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter both Username and Email.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Generate a premium secure temporary password (e.g., Reset@48291)
        Random random = new Random();
        int digits = 10000 + random.nextInt(90000); // 5-digit random number
        String tempPassword = "Reset@" + digits;

        // Reset the password in the database
        boolean resetSucceeded = userDao.updatePassword(username, email, tempPassword);

        if (resetSucceeded) {
            String message = "Account verified successfully!\n\n"
                    + "Your temporary password is:  " + tempPassword + "\n\n"
                    + "Please use this temporary password to log in and update your security settings.";
            JOptionPane.showMessageDialog(view, message, "Password Reset Succeeded", JOptionPane.INFORMATION_MESSAGE);

            // Redirect back to Login
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            view.dispose();
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Verification failed.\nNo account matches the provided Username and Email.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}

package controller;

import javax.swing.table.DefaultTableModel;
import view.CustomerPanel;


import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import view.LoginForm;
import view.SignupForm;
import view.ForgotPasswordForm;
import view.AdminDashboard;
import view.UserDashboard;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**

 * 
 * @author dipes
 */
public class UserController {

    private final UserDao userDao;

    public UserController() {
        this.userDao = new UserDaoImpl();
    }

    // ==================== Authentication ====================

    /**
     * Handles the authentication request from the LoginForm.
     * Reads credentials directly from the view's public getters.
     * Routes to AdminDashboard or UserDashboard based on the user's role.
     * 
     * @param view The LoginForm instance
     * @return true if login was successful, false otherwise
     */
    public boolean handleLogin(LoginForm view) {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter both Username and Password.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        User user = userDao.loginUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(view, "Login Successful!\nWelcome, " + user.getUsername() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Route to the appropriate dashboard based on role
            if ("admin".equalsIgnoreCase(user.getRole())) {
                navigateToAdminDashboard(view, user.getUsername());
            } else {
                navigateToUserDashboard(view, user.getUsername());
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
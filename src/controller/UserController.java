package controller;

import javax.swing.table.DefaultTableModel;
import view.CustomerPanel;


import dao.UserDao;
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
        this.userDao = new UserDao();
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

    // ==================== Registration ====================

    /**
     * Handles the user registration request from the SignupForm.
     * Reads all form data directly from the view's public getters,
     * including the selected role (admin or user).
     * 
     * @param view The SignupForm instance
     * @return true if registration succeeded, false otherwise
     */
    public boolean handleSignup(SignupForm view) {
        String username = view.getUsername();
        String email = view.getEmail();
        String password = view.getPassword();
        String confirmPassword = view.getConfirmPassword();
        String role = view.getSelectedRole();
        String securityQuestion = view.getSecurityQuestion();
        String securityAnswer = view.getSecurityAnswer();

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

        // Package into User model and register with selected role
        User user = new User(username, email, password, securityQuestion, securityAnswer, role);
        boolean registered = userDao.registerUser(user);

        if (registered) {
            JOptionPane.showMessageDialog(view, "Registration Successful!\nYou can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            navigateToLogin(view);
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Registration failed due to a database error. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==================== Password Recovery ====================

    /**
     * Handles fetching the security question for a given username.
     * Reads the username from the view and updates the question display label.
     * 
     * @param view The ForgotPasswordForm instance
     */
    public void handleFetchQuestion(ForgotPasswordForm view) {
        String username = view.getUsername();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter your Username to retrieve your security question.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String question = userDao.getSecurityQuestion(username);
        if (question != null) {
            view.setQuestionDisplayText(question);
        } else {
            JOptionPane.showMessageDialog(view, "Username not found. Please verify spelling.", "Error", JOptionPane.ERROR_MESSAGE);
            view.setQuestionDisplayText("Retrieve your question first.");
        }
    }

    /**
     * Verifies the security answer and resets the user's password.
     * Reads all form data directly from the view's public getters.
     * 
     * @param view The ForgotPasswordForm instance
     * @return true if password reset succeeded, false otherwise
     */
    public boolean handleResetPassword(ForgotPasswordForm view) {
        String username = view.getUsername();
        String answer = view.getSecurityAnswer();
        String newPassword = view.getNewPassword();
        String confirmPassword = view.getConfirmPassword();

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
            navigateToLogin(view);
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Incorrect answer to the security question. Reset failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==================== Navigation ====================

    /**
     * Navigates from any screen to the Login screen.
     * 
     * @param currentView The currently visible JFrame to dispose
     */
    public void navigateToLogin(JFrame currentView) {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        currentView.dispose();
    }

    /**
     * Navigates from any screen to the Signup screen.
     * 
     * @param currentView The currently visible JFrame to dispose
     */
    public void navigateToSignup(JFrame currentView) {
        SignupForm signupForm = new SignupForm();
        signupForm.setVisible(true);
        currentView.dispose();
    }

    /**
     * Navigates from any screen to the Forgot Password screen.
     * 
     * @param currentView The currently visible JFrame to dispose
     */
    public void navigateToForgotPassword(JFrame currentView) {
        ForgotPasswordForm fpForm = new ForgotPasswordForm();
        fpForm.setVisible(true);
        currentView.dispose();
    }

    /**
     * Navigates to the Admin Dashboard with a personalized welcome message.
     * 
     * @param currentView The currently visible JFrame to dispose
     * @param username The authenticated admin's username
     */
    public void navigateToAdminDashboard(JFrame currentView, String username) {
        AdminDashboard dashboard = new AdminDashboard();
        dashboard.setWelcomeText("Welcome, " + username + "!");
        dashboard.setVisible(true);
        currentView.dispose();
    }

    /**
     * Navigates to the User Dashboard with a personalized welcome message.
     * 
     * @param currentView The currently visible JFrame to dispose
     * @param username The authenticated user's username
     */
    public void navigateToUserDashboard(JFrame currentView, String username) {
        UserDashboard dashboard = new UserDashboard();
        dashboard.setWelcomeText("Welcome, " + username + "!");
        dashboard.setVisible(true);
        currentView.dispose();
    }

    public void handleLogout(JFrame currentView) {
        navigateToLogin(currentView);
    }

    public void handleAdminTabChanged(AdminDashboard view, String tabName) {
        view.showPanel(tabName);
    }

    /**
     * Handles tab switching inside the User Dashboard.
     * 
     * @param view The UserDashboard instance
     * @param tabName The target tab/panel name to show
     */
    public void handleUserTabChanged(UserDashboard view, String tabName) {
        view.showPanel(tabName);
    }

    // ==================== UI Helpers ====================

    /**
     * Toggles password visibility on the Login form.
     * Ensures it does not mess up if placeholder is active.
     * 
     * @param view The LoginForm instance
     */
    public void toggleLoginPasswordVisibility(LoginForm view) {
        javax.swing.JPasswordField pf = view.getPasswordField();
        String currentText = new String(pf.getPassword());
        if ("Enter Password".equals(currentText) && pf.getForeground().equals(java.awt.Color.GRAY)) {
            return;
        }
        if (view.isShowPasswordSelected()) {
            view.setPasswordEchoChar((char) 0);
        } else {
            view.setPasswordEchoChar('*');
        }
    }

    /**
     * Toggles password visibility on the Signup form (both password fields).
     * Ensures it does not mess up if placeholders are active.
     * 
     * @param view The SignupForm instance
     */
    public void toggleSignupPasswordVisibility(SignupForm view) {
        javax.swing.JPasswordField pf = view.getPasswordField();
        javax.swing.JPasswordField cpf = view.getConfirmPasswordField();
        boolean show = view.isShowPasswordSelected();

        // Handle password field
        String passText = new String(pf.getPassword());
        if (!("Enter Password".equals(passText) && pf.getForeground().equals(java.awt.Color.GRAY))) {
            pf.setEchoChar(show ? (char) 0 : '*');
        }

        // Handle confirm password field
        String confirmText = new String(cpf.getPassword());
        if (!("Confirm Password".equals(confirmText) && cpf.getForeground().equals(java.awt.Color.GRAY))) {
            cpf.setEchoChar(show ? (char) 0 : '*');
        }
    }

    /**
     * Setup focus gain/lost placeholders for LoginForm fields.
     */
    public void setupLoginPlaceholders(LoginForm view) {
        setupPlaceholder(view.getUserTextField(), "Enter Username");
        setupPasswordPlaceholder(view.getPasswordField(), "Enter Password");
    }

    /**
     * Setup focus gain/lost placeholders for SignupForm fields.
     */
    public void setupSignupPlaceholders(SignupForm view) {
        setupPlaceholder(view.getUserTextField(), "Enter Username");
        setupPlaceholder(view.getEmailTextField(), "Enter Email");
        setupPasswordPlaceholder(view.getPasswordField(), "Enter Password");
        setupPasswordPlaceholder(view.getConfirmPasswordField(), "Confirm Password");
        setupPlaceholder(view.getSecurityAnswerTextField(), "Enter Security Answer");
    }

    /**
     * Setup focus gain/lost placeholders for ForgotPasswordForm fields.
     */
    public void setupForgotPasswordPlaceholders(ForgotPasswordForm view) {
        setupPlaceholder(view.getUserTextField(), "Enter Username");
        setupPlaceholder(view.getSecurityAnswerTextField(), "Enter Security Answer");
        setupPasswordPlaceholder(view.getNewPasswordField(), "Enter New Password");
        setupPasswordPlaceholder(view.getConfirmNewPasswordField(), "Confirm New Password");
    }

    /**
     * Shared helper to attach placeholder logic to regular text fields.
     */
    private void setupPlaceholder(javax.swing.JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(java.awt.Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (placeholder.equals(field.getText())) {
                    field.setText("");
                    field.setForeground(new java.awt.Color(48, 48, 48)); // Standard dark gray input color
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }

    /**
     * Shared helper to attach placeholder logic to password fields, managing character masking.
     */
    private void setupPasswordPlaceholder(javax.swing.JPasswordField field, String placeholder) {
        field.setText(placeholder);
        field.setEchoChar((char) 0); // Show plain text for placeholder
        field.setForeground(java.awt.Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                String pass = new String(field.getPassword());
                if (placeholder.equals(pass)) {
                    field.setText("");
                    field.setEchoChar('*'); // Standard dot mask
                    field.setForeground(new java.awt.Color(48, 48, 48));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String pass = new String(field.getPassword());
                if (pass.trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setEchoChar((char) 0); // Unmask placeholder text
                    field.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }

    /**
     * Exits the entire application.
     */
    public void exitApplication() {
        System.exit(0);
    }
    // ==================== Customer Management ====================

    /**
     * Loads all registered customers into the CustomerPanel's JTable.
     * 
     * @param view The CustomerPanel instance
     */
    public void loadCustomersTable(CustomerPanel view) {
        DefaultTableModel model = (DefaultTableModel) view.getCustomersTable().getModel();
        model.setRowCount(0); // Reset existing rows

        java.util.List<User> customers = userDao.getAllCustomers();
        for (User u : customers) {
            model.addRow(new Object[]{
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRole(),
                u.getStatus()
            });
        }
    }

    /**
     * Toggles the active status of the selected customer between Active and Suspended.
     * 
     * @param view The CustomerPanel instance
     */
    public void handleStatusToggle(CustomerPanel view) {
        int selectedRow = view.getCustomersTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a customer from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) view.getCustomersTable().getValueAt(selectedRow, 0);
        String username = view.getCustomersTable().getValueAt(selectedRow, 1).toString();
        String currentStatus = view.getCustomersTable().getValueAt(selectedRow, 4).toString();

        String newStatus = "Active".equals(currentStatus) ? "Suspended" : "Active";

        boolean success = userDao.updateUserStatus(userId, newStatus);
        if (success) {
            JOptionPane.showMessageDialog(view, "Successfully updated status of customer '" + username + "' to " + newStatus + "!", "Status Updated", JOptionPane.INFORMATION_MESSAGE);
            loadCustomersTable(view);
            view.clearInputs();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to update customer status due to a database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Permanently deletes the selected customer account.
     * 
     * @param view The CustomerPanel instance
     */
    public void handleDeleteCustomer(CustomerPanel view) {
        int selectedRow = view.getCustomersTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a customer from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) view.getCustomersTable().getValueAt(selectedRow, 0);
        String username = view.getCustomersTable().getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(view, 
            "Are you sure you want to permanently delete customer '" + username + "'?\nThis action cannot be undone.", 
            "Confirm Permanent Deletion", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = userDao.deleteUser(userId);
            if (success) {
                JOptionPane.showMessageDialog(view, "Successfully deleted customer '" + username + "'!", "Customer Deleted", JOptionPane.INFORMATION_MESSAGE);
                loadCustomersTable(view);
                view.clearInputs();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete customer due to a database error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Performs a real-time search and filter on customers by username or email.
     * 
     * @param view The CustomerPanel instance
     */
    public void handleSearchCustomer(CustomerPanel view) {
        String query = view.getSearchField().getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) view.getCustomersTable().getModel();
        model.setRowCount(0);

        java.util.List<User> customers = userDao.getAllCustomers();
        for (User u : customers) {
            if (u.getUsername().toLowerCase().contains(query) || u.getEmail().toLowerCase().contains(query)) {
                model.addRow(new Object[]{
                    u.getId(),
                    u.getUsername(),
                    u.getEmail(),
                    u.getRole(),
                    u.getStatus()
                });
            }
        }
    }
}
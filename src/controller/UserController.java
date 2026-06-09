package controller;

import dao.UserDao;
import model.User;
import view.LoginForm;
import view.SignupForm;
import view.ForgotPasswordForm;
import view.AdminDashboard;
import view.UserDashboard;
import view.CustomerPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Controller class managing user authentication, registration, password recovery,
 * dashboard coordination, and customer administrative management.
 * Overloaded constructors support strict MVC separation across all user-related views.
 * 
 * @author dipes
 */
public class UserController {

    private final UserDao userDao;
    private LoginForm loginView;
    private SignupForm signupView;
    private ForgotPasswordForm forgotView;
    private AdminDashboard adminDashboard;
    private UserDashboard userDashboard;
    private CustomerPanel customerPanel;

    public UserController() {
        this.userDao = new UserDao();
    }

    public UserController(LoginForm loginView) {
        this.userDao = new UserDao();
        this.loginView = loginView;
        setupLoginPlaceholders();
        
        // Wire listeners
        this.loginView.addCloseListener(new CloseListener());
        this.loginView.addShowPasswordListener(new ToggleLoginPasswordListener());
        this.loginView.addLoginListener(new LoginListener());
        this.loginView.addForgotPasswordListener(new NavigateForgotPasswordListener());
        this.loginView.addSignupListener(new NavigateSignupListener());
    }

    public UserController(SignupForm signupView) {
        this.userDao = new UserDao();
        this.signupView = signupView;
        setupSignupPlaceholders();

        // Wire listeners
        this.signupView.addShowPasswordListener(new ToggleSignupPasswordListener());
        this.signupView.addRegisterListener(new SignupListener());
        this.signupView.addLoginLabelListener(new NavigateLoginListener());
    }

    public UserController(ForgotPasswordForm forgotView) {
        this.userDao = new UserDao();
        this.forgotView = forgotView;
        setupForgotPasswordPlaceholders();

        // Wire listeners
        this.forgotView.addCloseListener(new CloseListener());
        this.forgotView.addBackToLoginListener(new NavigateLoginListener());
        this.forgotView.addFetchListener(new FetchQuestionListener());
        this.forgotView.addResetListener(new ResetPasswordListener());
    }

    public UserController(AdminDashboard adminDashboard) {
        this.userDao = new UserDao();
        this.adminDashboard = adminDashboard;
        
        // Wire listeners
        this.adminDashboard.addLogoutListener(new LogoutListener());
    }

    public UserController(UserDashboard userDashboard) {
        this.userDao = new UserDao();
        this.userDashboard = userDashboard;

        // Wire listeners
        this.userDashboard.addLogoutListener(new LogoutListener());
    }

    public UserController(CustomerPanel customerPanel) {
        this.userDao = new UserDao();
        this.customerPanel = customerPanel;

        // Load initial records
        loadCustomersTable(this.customerPanel);

        // Wire listeners
        this.customerPanel.getBtnToggleStatus().addActionListener(new ToggleStatusListener());
        this.customerPanel.getBtnDeleteCustomer().addActionListener(new DeleteCustomerListener());
        this.customerPanel.getBtnRefresh().addActionListener(new RefreshCustomerListener());
        
        this.customerPanel.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                handleSearchCustomer(customerPanel);
            }
        });
    }

    // ==================== Listeners & Inner Classes ====================

    class CloseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.exit(0);
        }
    }

    class ToggleLoginPasswordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginView == null) return;
            JPasswordField pf = loginView.getPasswordField();
            String currentText = new String(pf.getPassword());
            if ("Enter Password".equals(currentText) && pf.getForeground().equals(java.awt.Color.GRAY)) {
                return;
            }
            if (loginView.isShowPasswordSelected()) {
                loginView.setPasswordEchoChar((char) 0);
            } else {
                loginView.setPasswordEchoChar('*');
            }
        }
    }

    class ToggleSignupPasswordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (signupView == null) return;
            JPasswordField pf = signupView.getPasswordField();
            JPasswordField cpf = signupView.getConfirmPasswordField();
            boolean show = signupView.isShowPasswordSelected();

            String passText = new String(pf.getPassword());
            if (!("Enter Password".equals(passText) && pf.getForeground().equals(java.awt.Color.GRAY))) {
                pf.setEchoChar(show ? (char) 0 : '*');
            }

            String confirmText = new String(cpf.getPassword());
            if (!("Confirm Password".equals(confirmText) && cpf.getForeground().equals(java.awt.Color.GRAY))) {
                cpf.setEchoChar(show ? (char) 0 : '*');
            }
        }
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginView == null) return;
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "Please enter both Username and Password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = userDao.loginUser(username, password);
            if (user != null) {
                if ("Suspended".equalsIgnoreCase(user.getStatus())) {
                    JOptionPane.showMessageDialog(loginView, "Your account is suspended. Please contact support.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(loginView, "Login Successful!\nWelcome, " + user.getUsername() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);

                if ("admin".equalsIgnoreCase(user.getRole())) {
                    AdminDashboard dashboard = new AdminDashboard();
                    new UserController(dashboard);
                    
                    // Wire sub-panel controllers
                    BrandController brandController = new BrandController(dashboard.getBrandPanel());
                    CarController carController = new CarController(dashboard.getCarPanel());
                    new UserController(dashboard.getCustomerPanel());
                    new LocationController(dashboard.getLocationsPanel());
                    new BookingController(dashboard);
                    
                    // Wire redirect callback in controller: refresh brand data, then show
                    dashboard.getCarPanel().setOnBrandsTabRedirect(() -> {
                        brandController.loadBrandTable();
                        dashboard.showPanel("brands");
                    });
                    
                    dashboard.setWelcomeText("Welcome, " + user.getUsername() + "!");
                    dashboard.setVisible(true);
                } else {
                    UserDashboard dashboard = new UserDashboard();
                    new UserController(dashboard);
                    
                    // Wire sub-panel controllers for User Dashboard mode
                    CarController carController = new CarController(dashboard.getCarPanel());
                    carController.setCurrentUser(user);
                    
                    BrandController brandController = new BrandController(dashboard.getBrandPanel());
                    
                    // Wire redirect callback in controller: refresh brand data, then show
                    dashboard.getCarPanel().setOnBrandsTabRedirect(() -> {
                        brandController.loadBrandTable();
                        dashboard.showPanel("brands");
                    });
                    
                    BookingController bookingController = new BookingController(dashboard, user);
                    
                    // Wire automatic reload of history when reservation completes
                    carController.setOnBookingComplete(() -> {
                        bookingController.loadUserBookingsTable();
                    });
                    
                    // Wire brand category visual fleet filtration callback in User Mode!
                    dashboard.getBrandPanel().setOnViewFleetCallback(brandName -> {
                        new CarController().loadAdminCarTable(dashboard.getCarPanel(), brandName);
                        dashboard.showPanel("browse");
                    });
                    
                    dashboard.setWelcomeText("Welcome, " + user.getUsername() + "!");
                    dashboard.setVisible(true);
                }
                loginView.dispose();
            } else {
                JOptionPane.showMessageDialog(loginView, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class SignupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (signupView == null) return;
            String username = signupView.getUsername();
            String email = signupView.getEmail();
            String password = signupView.getPassword();
            String confirmPassword = signupView.getConfirmPassword();
            String role = signupView.getSelectedRole();
            String securityQuestion = signupView.getSecurityQuestion();
            String securityAnswer = signupView.getSecurityAnswer();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || securityAnswer.isEmpty()) {
                JOptionPane.showMessageDialog(signupView, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(signupView, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userDao.checkUserExists(username)) {
                JOptionPane.showMessageDialog(signupView, "Username is already taken.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = new User(username, email, password, securityQuestion, securityAnswer, role);
            boolean registered = userDao.registerUser(user);

            if (registered) {
                JOptionPane.showMessageDialog(signupView, "Registration Successful!\nYou can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                LoginForm loginForm = new LoginForm();
                new UserController(loginForm);
                loginForm.setVisible(true);
                signupView.dispose();
            } else {
                JOptionPane.showMessageDialog(signupView, "Registration failed due to a database error. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class FetchQuestionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (forgotView == null) return;
            String username = forgotView.getUsername();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(forgotView, "Please enter your Username to retrieve your security question.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String question = userDao.getSecurityQuestion(username);
            if (question != null) {
                forgotView.setQuestionDisplayText(question);
            } else {
                JOptionPane.showMessageDialog(forgotView, "Username not found. Please verify spelling.", "Error", JOptionPane.ERROR_MESSAGE);
                forgotView.setQuestionDisplayText("Retrieve your question first.");
            }
        }
    }

    class ResetPasswordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (forgotView == null) return;
            String username = forgotView.getUsername();
            String answer = forgotView.getSecurityAnswer();
            String newPassword = forgotView.getNewPassword();
            String confirmPassword = forgotView.getConfirmPassword();

            if (username.isEmpty() || answer.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(forgotView, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(forgotView, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean resetSucceeded = userDao.verifyAnswerAndUpdatePassword(username, answer, newPassword);

            if (resetSucceeded) {
                JOptionPane.showMessageDialog(forgotView, "Password reset successfully!\nYou can now log in with your new password.", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                LoginForm loginForm = new LoginForm();
                new UserController(loginForm);
                loginForm.setVisible(true);
                forgotView.dispose();
            } else {
                JOptionPane.showMessageDialog(forgotView, "Incorrect answer to the security question. Reset failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class NavigateForgotPasswordListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            ForgotPasswordForm fpForm = new ForgotPasswordForm();
            new UserController(fpForm);
            fpForm.setVisible(true);
            if (loginView != null) loginView.dispose();
        }
    }

    class NavigateSignupListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            SignupForm sForm = new SignupForm();
            new UserController(sForm);
            sForm.setVisible(true);
            if (loginView != null) loginView.dispose();
        }
    }

    class NavigateLoginListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            LoginForm loginForm = new LoginForm();
            new UserController(loginForm);
            loginForm.setVisible(true);
            if (signupView != null) signupView.dispose();
            if (forgotView != null) forgotView.dispose();
        }
    }

    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginForm loginForm = new LoginForm();
            new UserController(loginForm);
            loginForm.setVisible(true);
            
            if (adminDashboard != null) adminDashboard.dispose();
            if (userDashboard != null) userDashboard.dispose();
        }
    }

    class ToggleStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (customerPanel == null) return;
            int selectedRow = customerPanel.getCustomersTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(customerPanel, "Please select a customer from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int userId = (int) customerPanel.getCustomersTable().getValueAt(selectedRow, 0);
            String username = customerPanel.getCustomersTable().getValueAt(selectedRow, 1).toString();
            String currentStatus = customerPanel.getCustomersTable().getValueAt(selectedRow, 4).toString();

            String newStatus = "Active".equals(currentStatus) ? "Suspended" : "Active";

            boolean success = userDao.updateUserStatus(userId, newStatus);
            if (success) {
                JOptionPane.showMessageDialog(customerPanel, "Successfully updated status of customer '" + username + "' to " + newStatus + "!", "Status Updated", JOptionPane.INFORMATION_MESSAGE);
                loadCustomersTable(customerPanel);
                customerPanel.clearInputs();
            } else {
                JOptionPane.showMessageDialog(customerPanel, "Failed to update customer status due to a database error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DeleteCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (customerPanel == null) return;
            int selectedRow = customerPanel.getCustomersTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(customerPanel, "Please select a customer from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int userId = (int) customerPanel.getCustomersTable().getValueAt(selectedRow, 0);
            String username = customerPanel.getCustomersTable().getValueAt(selectedRow, 1).toString();

            int confirm = JOptionPane.showConfirmDialog(customerPanel, 
                "Are you sure you want to permanently delete customer '" + username + "'?\nThis action cannot be undone.", 
                "Confirm Permanent Deletion", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = userDao.deleteUser(userId);
                if (success) {
                    JOptionPane.showMessageDialog(customerPanel, "Successfully deleted customer '" + username + "'!", "Customer Deleted", JOptionPane.INFORMATION_MESSAGE);
                    loadCustomersTable(customerPanel);
                    customerPanel.clearInputs();
                } else {
                    JOptionPane.showMessageDialog(customerPanel, "Failed to delete customer due to a database error.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class RefreshCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (customerPanel == null) return;
            loadCustomersTable(customerPanel);
            customerPanel.clearInputs();
        }
    }

    // ==================== Customer Management operations ====================

    /**
     * Loads all registered customers into the CustomerPanel's JTable.
     */
    public void loadCustomersTable(CustomerPanel viewPanel) {
        DefaultTableModel model = (DefaultTableModel) viewPanel.getCustomersTable().getModel();
        model.setRowCount(0); // Reset existing rows

        List<User> customers = userDao.getAllCustomers();
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
     * Performs a real-time search and filter on customers by username or email.
     */
    public void handleSearchCustomer(CustomerPanel viewPanel) {
        String query = viewPanel.getSearchField().getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) viewPanel.getCustomersTable().getModel();
        model.setRowCount(0);

        List<User> customers = userDao.getAllCustomers();
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

    // ==================== UI Helpers ====================

    /**
     * Setup focus gain/lost placeholders for LoginForm fields.
     */
    private void setupLoginPlaceholders() {
        setupPlaceholder(loginView.getUserTextField(), "Enter Username");
        setupPasswordPlaceholder(loginView.getPasswordField(), "Enter Password");
    }

    /**
     * Setup focus gain/lost placeholders for SignupForm fields.
     */
    private void setupSignupPlaceholders() {
        setupPlaceholder(signupView.getUserTextField(), "Enter Username");
        setupPlaceholder(signupView.getEmailTextField(), "Enter Email");
        setupPasswordPlaceholder(signupView.getPasswordField(), "Enter Password");
        setupPasswordPlaceholder(signupView.getConfirmPasswordField(), "Confirm Password");
        setupPlaceholder(signupView.getSecurityAnswerTextField(), "Enter Security Answer");
    }

    /**
     * Setup focus gain/lost placeholders for ForgotPasswordForm fields.
     */
    private void setupForgotPasswordPlaceholders() {
        setupPlaceholder(forgotView.getUserTextField(), "Enter Username");
        setupPlaceholder(forgotView.getSecurityAnswerTextField(), "Enter Security Answer");
        setupPasswordPlaceholder(forgotView.getNewPasswordField(), "Enter New Password");
        setupPasswordPlaceholder(forgotView.getConfirmNewPasswordField(), "Confirm New Password");
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
}
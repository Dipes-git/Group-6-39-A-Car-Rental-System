package controller;

import dao.UserDao;
import dao.UserDaoImpl;
import javax.swing.JFrame;
import view.LoginForm;
import view.SignupForm;
import view.ForgotPasswordForm;
import view.AdminDashboard;
import view.UserDashboard;

/**
 * Controller class coordinating interactions between dashboards/forms (views)
 * and the User DAO layer (database operations).
 * Now supports password visibility toggling and focus text placeholders.
 * 
 * @author dipes
 */
public class UserController {

    private final UserDao userDao;

    public UserController() {
        this.userDao = new UserDaoImpl();
    }

    // ==================== Navigation ====================

    public void navigateToLogin(JFrame currentView) {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        currentView.dispose();
    }

    public void navigateToSignup(JFrame currentView) {
        SignupForm signupForm = new SignupForm();
        signupForm.setVisible(true);
        currentView.dispose();
    }

    public void navigateToForgotPassword(JFrame currentView) {
        ForgotPasswordForm fpForm = new ForgotPasswordForm();
        fpForm.setVisible(true);
        currentView.dispose();
    }

    public void navigateToAdminDashboard(JFrame currentView, String username) {
        AdminDashboard dashboard = new AdminDashboard();
        dashboard.setWelcomeText("Welcome, " + username + "!");
        dashboard.setVisible(true);
        currentView.dispose();
    }

    public void navigateToUserDashboard(JFrame currentView, String username) {
        UserDashboard dashboard = new UserDashboard();
        dashboard.setWelcomeText("Welcome, " + username + "!");
        dashboard.setVisible(true);
        currentView.dispose();
    }

    public void handleLogout(JFrame currentView) {
        navigateToLogin(currentView);
    }

    public void exitApplication() {
        System.exit(0);
    }

    // ==================== UI Helpers & Placeholders ====================

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

    public void toggleSignupPasswordVisibility(SignupForm view) {
        javax.swing.JPasswordField pf = view.getPasswordField();
        javax.swing.JPasswordField cpf = view.getConfirmPasswordField();
        boolean show = view.isShowPasswordSelected();

        String passText = new String(pf.getPassword());
        if (!("Enter Password".equals(passText) && pf.getForeground().equals(java.awt.Color.GRAY))) {
            pf.setEchoChar(show ? (char) 0 : '*');
        }

        String confirmText = new String(cpf.getPassword());
        if (!("Confirm Password".equals(confirmText) && cpf.getForeground().equals(java.awt.Color.GRAY))) {
            cpf.setEchoChar(show ? (char) 0 : '*');
        }
    }

    public void setupLoginPlaceholders(LoginForm view) {
        setupPlaceholder(view.getUserTextField(), "Enter Username");
        setupPasswordPlaceholder(view.getPasswordField(), "Enter Password");
    }

    public void setupSignupPlaceholders(SignupForm view) {
        setupPlaceholder(view.getUserTextField(), "Enter Username");
        setupPlaceholder(view.getEmailTextField(), "Enter Email");
        setupPasswordPlaceholder(view.getPasswordField(), "Enter Password");
        setupPasswordPlaceholder(view.getConfirmPasswordField(), "Confirm Password");
        setupPlaceholder(view.getSecurityAnswerTextField(), "Enter Security Answer");
    }

    public void setupForgotPasswordPlaceholders(ForgotPasswordForm view) {
        setupPlaceholder(view.getUserTextField(), "Enter Username");
        setupPlaceholder(view.getSecurityAnswerTextField(), "Enter Security Answer");
        setupPasswordPlaceholder(view.getNewPasswordField(), "Enter New Password");
        setupPasswordPlaceholder(view.getConfirmNewPasswordField(), "Confirm New Password");
    }

    private void setupPlaceholder(javax.swing.JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(java.awt.Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (placeholder.equals(field.getText())) {
                    field.setText("");
                    field.setForeground(new java.awt.Color(48, 48, 48));
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

    private void setupPasswordPlaceholder(javax.swing.JPasswordField field, String placeholder) {
        field.setText(placeholder);
        field.setEchoChar((char) 0);
        field.setForeground(java.awt.Color.GRAY);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                String pass = new String(field.getPassword());
                if (placeholder.equals(pass)) {
                    field.setText("");
                    field.setEchoChar('*');
                    field.setForeground(new java.awt.Color(48, 48, 48));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String pass = new String(field.getPassword());
                if (pass.trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setEchoChar((char) 0);
                    field.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }
}

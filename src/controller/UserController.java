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
}

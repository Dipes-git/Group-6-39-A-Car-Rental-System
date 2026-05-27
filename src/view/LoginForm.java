

package view;

import controller.UserController;

/**
 * Visual Form for User Login.
 * Contains only UI components and delegates all logic to UserController.
 * No business logic, data extraction, or navigation resides in this class.
 * 
 * @author dipes
 */
public class LoginForm extends javax.swing.JFrame {

    private final UserController controller;

    public LoginForm() {
        controller = new UserController();
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
        controller.setupLoginPlaceholders(this);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        carLabel = new javax.swing.JLabel();
        rightPanel = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        loginTitleLabel = new javax.swing.JLabel();
        underlinePanel = new javax.swing.JPanel();
        userIconLabel = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        lockIconLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        showPasswordCheckbox = new javax.swing.JCheckBox();
        loginButton = new javax.swing.JButton();
        forgotPasswordLabel = new javax.swing.JLabel();
        signupLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(48, 48, 48));
        mainPanel.setLayout(null);

        leftPanel.setLayout(null);

        carLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/car_banner.png"))); // NOI18N
        leftPanel.add(carLabel);
        carLabel.setBounds(0, 0, 400, 600);

        mainPanel.add(leftPanel);
        leftPanel.setBounds(0, 0, 400, 600);

        rightPanel.setLayout(null);

        closeLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeLabel.setText("X");
        closeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
        });
        rightPanel.add(closeLabel);
        closeLabel.setBounds(360, 10, 30, 30);

        loginTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        loginTitleLabel.setForeground(new java.awt.Color(48, 48, 48));
        loginTitleLabel.setText("Login");
        rightPanel.add(loginTitleLabel);
        loginTitleLabel.setBounds(40, 100, 200, 50);

        underlinePanel.setLayout(null);
        rightPanel.add(underlinePanel);
        underlinePanel.setBounds(40, 152, 100, 4);

        userIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        rightPanel.add(userIconLabel);
        userIconLabel.setBounds(40, 220, 40, 40);

        userTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        userTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                userTextFieldFocusGained(evt);
            }
        });
        rightPanel.add(userTextField);
        userTextField.setBounds(90, 220, 270, 40);

        lockIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock_icon.png"))); // NOI18N
        rightPanel.add(lockIconLabel);
        lockIconLabel.setBounds(40, 280, 40, 40);

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(passwordField);
        passwordField.setBounds(90, 280, 270, 40);

        showPasswordCheckbox.setText("Show Password");
        showPasswordCheckbox.setBorder(null);
        showPasswordCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordCheckboxActionPerformed(evt);
            }
        });
        rightPanel.add(showPasswordCheckbox);
        showPasswordCheckbox.setBounds(90, 330, 200, 20);

        loginButton.setBackground(new java.awt.Color(255, 153, 0));
        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        loginButton.setText("Login");
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        rightPanel.add(loginButton);
        loginButton.setBounds(40, 380, 320, 45);

        forgotPasswordLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        forgotPasswordLabel.setText("Forgot Password?");
        forgotPasswordLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgotPasswordLabelMouseClicked(evt);
            }
        });
        rightPanel.add(forgotPasswordLabel);
        forgotPasswordLabel.setBounds(40, 440, 150, 20);

        signupLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        signupLabel.setText("Don't have an account? Sign Up");
        signupLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signupLabelMouseClicked(evt);
            }
        });
        rightPanel.add(signupLabel);
        signupLabel.setBounds(40, 470, 250, 20);

        mainPanel.add(rightPanel);
        rightPanel.setBounds(400, 0, 400, 600);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 800, 600);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        controller.exitApplication();
    }//GEN-LAST:event_closeLabelMouseClicked

    private void showPasswordCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordCheckboxActionPerformed
        controller.toggleLoginPasswordVisibility(this);
    }//GEN-LAST:event_showPasswordCheckboxActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        controller.handleLogin(this);
    }//GEN-LAST:event_loginButtonActionPerformed

    private void forgotPasswordLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotPasswordLabelMouseClicked
        controller.navigateToForgotPassword(this);
    }//GEN-LAST:event_forgotPasswordLabelMouseClicked

    private void signupLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupLabelMouseClicked
        controller.navigateToSignup(this);
    }//GEN-LAST:event_signupLabelMouseClicked

    private void userTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userTextFieldFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_userTextFieldFocusGained

    // --- Public Getters (for Controller to read UI data) ---

    public String getUsername() {
        String text = userTextField.getText().trim();
        if ("Enter Username".equals(text) && userTextField.getForeground().equals(java.awt.Color.GRAY)) {
            return "";
        }
        return text;
    }

    public String getPassword() {
        String text = new String(passwordField.getPassword()).trim();
        if ("Enter Password".equals(text) && passwordField.getForeground().equals(java.awt.Color.GRAY)) {
            return "";
        }
        return text;
    }

    public boolean isShowPasswordSelected() {
        return showPasswordCheckbox.isSelected();
    }

    public javax.swing.JTextField getUserTextField() {
        return userTextField;
    }

    public javax.swing.JPasswordField getPasswordField() {
        return passwordField;
    }

    // --- Public Setters (for Controller to update UI) ---

    public void setPasswordEchoChar(char c) {
        passwordField.setEchoChar(c);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel carLabel;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel forgotPasswordLabel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel lockIconLabel;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginTitleLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JCheckBox showPasswordCheckbox;
    private javax.swing.JLabel signupLabel;
    private javax.swing.JPanel underlinePanel;
    private javax.swing.JLabel userIconLabel;
    private javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables
}

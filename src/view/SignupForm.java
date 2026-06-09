package view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Visual Form for User Account Registration.
 * Contains only UI components.
 * No business logic, data extraction, or navigation resides in this class.
 * 
 * @author dipes
 */
public class SignupForm extends javax.swing.JFrame {

    public SignupForm() {
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        carLabel = new javax.swing.JLabel();
        rightPanel = new javax.swing.JPanel();
        signUpTitleLabel = new javax.swing.JLabel();
        underlinePanel = new javax.swing.JPanel();
        userIconLabel = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        emailIconLabel = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        lockIconLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmLockIconLabel = new javax.swing.JLabel();
        confirmPasswordField = new javax.swing.JPasswordField();
        roleLabel = new javax.swing.JLabel();
        roleComboBox = new javax.swing.JComboBox<>();
        securityQuestionComboBox = new javax.swing.JComboBox<>();
        answerIconLabel = new javax.swing.JLabel();
        securityAnswerTextField = new javax.swing.JTextField();
        showPasswordCheckbox = new javax.swing.JCheckBox();
        registerButton = new javax.swing.JButton();
        loginLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(48, 48, 48));
        mainPanel.setLayout(null);

        leftPanel.setLayout(null);

        carLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/signup_banner.png"))); // NOI18N
        leftPanel.add(carLabel);
        carLabel.setBounds(0, 0, 400, 600);

        mainPanel.add(leftPanel);
        leftPanel.setBounds(0, 0, 400, 600);

        rightPanel.setLayout(null);

        signUpTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        signUpTitleLabel.setForeground(new java.awt.Color(48, 48, 48));
        signUpTitleLabel.setText("Sign Up");
        rightPanel.add(signUpTitleLabel);
        signUpTitleLabel.setBounds(40, 20, 200, 40);

        underlinePanel.setLayout(null);
        rightPanel.add(underlinePanel);
        underlinePanel.setBounds(40, 62, 100, 4);

        userIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        rightPanel.add(userIconLabel);
        userIconLabel.setBounds(40, 78, 40, 32);

        userTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(userTextField);
        userTextField.setBounds(90, 78, 270, 32);

        emailIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/email_icon.png"))); // NOI18N
        rightPanel.add(emailIconLabel);
        emailIconLabel.setBounds(40, 120, 40, 32);

        emailTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(emailTextField);
        emailTextField.setBounds(90, 120, 270, 32);

        lockIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock_icon.png"))); // NOI18N
        rightPanel.add(lockIconLabel);
        lockIconLabel.setBounds(40, 162, 40, 32);

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(passwordField);
        passwordField.setBounds(90, 162, 270, 32);

        confirmLockIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock_icon.png"))); // NOI18N
        rightPanel.add(confirmLockIconLabel);
        confirmLockIconLabel.setBounds(40, 204, 40, 32);

        confirmPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        confirmPasswordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(confirmPasswordField);
        confirmPasswordField.setBounds(90, 204, 270, 32);

        roleLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roleLabel.setText("Register as:");
        rightPanel.add(roleLabel);
        roleLabel.setBounds(40, 250, 80, 30);

        roleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin" }));
        roleComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rightPanel.add(roleComboBox);
        roleComboBox.setBounds(125, 250, 235, 30);

        securityQuestionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "What is your pet's name?", "What city were you born in?", "What is your mother's maiden name?", "What was your first car?" }));
        rightPanel.add(securityQuestionComboBox);
        securityQuestionComboBox.setBounds(40, 295, 320, 32);

        answerIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        rightPanel.add(answerIconLabel);
        answerIconLabel.setBounds(40, 340, 40, 32);

        securityAnswerTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        securityAnswerTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(securityAnswerTextField);
        securityAnswerTextField.setBounds(90, 340, 270, 32);

        showPasswordCheckbox.setText("Show Password");
        showPasswordCheckbox.setBorder(null);
        showPasswordCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordCheckboxActionPerformed(evt);
            }
        });
        rightPanel.add(showPasswordCheckbox);
        showPasswordCheckbox.setBounds(90, 385, 200, 20);

        registerButton.setBackground(new java.awt.Color(102, 255, 153));
        registerButton.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        registerButton.setText("Sign Up");
        registerButton.setBorderPainted(false);
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });
        rightPanel.add(registerButton);
        registerButton.setBounds(40, 420, 320, 42);

        loginLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        loginLabel.setText("Already have an account? Login");
        loginLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLabelMouseClicked(evt);
            }
        });
        rightPanel.add(loginLabel);
        loginLabel.setBounds(40, 475, 250, 20);

        mainPanel.add(rightPanel);
        rightPanel.setBounds(400, 0, 400, 600);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 800, 600);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void showPasswordCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordCheckboxActionPerformed
        // Handled in Controller
    }//GEN-LAST:event_showPasswordCheckboxActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        // Handled in Controller
    }//GEN-LAST:event_registerButtonActionPerformed

    private void loginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseClicked
        // Handled in Controller
    }//GEN-LAST:event_loginLabelMouseClicked

    // --- Listener Hooks for Controller ---
    public void addShowPasswordListener(ActionListener listener) {
        showPasswordCheckbox.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addLoginLabelListener(MouseListener listener) {
        loginLabel.addMouseListener(listener);
    }

    // --- Public Getters (for Controller to read UI data) ---

    public String getUsername() {
        String text = userTextField.getText().trim();
        if ("Enter Username".equals(text) && userTextField.getForeground().equals(java.awt.Color.GRAY)) {
            return "";
        }
        return text;
    }

    public String getEmail() {
        String text = emailTextField.getText().trim();
        if ("Enter Email".equals(text) && emailTextField.getForeground().equals(java.awt.Color.GRAY)) {
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

    public String getConfirmPassword() {
        String text = new String(confirmPasswordField.getPassword()).trim();
        if ("Confirm Password".equals(text) && confirmPasswordField.getForeground().equals(java.awt.Color.GRAY)) {
            return "";
        }
        return text;
    }

    public String getSelectedRole() {
        return ((String) roleComboBox.getSelectedItem()).toLowerCase();
    }

    public String getSecurityQuestion() {
        return (String) securityQuestionComboBox.getSelectedItem();
    }

    public String getSecurityAnswer() {
        String text = securityAnswerTextField.getText().trim();
        if ("Enter Security Answer".equals(text) && securityAnswerTextField.getForeground().equals(java.awt.Color.GRAY)) {
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

    public javax.swing.JTextField getEmailTextField() {
        return emailTextField;
    }

    public javax.swing.JPasswordField getPasswordField() {
        return passwordField;
    }

    public javax.swing.JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public javax.swing.JTextField getSecurityAnswerTextField() {
        return securityAnswerTextField;
    }

    // --- Public Setters (for Controller to update UI) ---

    public void setPasswordEchoChar(char c) {
        passwordField.setEchoChar(c);
    }

    public void setConfirmPasswordEchoChar(char c) {
        confirmPasswordField.setEchoChar(c);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel answerIconLabel;
    private javax.swing.JLabel carLabel;
    private javax.swing.JLabel confirmLockIconLabel;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JLabel emailIconLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel lockIconLabel;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerButton;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JComboBox<String> roleComboBox;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JTextField securityAnswerTextField;
    private javax.swing.JComboBox<String> securityQuestionComboBox;
    private javax.swing.JCheckBox showPasswordCheckbox;
    private javax.swing.JLabel signUpTitleLabel;
    private javax.swing.JPanel underlinePanel;
    private javax.swing.JLabel userIconLabel;
    private javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables
}

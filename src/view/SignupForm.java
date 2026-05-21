package view;

/**
 * Visual Form for User Account Registration.
 * Redesigned to support security question setups and 100% drag-and-drop compliance with NetBeans GUI Builder.
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
        securityQuestionComboBox = new javax.swing.JComboBox<>();
        answerIconLabel = new javax.swing.JLabel();
        securityAnswerTextField = new javax.swing.JTextField();
        showPasswordCheckbox = new javax.swing.JCheckBox();
        registerButton = new javax.swing.JButton();
        loginLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(30, 30, 30));
        mainPanel.setLayout(null);

        leftPanel.setLayout(null);

        carLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/signup_banner.png"))); // NOI18N
        leftPanel.add(carLabel);
        carLabel.setBounds(0, 0, 400, 600);

        mainPanel.add(leftPanel);
        leftPanel.setBounds(0, 0, 400, 600);

        rightPanel.setBackground(new java.awt.Color(255, 255, 255));
        rightPanel.setLayout(null);

        signUpTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        signUpTitleLabel.setForeground(new java.awt.Color(30, 30, 30));
        signUpTitleLabel.setText("Sign Up");
        rightPanel.add(signUpTitleLabel);
        signUpTitleLabel.setBounds(40, 35, 200, 45);

        underlinePanel.setBackground(new java.awt.Color(237, 40, 54));
        underlinePanel.setLayout(null);
        rightPanel.add(underlinePanel);
        underlinePanel.setBounds(40, 82, 100, 4);

        userIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        rightPanel.add(userIconLabel);
        userIconLabel.setBounds(40, 100, 40, 35);

        userTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        rightPanel.add(userTextField);
        userTextField.setBounds(90, 100, 270, 35);

        emailIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/email_icon.png"))); // NOI18N
        rightPanel.add(emailIconLabel);
        emailIconLabel.setBounds(40, 150, 40, 35);

        emailTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        rightPanel.add(emailTextField);
        emailTextField.setBounds(90, 150, 270, 35);

        lockIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock_icon.png"))); // NOI18N
        rightPanel.add(lockIconLabel);
        lockIconLabel.setBounds(40, 200, 40, 35);

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        rightPanel.add(passwordField);
        passwordField.setBounds(90, 200, 270, 35);

        confirmLockIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock_icon.png"))); // NOI18N
        rightPanel.add(confirmLockIconLabel);
        confirmLockIconLabel.setBounds(40, 250, 40, 35);

        confirmPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        confirmPasswordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        rightPanel.add(confirmPasswordField);
        confirmPasswordField.setBounds(90, 250, 270, 35);

        securityQuestionComboBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        securityQuestionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "What is your pet's name?",
            "What city were you born in?",
            "What was your first school?",
            "What is your favorite food?"
        }));
        rightPanel.add(securityQuestionComboBox);
        securityQuestionComboBox.setBounds(40, 300, 320, 35);

        answerIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        rightPanel.add(answerIconLabel);
        answerIconLabel.setBounds(40, 350, 40, 35);

        securityAnswerTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        securityAnswerTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        rightPanel.add(securityAnswerTextField);
        securityAnswerTextField.setBounds(90, 350, 270, 35);

        showPasswordCheckbox.setBackground(new java.awt.Color(255, 255, 255));
        showPasswordCheckbox.setText("Show Password");
        showPasswordCheckbox.setBorder(null);
        showPasswordCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordCheckboxActionPerformed(evt);
            }
        });
        rightPanel.add(showPasswordCheckbox);
        showPasswordCheckbox.setBounds(90, 400, 200, 20);

        registerButton.setBackground(new java.awt.Color(0, 168, 89));
        registerButton.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        registerButton.setForeground(new java.awt.Color(255, 255, 255));
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
        registerButton.setBounds(40, 440, 320, 45);

        loginLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        loginLabel.setForeground(new java.awt.Color(242, 95, 52));
        loginLabel.setText("Already have an account? Login");
        loginLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLabelMouseClicked(evt);
            }
        });
        rightPanel.add(loginLabel);
        loginLabel.setBounds(40, 505, 250, 20);

        mainPanel.add(rightPanel);
        rightPanel.setBounds(400, 0, 400, 600);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 800, 600);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void showPasswordCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordCheckboxActionPerformed
        if (showPasswordCheckbox.isSelected()) {
            passwordField.setEchoChar((char) 0);
            confirmPasswordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('*');
            confirmPasswordField.setEchoChar('*');
        }
    }//GEN-LAST:event_showPasswordCheckboxActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        String username = userTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String question = (String) securityQuestionComboBox.getSelectedItem();
        String answer = securityAnswerTextField.getText().trim();
        
        new controller.UserController().handleSignup(this, username, email, password, confirmPassword, question, answer);
    }//GEN-LAST:event_registerButtonActionPerformed

    private void loginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseClicked
        LoginForm lForm = new LoginForm();
        lForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_loginLabelMouseClicked

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
    private javax.swing.JTextField securityAnswerTextField;
    private javax.swing.JComboBox<String> securityQuestionComboBox;
    private javax.swing.JCheckBox showPasswordCheckbox;
    private javax.swing.JLabel signUpTitleLabel;
    private javax.swing.JPanel underlinePanel;
    private javax.swing.JLabel userIconLabel;
    private javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables
}

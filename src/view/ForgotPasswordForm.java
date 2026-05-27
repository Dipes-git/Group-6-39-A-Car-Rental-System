package view;

import controller.UserController;

/**
 * Visual Form for Password Recovery utilizing Security Questions.
 * Contains only UI components and delegates all logic to UserController.
 * No business logic, data extraction, or navigation resides in this class.
 * 
 * @author dipes
 */
public class ForgotPasswordForm extends javax.swing.JFrame {

    private final UserController controller;

    public ForgotPasswordForm() {
        controller = new UserController();
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
        controller.setupForgotPasswordPlaceholders(this);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        carLabel = new javax.swing.JLabel();
        rightPanel = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        recoverTitleLabel = new javax.swing.JLabel();
        underlinePanel = new javax.swing.JPanel();
        userIconLabel = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        fetchButton = new javax.swing.JButton();
        questionLabel = new javax.swing.JLabel();
        questionDisplayLabel = new javax.swing.JLabel();
        answerIconLabel = new javax.swing.JLabel();
        securityAnswerTextField = new javax.swing.JTextField();
        newPasswordIconLabel = new javax.swing.JLabel();
        newPasswordField = new javax.swing.JPasswordField();
        confirmPasswordIconLabel = new javax.swing.JLabel();
        confirmNewPasswordField = new javax.swing.JPasswordField();
        resetButton = new javax.swing.JButton();
        backToLoginLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(48, 48, 48));
        mainPanel.setLayout(null);

        leftPanel.setLayout(null);

        carLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/recover_banner.png"))); // NOI18N
        leftPanel.add(carLabel);
        carLabel.setBounds(0, 0, 400, 600);

        mainPanel.add(leftPanel);
        leftPanel.setBounds(0, 0, 400, 600);

        rightPanel.setBackground(new java.awt.Color(204, 204, 255));
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

        recoverTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        recoverTitleLabel.setForeground(new java.awt.Color(48, 48, 48));
        recoverTitleLabel.setText("Recover");
        rightPanel.add(recoverTitleLabel);
        recoverTitleLabel.setBounds(40, 50, 200, 50);

        underlinePanel.setLayout(null);
        rightPanel.add(underlinePanel);
        underlinePanel.setBounds(40, 102, 100, 4);

        userIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        rightPanel.add(userIconLabel);
        userIconLabel.setBounds(40, 130, 40, 35);

        userTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(userTextField);
        userTextField.setBounds(90, 130, 170, 35);

        fetchButton.setBackground(new java.awt.Color(255, 153, 0));
        fetchButton.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        fetchButton.setText("Fetch Question");
        fetchButton.setBorderPainted(false);
        fetchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fetchButton.setFocusPainted(false);
        fetchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fetchButtonActionPerformed(evt);
            }
        });
        rightPanel.add(fetchButton);
        fetchButton.setBounds(270, 130, 110, 35);

        questionLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        questionLabel.setText("Security Question:");
        rightPanel.add(questionLabel);
        questionLabel.setBounds(40, 185, 320, 20);

        questionDisplayLabel.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        questionDisplayLabel.setForeground(new java.awt.Color(72, 72, 72));
        questionDisplayLabel.setText("Retrieve your question first.");
        rightPanel.add(questionDisplayLabel);
        questionDisplayLabel.setBounds(40, 210, 320, 30);

        answerIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        rightPanel.add(answerIconLabel);
        answerIconLabel.setBounds(40, 255, 40, 35);

        securityAnswerTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        securityAnswerTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(securityAnswerTextField);
        securityAnswerTextField.setBounds(90, 255, 270, 35);

        newPasswordIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock_icon.png"))); // NOI18N
        rightPanel.add(newPasswordIconLabel);
        newPasswordIconLabel.setBounds(40, 310, 40, 35);

        newPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        newPasswordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(newPasswordField);
        newPasswordField.setBounds(90, 310, 270, 35);

        confirmPasswordIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock_icon.png"))); // NOI18N
        rightPanel.add(confirmPasswordIconLabel);
        confirmPasswordIconLabel.setBounds(40, 365, 40, 35);

        confirmNewPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        confirmNewPasswordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(confirmNewPasswordField);
        confirmNewPasswordField.setBounds(90, 365, 270, 35);

        resetButton.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        resetButton.setText("Reset Password");
        resetButton.setBorderPainted(false);
        resetButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        rightPanel.add(resetButton);
        resetButton.setBounds(40, 425, 320, 45);

        backToLoginLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backToLoginLabel.setText("Back to Login");
        backToLoginLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backToLoginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backToLoginLabelMouseClicked(evt);
            }
        });
        rightPanel.add(backToLoginLabel);
        backToLoginLabel.setBounds(40, 495, 250, 20);

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

    private void backToLoginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backToLoginLabelMouseClicked
        controller.navigateToLogin(this);
    }//GEN-LAST:event_backToLoginLabelMouseClicked

    private void fetchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fetchButtonActionPerformed
        controller.handleFetchQuestion(this);
    }//GEN-LAST:event_fetchButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        controller.handleResetPassword(this);
    }//GEN-LAST:event_resetButtonActionPerformed

    // --- Public Getters (for Controller to read UI data) ---

    public String getUsername() {
        String text = userTextField.getText().trim();
        if ("Enter Username".equals(text) && userTextField.getForeground().equals(java.awt.Color.GRAY)) {
            return "";
        }
        return text;
    }

    public String getSecurityAnswer() {
        String text = securityAnswerTextField.getText().trim();
        if ("Enter Security Answer".equals(text) && securityAnswerTextField.getForeground().equals(java.awt.Color.GRAY)) {
            return "";
        }
        return text;
    }

    public String getNewPassword() {
        String text = new String(newPasswordField.getPassword()).trim();
        if ("Enter New Password".equals(text) && newPasswordField.getForeground().equals(java.awt.Color.GRAY)) {
            return "";
        }
        return text;
    }

    public String getConfirmPassword() {
        String text = new String(confirmNewPasswordField.getPassword()).trim();
        if ("Confirm New Password".equals(text) && confirmNewPasswordField.getForeground().equals(java.awt.Color.GRAY)) {
            return "";
        }
        return text;
    }

    public javax.swing.JTextField getUserTextField() {
        return userTextField;
    }

    public javax.swing.JTextField getSecurityAnswerTextField() {
        return securityAnswerTextField;
    }

    public javax.swing.JPasswordField getNewPasswordField() {
        return newPasswordField;
    }

    public javax.swing.JPasswordField getConfirmNewPasswordField() {
        return confirmNewPasswordField;
    }

    // --- Public Setters (for Controller to update UI) ---

    public void setQuestionDisplayText(String text) {
        questionDisplayLabel.setText(text);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel answerIconLabel;
    private javax.swing.JLabel backToLoginLabel;
    private javax.swing.JLabel carLabel;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JPasswordField confirmNewPasswordField;
    private javax.swing.JLabel confirmPasswordIconLabel;
    private javax.swing.JButton fetchButton;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPasswordField newPasswordField;
    private javax.swing.JLabel newPasswordIconLabel;
    private javax.swing.JLabel questionDisplayLabel;
    private javax.swing.JLabel questionLabel;
    private javax.swing.JLabel recoverTitleLabel;
    private javax.swing.JButton resetButton;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTextField securityAnswerTextField;
    private javax.swing.JPanel underlinePanel;
    private javax.swing.JLabel userIconLabel;
    private javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables
}

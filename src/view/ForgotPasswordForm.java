package view;

public class ForgotPasswordForm extends javax.swing.JFrame {

    public ForgotPasswordForm() {
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
        closeLabel = new javax.swing.JLabel();
        recoverTitleLabel = new javax.swing.JLabel();
        underlinePanel = new javax.swing.JPanel();
        userIconLabel = new javax.swing.JLabel();
        userTextField = new javax.swing.JTextField();
        emailIconLabel = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        recoverButton = new javax.swing.JButton();
        backToLoginLabel = new javax.swing.JLabel();

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

        recoverTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        recoverTitleLabel.setForeground(new java.awt.Color(48, 48, 48));
        recoverTitleLabel.setText("Recover");
        rightPanel.add(recoverTitleLabel);
        recoverTitleLabel.setBounds(40, 100, 200, 50);

        underlinePanel.setLayout(null);
        rightPanel.add(underlinePanel);
        underlinePanel.setBounds(40, 152, 100, 4);

        userIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_icon.png"))); // NOI18N
        rightPanel.add(userIconLabel);
        userIconLabel.setBounds(40, 220, 40, 40);

        userTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(userTextField);
        userTextField.setBounds(90, 220, 270, 40);

        emailIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/email_icon.png"))); // NOI18N
        rightPanel.add(emailIconLabel);
        emailIconLabel.setBounds(40, 280, 40, 40);

        emailTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightPanel.add(emailTextField);
        emailTextField.setBounds(90, 280, 270, 40);

        recoverButton.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        recoverButton.setText("Recover Password");
        recoverButton.setBorderPainted(false);
        recoverButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        recoverButton.setFocusPainted(false);
        recoverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recoverButtonActionPerformed(evt);
            }
        });
        rightPanel.add(recoverButton);
        recoverButton.setBounds(40, 360, 320, 45);

        backToLoginLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        backToLoginLabel.setText("Back to Login");
        backToLoginLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backToLoginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backToLoginLabelMouseClicked(evt);
            }
        });
        rightPanel.add(backToLoginLabel);
        backToLoginLabel.setBounds(40, 430, 250, 20);

        mainPanel.add(rightPanel);
        rightPanel.setBounds(400, 0, 400, 600);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 800, 600);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeLabelMouseClicked

    private void recoverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recoverButtonActionPerformed
        String username = userTextField.getText().trim();
        String email = emailTextField.getText().trim();
        
        new controller.UserController().handlePasswordRecovery(this, username, email);
    }//GEN-LAST:event_recoverButtonActionPerformed

    private void backToLoginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backToLoginLabelMouseClicked
        LoginForm lForm = new LoginForm();
        lForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backToLoginLabelMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backToLoginLabel;
    private javax.swing.JLabel carLabel;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel emailIconLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton recoverButton;
    private javax.swing.JLabel recoverTitleLabel;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JPanel underlinePanel;
    private javax.swing.JLabel userIconLabel;
    private javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables
}

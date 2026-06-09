package view;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Reusable visual JPanel component managing Customer database listings,
 * account status, and administrative suspend/activation/deletion.
 * 
 * Strict MVC compliance: Contains zero logic or database code.
 * All user actions are delegated directly to the UserController.
 * Fully compatible with NetBeans Matisse GUI builder drag-and-drop.
 * 
 * @author dipes
 */
public class CustomerPanel extends javax.swing.JPanel {

    public CustomerPanel() {
        initComponents();
        setupListeners();
    }

    private void setupListeners() {
        // Clear Action
        btnClear.addActionListener(evt -> clearInputs());

        // Navigation
        btnFirst.addActionListener(evt -> selectRow(0));
        btnLast.addActionListener(evt -> selectRow(customersTable.getRowCount() - 1));
        btnNext.addActionListener(evt -> {
            int current = customersTable.getSelectedRow();
            if (current < customersTable.getRowCount() - 1) {
                selectRow(current + 1);
            }
        });
        btnPrev.addActionListener(evt -> {
            int current = customersTable.getSelectedRow();
            if (current > 0) {
                selectRow(current - 1);
            }
        });

        // Table Selection Listener
        customersTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                int selectedRow = customersTable.getSelectedRow();
                if (selectedRow != -1) {
                    userIdField.setText(customersTable.getValueAt(selectedRow, 0).toString());
                    usernameField.setText(customersTable.getValueAt(selectedRow, 1).toString());
                    emailField.setText(customersTable.getValueAt(selectedRow, 2).toString());
                    statusField.setText(customersTable.getValueAt(selectedRow, 4).toString());
                }
            }
        });
    }

    public void selectRow(int index) {
        if (customersTable.getRowCount() > 0 && index >= 0 && index < customersTable.getRowCount()) {
            customersTable.setRowSelectionInterval(index, index);
            customersTable.scrollRectToVisible(customersTable.getCellRect(index, 0, true));
        }
    }

    public void clearInputs() {
        userIdField.setText("");
        usernameField.setText("");
        emailField.setText("");
        statusField.setText("");
        searchField.setText("");
        customersTable.clearSelection();
    }

    // --- Public Getters/Setters for UserController ---

    public JTable getCustomersTable() {
        return customersTable;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JTextField getUserIdField() {
        return userIdField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getStatusField() {
        return statusField;
    }

    public JButton getBtnToggleStatus() {
        return btnToggleStatus;
    }

    public JButton getBtnDeleteCustomer() {
        return btnDeleteCustomer;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public JButton getBtnClear() {
        return btnClear;
    }

    // --- NetBeans Generated GUI Components ---
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customersManageTitle = new javax.swing.JLabel();
        searchLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        customersScrollPane = new javax.swing.JScrollPane();
        userIdLabel = new javax.swing.JLabel();
        userIdField = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        statusLabel = new javax.swing.JLabel();
        statusField = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnToggleStatus = new javax.swing.JButton();
        btnDeleteCustomer = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        customersBgLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(45, 45, 45));
        setLayout(null);

        customersManageTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        customersManageTitle.setForeground(new java.awt.Color(255, 255, 255));
        customersManageTitle.setText("Manage Customers");
        add(customersManageTitle);
        customersManageTitle.setBounds(30, 20, 300, 35);

        searchLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchLabel.setText("Search:");
        add(searchLabel);
        searchLabel.setBounds(320, 25, 50, 25);

        searchField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        add(searchField);
        searchField.setBounds(380, 25, 190, 25);

        customersTable = new javax.swing.JTable();
        customersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Username", "Email", "Role", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        // Apply Premium Dark styling
        customersTable.setBackground(new java.awt.Color(50, 50, 50));
        customersTable.setForeground(java.awt.Color.WHITE);
        customersTable.setGridColor(new java.awt.Color(70, 70, 70));
        customersTable.setSelectionBackground(new java.awt.Color(0, 102, 153));
        customersTable.setSelectionForeground(java.awt.Color.WHITE);
        customersTable.setRowHeight(25);
        customersTable.getTableHeader().setBackground(new java.awt.Color(74, 83, 97));
        customersTable.getTableHeader().setForeground(java.awt.Color.WHITE);
        customersTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 12));

        customersScrollPane.setViewportView(customersTable);

        add(customersScrollPane);
        customersScrollPane.setBounds(30, 80, 540, 220);

        userIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        userIdLabel.setForeground(new java.awt.Color(255, 255, 255));
        userIdLabel.setText("ID:");
        add(userIdLabel);
        userIdLabel.setBounds(30, 320, 50, 25);

        userIdField.setEditable(false);
        userIdField.setBackground(new java.awt.Color(58, 58, 58));
        userIdField.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        userIdField.setForeground(new java.awt.Color(255, 255, 255));
        add(userIdField);
        userIdField.setBounds(90, 320, 80, 25);

        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabel.setText("Username:");
        add(usernameLabel);
        usernameLabel.setBounds(190, 320, 80, 25);

        usernameField.setEditable(false);
        usernameField.setBackground(new java.awt.Color(58, 58, 58));
        usernameField.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        usernameField.setForeground(new java.awt.Color(255, 255, 255));
        add(usernameField);
        usernameField.setBounds(270, 320, 110, 25);

        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("Email:");
        add(emailLabel);
        emailLabel.setBounds(30, 360, 50, 25);

        emailField.setEditable(false);
        emailField.setBackground(new java.awt.Color(58, 58, 58));
        emailField.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        emailField.setForeground(new java.awt.Color(255, 255, 255));
        add(emailField);
        emailField.setBounds(90, 360, 290, 25);

        statusLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(255, 255, 255));
        statusLabel.setText("Status:");
        add(statusLabel);
        statusLabel.setBounds(30, 400, 50, 25);

        statusField.setEditable(false);
        statusField.setBackground(new java.awt.Color(58, 58, 58));
        statusField.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        statusField.setForeground(new java.awt.Color(255, 255, 255));
        add(statusField);
        statusField.setBounds(90, 400, 120, 25);

        btnFirst.setBackground(new java.awt.Color(80, 80, 80));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnFirst.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst.setText("<<");
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnFirst);
        btnFirst.setBounds(400, 320, 38, 25);

        btnPrev.setBackground(new java.awt.Color(80, 80, 80));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnPrev.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev.setText("<");
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnPrev);
        btnPrev.setBounds(442, 320, 38, 25);

        btnNext.setBackground(new java.awt.Color(80, 80, 80));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">");
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnNext);
        btnNext.setBounds(484, 320, 38, 25);

        btnLast.setBackground(new java.awt.Color(80, 80, 80));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnLast.setForeground(new java.awt.Color(255, 255, 255));
        btnLast.setText(">>");
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnLast);
        btnLast.setBounds(526, 320, 38, 25);

        btnToggleStatus.setBackground(new java.awt.Color(0, 153, 102));
        btnToggleStatus.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnToggleStatus.setForeground(new java.awt.Color(255, 255, 255));
        btnToggleStatus.setText("Toggle Active Status");
        btnToggleStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnToggleStatus);
        btnToggleStatus.setBounds(400, 360, 164, 30);

        btnDeleteCustomer.setBackground(new java.awt.Color(237, 40, 54));
        btnDeleteCustomer.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnDeleteCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteCustomer.setText("Delete Customer");
        btnDeleteCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnDeleteCustomer);
        btnDeleteCustomer.setBounds(400, 400, 164, 30);

        btnRefresh.setBackground(new java.awt.Color(80, 80, 80));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnRefresh);
        btnRefresh.setBounds(400, 440, 77, 30);

        btnClear.setBackground(new java.awt.Color(120, 120, 120));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnClear);
        btnClear.setBounds(487, 440, 77, 30);

        customersBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin_bg.png"))); // NOI18N
        add(customersBgLabel);
        customersBgLabel.setBounds(0, 0, 600, 520);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDeleteCustomer;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnToggleStatus;
    private javax.swing.JLabel customersBgLabel;
    private javax.swing.JLabel customersManageTitle;
    private javax.swing.JScrollPane customersScrollPane;
    private javax.swing.JTable customersTable;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField statusField;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextField userIdField;
    private javax.swing.JLabel userIdLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
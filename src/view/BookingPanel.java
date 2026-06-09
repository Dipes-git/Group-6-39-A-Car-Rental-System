package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Visual JPanel container for Bookings management.
 * Designed exclusively in NetBeans Matisse drag and drop XML structure.
 * Decoupled from logic - exposes components via getters/setters for controllers.
 * 
 * @author dipes
 */
public class BookingPanel extends JPanel {

    private boolean isAdminMode = true;

    public BookingPanel() {
        initComponents();
        setAdminMode(true); // Default to admin mode
    }

    /**
     * Configures the layout, labels, and button visibilities for User vs Admin roles.
     */
    public void setAdminMode(boolean admin) {
        this.isAdminMode = admin;
        
        // Change title text
        bookingsTitle.setText(admin ? "Manage Bookings" : "My Rental History");
        
        // Load correct background image resource
        try {
            String bgResource = admin ? "/images/admin_bg.png" : "/images/user_bg.png";
            java.net.URL bgUrl = getClass().getResource(bgResource);
            if (bgUrl != null) {
                bgLabel.setIcon(new javax.swing.ImageIcon(bgUrl));
            }
        } catch (Exception e) {
            System.err.println("[BookingPanel] Background load error: " + e.getMessage());
        }
        
        // Show/hide administrative buttons
        btnApprove.setVisible(admin);
        btnReject.setVisible(admin);
        btnReturn.setVisible(admin);
        
        // Always show Edit / Cancel Booking button
        btnEditCancel.setVisible(true);
        
        if (admin) {
            btnApprove.setBounds(30, 390, 100, 35);
            btnReject.setBounds(140, 390, 100, 35);
            btnReturn.setBounds(250, 390, 120, 35);
            btnEditCancel.setBounds(380, 390, 110, 35);
            btnEditCancel.setText("Edit / Cancel");
            btnRefresh.setBounds(500, 390, 80, 35);
            btnRefresh.setText("Refresh");
        } else {
            // In user mode, align nicely
            btnRefresh.setBounds(30, 390, 260, 35);
            btnRefresh.setText("Refresh History");
            btnEditCancel.setBounds(310, 390, 260, 35);
            btnEditCancel.setText("Edit / Cancel Booking");
        }
        
        // Adjust JTable Columns visibility or headers if needed
        if (!admin) {
            // Under user mode, we can hide the "User" column (index 1) to make the table look cleaner
            bookingsTable.getColumnModel().getColumn(1).setMinWidth(0);
            bookingsTable.getColumnModel().getColumn(1).setMaxWidth(0);
            bookingsTable.getColumnModel().getColumn(1).setWidth(0);
        } else {
            // Re-enable User column in admin mode
            bookingsTable.getColumnModel().getColumn(1).setMinWidth(50);
            bookingsTable.getColumnModel().getColumn(1).setMaxWidth(150);
            bookingsTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        }
        scrollPane.setBounds(30, 80, 540, 290);
    }

    public boolean isAdminMode() {
        return isAdminMode;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        bookingsTitle = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        bookingsTable = new javax.swing.JTable();
        btnApprove = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnEditCancel = new javax.swing.JButton();
        bgLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(45, 45, 45));
        setPreferredSize(new java.awt.Dimension(600, 520));
        setLayout(null);

        headerPanel.setBackground(new java.awt.Color(74, 83, 97));
        headerPanel.setLayout(null);

        bookingsTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        bookingsTitle.setForeground(new java.awt.Color(255, 255, 255));
        bookingsTitle.setText("Manage Bookings");
        headerPanel.add(bookingsTitle);
        bookingsTitle.setBounds(30, 10, 400, 40);

        add(headerPanel);
        headerPanel.setBounds(0, 0, 600, 60);

        bookingsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "User", "Car", "Pickup Branch", "Return Branch", "Start Date", "End Date", "Price", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        // Apply Premium Dark styling
        bookingsTable.setBackground(new java.awt.Color(50, 50, 50));
        bookingsTable.setForeground(java.awt.Color.WHITE);
        bookingsTable.setGridColor(new java.awt.Color(70, 70, 70));
        bookingsTable.setSelectionBackground(new java.awt.Color(0, 102, 153));
        bookingsTable.setSelectionForeground(java.awt.Color.WHITE);
        bookingsTable.setRowHeight(25);
        bookingsTable.getTableHeader().setBackground(new java.awt.Color(74, 83, 97));
        bookingsTable.getTableHeader().setForeground(java.awt.Color.WHITE);
        bookingsTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 12));

        scrollPane.setViewportView(bookingsTable);

        add(scrollPane);
        scrollPane.setBounds(30, 80, 540, 290);

        btnApprove.setBackground(new java.awt.Color(39, 174, 96));
        btnApprove.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnApprove.setForeground(new java.awt.Color(255, 255, 255));
        btnApprove.setText("Approve");
        add(btnApprove);
        btnApprove.setBounds(30, 390, 110, 35);

        btnReject.setBackground(new java.awt.Color(192, 57, 43));
        btnReject.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReject.setForeground(new java.awt.Color(255, 255, 255));
        btnReject.setText("Reject");
        add(btnReject);
        btnReject.setBounds(150, 390, 110, 35);

        btnReturn.setBackground(new java.awt.Color(41, 128, 185));
        btnReturn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReturn.setForeground(new java.awt.Color(255, 255, 255));
        btnReturn.setText("Mark Returned");
        add(btnReturn);
        btnReturn.setBounds(270, 390, 140, 35);

        btnRefresh.setBackground(new java.awt.Color(74, 83, 97));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        add(btnRefresh);
        btnRefresh.setBounds(420, 390, 150, 35);

        btnEditCancel.setBackground(new java.awt.Color(41, 128, 185));
        btnEditCancel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnEditCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnEditCancel.setText("Edit / Cancel Booking");
        add(btnEditCancel);
        btnEditCancel.setBounds(250, 390, 160, 35);

        bgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin_bg.png"))); // NOI18N
        add(bgLabel);
        bgLabel.setBounds(0, 0, 600, 520);
    }// </editor-fold>//GEN-END:initComponents

    // --- Component Getters ---
    public JTable getBookingsTable() {
        return bookingsTable;
    }

    public JButton getBtnApprove() {
        return btnApprove;
    }

    public JButton getBtnReject() {
        return btnReject;
    }

    public JButton getBtnReturn() {
        return btnReturn;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public JButton getBtnEditCancel() {
        return btnEditCancel;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bgLabel;
    private javax.swing.JTable bookingsTable;
    private javax.swing.JLabel bookingsTitle;
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnEditCancel;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReject;
    private javax.swing.JButton btnReturn;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}

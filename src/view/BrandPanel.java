package view;

import controller.BrandController;
import java.io.File;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Reusable visual JPanel component managing Brand inventory lists,
 * logo previews, and CRUD administrative interactions.
 * 
 * Natively supports drag-and-drop embedding in both AdminDashboard
 * and UserDashboard with automatic mode toggling.
 * 
 * @author dipes
 */
public class BrandPanel extends javax.swing.JPanel {

    private final BrandController brandController;
    private File selectedLogoFile;
    private boolean isAdminMode = true;
    
    // Decoupled callbacks for dashboard navigation and catalog filtering
    private java.util.function.Consumer<String> onViewFleetCallback;

    public BrandPanel() {
        brandController = new BrandController();
        initComponents();
        setupListeners();
        setAdminMode(true); // Default to admin mode on startup
    }

    /**
     * Toggles between full CRUD Admin mode and read-only User browsing mode.
     */
    public void setAdminMode(boolean admin) {
        this.isAdminMode = admin;
        
        // Title text adjustment
        brandsManageTitle.setText(admin ? "Manage Brands" : "Browse Car Brands");
        
        // Hide/show admin specific fields & labels
        brandIdLabel.setVisible(admin);
        brandIdField.setVisible(admin);
        brandNameLabel.setVisible(admin);
        brandNameField.setVisible(admin);
        brandLogoLabel.setVisible(admin);
        btnBrowseLogo.setVisible(admin);
        
        // Hide/show CRUD buttons
        btnAddBrand.setVisible(admin);
        btnUpdateBrand.setVisible(admin);
        btnDeleteBrand.setVisible(admin);
        btnClearBrand.setVisible(admin);
        
        // Hide/show user specific View Fleet button
        btnViewFleet.setVisible(!admin);
        
        // Refresh and navigation buttons stay visible in both modes
        btnRefreshBrand.setVisible(true);
        btnBrandFirst.setVisible(true);
        btnBrandLast.setVisible(true);
        btnBrandNext.setVisible(true);
        btnBrandPrev.setVisible(true);
        
        // If switching to user mode, clear selections and values
        if (!admin) {
            clearBrandInputs();
        }
    }

    private void setupListeners() {
        // Load initial records
        brandController.loadBrandTable(this);

        // CRUD Button Actions
        btnAddBrand.addActionListener(evt -> brandController.handleAddBrand(this));
        btnUpdateBrand.addActionListener(evt -> brandController.handleUpdateBrand(this));
        btnDeleteBrand.addActionListener(evt -> brandController.handleDeleteBrand(this));
        btnRefreshBrand.addActionListener(evt -> {
            brandController.loadBrandTable(this);
            clearBrandInputs();
        });
        btnClearBrand.addActionListener(evt -> clearBrandInputs());
        btnBrowseLogo.addActionListener(evt -> brandController.handleBrowseLogo(this));

        // Navigation Actions
        btnBrandFirst.addActionListener(evt -> selectBrandRow(0));
        btnBrandLast.addActionListener(evt -> selectBrandRow(brandsTable.getRowCount() - 1));
        btnBrandNext.addActionListener(evt -> {
            int current = brandsTable.getSelectedRow();
            if (current < brandsTable.getRowCount() - 1) {
                selectBrandRow(current + 1);
            }
        });
        btnBrandPrev.addActionListener(evt -> {
            int current = brandsTable.getSelectedRow();
            if (current > 0) {
                selectBrandRow(current - 1);
            }
        });

        // User Custom Callback for filtering
        btnViewFleet.addActionListener(evt -> {
            int selectedRow = brandsTable.getSelectedRow();
            if (selectedRow != -1 && onViewFleetCallback != null) {
                String brandName = brandsTable.getValueAt(selectedRow, 1).toString();
                onViewFleetCallback.accept(brandName);
            }
        });

        // Table List Selection Listener to update visual fields & logos
        brandsTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                int selectedRow = brandsTable.getSelectedRow();
                if (selectedRow != -1) {
                    brandIdField.setText(brandsTable.getValueAt(selectedRow, 0).toString());
                    brandNameField.setText(brandsTable.getValueAt(selectedRow, 1).toString());
                    String logoPath = brandsTable.getValueAt(selectedRow, 2).toString();
                    showLogoPreview(logoPath);
                }
            }
        });
    }

    public void selectBrandRow(int index) {
        if (brandsTable.getRowCount() > 0 && index >= 0 && index < brandsTable.getRowCount()) {
            brandsTable.setRowSelectionInterval(index, index);
            brandsTable.scrollRectToVisible(brandsTable.getCellRect(index, 0, true));
        }
    }

    public void clearBrandInputs() {
        brandIdField.setText("");
        brandNameField.setText("");
        selectedLogoFile = null;
        logoPreviewLabel.setIcon(null);
        logoPreviewLabel.setText("No Logo Preview");
        brandsTable.clearSelection();
    }

    public void showLogoPreview(String path) {
        if (path == null || path.isEmpty()) {
            logoPreviewLabel.setIcon(null);
            logoPreviewLabel.setText("No Logo Preview");
            return;
        }

        try {
            ImageIcon icon = null;
            if (path.startsWith("/")) {
                URL imgUrl = getClass().getResource(path);
                if (imgUrl != null) {
                    icon = new ImageIcon(imgUrl);
                } else {
                    File file = new File("src" + path);
                    if (!file.exists()) file = new File("build/classes" + path);
                    if (file.exists()) {
                        icon = new ImageIcon(file.getAbsolutePath());
                    }
                }
            } else {
                File file = new File(path);
                if (file.exists()) {
                    icon = new ImageIcon(file.getAbsolutePath());
                }
            }

            if (icon != null) {
                Image img = icon.getImage().getScaledInstance(logoPreviewLabel.getWidth(), logoPreviewLabel.getHeight(), Image.SCALE_SMOOTH);
                logoPreviewLabel.setIcon(new ImageIcon(img));
                logoPreviewLabel.setText("");
            } else {
                logoPreviewLabel.setIcon(null);
                logoPreviewLabel.setText("No Preview");
            }
        } catch (Exception e) {
            logoPreviewLabel.setIcon(null);
            logoPreviewLabel.setText("Preview Error");
        }
    }

    // --- Public Getters/Setters for controllers ---
    public JTable getBrandsTable() {
        return brandsTable;
    }

    public File getSelectedLogoFile() {
        return selectedLogoFile;
    }

    public void setSelectedLogoFile(File file) {
        this.selectedLogoFile = file;
    }

    public String getBrandNameInput() {
        return brandNameField.getText().trim();
    }

    public String getBrandIdInput() {
        return brandIdField.getText().trim();
    }

    public void setOnViewFleetCallback(java.util.function.Consumer<String> callback) {
        this.onViewFleetCallback = callback;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        brandsManageTitle = new javax.swing.JLabel();
        brandsScrollPane = new javax.swing.JScrollPane();
        brandsTable = new javax.swing.JTable();
        brandIdLabel = new javax.swing.JLabel();
        brandIdField = new javax.swing.JTextField();
        brandNameLabel = new javax.swing.JLabel();
        brandNameField = new javax.swing.JTextField();
        brandLogoLabel = new javax.swing.JLabel();
        logoPreviewLabel = new javax.swing.JLabel();
        btnBrowseLogo = new javax.swing.JButton();
        btnAddBrand = new javax.swing.JButton();
        btnUpdateBrand = new javax.swing.JButton();
        btnDeleteBrand = new javax.swing.JButton();
        btnRefreshBrand = new javax.swing.JButton();
        btnClearBrand = new javax.swing.JButton();
        btnBrandFirst = new javax.swing.JButton();
        btnBrandNext = new javax.swing.JButton();
        btnBrandPrev = new javax.swing.JButton();
        btnBrandLast = new javax.swing.JButton();
        btnViewFleet = new javax.swing.JButton();
        brandsBgLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(45, 45, 45));
        setLayout(null);

        brandsManageTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        brandsManageTitle.setForeground(new java.awt.Color(255, 255, 255));
        brandsManageTitle.setText("Manage Brands");
        add(brandsManageTitle);
        brandsManageTitle.setBounds(30, 20, 300, 35);

        brandsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Logo Path"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        brandsScrollPane.setViewportView(brandsTable);

        add(brandsScrollPane);
        brandsScrollPane.setBounds(320, 80, 250, 260);

        brandIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        brandIdLabel.setForeground(new java.awt.Color(255, 255, 255));
        brandIdLabel.setText("ID:");
        add(brandIdLabel);
        brandIdLabel.setBounds(30, 80, 50, 25);

        brandIdField.setEditable(false);
        brandIdField.setBackground(new java.awt.Color(58, 58, 58));
        brandIdField.setForeground(new java.awt.Color(255, 255, 255));
        add(brandIdField);
        brandIdField.setBounds(120, 80, 80, 25);

        brandNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        brandNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        brandNameLabel.setText("Name:");
        add(brandNameLabel);
        brandNameLabel.setBounds(30, 120, 80, 25);
        add(brandNameField);
        brandNameField.setBounds(120, 120, 180, 25);

        brandLogoLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        brandLogoLabel.setForeground(new java.awt.Color(255, 255, 255));
        brandLogoLabel.setText("Logo:");
        add(brandLogoLabel);
        brandLogoLabel.setBounds(30, 160, 80, 25);

        logoPreviewLabel.setBackground(new java.awt.Color(58, 58, 58));
        logoPreviewLabel.setOpaque(true);
        logoPreviewLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoPreviewLabel.setText("No Logo Preview");
        logoPreviewLabel.setForeground(new java.awt.Color(170, 170, 170));
        add(logoPreviewLabel);
        logoPreviewLabel.setBounds(120, 160, 180, 120);

        btnBrowseLogo.setBackground(new java.awt.Color(0, 102, 153));
        btnBrowseLogo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBrowseLogo.setForeground(new java.awt.Color(255, 255, 255));
        btnBrowseLogo.setText("Browse");
        btnBrowseLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnBrowseLogo);
        btnBrowseLogo.setBounds(120, 290, 180, 30);

        btnAddBrand.setBackground(new java.awt.Color(0, 102, 153));
        btnAddBrand.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnAddBrand.setForeground(new java.awt.Color(255, 255, 255));
        btnAddBrand.setText("Add");
        btnAddBrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnAddBrand);
        btnAddBrand.setBounds(30, 360, 80, 35);

        btnUpdateBrand.setBackground(new java.awt.Color(0, 153, 102));
        btnUpdateBrand.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUpdateBrand.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateBrand.setText("Edit");
        btnUpdateBrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnUpdateBrand);
        btnUpdateBrand.setBounds(120, 360, 80, 35);

        btnDeleteBrand.setBackground(new java.awt.Color(204, 34, 34));
        btnDeleteBrand.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnDeleteBrand.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteBrand.setText("Remove");
        btnDeleteBrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnDeleteBrand);
        btnDeleteBrand.setBounds(210, 360, 90, 35);

        btnRefreshBrand.setBackground(new java.awt.Color(230, 126, 52));
        btnRefreshBrand.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnRefreshBrand.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshBrand.setText("Refresh");
        btnRefreshBrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnRefreshBrand);
        btnRefreshBrand.setBounds(30, 410, 130, 35);

        btnClearBrand.setBackground(new java.awt.Color(255, 204, 0));
        btnClearBrand.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnClearBrand.setText("Clear");
        btnClearBrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnClearBrand);
        btnClearBrand.setBounds(170, 410, 130, 35);

        btnBrandFirst.setText("<<");
        btnBrandFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnBrandFirst);
        btnBrandFirst.setBounds(320, 360, 50, 30);

        btnBrandNext.setText(">");
        btnBrandNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnBrandNext);
        btnBrandNext.setBounds(380, 360, 50, 30);

        btnBrandPrev.setText("<");
        btnBrandPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnBrandPrev);
        btnBrandPrev.setBounds(440, 360, 50, 30);

        btnBrandLast.setText(">>");
        btnBrandLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnBrandLast);
        btnBrandLast.setBounds(500, 360, 50, 30);

        btnViewFleet.setBackground(new java.awt.Color(0, 102, 153));
        btnViewFleet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnViewFleet.setForeground(new java.awt.Color(255, 255, 255));
        btnViewFleet.setText("Browse Cars of this Brand");
        btnViewFleet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnViewFleet);
        btnViewFleet.setBounds(30, 360, 270, 45);

        brandsBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin_bg.png"))); // NOI18N
        add(brandsBgLabel);
        brandsBgLabel.setBounds(0, 0, 600, 520);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField brandIdField;
    private javax.swing.JLabel brandIdLabel;
    private javax.swing.JLabel brandLogoLabel;
    private javax.swing.JTextField brandNameField;
    private javax.swing.JLabel brandNameLabel;
    private javax.swing.JLabel brandsBgLabel;
    private javax.swing.JLabel brandsManageTitle;
    private javax.swing.JScrollPane brandsScrollPane;
    private javax.swing.JTable brandsTable;
    private javax.swing.JButton btnAddBrand;
    private javax.swing.JButton btnBrandFirst;
    private javax.swing.JButton btnBrandLast;
    private javax.swing.JButton btnBrandNext;
    private javax.swing.JButton btnBrandPrev;
    private javax.swing.JButton btnBrowseLogo;
    private javax.swing.JButton btnClearBrand;
    private javax.swing.JButton btnDeleteBrand;
    private javax.swing.JButton btnRefreshBrand;
    private javax.swing.JButton btnUpdateBrand;
    private javax.swing.JButton btnViewFleet;
    private javax.swing.JLabel logoPreviewLabel;
    // End of variables declaration//GEN-END:variables
}

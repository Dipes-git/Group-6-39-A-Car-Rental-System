package view;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

/**
 * Reusable visual JPanel component managing Location database listings,
 * addresses, and administrative operations (Add, Edit, Remove, Reresh, Clear).
 * 
 * Strict MVC compliance: Contains zero database code or business logic.
 * All events are delegated directly to the LocationController.
 * Compatible with NetBeans Matisse GUI builder drag-and-drop.
 * 
 * @author dipes
 */
public class LocationPanel extends javax.swing.JPanel {

    public LocationPanel() {
        initComponents();
        styleComponents();
        setupListeners();
        generateHeaderIcon();
    }

    private void styleComponents() {
        // ID Spinner initial config
        idSpinner.setModel(new SpinnerNumberModel(0, 0, 9999, 1));
        
        // Button Custom Styling to match premium mockup aesthetics
        styleButton(btnAdd, new Color(0, 150, 136), Color.WHITE);      // Teal
        styleButton(btnEdit, new Color(33, 150, 243), Color.WHITE);    // Blue
        styleButton(btnRemove, new Color(244, 67, 54), Color.WHITE);   // Red-Orange
        styleButton(btnReresh, new Color(255, 152, 0), Color.WHITE);   // Orange
        styleButton(btnClear, new Color(255, 193, 7), Color.BLACK);    // Yellow-Gold
        
        // Table navigation buttons styling (Dark Slate Navy)
        Color navBg = new Color(44, 62, 80);
        styleButton(btnFirst, navBg, Color.WHITE);
        styleButton(btnNext, navBg, Color.WHITE);
        styleButton(btnPrev, navBg, Color.WHITE);
        styleButton(btnLast, navBg, Color.WHITE);
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void setupListeners() {
        btnClear.addActionListener(evt -> clearInputs());

        // Navigations
        btnFirst.addActionListener(evt -> selectRow(0));
        btnLast.addActionListener(evt -> selectRow(locationsTable.getRowCount() - 1));
        btnNext.addActionListener(evt -> {
            int current = locationsTable.getSelectedRow();
            if (current < locationsTable.getRowCount() - 1) {
                selectRow(current + 1);
            }
        });
        btnPrev.addActionListener(evt -> {
            int current = locationsTable.getSelectedRow();
            if (current > 0) {
                selectRow(current - 1);
            }
        });

        // Row selection updates inputs
        locationsTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                int selectedRow = locationsTable.getSelectedRow();
                if (selectedRow != -1) {
                    idSpinner.setValue((Integer) locationsTable.getValueAt(selectedRow, 0));
                    cityCombo.setSelectedItem(locationsTable.getValueAt(selectedRow, 1).toString());
                    addressText.setText(locationsTable.getValueAt(selectedRow, 2).toString());
                }
            }
        });
    }

    public javax.swing.JButton getBtnAdd() {
        return btnAdd;
    }

    public javax.swing.JButton getBtnEdit() {
        return btnEdit;
    }

    public javax.swing.JButton getBtnRemove() {
        return btnRemove;
    }

    public javax.swing.JButton getBtnReresh() {
        return btnReresh;
    }

    public void selectRow(int index) {
        if (locationsTable.getRowCount() > 0 && index >= 0 && index < locationsTable.getRowCount()) {
            locationsTable.setRowSelectionInterval(index, index);
            locationsTable.scrollRectToVisible(locationsTable.getCellRect(index, 0, true));
        }
    }

    public void clearInputs() {
        idSpinner.setValue(0);
        cityCombo.setSelectedIndex(0);
        addressText.setText("");
        locationsTable.clearSelection();
    }

    /**
     * Generates a high-quality location pin vector icon on a blue circular background
     * using Java2D Graphics rendering, avoiding the need for external static image assets.
     */
    private void generateHeaderIcon() {
        int size = 50;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Draw blue circle background
        g2.setColor(new Color(33, 150, 243));
        g2.fillOval(0, 0, size, size);

        // 2. Draw white pin outline & tip
        g2.setColor(Color.WHITE);
        // Pin outer circle head
        g2.fillOval(15, 10, 20, 20);
        
        // Pin triangular point down
        int[] xPoints = {15, 25, 35};
        int[] yPoints = {22, 38, 22};
        g2.fillPolygon(xPoints, yPoints, 3);

        // 3. Inner transparent/blue circle dot
        g2.setColor(new Color(33, 150, 243));
        g2.fillOval(21, 16, 8, 8);

        g2.dispose();
        headerIconLabel.setIcon(new ImageIcon(img));
    }

    // --- Public accessors for the LocationController ---

    public JTable getLocationsTable() {
        return locationsTable;
    }

    public int getIdInput() {
        return (Integer) idSpinner.getValue();
    }

    public void setIdInput(int id) {
        idSpinner.setValue(id);
    }

    public String getCityInput() {
        return (String) cityCombo.getSelectedItem();
    }

    public void setCityInput(String city) {
        cityCombo.setSelectedItem(city);
    }

    public String getAddressInput() {
        return addressText.getText();
    }

    public void setAddressInput(String address) {
        addressText.setText(address);
    }

    // --- Matisse Generated Components & Code Block ---
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlePanel = new javax.swing.JPanel();
        locationsTitle = new javax.swing.JLabel();
        headerIconLabel = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        idSpinner = new javax.swing.JSpinner();
        cityLabel = new javax.swing.JLabel();
        cityCombo = new javax.swing.JComboBox<>();
        addressLabel = new javax.swing.JLabel();
        addressScrollPane = new javax.swing.JScrollPane();
        addressText = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnReresh = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        tableScrollPane = new javax.swing.JScrollPane();
        locationsTable = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        locBgLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(45, 45, 45));
        setPreferredSize(new java.awt.Dimension(600, 520));
        setLayout(null);

        titlePanel.setBackground(new java.awt.Color(74, 83, 97));
        titlePanel.setLayout(null);

        locationsTitle.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        locationsTitle.setForeground(new java.awt.Color(255, 255, 255));
        locationsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        locationsTitle.setText("Locations");
        titlePanel.add(locationsTitle);
        locationsTitle.setBounds(80, 20, 440, 40);

        headerIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titlePanel.add(headerIconLabel);
        headerIconLabel.setBounds(20, 15, 50, 50);

        add(titlePanel);
        titlePanel.setBounds(0, 0, 600, 80);

        idLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        idLabel.setForeground(new java.awt.Color(255, 255, 255));
        idLabel.setText("ID:");
        add(idLabel);
        idLabel.setBounds(30, 100, 80, 30);
        add(idSpinner);
        idSpinner.setBounds(120, 100, 100, 30);

        cityLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cityLabel.setForeground(new java.awt.Color(255, 255, 255));
        cityLabel.setText("City:");
        add(cityLabel);
        cityLabel.setBounds(30, 150, 80, 30);

        cityCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "City 1", "City 2", "City 3", "City 4" }));
        add(cityCombo);
        cityCombo.setBounds(120, 150, 160, 30);

        addressLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        addressLabel.setForeground(new java.awt.Color(255, 255, 255));
        addressLabel.setText("Address:");
        add(addressLabel);
        addressLabel.setBounds(30, 200, 80, 30);

        addressText.setColumns(20);
        addressText.setRows(5);
        addressScrollPane.setViewportView(addressText);

        add(addressScrollPane);
        addressScrollPane.setBounds(120, 200, 160, 120);

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnAdd.setText("Add");
        add(btnAdd);
        btnAdd.setBounds(15, 340, 85, 35);

        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnEdit.setText("Edit");
        add(btnEdit);
        btnEdit.setBounds(110, 340, 85, 35);

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnRemove.setText("Remove");
        add(btnRemove);
        btnRemove.setBounds(205, 340, 85, 35);

        btnReresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReresh.setText("Reresh");
        add(btnReresh);
        btnReresh.setBounds(15, 390, 120, 35);

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnClear.setText("Clear");
        add(btnClear);
        btnClear.setBounds(150, 390, 120, 35);

        locationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "City", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableScrollPane.setViewportView(locationsTable);

        add(tableScrollPane);
        tableScrollPane.setBounds(300, 100, 280, 220);

        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnFirst.setText("<<");
        add(btnFirst);
        btnFirst.setBounds(330, 340, 50, 30);

        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnNext.setText(">");
        add(btnNext);
        btnNext.setBounds(390, 340, 50, 30);

        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnPrev.setText("<");
        add(btnPrev);
        btnPrev.setBounds(450, 340, 50, 30);

        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnLast.setText(">>");
        add(btnLast);
        btnLast.setBounds(510, 340, 50, 30);

        locBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin_bg.png"))); // NOI18N
        add(locBgLabel);
        locBgLabel.setBounds(0, 0, 600, 520);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane addressScrollPane;
    private javax.swing.JTextArea addressText;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnReresh;
    private javax.swing.JComboBox<String> cityCombo;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JLabel headerIconLabel;
    private javax.swing.JLabel idLabel;
    private javax.swing.JSpinner idSpinner;
    private javax.swing.JTable locationsTable;
    private javax.swing.JLabel locationsTitle;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel locBgLabel;
    // End of variables declaration//GEN-END:variables
}

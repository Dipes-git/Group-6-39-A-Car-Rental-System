package view;

import java.io.File;

/**
 * Admin Dashboard - Landing page for users with the "admin" role.
 * Contains only UI components.
 * Upgraded to modularly embed view.CarPanel for premium fleet visual management.
 * 
 * @author dipes
 */
public class AdminDashboard extends javax.swing.JFrame {

    private java.awt.CardLayout cardLayout;

    public AdminDashboard() {
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Enforce modern styling on sidebar buttons at runtime
        styleSidebarButton(overviewButton);
        styleSidebarButton(brandsButton);
        styleSidebarButton(carsButton);
        styleSidebarButton(locationsButton);
        styleSidebarButton(bookingsButton);
        styleSidebarButton(customersButton);
        
        setupDashboardPanels();
        
        // Configure modular components modes
        brandPanel.setAdminMode(true);
        carPanel.setAdminMode(true);
    }

    private void styleSidebarButton(javax.swing.JButton button) {
        button.setBackground(new java.awt.Color(40, 40, 40));
        button.setForeground(java.awt.Color.WHITE);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    private void setupDashboardPanels() {
        cardLayout = (java.awt.CardLayout) contentPanel.getLayout();
        showPanel("overview");
    }

    // --- Decoupled helpers for Brand synchronization ---
    public File getSelectedLogoFile() {
        return brandPanel.getSelectedLogoFile();
    }

    public void setSelectedLogoFile(File file) {
        brandPanel.setSelectedLogoFile(file);
    }

    public void clearBrandInputs() {
        brandPanel.clearBrandInputs();
    }

    public void showLogoPreview(String path) {
        brandPanel.showLogoPreview(path);
    }

    // --- Accessor for nested CarPanel (Controller-safe) ---
    public view.CarPanel getCarPanel() {
        return carPanel;
    }

    public javax.swing.JTable getBrandsTable() {
        return brandPanel.getBrandsTable();
    }

    public String getBrandNameInput() {
        return brandPanel.getBrandNameInput();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        roleTagLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        sidebarPanel = new javax.swing.JPanel();
        overviewButton = new javax.swing.JButton();
        brandsButton = new javax.swing.JButton();
        carsButton = new javax.swing.JButton();
        locationsButton = new javax.swing.JButton();
        bookingsButton = new javax.swing.JButton();
        customersButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        overviewPanel = new javax.swing.JPanel();
        overviewTitle = new javax.swing.JLabel();
        carsCard = new javax.swing.JPanel();
        carsTitle = new javax.swing.JLabel();
        carsVal = new javax.swing.JLabel();
        activeCard = new javax.swing.JPanel();
        activeTitle = new javax.swing.JLabel();
        activeVal = new javax.swing.JLabel();
        pendingCard = new javax.swing.JPanel();
        pendingTitle = new javax.swing.JLabel();
        pendingVal = new javax.swing.JLabel();
        earningsCard = new javax.swing.JPanel();
        earningsTitle = new javax.swing.JLabel();
        earningsVal = new javax.swing.JLabel();
        bgLabel = new javax.swing.JLabel();
        brandPanel = new view.BrandPanel();
        carPanel = new view.CarPanel();
        customerPanel = new view.CustomerPanel();
        locationsPanel = new view.LocationPanel();
        bookingPanel = new view.BookingPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(30, 30, 30));
        mainPanel.setLayout(null);

        headerPanel.setBackground(new java.awt.Color(0, 51, 102));
        headerPanel.setLayout(null);

        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        welcomeLabel.setForeground(new java.awt.Color(255, 255, 255));
        welcomeLabel.setText("Welcome, Admin!");
        headerPanel.add(welcomeLabel);
        welcomeLabel.setBounds(30, 15, 400, 30);

        roleTagLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roleTagLabel.setForeground(new java.awt.Color(200, 220, 255));
        roleTagLabel.setText("ADMIN PANEL");
        headerPanel.add(roleTagLabel);
        roleTagLabel.setBounds(30, 50, 200, 20);

        logoutButton.setBackground(new java.awt.Color(0, 80, 120));
        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setText("Logout");
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        headerPanel.add(logoutButton);
        logoutButton.setBounds(660, 20, 110, 40);

        mainPanel.add(headerPanel);
        headerPanel.setBounds(0, 0, 800, 80);

        sidebarPanel.setBackground(new java.awt.Color(40, 40, 40));
        sidebarPanel.setLayout(null);

        overviewButton.setBackground(new java.awt.Color(40, 40, 40));
        overviewButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        overviewButton.setForeground(new java.awt.Color(255, 255, 255));
        overviewButton.setText("Overview");
        overviewButton.setBorderPainted(false);
        overviewButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        overviewButton.setFocusPainted(false);
        overviewButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        overviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                overviewButtonActionPerformed(evt);
            }
        });
        sidebarPanel.add(overviewButton);
        overviewButton.setBounds(10, 30, 180, 45);

        brandsButton.setBackground(new java.awt.Color(40, 40, 40));
        brandsButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        brandsButton.setForeground(new java.awt.Color(255, 255, 255));
        brandsButton.setText("Brands");
        brandsButton.setBorderPainted(false);
        brandsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        brandsButton.setFocusPainted(false);
        brandsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        brandsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brandsButtonActionPerformed(evt);
            }
        });
        sidebarPanel.add(brandsButton);
        brandsButton.setBounds(10, 95, 180, 45);

        carsButton.setBackground(new java.awt.Color(40, 40, 40));
        carsButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        carsButton.setForeground(new java.awt.Color(255, 255, 255));
        carsButton.setText("Cars");
        carsButton.setBorderPainted(false);
        carsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carsButton.setFocusPainted(false);
        carsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        carsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carsButtonActionPerformed(evt);
            }
        });
        sidebarPanel.add(carsButton);
        carsButton.setBounds(10, 160, 180, 45);

        locationsButton.setBackground(new java.awt.Color(40, 40, 40));
        locationsButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        locationsButton.setForeground(new java.awt.Color(255, 255, 255));
        locationsButton.setText("Locations");
        locationsButton.setBorderPainted(false);
        locationsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        locationsButton.setFocusPainted(false);
        locationsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        locationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationsButtonActionPerformed(evt);
            }
        });
        sidebarPanel.add(locationsButton);
        locationsButton.setBounds(10, 225, 180, 45);

        bookingsButton.setBackground(new java.awt.Color(40, 40, 40));
        bookingsButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bookingsButton.setForeground(new java.awt.Color(255, 255, 255));
        bookingsButton.setText("Bookings");
        bookingsButton.setBorderPainted(false);
        bookingsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookingsButton.setFocusPainted(false);
        bookingsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bookingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookingsButtonActionPerformed(evt);
            }
        });
        sidebarPanel.add(bookingsButton);
        bookingsButton.setBounds(10, 290, 180, 45);

        customersButton.setBackground(new java.awt.Color(40, 40, 40));
        customersButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        customersButton.setForeground(new java.awt.Color(255, 255, 255));
        customersButton.setText("Customers");
        customersButton.setBorderPainted(false);
        customersButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customersButton.setFocusPainted(false);
        customersButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        customersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customersButtonActionPerformed(evt);
            }
        });
        sidebarPanel.add(customersButton);
        customersButton.setBounds(10, 355, 180, 45);

        mainPanel.add(sidebarPanel);
        sidebarPanel.setBounds(0, 80, 200, 520);

        contentPanel.setBackground(new java.awt.Color(45, 45, 45));
        contentPanel.setLayout(new java.awt.CardLayout());

        overviewPanel.setBackground(new java.awt.Color(45, 45, 45));
        overviewPanel.setLayout(null);

        overviewTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        overviewTitle.setForeground(new java.awt.Color(255, 255, 255));
        overviewTitle.setText("System Overview");
        overviewPanel.add(overviewTitle);
        overviewTitle.setBounds(30, 20, 300, 35);

        carsCard.setBackground(new java.awt.Color(0, 102, 153));
        carsCard.setLayout(null);

        carsTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        carsTitle.setForeground(new java.awt.Color(230, 230, 230));
        carsTitle.setText("Total Cars");
        carsCard.add(carsTitle);
        carsTitle.setBounds(20, 20, 220, 20);

        carsVal.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        carsVal.setForeground(new java.awt.Color(255, 255, 255));
        carsVal.setText("12");
        carsCard.add(carsVal);
        carsVal.setBounds(20, 50, 220, 45);

        overviewPanel.add(carsCard);
        carsCard.setBounds(30, 80, 260, 130);

        activeCard.setBackground(new java.awt.Color(0, 153, 102));
        activeCard.setLayout(null);

        activeTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        activeTitle.setForeground(new java.awt.Color(230, 230, 230));
        activeTitle.setText("Active Bookings");
        activeCard.add(activeTitle);
        activeTitle.setBounds(20, 20, 220, 20);

        activeVal.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        activeVal.setForeground(new java.awt.Color(255, 255, 255));
        activeVal.setText("5");
        activeCard.add(activeVal);
        activeVal.setBounds(20, 50, 220, 45);

        overviewPanel.add(activeCard);
        activeCard.setBounds(310, 80, 260, 130);

        pendingCard.setBackground(new java.awt.Color(230, 126, 34));
        pendingCard.setLayout(null);

        pendingTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        pendingTitle.setForeground(new java.awt.Color(230, 230, 230));
        pendingTitle.setText("Pending Requests");
        pendingCard.add(pendingTitle);
        pendingTitle.setBounds(20, 20, 220, 20);

        pendingVal.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        pendingVal.setForeground(new java.awt.Color(255, 255, 255));
        pendingVal.setText("3");
        pendingCard.add(pendingVal);
        pendingVal.setBounds(20, 50, 220, 45);

        overviewPanel.add(pendingCard);
        pendingCard.setBounds(30, 240, 260, 130);

        earningsCard.setBackground(new java.awt.Color(156, 89, 184));
        earningsCard.setLayout(null);

        earningsTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        earningsTitle.setForeground(new java.awt.Color(230, 230, 230));
        earningsTitle.setText("Total Earnings");
        earningsCard.add(earningsTitle);
        earningsTitle.setBounds(20, 20, 220, 20);

        earningsVal.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        earningsVal.setForeground(new java.awt.Color(255, 255, 255));
        earningsVal.setText("$1,450.00");
        earningsCard.add(earningsVal);
        earningsVal.setBounds(20, 50, 220, 45);

        overviewPanel.add(earningsCard);
        earningsCard.setBounds(310, 240, 260, 130);

        bgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin_bg.png"))); // NOI18N
        overviewPanel.add(bgLabel);
        bgLabel.setBounds(0, 0, 600, 520);

        contentPanel.add(overviewPanel, "overview");
        contentPanel.add(brandPanel, "brands");
        contentPanel.add(carPanel, "cars");
        contentPanel.add(customerPanel, "customers");

        contentPanel.add(locationsPanel, "locations");

        contentPanel.add(bookingPanel, "bookings");

        mainPanel.add(contentPanel);
        contentPanel.setBounds(200, 80, 600, 520);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 800, 600);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // Handled in Controller
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void overviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overviewButtonActionPerformed
        showPanel("overview");
    }//GEN-LAST:event_overviewButtonActionPerformed

    private void carsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carsButtonActionPerformed
        showPanel("cars");
    }//GEN-LAST:event_carsButtonActionPerformed

    private void locationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationsButtonActionPerformed
        showPanel("locations");
    }//GEN-LAST:event_locationsButtonActionPerformed

    private void brandsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        showPanel("brands");
    }

    private void bookingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingsButtonActionPerformed
        showPanel("bookings");
    }//GEN-LAST:event_bookingsButtonActionPerformed

    private void customersButtonActionPerformed(java.awt.event.ActionEvent evt) {
        showPanel("customers");
    }

    // --- Accessors for sub-panels and listener wiring ---
    public view.CustomerPanel getCustomerPanel() {
        return customerPanel;
    }

    public view.LocationPanel getLocationsPanel() {
        return locationsPanel;
    }

    public view.BrandPanel getBrandPanel() {
        return brandPanel;
    }

    public javax.swing.JButton getLogoutButton() {
        return logoutButton;
    }

    public void addLogoutListener(java.awt.event.ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public view.BookingPanel getBookingPanel() {
        return bookingPanel;
    }

    public void setWelcomeText(String text) {
        welcomeLabel.setText(text);
    }

    public void setSystemSummary(model.DashboardMetrics metrics) {
        carsVal.setText(String.valueOf(metrics.getTotalCars()));
        activeVal.setText(String.valueOf(metrics.getActiveBookings()));
        pendingVal.setText(String.valueOf(metrics.getPendingRequests()));
        earningsVal.setText(String.format("$%.2f", metrics.getTotalEarnings()));
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
        
        // Highlight active sidebar button
        overviewButton.setBackground(name.equals("overview") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
        brandsButton.setBackground(name.equals("brands") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
        carsButton.setBackground(name.equals("cars") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
        locationsButton.setBackground(name.equals("locations") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
        bookingsButton.setBackground(name.equals("bookings") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
        customersButton.setBackground(name.equals("customers") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel activeCard;
    private javax.swing.JLabel activeTitle;
    private javax.swing.JLabel activeVal;
    private javax.swing.JLabel bgLabel;
    private javax.swing.JButton bookingsButton;
    private view.BookingPanel bookingPanel;
    private view.BrandPanel brandPanel;
    private view.CarPanel carPanel;
    private view.CustomerPanel customerPanel;
    private javax.swing.JButton brandsButton;
    private javax.swing.JButton carsButton;
    private javax.swing.JPanel carsCard;
    private javax.swing.JLabel carsTitle;
    private javax.swing.JLabel carsVal;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton customersButton;
    private javax.swing.JPanel earningsCard;
    private javax.swing.JLabel earningsTitle;
    private javax.swing.JLabel earningsVal;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton locationsButton;
    private view.LocationPanel locationsPanel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton overviewButton;
    private javax.swing.JPanel overviewPanel;
    private javax.swing.JLabel overviewTitle;
    private javax.swing.JPanel pendingCard;
    private javax.swing.JLabel pendingTitle;
    private javax.swing.JLabel pendingVal;
    private javax.swing.JLabel roleTagLabel;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
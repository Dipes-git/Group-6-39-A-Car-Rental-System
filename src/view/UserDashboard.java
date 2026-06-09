package view;

/**
 * User Dashboard - Landing page for users with the "user" role.
 * Contains only UI components.
 * No business logic, data extraction, or navigation resides in this class.
 * 
 * @author dipes
 */
public class UserDashboard extends javax.swing.JFrame {

    private java.awt.CardLayout cardLayout;


    public UserDashboard() {
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Enforce modern styling on sidebar buttons at runtime
        styleSidebarButton(homeButton);
        styleSidebarButton(brandsButton);
        styleSidebarButton(browseButton);
        styleSidebarButton(bookingsButton);
        
        // Configure nested CarPanel in Customer mode
        carPanel.setAdminMode(false);
        brandPanel.setAdminMode(false);
        setupDashboardPanels();
    }



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        roleTagLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        sidebarPanel = new javax.swing.JPanel();
        homeButton = new javax.swing.JButton();
        brandsButton = new javax.swing.JButton();
        browseButton = new javax.swing.JButton();
        bookingsButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        banner = new javax.swing.JPanel();
        bannerTitle = new javax.swing.JLabel();
        bannerSub = new javax.swing.JLabel();
        statusTitle = new javax.swing.JLabel();
        rentalsCard = new javax.swing.JPanel();
        rentalsTitle = new javax.swing.JLabel();
        rentalsVal = new javax.swing.JLabel();
        pendingCard = new javax.swing.JPanel();
        pendingTitle = new javax.swing.JLabel();
        pendingVal = new javax.swing.JLabel();
        daysCard = new javax.swing.JPanel();
        daysTitle = new javax.swing.JLabel();
        daysVal = new javax.swing.JLabel();
        tipPanel = new javax.swing.JPanel();
        tipTitle = new javax.swing.JLabel();
        tipText1 = new javax.swing.JLabel();
        tipText2 = new javax.swing.JLabel();
        homeBgLabel = new javax.swing.JLabel();
        carPanel = new view.CarPanel();
        bookingPanel = new view.BookingPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(30, 30, 30));
        mainPanel.setLayout(null);

        headerPanel.setBackground(new java.awt.Color(0, 102, 153));
        headerPanel.setLayout(null);

        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        welcomeLabel.setForeground(new java.awt.Color(255, 255, 255));
        welcomeLabel.setText("Welcome, User!");
        headerPanel.add(welcomeLabel);
        welcomeLabel.setBounds(30, 15, 400, 30);

        roleTagLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        roleTagLabel.setForeground(new java.awt.Color(200, 230, 255));
        roleTagLabel.setText("USER PANEL");
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

        homeButton.setBackground(new java.awt.Color(40, 40, 40));
        homeButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        homeButton.setForeground(new java.awt.Color(255, 255, 255));
        homeButton.setText("Home");
        homeButton.setBorderPainted(false);
        homeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeButton.setFocusPainted(false);
        homeButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });
        sidebarPanel.add(homeButton);
        homeButton.setBounds(10, 30, 180, 45);

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

        browseButton.setBackground(new java.awt.Color(40, 40, 40));
        browseButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        browseButton.setForeground(new java.awt.Color(255, 255, 255));
        browseButton.setText("Browse Cars");
        browseButton.setBorderPainted(false);
        browseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        browseButton.setFocusPainted(false);
        browseButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        sidebarPanel.add(browseButton);
        browseButton.setBounds(10, 160, 180, 45);

        bookingsButton.setBackground(new java.awt.Color(40, 40, 40));
        bookingsButton.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bookingsButton.setForeground(new java.awt.Color(255, 255, 255));
        bookingsButton.setText("My Bookings");
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
        bookingsButton.setBounds(10, 225, 180, 45);

        mainPanel.add(sidebarPanel);
        sidebarPanel.setBounds(0, 80, 200, 520);

        contentPanel.setBackground(new java.awt.Color(45, 45, 45));
        contentPanel.setLayout(new java.awt.CardLayout());

        homePanel.setBackground(new java.awt.Color(45, 45, 45));
        homePanel.setLayout(null);

        banner.setBackground(new java.awt.Color(0, 102, 153));
        banner.setLayout(null);

        bannerTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        bannerTitle.setForeground(new java.awt.Color(255, 255, 255));
        bannerTitle.setText("Ready to hit the road?");
        banner.add(bannerTitle);
        bannerTitle.setBounds(25, 20, 400, 30);

        bannerSub.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        bannerSub.setForeground(new java.awt.Color(220, 240, 255));
        bannerSub.setText("Select a car, choose your dates, and start your journey today.");
        banner.add(bannerSub);
        bannerSub.setBounds(25, 55, 480, 20);

        homePanel.add(banner);
        banner.setBounds(30, 20, 540, 100);

        statusTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        statusTitle.setForeground(new java.awt.Color(255, 255, 255));
        statusTitle.setText("Your Activity Summary");
        homePanel.add(statusTitle);
        statusTitle.setBounds(30, 150, 300, 25);

        rentalsCard.setBackground(new java.awt.Color(0, 153, 102));
        rentalsCard.setLayout(null);

        rentalsTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rentalsTitle.setForeground(new java.awt.Color(230, 230, 230));
        rentalsTitle.setText("Active Rentals");
        rentalsCard.add(rentalsTitle);
        rentalsTitle.setBounds(15, 20, 130, 20);

        rentalsVal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        rentalsVal.setForeground(new java.awt.Color(255, 255, 255));
        rentalsVal.setText("1 Rental");
        rentalsCard.add(rentalsVal);
        rentalsVal.setBounds(15, 50, 130, 45);

        homePanel.add(rentalsCard);
        rentalsCard.setBounds(30, 190, 160, 130);

        pendingCard.setBackground(new java.awt.Color(230, 126, 34));
        pendingCard.setLayout(null);

        pendingTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        pendingTitle.setForeground(new java.awt.Color(230, 230, 230));
        pendingTitle.setText("Pending Bookings");
        pendingCard.add(pendingTitle);
        pendingTitle.setBounds(15, 20, 130, 20);

        pendingVal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        pendingVal.setForeground(new java.awt.Color(255, 255, 255));
        pendingVal.setText("0 Pending");
        pendingCard.add(pendingVal);
        pendingVal.setBounds(15, 50, 130, 45);

        homePanel.add(pendingCard);
        pendingCard.setBounds(210, 190, 160, 130);

        daysCard.setBackground(new java.awt.Color(156, 89, 184));
        daysCard.setLayout(null);

        daysTitle.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        daysTitle.setForeground(new java.awt.Color(230, 230, 230));
        daysTitle.setText("Total Rent Days");
        daysCard.add(daysTitle);
        daysTitle.setBounds(15, 20, 150, 20);

        daysVal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        daysVal.setForeground(new java.awt.Color(255, 255, 255));
        daysVal.setText("14 Days");
        daysCard.add(daysVal);
        daysVal.setBounds(15, 50, 150, 45);

        homePanel.add(daysCard);
        daysCard.setBounds(390, 190, 180, 130);

        tipPanel.setBackground(new java.awt.Color(55, 55, 55));
        tipPanel.setLayout(null);

        tipTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tipTitle.setForeground(new java.awt.Color(0, 153, 204));
        tipTitle.setText("💡 Important Customer Guidelines");
        tipPanel.add(tipTitle);
        tipTitle.setBounds(20, 15, 300, 20);

        tipText1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tipText1.setForeground(new java.awt.Color(200, 200, 200));
        tipText1.setText("• Please bring your valid driver's license at the time of pickup.");
        tipPanel.add(tipText1);
        tipText1.setBounds(20, 45, 500, 20);

        tipText2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tipText2.setForeground(new java.awt.Color(200, 200, 200));
        tipText2.setText("• Bookings can be cancelled up to 24 hours prior without any fees.");
        tipPanel.add(tipText2);
        tipText2.setBounds(20, 70, 500, 20);

        homePanel.add(tipPanel);
        tipPanel.setBounds(30, 360, 540, 110);

        homeBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user_bg.png"))); // NOI18N
        homePanel.add(homeBgLabel);
        homeBgLabel.setBounds(0, 0, 600, 520);

        contentPanel.add(homePanel, "home");

        contentPanel.add(carPanel, "browse");

        contentPanel.add(bookingPanel, "bookings");

        brandPanel = new view.BrandPanel();
        contentPanel.add(brandPanel, "brands");

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

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        showPanel("home");
    }//GEN-LAST:event_homeButtonActionPerformed

    private void brandsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        showPanel("brands");
    }

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        showPanel("browse");
    }//GEN-LAST:event_browseButtonActionPerformed

    private void bookingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingsButtonActionPerformed
        showPanel("bookings");
    }//GEN-LAST:event_bookingsButtonActionPerformed

    // --- Accessors for sub-panels and listener wiring ---
    public view.CarPanel getCarPanel() {
        return carPanel;
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
        showPanel("home");
    }

    // --- Public Setters (for Controller to update UI) ---

    public void setWelcomeText(String text) {
        welcomeLabel.setText(text);
    }

    public void setActivitySummary(int activeCount, int pendingCount, int totalDays) {
        rentalsVal.setText(activeCount + " Active");
        pendingVal.setText(pendingCount + " Pending");
        daysVal.setText(totalDays + " Day(s)");
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
        
        // Highlight active sidebar button
        homeButton.setBackground(name.equals("home") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
        brandsButton.setBackground(name.equals("brands") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
        browseButton.setBackground(name.equals("browse") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
        bookingsButton.setBackground(name.equals("bookings") ? new java.awt.Color(60, 60, 60) : new java.awt.Color(40, 40, 40));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel banner;
    private javax.swing.JLabel bannerSub;
    private javax.swing.JLabel bannerTitle;
    private javax.swing.JButton bookingsButton;
    private view.BookingPanel bookingPanel;
    private javax.swing.JButton browseButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel daysCard;
    private javax.swing.JLabel daysTitle;
    private javax.swing.JLabel daysVal;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel homeBgLabel;
    private javax.swing.JButton homeButton;
    private javax.swing.JButton brandsButton;
    private view.BrandPanel brandPanel;
    private view.CarPanel carPanel;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel pendingCard;
    private javax.swing.JLabel pendingTitle;
    private javax.swing.JLabel pendingVal;
    private javax.swing.JPanel rentalsCard;
    private javax.swing.JLabel rentalsTitle;
    private javax.swing.JLabel rentalsVal;
    private javax.swing.JLabel roleTagLabel;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JLabel statusTitle;
    private javax.swing.JPanel tipPanel;
    private javax.swing.JLabel tipText1;
    private javax.swing.JLabel tipText2;
    private javax.swing.JLabel tipTitle;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}

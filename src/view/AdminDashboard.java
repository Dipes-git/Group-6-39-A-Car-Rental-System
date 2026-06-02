package view;

import controller.UserController;
import controller.CarController;
import java.io.File;

/**
 * Admin Dashboard - Landing page for users with the "admin" role.
 * Contains only UI components and delegates all logic to UserController and CarController.
 * Upgraded to modularly embed view.CarPanel for premium fleet visual management.
 * 
 * @author dipes
 */
public class AdminDashboard extends javax.swing.JFrame {

    private final UserController controller;
    private final CarController carController;
    private java.awt.CardLayout cardLayout;

    public AdminDashboard() {
        controller = new UserController();
        carController = new CarController();
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
        
        // Load options and callbacks
        carController.populateBrandCombo(carPanel);
        
        // Tab redirect callbacks on modular visual tabs
        carPanel.setOnBrandsTabRedirect(() -> {
            controller.handleAdminTabChanged(this, "brands");
        });
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
}
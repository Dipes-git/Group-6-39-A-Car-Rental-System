package view;

import controller.LocationController;
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

    private final LocationController controller;

    public LocationPanel() {
        controller = new LocationController();
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

}
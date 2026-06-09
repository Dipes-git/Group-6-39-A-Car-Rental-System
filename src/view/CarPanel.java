package view;
import model.Brand;
import model.Car;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Reusable visual JPanel component managing Car inventory,
 * detailed specs (Fuel, Color, Class, Passengers, Gearbox, Features),
 * and CRUD administrative interactions.
 * 
 * Supports multi-card transitions (Form view, Table list, Photo uploader)
 * and pins visual tabs at the bottom.
 * 
 * @author dipes
 */
public class CarPanel extends javax.swing.JPanel {

    private CardLayout cardLayout;
    private ButtonGroup gearboxGroup;
    private boolean isAdminMode = true;
    
    // Callbacks to let the dashboard handle redirect actions
    private Runnable onBrandsTabRedirectCallback;

    public CarPanel() {
        initComponents();
        setupComboBoxModels();
        scaleHeaderIcon();
        setupGearboxGroup();
        setupListeners();
        setAdminMode(true); // Default to admin mode
    }

    private void setupComboBoxModels() {
        fuelCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gas", "Diesel", "Electric", "Hybrid" }));
        colorCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "White", "Black", "Red", "Blue", "Grey", "Silver", "Green" }));
        classCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sedan", "SUV", "Hatchback", "Luxury", "Sports", "Van" }));
        statusCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Rented", "Maintenance" }));
    }

    private void scaleHeaderIcon() {
        try {
            java.net.URL imgUrl = getClass().getResource("/images/car_icon.png");
            if (imgUrl != null) {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgUrl);
                java.awt.Image img = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                carsHeaderIcon.setIcon(new javax.swing.ImageIcon(img));
            }
        } catch (Exception e) {
            System.err.println("[CarPanel] Failed to scale header icon: " + e.getMessage());
        }
    }

    private void setupGearboxGroup() {
        gearboxGroup = new ButtonGroup();
        gearboxGroup.add(rbAutomatic);
        gearboxGroup.add(rbManual);
    }

    /**
     * Toggles between Admin CRUD mode and read-only User mode.
     */
    public void setAdminMode(boolean admin) {
        this.isAdminMode = admin;
        
        // Adjust title
        carsHeaderTitle.setText(admin ? "Manage Cars" : "Browse Available Cars");
        
        // Hide/show admin specific fields & buttons
        btnAdd.setVisible(admin);
        btnUpdate.setVisible(admin);
        btnDelete.setVisible(admin);
        btnReset.setVisible(admin);
        btnSearch.setVisible(admin);
        
        // Show/hide customer reserve button
        btnRent.setVisible(!admin);
        
        // Bottom image button is only relevant in admin mode
        btnAddCarImages.setVisible(admin);
        
        // Dynamic visual locking of specification input controls
        modelField.setEditable(admin);
        priceField.setEditable(admin);
        brandCombo.setEnabled(admin);
        fuelCombo.setEnabled(admin);
        colorCombo.setEnabled(admin);
        classCombo.setEnabled(admin);
        passengersSpinner.setEnabled(admin);
        rbAutomatic.setEnabled(admin);
        rbManual.setEnabled(admin);
        statusCombo.setEnabled(admin);
        
        chkAirCon.setEnabled(admin);
        chkSunroof.setEnabled(admin);
        chkNav.setEnabled(admin);
        chkElectric.setEnabled(admin);
        chkAirbags.setEnabled(admin);
        chkHeated.setEnabled(admin);
        chkBluetooth.setEnabled(admin);
        chkGps.setEnabled(admin);
        
        if (!admin) {
            clearCarInputs();
        }
    }

    public boolean isAdminMode() {
        return isAdminMode;
    }

    private void setupListeners() {
        cardLayout = (CardLayout) cardPanel.getLayout();

        // Bind reset Action
        btnReset.addActionListener(evt -> clearCarInputs());

        // Navigation bottom tabs (pure UI card transitions)
        btnCarsList.addActionListener(evt -> {
            String currentCard = getActiveCardName();
            if ("list".equals(currentCard)) {
                cardLayout.show(cardPanel, "form");
            } else {
                cardLayout.show(cardPanel, "list");
            }
        });

        btnAddCarImages.addActionListener(evt -> cardLayout.show(cardPanel, "images"));
        btnBrandsList.addActionListener(evt -> {
            if (onBrandsTabRedirectCallback != null) {
                onBrandsTabRedirectCallback.run();
            }
        });

        // Inner Card navigation buttons
        btnBackToForm.addActionListener(evt -> cardLayout.show(cardPanel, "form"));
        btnBackToFormImages.addActionListener(evt -> cardLayout.show(cardPanel, "form"));
    }

    // --- Inputs Getters/Setters for controllers ---
    public javax.swing.JTextField getIdField() {
        return idField;
    }

    public javax.swing.JTextField getModelField() {
        return modelField;
    }

    public javax.swing.JTextField getPriceField() {
        return priceField;
    }

    public javax.swing.JButton getBtnAdd() {
        return btnAdd;
    }

    public javax.swing.JButton getBtnUpdate() {
        return btnUpdate;
    }

    public javax.swing.JButton getBtnDelete() {
        return btnDelete;
    }

    public javax.swing.JButton getBtnReset() {
        return btnReset;
    }

    public javax.swing.JButton getBtnSearch() {
        return btnSearch;
    }

    public javax.swing.JButton getBtnRent() {
        return btnRent;
    }

    public javax.swing.JButton getBtnCarsList() {
        return btnCarsList;
    }

    public javax.swing.JButton getBtnAddCarImages() {
        return btnAddCarImages;
    }

    public javax.swing.JButton getBtnBrandsList() {
        return btnBrandsList;
    }

    public java.awt.CardLayout getCardLayout() {
        return cardLayout;
    }

    public javax.swing.JPanel getCardPanel() {
        return cardPanel;
    }

    public Runnable getOnBrandsTabRedirectCallback() {
        return onBrandsTabRedirectCallback;
    }

    private String getActiveCardName() {
        // Safe check for the active panel in CardLayout
        for (java.awt.Component comp : cardPanel.getComponents()) {
            if (comp.isVisible()) {
                if (comp == formPanel) return "form";
                if (comp == listPanel) return "list";
                if (comp == imagesPanel) return "images";
            }
        }
        return "form";
    }

    // Handled in Controller

    public void populateEditorFields(Car car) {
        idField.setText(String.valueOf(car.getId()));
        modelField.setText(car.getModel());
        priceField.setText(String.valueOf(car.getPricePerDay()));
        
        // Select matching brand
        for (int i = 0; i < brandCombo.getItemCount(); i++) {
            Brand b = brandCombo.getItemAt(i);
            if (b.getId() == car.getBrandId() || b.getName().equalsIgnoreCase(car.getBrand())) {
                brandCombo.setSelectedIndex(i);
                break;
            }
        }

        fuelCombo.setSelectedItem(car.getFuel());
        colorCombo.setSelectedItem(car.getColor());
        classCombo.setSelectedItem(car.getCategory());
        passengersSpinner.setValue(car.getPassengers());
        statusCombo.setSelectedItem(car.getStatus());

        if ("Manual".equalsIgnoreCase(car.getGearbox())) {
            rbManual.setSelected(true);
        } else {
            rbAutomatic.setSelected(true);
        }

        // Checklist deserialization
        String featuresCsv = car.getFeatures() != null ? car.getFeatures() : "";
        chkAirCon.setSelected(featuresCsv.contains("Air Conditioning"));
        chkSunroof.setSelected(featuresCsv.contains("Sunroof"));
        chkNav.setSelected(featuresCsv.contains("Navigation systems"));
        chkElectric.setSelected(featuresCsv.contains("Electric Windows"));
        chkAirbags.setSelected(featuresCsv.contains("Airbags"));
        chkHeated.setSelected(featuresCsv.contains("Heated Seats"));
        chkBluetooth.setSelected(featuresCsv.contains("Bluetooth"));
        chkGps.setSelected(featuresCsv.contains("GPS"));
    }

    public void clearCarInputs() {
        idField.setText("0");
        modelField.setText("");
        priceField.setText("10");
        if (brandCombo.getItemCount() > 0) {
            brandCombo.setSelectedIndex(0);
        }
        fuelCombo.setSelectedIndex(0);
        colorCombo.setSelectedIndex(0);
        classCombo.setSelectedIndex(0);
        passengersSpinner.setValue(5);
        rbAutomatic.setSelected(true);
        statusCombo.setSelectedIndex(0);

        chkAirCon.setSelected(false);
        chkSunroof.setSelected(false);
        chkNav.setSelected(false);
        chkElectric.setSelected(false);
        chkAirbags.setSelected(false);
        chkHeated.setSelected(false);
        chkBluetooth.setSelected(false);
        chkGps.setSelected(false);
        
        carTable.clearSelection();
    }

    // --- Inputs Getters/Setters for controllers ---
    public JTable getCarTable() {
        return carTable;
    }

    public JComboBox<Brand> getBrandCombo() {
        return brandCombo;
    }

    public String getModelInput() {
        return modelField.getText().trim();
    }

    public String getPriceInput() {
        return priceField.getText().trim();
    }

    public String getCategoryInput() {
        return classCombo.getSelectedItem() != null ? classCombo.getSelectedItem().toString() : "Sedan";
    }

    public String getStatusInput() {
        return statusCombo.getSelectedItem() != null ? statusCombo.getSelectedItem().toString() : "Available";
    }

    public String getFuelInput() {
        return fuelCombo.getSelectedItem() != null ? fuelCombo.getSelectedItem().toString() : "Gas";
    }

    public String getColorInput() {
        return colorCombo.getSelectedItem() != null ? colorCombo.getSelectedItem().toString() : "White";
    }

    public int getPassengersInput() {
        return (Integer) passengersSpinner.getValue();
    }

    public String getGearboxInput() {
        return rbManual.isSelected() ? "Manual" : "Automatic";
    }

    /**
     * Serializes selected checkboxes into a clean comma-separated list of strings.
     */
    public String getFeaturesInput() {
        List<String> activeFeatures = new ArrayList<>();
        if (chkAirCon.isSelected()) activeFeatures.add("Air Conditioning");
        if (chkSunroof.isSelected()) activeFeatures.add("Sunroof");
        if (chkNav.isSelected()) activeFeatures.add("Navigation systems");
        if (chkElectric.isSelected()) activeFeatures.add("Electric Windows");
        if (chkAirbags.isSelected()) activeFeatures.add("Airbags");
        if (chkHeated.isSelected()) activeFeatures.add("Heated Seats");
        if (chkBluetooth.isSelected()) activeFeatures.add("Bluetooth");
        if (chkGps.isSelected()) activeFeatures.add("GPS");
        return String.join(",", activeFeatures);
    }

    public String getCarIdInput() {
        return idField.getText().trim();
    }

    public void setOnBrandsTabRedirect(Runnable callback) {
        this.onBrandsTabRedirectCallback = callback;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        carsHeaderIcon = new javax.swing.JLabel();
        carsHeaderTitle = new javax.swing.JLabel();
        cardPanel = new javax.swing.JPanel();
        formPanel = new javax.swing.JPanel();
        idLabel = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        brandLabel = new javax.swing.JLabel();
        brandCombo = new javax.swing.JComboBox<>();
        modelLabel = new javax.swing.JLabel();
        modelField = new javax.swing.JTextField();
        fuelLabel = new javax.swing.JLabel();
        fuelCombo = new javax.swing.JComboBox<>();
        colorLabel = new javax.swing.JLabel();
        colorCombo = new javax.swing.JComboBox<>();
        statusLabel = new javax.swing.JLabel();
        statusCombo = new javax.swing.JComboBox<>();
        classLabel = new javax.swing.JLabel();
        classCombo = new javax.swing.JComboBox<>();
        passengersLabel = new javax.swing.JLabel();
        passengersSpinner = new javax.swing.JSpinner();
        gearboxLabel = new javax.swing.JLabel();
        rbAutomatic = new javax.swing.JRadioButton();
        rbManual = new javax.swing.JRadioButton();
        priceLabel = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        featuresTitle = new javax.swing.JLabel();
        featuresPanel = new javax.swing.JPanel();
        chkAirCon = new javax.swing.JCheckBox();
        chkSunroof = new javax.swing.JCheckBox();
        chkNav = new javax.swing.JCheckBox();
        chkElectric = new javax.swing.JCheckBox();
        chkAirbags = new javax.swing.JCheckBox();
        chkHeated = new javax.swing.JCheckBox();
        chkBluetooth = new javax.swing.JCheckBox();
        chkGps = new javax.swing.JCheckBox();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnRent = new javax.swing.JButton();
        listPanel = new javax.swing.JPanel();
        carsScrollPane = new javax.swing.JScrollPane();
        carTable = new javax.swing.JTable();
        btnBackToForm = new javax.swing.JButton();
        imagesPanel = new javax.swing.JPanel();
        imagesTitle = new javax.swing.JLabel();
        imagesUploadLabel = new javax.swing.JLabel();
        btnBackToFormImages = new javax.swing.JButton();
        bottomPanel = new javax.swing.JPanel();
        btnAddCarImages = new javax.swing.JButton();
        btnBrandsList = new javax.swing.JButton();
        btnCarsList = new javax.swing.JButton();
        carsBgLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(45, 45, 45));
        setLayout(null);

        headerPanel.setBackground(new java.awt.Color(74, 83, 97));
        headerPanel.setLayout(null);

        carsHeaderIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/car_icon.png"))); // NOI18N
        headerPanel.add(carsHeaderIcon);
        carsHeaderIcon.setBounds(15, 10, 40, 40);

        carsHeaderTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        carsHeaderTitle.setForeground(new java.awt.Color(255, 255, 255));
        carsHeaderTitle.setText("Cars");
        headerPanel.add(carsHeaderTitle);
        carsHeaderTitle.setBounds(65, 10, 400, 40);

        add(headerPanel);
        headerPanel.setBounds(0, 0, 600, 60);

        cardPanel.setOpaque(false);
        cardPanel.setLayout(new java.awt.CardLayout());

        formPanel.setOpaque(false);
        formPanel.setLayout(null);

        idLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        idLabel.setForeground(new java.awt.Color(255, 255, 255));
        idLabel.setText("ID:");
        formPanel.add(idLabel);
        idLabel.setBounds(30, 20, 50, 25);

        idField.setEditable(false);
        idField.setBackground(new java.awt.Color(58, 58, 58));
        idField.setForeground(new java.awt.Color(255, 255, 255));
        idField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        idField.setText("0");
        formPanel.add(idField);
        idField.setBounds(120, 20, 70, 25);

        btnSearch.setBackground(new java.awt.Color(30, 125, 250));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        formPanel.add(btnSearch);
        btnSearch.setBounds(200, 20, 100, 25);

        brandLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        brandLabel.setForeground(new java.awt.Color(255, 255, 255));
        brandLabel.setText("Brand:");
        formPanel.add(brandLabel);
        brandLabel.setBounds(30, 60, 80, 25);

        formPanel.add(brandCombo);
        brandCombo.setBounds(120, 60, 180, 25);

        modelLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        modelLabel.setForeground(new java.awt.Color(255, 255, 255));
        modelLabel.setText("Model:");
        formPanel.add(modelLabel);
        modelLabel.setBounds(30, 100, 80, 25);
        formPanel.add(modelField);
        modelField.setBounds(120, 100, 180, 25);

        fuelLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        fuelLabel.setForeground(new java.awt.Color(255, 255, 255));
        fuelLabel.setText("Fuel:");
        formPanel.add(fuelLabel);
        fuelLabel.setBounds(30, 140, 80, 25);

        formPanel.add(fuelCombo);
        fuelCombo.setBounds(120, 140, 180, 25);

        colorLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        colorLabel.setForeground(new java.awt.Color(255, 255, 255));
        colorLabel.setText("Color:");
        formPanel.add(colorLabel);
        colorLabel.setBounds(30, 180, 80, 25);

        formPanel.add(colorCombo);
        colorCombo.setBounds(120, 180, 180, 25);

        statusLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(255, 255, 255));
        statusLabel.setText("Status:");
        formPanel.add(statusLabel);
        statusLabel.setBounds(325, 20, 80, 25);

        formPanel.add(statusCombo);
        statusCombo.setBounds(415, 20, 155, 25);

        classLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        classLabel.setForeground(new java.awt.Color(255, 255, 255));
        classLabel.setText("Class:");
        formPanel.add(classLabel);
        classLabel.setBounds(325, 60, 80, 25);

        formPanel.add(classCombo);
        classCombo.setBounds(415, 60, 155, 25);

        passengersLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passengersLabel.setForeground(new java.awt.Color(255, 255, 255));
        passengersLabel.setText("Passengers:");
        formPanel.add(passengersLabel);
        passengersLabel.setBounds(325, 100, 85, 25);
        formPanel.add(passengersSpinner);
        passengersSpinner.setBounds(415, 100, 155, 25);

        gearboxLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        gearboxLabel.setForeground(new java.awt.Color(255, 255, 255));
        gearboxLabel.setText("Gearbox:");
        formPanel.add(gearboxLabel);
        gearboxLabel.setBounds(325, 140, 80, 25);

        rbAutomatic.setForeground(new java.awt.Color(255, 255, 255));
        rbAutomatic.setSelected(true);
        rbAutomatic.setText("Automatic");
        formPanel.add(rbAutomatic);
        rbAutomatic.setBounds(415, 140, 80, 25);

        rbManual.setForeground(new java.awt.Color(255, 255, 255));
        rbManual.setText("Manual");
        formPanel.add(rbManual);
        rbManual.setBounds(495, 140, 75, 25);

        priceLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        priceLabel.setForeground(new java.awt.Color(255, 255, 255));
        priceLabel.setText("Price/day:");
        formPanel.add(priceLabel);
        priceLabel.setBounds(325, 180, 80, 25);

        priceField.setText("10");
        formPanel.add(priceField);
        priceField.setBounds(415, 180, 155, 25);

        featuresTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        featuresTitle.setForeground(new java.awt.Color(51, 204, 255));
        featuresTitle.setText("Features:");
        formPanel.add(featuresTitle);
        featuresTitle.setBounds(30, 215, 200, 30);

        featuresPanel.setBackground(new java.awt.Color(236, 236, 238));
        featuresPanel.setLayout(null);

        chkAirCon.setText("Air Conditioning");
        featuresPanel.add(chkAirCon);
        chkAirCon.setBounds(15, 10, 130, 20);

        chkSunroof.setText("Sunroof");
        featuresPanel.add(chkSunroof);
        chkSunroof.setBounds(155, 10, 90, 20);

        chkNav.setText("Navigation systems");
        featuresPanel.add(chkNav);
        chkNav.setBounds(255, 10, 140, 20);

        chkElectric.setText("Electric Windows");
        featuresPanel.add(chkElectric);
        chkElectric.setBounds(405, 10, 130, 20);

        chkAirbags.setText("Airbags");
        featuresPanel.add(chkAirbags);
        chkAirbags.setBounds(15, 45, 130, 20);

        chkHeated.setText("Heated Seats");
        featuresPanel.add(chkHeated);
        chkHeated.setBounds(155, 45, 100, 20);

        chkBluetooth.setText("Bluetooth");
        featuresPanel.add(chkBluetooth);
        chkBluetooth.setBounds(255, 45, 110, 20);

        chkGps.setText("GPS");
        featuresPanel.add(chkGps);
        chkGps.setBounds(405, 45, 100, 20);

        formPanel.add(featuresPanel);
        featuresPanel.setBounds(30, 250, 540, 80);

        btnAdd.setBackground(new java.awt.Color(19, 138, 114));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        formPanel.add(btnAdd);
        btnAdd.setBounds(30, 345, 120, 35);

        btnUpdate.setBackground(new java.awt.Color(41, 128, 185));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Edit");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        formPanel.add(btnUpdate);
        btnUpdate.setBounds(165, 345, 120, 35);

        btnDelete.setBackground(new java.awt.Color(211, 84, 0));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Remove");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        formPanel.add(btnDelete);
        btnDelete.setBounds(300, 345, 120, 35);

        btnReset.setBackground(new java.awt.Color(243, 156, 18));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReset.setText("Reset");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        formPanel.add(btnReset);
        btnReset.setBounds(435, 345, 135, 35);

        btnRent.setBackground(new java.awt.Color(39, 174, 96));
        btnRent.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRent.setForeground(new java.awt.Color(255, 255, 255));
        btnRent.setText("Reserve Selected Car");
        btnRent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        formPanel.add(btnRent);
        btnRent.setBounds(30, 345, 540, 35);

        cardPanel.add(formPanel, "form");

        listPanel.setOpaque(false);
        listPanel.setLayout(null);

        carTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Model", "Category", "Price/Day", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        carsScrollPane.setViewportView(carTable);

        listPanel.add(carsScrollPane);
        carsScrollPane.setBounds(30, 20, 540, 310);

        btnBackToForm.setBackground(new java.awt.Color(142, 68, 173));
        btnBackToForm.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBackToForm.setForeground(new java.awt.Color(255, 255, 255));
        btnBackToForm.setText("Back to Editor");
        btnBackToForm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listPanel.add(btnBackToForm);
        btnBackToForm.setBounds(30, 340, 160, 35);

        cardPanel.add(listPanel, "list");

        imagesPanel.setOpaque(false);
        imagesPanel.setLayout(null);

        imagesTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        imagesTitle.setForeground(new java.awt.Color(255, 255, 255));
        imagesTitle.setText("Car Visual Loader");
        imagesPanel.add(imagesTitle);
        imagesTitle.setBounds(30, 20, 300, 25);

        imagesUploadLabel.setBackground(new java.awt.Color(58, 58, 58));
        imagesUploadLabel.setOpaque(true);
        imagesUploadLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagesUploadLabel.setText("Car photo uploader placeholder (Sprint 3 / 4)");
        imagesUploadLabel.setForeground(new java.awt.Color(170, 170, 170));
        imagesPanel.add(imagesUploadLabel);
        imagesUploadLabel.setBounds(30, 60, 540, 260);

        btnBackToFormImages.setBackground(new java.awt.Color(142, 68, 173));
        btnBackToFormImages.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBackToFormImages.setForeground(new java.awt.Color(255, 255, 255));
        btnBackToFormImages.setText("Back to Editor");
        btnBackToFormImages.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imagesPanel.add(btnBackToFormImages);
        btnBackToFormImages.setBounds(30, 340, 160, 35);

        cardPanel.add(imagesPanel, "images");

        add(cardPanel);
        cardPanel.setBounds(0, 60, 600, 390);

        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(null);

        btnAddCarImages.setBackground(new java.awt.Color(142, 68, 173));
        btnAddCarImages.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnAddCarImages.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCarImages.setText("Add Car Images");
        btnAddCarImages.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bottomPanel.add(btnAddCarImages);
        btnAddCarImages.setBounds(30, 15, 160, 35);

        btnBrandsList.setBackground(new java.awt.Color(142, 68, 173));
        btnBrandsList.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBrandsList.setForeground(new java.awt.Color(255, 255, 255));
        btnBrandsList.setText("Brands List");
        btnBrandsList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bottomPanel.add(btnBrandsList);
        btnBrandsList.setBounds(210, 15, 160, 35);

        btnCarsList.setBackground(new java.awt.Color(142, 68, 173));
        btnCarsList.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnCarsList.setForeground(new java.awt.Color(255, 255, 255));
        btnCarsList.setText("Cars List");
        btnCarsList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bottomPanel.add(btnCarsList);
        btnCarsList.setBounds(390, 15, 180, 35);

        add(bottomPanel);
        bottomPanel.setBounds(0, 450, 600, 70);

        carsBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin_bg.png"))); // NOI18N
        add(carsBgLabel);
        carsBgLabel.setBounds(0, 0, 600, 520);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JComboBox<model.Brand> brandCombo;
    private javax.swing.JLabel brandLabel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddCarImages;
    private javax.swing.JButton btnBackToForm;
    private javax.swing.JButton btnBackToFormImages;
    private javax.swing.JButton btnBrandsList;
    private javax.swing.JButton btnCarsList;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRent;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTable carTable;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JLabel carsBgLabel;
    private javax.swing.JLabel carsHeaderIcon;
    private javax.swing.JLabel carsHeaderTitle;
    private javax.swing.JScrollPane carsScrollPane;
    private javax.swing.JCheckBox chkAirCon;
    private javax.swing.JCheckBox chkAirbags;
    private javax.swing.JCheckBox chkBluetooth;
    private javax.swing.JCheckBox chkElectric;
    private javax.swing.JCheckBox chkGps;
    private javax.swing.JCheckBox chkHeated;
    private javax.swing.JCheckBox chkNav;
    private javax.swing.JCheckBox chkSunroof;
    private javax.swing.JComboBox<String> classCombo;
    private javax.swing.JLabel classLabel;
    private javax.swing.JComboBox<String> colorCombo;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JPanel featuresPanel;
    private javax.swing.JLabel featuresTitle;
    private javax.swing.JPanel formPanel;
    private javax.swing.JComboBox<String> fuelCombo;
    private javax.swing.JLabel fuelLabel;
    private javax.swing.JLabel gearboxLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idLabel;
    private javax.swing.JPanel imagesPanel;
    private javax.swing.JLabel imagesTitle;
    private javax.swing.JLabel imagesUploadLabel;
    private javax.swing.JPanel listPanel;
    private javax.swing.JTextField modelField;
    private javax.swing.JLabel modelLabel;
    private javax.swing.JLabel passengersLabel;
    private javax.swing.JSpinner passengersSpinner;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JRadioButton rbAutomatic;
    private javax.swing.JRadioButton rbManual;
    private javax.swing.JComboBox<String> statusCombo;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}

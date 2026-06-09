package view;

import model.Car;
import model.User;
import model.Location;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * High-fidelity visual form JDialog for booking a car.
 * Matches Image 1 layout specifications.
 * Strictly decoupled from logic: exposes fields and registers actions.
 * 
 * @author dipes
 */
public class BookingDialog extends javax.swing.JDialog {

    private final Car car;
    private final User currentUser;

    public BookingDialog(java.awt.Frame parent, Car car, User user) {
        super(parent, "Book a Car - " + car.getBrand() + " " + car.getModel(), true);
        this.car = car;
        this.currentUser = user;
        
        initComponents();
        
        // Populate Customer Details
        txtCustomerID.setText(String.valueOf(user.getId()));
        txtCustomerName.setText(user.getUsername());
        
        // Populate Car Details fields
        txtCarID.setText(String.valueOf(car.getId()));
        txtCarName.setText(car.getBrand() + " " + car.getModel());
        txtPricePerDay.setText(String.valueOf((int) car.getPricePerDay()));
        
        // Populate Car details labels in the right card
        valBrand.setText(car.getBrand());
        valModel.setText(car.getModel());
        valColor.setText(car.getColor());
        valSeats.setText(String.valueOf(car.getPassengers()));
        valTransmission.setText(car.getGearbox());
        valFuel.setText(car.getFuel());
        valYear.setText("2024"); // Default model mockup year
        
        setDefaultDates();
        calculatePricePreview();
        // Recalculate total price dynamically on focus loss
        java.awt.event.FocusAdapter recalculateAdapter = new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                calculatePricePreview();
            }
        };
        txtFromDate.addFocusListener(recalculateAdapter);
        txtToDate.addFocusListener(recalculateAdapter);
        
        // Wire inner card buttons
        btnInnerBook.addActionListener(e -> btnConfirm.doClick());
        
        // Custom styling and background
        leftPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setBackground(new Color(245, 245, 245));
        
        setSize(720, 540);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void setDefaultDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        
        // Default start date = today
        txtFromDate.setText(sdf.format(cal.getTime()));
        
        // Default end date = tomorrow
        cal.add(Calendar.DAY_OF_MONTH, 1);
        txtToDate.setText(sdf.format(cal.getTime()));
    }

    public void calculatePricePreview() {
        try {
            Date start = Date.valueOf(txtFromDate.getText().trim());
            Date end = Date.valueOf(txtToDate.getText().trim());
            
            if (end.before(start)) {
                txtTotalDays.setText("Invalid");
                txtTotalAmount.setText("N/A");
                return;
            }
            
            long diffMs = end.getTime() - start.getTime();
            long days = diffMs / (1000 * 60 * 60 * 24);
            if (days == 0 && end.equals(start)) {
                days = 1;
            }
            
            double total = days * car.getPricePerDay();
            txtTotalDays.setText(String.valueOf(days));
            txtTotalAmount.setText("$" + String.format("%.2f", total));
            
        } catch (IllegalArgumentException e) {
            txtTotalDays.setText("Format Err");
            txtTotalAmount.setText("N/A");
        }
    }

    // --- Component Getters ---
    
    public String getStartDateString() {
        return txtFromDate.getText().trim();
    }
    
    public String getEndDateString() {
        return txtToDate.getText().trim();
    }
    
    public JButton getBtnConfirm() {
        return btnConfirm;
    }
    
    public JButton getBtnCancel() {
        return btnCancel;
    }
    
    public JButton getBtnReset() {
        return btnReset;
    }
    
    public JButton getBtnInnerEdit() {
        return btnInnerEdit;
    }

    public JButton getBtnSelectCar() {
        return btnSelectCar;
    }

    public JCheckBox getChkAgree() {
        return chkAgree;
    }
    
    public Car getCar() {
        return car;
    }
    
    public model.Location getPickupLocation() {
        return (model.Location) cmbPickupLocation.getSelectedItem();
    }
    
    public model.Location getReturnLocation() {
        return (model.Location) cmbReturnLocation.getSelectedItem();
    }
    public void setLocations(java.util.List<model.Location> locations) {
        cmbPickupLocation.removeAllItems();
        cmbReturnLocation.removeAllItems();
        for (model.Location loc : locations) {
            cmbPickupLocation.addItem(loc);
            cmbReturnLocation.addItem(loc);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        closeLabel = new javax.swing.JLabel();
        leftPanel = new javax.swing.JPanel();
        lblCustomerID = new javax.swing.JLabel();
        txtCustomerID = new javax.swing.JTextField();
        lblCustomerName = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        lblCarID = new javax.swing.JLabel();
        txtCarID = new javax.swing.JTextField();
        btnSelectCar = new javax.swing.JButton();
        lblCarName = new javax.swing.JLabel();
        txtCarName = new javax.swing.JTextField();
        lblFromDate = new javax.swing.JLabel();
        txtFromDate = new javax.swing.JTextField();
        lblToDate = new javax.swing.JLabel();
        txtToDate = new javax.swing.JTextField();
        lblTotalDays = new javax.swing.JLabel();
        txtTotalDays = new javax.swing.JTextField();
        lblPricePerDay = new javax.swing.JLabel();
        txtPricePerDay = new javax.swing.JTextField();
        lblTotalAmount = new javax.swing.JLabel();
        txtTotalAmount = new javax.swing.JTextField();
        chkAgree = new javax.swing.JCheckBox();
        btnInnerEdit = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        carImageLabel = new javax.swing.JLabel();
        lblCarDetailsTitle = new javax.swing.JLabel();
        lblDetailBrand = new javax.swing.JLabel();
        valBrand = new javax.swing.JLabel();
        lblDetailModel = new javax.swing.JLabel();
        valModel = new javax.swing.JLabel();
        lblDetailColor = new javax.swing.JLabel();
        valColor = new javax.swing.JLabel();
        lblDetailSeats = new javax.swing.JLabel();
        valSeats = new javax.swing.JLabel();
        lblDetailTransmission = new javax.swing.JLabel();
        valTransmission = new javax.swing.JLabel();
        lblDetailFuel = new javax.swing.JLabel();
        valFuel = new javax.swing.JLabel();
        lblDetailYear = new javax.swing.JLabel();
        valYear = new javax.swing.JLabel();
        btnInnerBook = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        mainPanel.setBackground(new java.awt.Color(30, 30, 30));
        mainPanel.setLayout(null);

        headerPanel.setBackground(new java.awt.Color(128, 64, 160));
        headerPanel.setLayout(null);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("Book a Car");
        headerPanel.add(titleLabel);
        titleLabel.setBounds(20, 10, 200, 30);

        closeLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        closeLabel.setForeground(new java.awt.Color(255, 255, 255));
        closeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeLabel.setText("X");
        closeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
        });
        headerPanel.add(closeLabel);
        closeLabel.setBounds(680, 10, 30, 30);

        mainPanel.add(headerPanel);
        headerPanel.setBounds(0, 0, 720, 50);

        leftPanel.setBackground(new java.awt.Color(255, 255, 255));
        leftPanel.setLayout(null);

        lblCustomerID.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblCustomerID.setForeground(new java.awt.Color(51, 51, 51));
        lblCustomerID.setText("Customer ID:");
        leftPanel.add(lblCustomerID);
        lblCustomerID.setBounds(10, 10, 150, 20);

        txtCustomerID.setEditable(false);
        txtCustomerID.setBackground(new java.awt.Color(240, 240, 240));
        leftPanel.add(txtCustomerID);
        txtCustomerID.setBounds(10, 30, 170, 25);

        lblCustomerName.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblCustomerName.setForeground(new java.awt.Color(51, 51, 51));
        lblCustomerName.setText("Customer Name:");
        leftPanel.add(lblCustomerName);
        lblCustomerName.setBounds(200, 10, 150, 20);

        txtCustomerName.setEditable(false);
        txtCustomerName.setBackground(new java.awt.Color(240, 240, 240));
        leftPanel.add(txtCustomerName);
        txtCustomerName.setBounds(200, 30, 170, 25);

        lblCarID.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblCarID.setForeground(new java.awt.Color(51, 51, 51));
        lblCarID.setText("Car ID:");
        leftPanel.add(lblCarID);
        lblCarID.setBounds(10, 65, 100, 20);

        txtCarID.setEditable(false);
        txtCarID.setBackground(new java.awt.Color(240, 240, 240));
        leftPanel.add(txtCarID);
        txtCarID.setBounds(10, 85, 100, 25);

        btnSelectCar.setBackground(new java.awt.Color(128, 64, 160));
        btnSelectCar.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnSelectCar.setForeground(new java.awt.Color(255, 255, 255));
        btnSelectCar.setText("Select Car");
        btnSelectCar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        leftPanel.add(btnSelectCar);
        btnSelectCar.setBounds(120, 85, 80, 25);

        lblCarName.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblCarName.setForeground(new java.awt.Color(51, 51, 51));
        lblCarName.setText("Car Name / Model:");
        leftPanel.add(lblCarName);
        lblCarName.setBounds(210, 65, 150, 20);

        txtCarName.setEditable(false);
        txtCarName.setBackground(new java.awt.Color(240, 240, 240));
        leftPanel.add(txtCarName);
        txtCarName.setBounds(210, 85, 160, 25);

        lblFromDate.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblFromDate.setForeground(new java.awt.Color(51, 51, 51));
        lblFromDate.setText("From Date:");
        leftPanel.add(lblFromDate);
        lblFromDate.setBounds(10, 120, 150, 20);
        leftPanel.add(txtFromDate);
        txtFromDate.setBounds(10, 140, 170, 25);

        lblToDate.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblToDate.setForeground(new java.awt.Color(51, 51, 51));
        lblToDate.setText("To Date:");
        leftPanel.add(lblToDate);
        lblToDate.setBounds(200, 120, 150, 20);
        leftPanel.add(txtToDate);
        txtToDate.setBounds(200, 140, 170, 25);

        lblTotalDays.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblTotalDays.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalDays.setText("Total Days:");
        leftPanel.add(lblTotalDays);
        lblTotalDays.setBounds(10, 175, 100, 20);

        txtTotalDays.setEditable(false);
        txtTotalDays.setText("0");
        leftPanel.add(txtTotalDays);
        txtTotalDays.setBounds(10, 195, 100, 25);

        lblPricePerDay.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblPricePerDay.setForeground(new java.awt.Color(51, 51, 51));
        lblPricePerDay.setText("Price per Day:");
        leftPanel.add(lblPricePerDay);
        lblPricePerDay.setBounds(120, 175, 100, 20);

        txtPricePerDay.setEditable(false);
        txtPricePerDay.setText("0");
        leftPanel.add(txtPricePerDay);
        txtPricePerDay.setBounds(120, 195, 100, 25);

        lblTotalAmount.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblTotalAmount.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalAmount.setText("Total Amount:");
        leftPanel.add(lblTotalAmount);
        lblTotalAmount.setBounds(230, 175, 130, 20);

        txtTotalAmount.setEditable(false);
        txtTotalAmount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalAmount.setText("$0");
        leftPanel.add(txtTotalAmount);
        txtTotalAmount.setBounds(230, 195, 130, 25);

        chkAgree.setText("I agree to the terms and conditions");
        chkAgree.setOpaque(false);
        leftPanel.add(chkAgree);
        chkAgree.setBounds(10, 240, 350, 20);

        btnInnerEdit.setBackground(new java.awt.Color(43, 124, 245));
        btnInnerEdit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInnerEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnInnerEdit.setText("Edit Booking");
        btnInnerEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        leftPanel.add(btnInnerEdit);
        btnInnerEdit.setBounds(10, 280, 140, 35);
        
        lblPickupLocation = new javax.swing.JLabel();
        lblPickupLocation.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblPickupLocation.setForeground(new java.awt.Color(51, 51, 51));
        lblPickupLocation.setText("Pickup Branch:");
        leftPanel.add(lblPickupLocation);
        lblPickupLocation.setBounds(10, 320, 170, 20);

        cmbPickupLocation = new javax.swing.JComboBox<>();
        leftPanel.add(cmbPickupLocation);
        cmbPickupLocation.setBounds(10, 340, 170, 25);

        lblReturnLocation = new javax.swing.JLabel();
        lblReturnLocation.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        lblReturnLocation.setForeground(new java.awt.Color(51, 51, 51));
        lblReturnLocation.setText("Return Branch:");
        leftPanel.add(lblReturnLocation);
        lblReturnLocation.setBounds(200, 320, 170, 20);

        cmbReturnLocation = new javax.swing.JComboBox<>();
        leftPanel.add(cmbReturnLocation);
        cmbReturnLocation.setBounds(200, 340, 170, 25);

        mainPanel.add(leftPanel);
        leftPanel.setBounds(20, 70, 390, 400);

        rightPanel.setBackground(new java.awt.Color(255, 255, 255));
        rightPanel.setLayout(null);

        carImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/maserati_suv.png"))); // NOI18N
        rightPanel.add(carImageLabel);
        carImageLabel.setBounds(10, 10, 250, 140);

        lblCarDetailsTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCarDetailsTitle.setForeground(new java.awt.Color(51, 51, 51));
        lblCarDetailsTitle.setText("Car Details");
        rightPanel.add(lblCarDetailsTitle);
        lblCarDetailsTitle.setBounds(10, 160, 250, 20);

        lblDetailBrand.setForeground(new java.awt.Color(102, 102, 102));
        lblDetailBrand.setText("Brand:");
        rightPanel.add(lblDetailBrand);
        lblDetailBrand.setBounds(10, 190, 110, 15);

        valBrand.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        valBrand.setText("Maserati");
        rightPanel.add(valBrand);
        valBrand.setBounds(10, 205, 110, 15);

        lblDetailModel.setForeground(new java.awt.Color(102, 102, 102));
        lblDetailModel.setText("Model:");
        rightPanel.add(lblDetailModel);
        lblDetailModel.setBounds(130, 190, 130, 15);

        valModel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        valModel.setText("Ghibli S Q4");
        rightPanel.add(valModel);
        valModel.setBounds(130, 205, 130, 15);

        lblDetailColor.setForeground(new java.awt.Color(102, 102, 102));
        lblDetailColor.setText("Color:");
        rightPanel.add(lblDetailColor);
        lblDetailColor.setBounds(10, 225, 110, 15);

        valColor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        valColor.setText("Blue");
        rightPanel.add(valColor);
        valColor.setBounds(10, 240, 110, 15);

        lblDetailSeats.setForeground(new java.awt.Color(102, 102, 102));
        lblDetailSeats.setText("Seats:");
        rightPanel.add(lblDetailSeats);
        lblDetailSeats.setBounds(130, 225, 130, 15);

        valSeats.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        valSeats.setText("5");
        rightPanel.add(valSeats);
        valSeats.setBounds(130, 240, 130, 15);

        lblDetailTransmission.setForeground(new java.awt.Color(102, 102, 102));
        lblDetailTransmission.setText("Transmission:");
        rightPanel.add(lblDetailTransmission);
        lblDetailTransmission.setBounds(10, 260, 110, 15);

        valTransmission.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        valTransmission.setText("Automatic");
        rightPanel.add(valTransmission);
        valTransmission.setBounds(10, 275, 110, 15);

        lblDetailFuel.setForeground(new java.awt.Color(102, 102, 102));
        lblDetailFuel.setText("Fuel Type:");
        rightPanel.add(lblDetailFuel);
        lblDetailFuel.setBounds(130, 260, 130, 15);

        valFuel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        valFuel.setText("Petrol");
        rightPanel.add(valFuel);
        valFuel.setBounds(130, 275, 130, 15);

        lblDetailYear.setForeground(new java.awt.Color(102, 102, 102));
        lblDetailYear.setText("Year:");
        rightPanel.add(lblDetailYear);
        lblDetailYear.setBounds(10, 295, 110, 15);

        valYear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        valYear.setText("2024");
        rightPanel.add(valYear);
        valYear.setBounds(10, 310, 110, 15);

        btnInnerBook.setBackground(new java.awt.Color(130, 218, 178));
        btnInnerBook.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnInnerBook.setForeground(new java.awt.Color(255, 255, 255));
        btnInnerBook.setText("Book Now");
        btnInnerBook.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rightPanel.add(btnInnerBook);
        btnInnerBook.setBounds(10, 345, 250, 35);

        mainPanel.add(rightPanel);
        rightPanel.setBounds(430, 70, 270, 400);

        btnConfirm.setBackground(new java.awt.Color(130, 218, 178));
        btnConfirm.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnConfirm.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirm.setText("Book Now");
        btnConfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainPanel.add(btnConfirm);
        btnConfirm.setBounds(190, 490, 100, 35);

        btnReset.setBackground(new java.awt.Color(255, 128, 0));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Clear");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainPanel.add(btnReset);
        btnReset.setBounds(310, 490, 100, 35);

        btnCancel.setBackground(new java.awt.Color(241, 238, 236));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainPanel.add(btnCancel);
        btnCancel.setBounds(430, 490, 100, 35);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 720, 540);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        dispose();
    }//GEN-LAST:event_closeLabelMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnInnerBook;
    private javax.swing.JButton btnInnerEdit;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSelectCar;
    private javax.swing.JComboBox<model.Location> cmbPickupLocation;
    private javax.swing.JComboBox<model.Location> cmbReturnLocation;
    private javax.swing.JLabel lblPickupLocation;
    private javax.swing.JLabel lblReturnLocation;
    private javax.swing.JLabel carImageLabel;
    private javax.swing.JCheckBox chkAgree;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel valBrand;
    private javax.swing.JLabel valColor;
    private javax.swing.JLabel valFuel;
    private javax.swing.JLabel valModel;
    private javax.swing.JLabel valSeats;
    private javax.swing.JLabel valTransmission;
    private javax.swing.JLabel valYear;
    private javax.swing.JLabel lblCarDetailsTitle;
    private javax.swing.JLabel lblCarID;
    private javax.swing.JLabel lblCarName;
    private javax.swing.JLabel lblCustomerID;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblDetailBrand;
    private javax.swing.JLabel lblDetailColor;
    private javax.swing.JLabel lblDetailFuel;
    private javax.swing.JLabel lblDetailModel;
    private javax.swing.JLabel lblDetailSeats;
    private javax.swing.JLabel lblDetailTransmission;
    private javax.swing.JLabel lblDetailYear;
    private javax.swing.JLabel lblFromDate;
    private javax.swing.JLabel lblPricePerDay;
    private javax.swing.JLabel lblToDate;
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JLabel lblTotalDays;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField txtCarID;
    private javax.swing.JTextField txtCarName;
    private javax.swing.JTextField txtCustomerID;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtFromDate;
    private javax.swing.JTextField txtPricePerDay;
    private javax.swing.JTextField txtToDate;
    private javax.swing.JTextField txtTotalAmount;
    private javax.swing.JTextField txtTotalDays;
    // End of variables declaration//GEN-END:variables
}

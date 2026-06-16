package controller;

import dao.BookingDao;
import dao.CarDao;
import dao.LocationDao;
import model.Booking;
import model.Car;
import model.Location;
import model.User;
import view.AdminDashboard;
import view.BookingDialog;
import view.UserDashboard;
import view.BookingPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * Controller class coordinating Booking operations across view and data layers.
 * Implements strict MVC separation.
 * 
 * @author dipes
 */
public class BookingController {

    private final BookingDao bookingDao;
    private final CarDao carDao;
    private final LocationDao locationDao;

    // Optional view references
    private UserDashboard userDashboard;
    private User currentUser;
    private JTable userBookingsTable;

    private AdminDashboard adminDashboard;
    private JTable adminBookingsTable;

    public BookingController() {
        this.bookingDao = new BookingDao();
        this.carDao = new CarDao();
        this.locationDao = new LocationDao();
    }

    /**
     * User dashboard constructor.
     */
    public BookingController(UserDashboard userDashboard, User currentUser) {
        this();
        this.userDashboard = userDashboard;
        this.currentUser = currentUser;

        BookingPanel bookingPanel = userDashboard.getBookingPanel();
        bookingPanel.setAdminMode(false);
        this.userBookingsTable = bookingPanel.getBookingsTable();

        // Bind listener for refresh
        bookingPanel.getBtnRefresh().addActionListener(e -> {
            loadUserBookingsTable();
            refreshUserDashboardMetrics();
        });

        // Bind listener for edit/cancel
        bookingPanel.getBtnEditCancel().addActionListener(new EditCancelBookingListener(false));

        // Bind listener for Invoice Dialog
        bookingPanel.getBtnInvoice().addActionListener(e -> {
            openInvoiceDialog(userDashboard, false);
        });


        // Toggles btnRate based on table row selection
        userBookingsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = userBookingsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Object statusVal = userBookingsTable.getValueAt(selectedRow, 8);
                    String status = statusVal != null ? statusVal.toString() : "";
                    bookingPanel.getBtnRate().setEnabled("Completed".equalsIgnoreCase(status));
                } else {
                    bookingPanel.getBtnRate().setEnabled(false);
                }
            }
        });
        bookingPanel.getBtnRate().setEnabled(false);

        loadUserBookingsTable();
        refreshUserDashboardMetrics();
    }

    /**
     * Admin dashboard constructor.
     */
    public BookingController(AdminDashboard adminDashboard) {
        this();
        this.adminDashboard = adminDashboard;

        BookingPanel bookingPanel = adminDashboard.getBookingPanel();
        bookingPanel.setAdminMode(true);
        this.adminBookingsTable = bookingPanel.getBookingsTable();

        // Bind listeners for admin actions
        bookingPanel.getBtnApprove().addActionListener(new ApproveBookingListener());
        bookingPanel.getBtnReject().addActionListener(new RejectBookingListener());
        bookingPanel.getBtnReturn().addActionListener(new CompleteReturnListener());
        bookingPanel.getBtnRefresh().addActionListener(e -> {
            loadAdminBookingsTable();
            refreshAdminDashboardMetrics();
        });

        // Bind listener for edit/cancel
        bookingPanel.getBtnEditCancel().addActionListener(new EditCancelBookingListener(true));

        // Bind listener for Invoice Dialog
        bookingPanel.getBtnInvoice().addActionListener(e -> {
            openInvoiceDialog(adminDashboard, true);
        });

        loadAdminBookingsTable();
        refreshAdminDashboardMetrics();
    }

    // ==================== Data Loading Operations ====================

    public void loadUserBookingsTable() {
        if (userBookingsTable == null || currentUser == null) return;

        DefaultTableModel model = (DefaultTableModel) userBookingsTable.getModel();
        model.setRowCount(0);

        List<Booking> bookings = bookingDao.getBookingsByUser(currentUser.getId());
        for (Booking b : bookings) {
            model.addRow(new Object[]{
                b.getId(),
                currentUser.getUsername(), // Populate hidden User column to match model columns count (9)
                b.getCarDetails(),
                b.getPickupLocationName(),
                b.getReturnLocationName(),
                b.getStartDate().toString(),
                b.getEndDate().toString(),
                String.format("$%.2f", b.getTotalPrice()),
                b.getStatus()
            });
        }
    }

    public void loadAdminBookingsTable() {
        if (adminBookingsTable == null) return;

        DefaultTableModel model = (DefaultTableModel) adminBookingsTable.getModel();
        model.setRowCount(0);

        List<Booking> bookings = bookingDao.getAllBookings();
        for (Booking b : bookings) {
            model.addRow(new Object[]{
                b.getId(),
                b.getUsername(),
                b.getCarDetails(),
                b.getPickupLocationName(),
                b.getReturnLocationName(),
                b.getStartDate().toString(),
                b.getEndDate().toString(),
                String.format("$%.2f", b.getTotalPrice()),
                b.getStatus()
            });
        }
    }

    private void refreshUserDashboardMetrics() {
        if (userDashboard == null || currentUser == null) return;

        List<Booking> bookings = bookingDao.getBookingsByUser(currentUser.getId());
        int activeCount = 0;
        int pendingCount = 0;
        int totalDays = 0;

        for (Booking b : bookings) {
            if ("Approved".equalsIgnoreCase(b.getStatus())) {
                activeCount++;
            } else if ("Pending".equalsIgnoreCase(b.getStatus())) {
                pendingCount++;
            }

            if ("Approved".equalsIgnoreCase(b.getStatus()) || "Completed".equalsIgnoreCase(b.getStatus())) {
                long diffMs = b.getEndDate().getTime() - b.getStartDate().getTime();
                long days = diffMs / (1000 * 60 * 60 * 24);
                if (days == 0 && b.getEndDate().equals(b.getStartDate())) {
                    days = 1;
                }
                totalDays += days;
            }
        }

        userDashboard.setActivitySummary(activeCount, pendingCount, totalDays);
    }

    private void refreshAdminDashboardMetrics() {
        if (adminDashboard == null) return;
        model.DashboardMetrics metrics = bookingDao.getDashboardMetrics();
        adminDashboard.setSystemSummary(metrics);
    }

    // ==================== Booking Creator Modal (Static) ====================

    public static void openBookingDialog(JFrame parent, Car car, User user, Runnable onComplete) {
        BookingDialog dialog = new BookingDialog(parent, car, user);
        
        // Populate locations from database dynamically using LocationDao (keeps view clean)
        dao.LocationDao locationDao = new dao.LocationDao();
        dialog.setLocations(locationDao.getAllLocations());
        
        dialog.getBtnConfirm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startStr = dialog.getStartDateString();
                String endStr = dialog.getEndDateString();
                Location pickup = dialog.getPickupLocation();
                Location returnLoc = dialog.getReturnLocation();

                if (pickup == null || returnLoc == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select pickup and return branches.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Date startDate, endDate;
                try {
                    startDate = Date.valueOf(startStr);
                    endDate = Date.valueOf(endStr);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter valid dates in YYYY-MM-DD format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Date sequence check
                if (endDate.before(startDate)) {
                    JOptionPane.showMessageDialog(dialog, "Return date must be after pickup date.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Date past check
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String todayStr = sdf.format(new java.util.Date());
                if (startStr.compareTo(todayStr) < 0) {
                    JOptionPane.showMessageDialog(dialog, "Pickup date cannot be in the past.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Calculate total cost
                long diffMs = endDate.getTime() - startDate.getTime();
                long days = diffMs / (1000 * 60 * 60 * 24);
                if (days == 0 && endDate.equals(startDate)) {
                    days = 1;
                }
                double totalPrice = days * car.getPricePerDay();

                // Save to database
                Booking booking = new Booking(
                    user.getId(),
                    car.getId(),
                    pickup.getId(),
                    returnLoc.getId(),
                    startDate,
                    endDate,
                    totalPrice,
                    "Pending"
                );

                BookingDao bDao = new BookingDao();
                boolean success = bDao.createBooking(booking);

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Reservation request submitted successfully!\nPending administrator approval.", "Reservation Pending", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Database Error: Failed to log reservation. Try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    // ==================== Action Listeners (Admin Actions) ====================

    class ApproveBookingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = adminBookingsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(adminDashboard, "Please select a booking request from the queue.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int bookingId = (int) adminBookingsTable.getValueAt(selectedRow, 0);
            Booking booking = bookingDao.getBookingById(bookingId);

            if (booking == null) {
                JOptionPane.showMessageDialog(adminDashboard, "Booking not found.", "Execution Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!"Pending".equalsIgnoreCase(booking.getStatus())) {
                JOptionPane.showMessageDialog(adminDashboard, "Only requests in 'Pending' status can be approved.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verify car availability
            Car car = carDao.getCarById(booking.getCarId());
            if (car == null) {
                JOptionPane.showMessageDialog(adminDashboard, "Associated vehicle record not found.", "Execution Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!"Available".equalsIgnoreCase(car.getStatus())) {
                JOptionPane.showMessageDialog(adminDashboard, "This vehicle is currently " + car.getStatus() + " and cannot be rented.", "Vehicle Unavailable", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Transactional Approve booking + update car status
            boolean success = bookingDao.updateBookingStatus(bookingId, "Approved");
            if (success) {
                car.setStatus("Rented");
                carDao.updateCar(car);

                JOptionPane.showMessageDialog(adminDashboard, "Booking ID " + bookingId + " has been Approved!\nVehicle status changed to 'Rented'.", "Booking Approved", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh views
                loadAdminBookingsTable();
                refreshAdminDashboardMetrics();
                
                // Refresh the nested fleet JTable inside AdminDashboard
                new CarController().loadAdminCarTable(adminDashboard.getCarPanel());
            } else {
                JOptionPane.showMessageDialog(adminDashboard, "Database error. Failed to approve booking.", "Execution Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class RejectBookingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = adminBookingsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(adminDashboard, "Please select a booking request from the queue.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int bookingId = (int) adminBookingsTable.getValueAt(selectedRow, 0);
            Booking booking = bookingDao.getBookingById(bookingId);

            if (booking == null) {
                JOptionPane.showMessageDialog(adminDashboard, "Booking not found.", "Execution Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!"Pending".equalsIgnoreCase(booking.getStatus())) {
                JOptionPane.showMessageDialog(adminDashboard, "Only requests in 'Pending' status can be rejected.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean success = bookingDao.updateBookingStatus(bookingId, "Rejected");
            if (success) {
                JOptionPane.showMessageDialog(adminDashboard, "Booking ID " + bookingId + " has been Rejected.", "Booking Rejected", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh views
                loadAdminBookingsTable();
                refreshAdminDashboardMetrics();
            } else {
                JOptionPane.showMessageDialog(adminDashboard, "Database error. Failed to reject booking.", "Execution Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class CompleteReturnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = adminBookingsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(adminDashboard, "Please select a booking from the table.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int bookingId = (int) adminBookingsTable.getValueAt(selectedRow, 0);
            Booking booking = bookingDao.getBookingById(bookingId);

            if (booking == null) {
                JOptionPane.showMessageDialog(adminDashboard, "Booking not found.", "Execution Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!"Approved".equalsIgnoreCase(booking.getStatus())) {
                JOptionPane.showMessageDialog(adminDashboard, "Only approved/active rentals can be marked returned.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Car car = carDao.getCarById(booking.getCarId());
            if (car == null) {
                JOptionPane.showMessageDialog(adminDashboard, "Associated vehicle record not found.", "Execution Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mark returned
            boolean success = bookingDao.updateBookingStatus(bookingId, "Completed");
            if (success) {
                // Update car status to Available, and update its branch/location to the return branch!
                car.setStatus("Available");
                car.setLocationId(booking.getReturnLocationId());
                carDao.updateCar(car);

                JOptionPane.showMessageDialog(adminDashboard, "Vehicle returned successfully!\nStatus reverted to 'Available' and located at return branch.", "Return Logged", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh views
                loadAdminBookingsTable();
                refreshAdminDashboardMetrics();
                
                // Refresh the nested fleet JTable inside AdminDashboard
                new CarController().loadAdminCarTable(adminDashboard.getCarPanel());
            } else {
                JOptionPane.showMessageDialog(adminDashboard, "Database error. Failed to register return.", "Execution Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ==================== Booking Edit / Remove Integration ====================

    class EditCancelBookingListener implements ActionListener {
        private final boolean isAdmin;

        public EditCancelBookingListener(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = isAdmin ? adminBookingsTable : userBookingsTable;
            JFrame parent = isAdmin ? adminDashboard : userDashboard;
            
            if (table == null) return;
            
            int selectedRow = table.getSelectedRow();
            
            // Create the dialog
            view.BookingEditDialog editDialog = new view.BookingEditDialog(parent, true);
            
            // Pre-populate if a row is selected
            if (selectedRow != -1) {
                int bookingId = (int) table.getValueAt(selectedRow, 0);
                Booking selectedBooking = bookingDao.getBookingById(bookingId);
                if (selectedBooking != null) {
                    populateDialogFields(editDialog, selectedBooking);
                }
            }
            
            // In User mode, restrict customer editing
            if (!isAdmin) {
                editDialog.getBtnSelectCustomer().setEnabled(false);
                editDialog.getTxtCustomerName().setEditable(false);
            }
            
            // Wire dialog button listeners
            editDialog.getBtnBookingList().addActionListener(evt -> {
                selectBookingFromList(editDialog, isAdmin);
            });
            
            editDialog.getBtnSelectCar().addActionListener(evt -> {
                selectCarFromList(editDialog);
            });
            
            editDialog.getBtnSelectCustomer().addActionListener(evt -> {
                if (isAdmin) {
                    selectCustomerFromList(editDialog);
                }
            });
            
            editDialog.getBtnCalculate().addActionListener(evt -> {
                calculateDialogPrice(editDialog);
            });
            
            editDialog.getBtnEdit().addActionListener(evt -> {
                handleUpdateBooking(editDialog);
            });
            
            editDialog.getBtnRemove().addActionListener(evt -> {
                handleDeleteBooking(editDialog);
            });
            
            editDialog.setVisible(true);
        }
    }

    private void populateDialogFields(view.BookingEditDialog dialog, Booking booking) {
        dialog.getTxtBookingID().setText(String.valueOf(booking.getId()));
        
        Car car = carDao.getCarById(booking.getCarId());
        if (car != null) {
            dialog.getTxtCarBrand().setText(car.getBrand());
            dialog.getTxtCarModel().setText(car.getModel());
            dialog.getValCarBrandID().setText(String.valueOf(car.getBrandId()));
            dialog.getValCarID().setText(String.valueOf(car.getId()));
            dialog.getValCarPrice().setText(String.valueOf((int) car.getPricePerDay()));
        }
        
        dialog.getValCustomerID().setText(String.valueOf(booking.getUserId()));
        dialog.getTxtCustomerName().setText(booking.getUsername());
        
        Location pickupLoc = locationDao.getLocationById(booking.getPickupLocationId());
        if (pickupLoc != null) {
            dialog.getTxtPickupCity().setText(pickupLoc.getCity());
            dialog.getTxtPickupAddress().setText(pickupLoc.getAddress());
        }
        dialog.getTxtPickupDate().setText(booking.getStartDate().toString());
        
        Location dropoffLoc = locationDao.getLocationById(booking.getReturnLocationId());
        if (dropoffLoc != null) {
            dialog.getTxtDropoffCity().setText(dropoffLoc.getCity());
            dialog.getTxtDropoffAddress().setText(dropoffLoc.getAddress());
        }
        dialog.getTxtDropoffDate().setText(booking.getEndDate().toString());
        
        dialog.getTxtTotalPrice().setText(String.valueOf((int) booking.getTotalPrice()));
    }

    private void selectBookingFromList(view.BookingEditDialog editDialog, boolean isAdmin) {
        List<Booking> bookings = isAdmin ? bookingDao.getAllBookings() : bookingDao.getBookingsByUser(currentUser.getId());
        if (bookings.isEmpty()) {
            JOptionPane.showMessageDialog(editDialog, "No bookings available to select.", "Booking Selector", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] headers = {"Booking ID", "Customer", "Car Details", "Start Date", "End Date", "Total Price", "Status"};
        Object[][] data = new Object[bookings.size()][7];
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            data[i][0] = b.getId();
            data[i][1] = b.getUsername();
            data[i][2] = b.getCarDetails();
            data[i][3] = b.getStartDate().toString();
            data[i][4] = b.getEndDate().toString();
            data[i][5] = String.format("$%.2f", b.getTotalPrice());
            data[i][6] = b.getStatus();
        }

        JFrame parentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(editDialog);
        showSelectionDialog(parentFrame, "Select a Booking", headers, data, selectedIdx -> {
            Booking selected = bookings.get(selectedIdx);
            populateDialogFields(editDialog, selected);
        });
    }

    private void selectCarFromList(view.BookingEditDialog editDialog) {
        List<Car> cars = carDao.getAllCars();
        if (cars.isEmpty()) {
            JOptionPane.showMessageDialog(editDialog, "No cars available to select.", "Car Selector", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] headers = {"Car ID", "Brand", "Model", "Category", "Price/Day", "Status"};
        Object[][] data = new Object[cars.size()][6];
        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);
            data[i][0] = c.getId();
            data[i][1] = c.getBrand();
            data[i][2] = c.getModel();
            data[i][3] = c.getCategory();
            data[i][4] = String.format("$%.2f", c.getPricePerDay());
            data[i][5] = c.getStatus();
        }

        JFrame parentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(editDialog);
        showSelectionDialog(parentFrame, "Select a Vehicle", headers, data, selectedIdx -> {
            Car selected = cars.get(selectedIdx);
            editDialog.getTxtCarBrand().setText(selected.getBrand());
            editDialog.getTxtCarModel().setText(selected.getModel());
            editDialog.getValCarBrandID().setText(String.valueOf(selected.getBrandId()));
            editDialog.getValCarID().setText(String.valueOf(selected.getId()));
            editDialog.getValCarPrice().setText(String.valueOf((int) selected.getPricePerDay()));
        });
    }

    private void selectCustomerFromList(view.BookingEditDialog editDialog) {
        dao.UserDao uDao = new dao.UserDao();
        List<User> customers = uDao.getAllCustomers();
        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(editDialog, "No customers registered in the system.", "Customer Selector", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] headers = {"Customer ID", "Username", "Email", "Status"};
        Object[][] data = new Object[customers.size()][4];
        for (int i = 0; i < customers.size(); i++) {
            User u = customers.get(i);
            data[i][0] = u.getId();
            data[i][1] = u.getUsername();
            data[i][2] = u.getEmail();
            data[i][3] = u.getStatus();
        }

        JFrame parentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(editDialog);
        showSelectionDialog(parentFrame, "Select a Customer", headers, data, selectedIdx -> {
            User selected = customers.get(selectedIdx);
            editDialog.getValCustomerID().setText(String.valueOf(selected.getId()));
            editDialog.getTxtCustomerName().setText(selected.getUsername());
        });
    }

    private void calculateDialogPrice(view.BookingEditDialog dialog) {
        try {
            Date start = Date.valueOf(dialog.getTxtPickupDate().getText().trim());
            Date end = Date.valueOf(dialog.getTxtDropoffDate().getText().trim());
            
            if (end.before(start)) {
                JOptionPane.showMessageDialog(dialog, "Dropoff date must be after pickup date.", "Validation Warning", JOptionPane.WARNING_MESSAGE);
                dialog.getTxtTotalPrice().setText("0");
                return;
            }
            
            long diffMs = end.getTime() - start.getTime();
            long days = diffMs / (1000 * 60 * 60 * 24);
            if (days == 0 && end.equals(start)) {
                days = 1;
            }
            
            int pricePerDay = Integer.parseInt(dialog.getValCarPrice().getText());
            double total = days * pricePerDay;
            dialog.getTxtTotalPrice().setText(String.valueOf((int) total));
            
        } catch (NumberFormatException e) {
            dialog.getTxtTotalPrice().setText("0");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(dialog, "Please enter valid dates in YYYY-MM-DD format.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
            dialog.getTxtTotalPrice().setText("0");
        }
    }

    private void handleUpdateBooking(view.BookingEditDialog dialog) {
        try {
            String bookingIdStr = dialog.getTxtBookingID().getText().trim();
            if ("00".equals(bookingIdStr) || bookingIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please select a booking from the booking list first.", "Update Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int bookingId = Integer.parseInt(bookingIdStr);

            int carId = Integer.parseInt(dialog.getValCarID().getText().trim());
            int customerId = Integer.parseInt(dialog.getValCustomerID().getText().trim());
            
            String startStr = dialog.getTxtPickupDate().getText().trim();
            String endStr = dialog.getTxtDropoffDate().getText().trim();
            Date startDate = Date.valueOf(startStr);
            Date endDate = Date.valueOf(endStr);

            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(dialog, "Dropoff date must be after pickup date.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String pickupCity = dialog.getTxtPickupCity().getText().trim();
            String pickupAddr = dialog.getTxtPickupAddress().getText().trim();
            String dropoffCity = dialog.getTxtDropoffCity().getText().trim();
            String dropoffAddr = dialog.getTxtDropoffAddress().getText().trim();

            if (pickupCity.isEmpty() || pickupAddr.isEmpty() || dropoffCity.isEmpty() || dropoffAddr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all pickup and dropoff location details.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int pickupLocId = getOrCreateLocationId(pickupCity, pickupAddr);
            int dropoffLocId = getOrCreateLocationId(dropoffCity, dropoffAddr);

            // Fetch current booking record
            Booking currentBooking = bookingDao.getBookingById(bookingId);
            if (currentBooking == null) {
                JOptionPane.showMessageDialog(dialog, "Booking not found.", "Execution Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate price
            long diffMs = endDate.getTime() - startDate.getTime();
            long days = diffMs / (1000 * 60 * 60 * 24);
            if (days == 0 && endDate.equals(startDate)) {
                days = 1;
            }
            double pricePerDay = Double.parseDouble(dialog.getValCarPrice().getText().trim());
            double totalAmount = days * pricePerDay;

            // Handle car status transfer if car was changed and booking was Approved (active)
            if ("Approved".equalsIgnoreCase(currentBooking.getStatus())) {
                if (currentBooking.getCarId() != carId) {
                    // Revert old car
                    Car oldCar = carDao.getCarById(currentBooking.getCarId());
                    if (oldCar != null) {
                        oldCar.setStatus("Available");
                        carDao.updateCar(oldCar);
                    }
                    // Rent new car
                    Car newCar = carDao.getCarById(carId);
                    if (newCar != null) {
                        newCar.setStatus("Rented");
                        carDao.updateCar(newCar);
                    }
                }
            }

            currentBooking.setCarId(carId);
            currentBooking.setUserId(customerId);
            currentBooking.setPickupLocationId(pickupLocId);
            currentBooking.setReturnLocationId(dropoffLocId);
            currentBooking.setStartDate(startDate);
            currentBooking.setEndDate(endDate);
            currentBooking.setTotalPrice(totalAmount);

            boolean success = bookingDao.updateBooking(currentBooking);
            if (success) {
                JOptionPane.showMessageDialog(dialog, "Booking ID " + bookingId + " updated successfully!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                
                // Refresh dashboards
                if (userDashboard != null) {
                    loadUserBookingsTable();
                    refreshUserDashboardMetrics();
                }
                if (adminDashboard != null) {
                    loadAdminBookingsTable();
                    refreshAdminDashboardMetrics();
                    // Refresh fleet table
                    new CarController().loadAdminCarTable(adminDashboard.getCarPanel());
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Database error: failed to update booking.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(dialog, "Please enter valid dates in YYYY-MM-DD format.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteBooking(view.BookingEditDialog dialog) {
        String bookingIdStr = dialog.getTxtBookingID().getText().trim();
        if ("00".equals(bookingIdStr) || bookingIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please select a booking from the booking list first.", "Removal Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int bookingId = Integer.parseInt(bookingIdStr);

        int confirm = JOptionPane.showConfirmDialog(dialog, 
            "Are you sure you want to cancel and remove booking ID: " + bookingId + "?",
            "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            Booking booking = bookingDao.getBookingById(bookingId);
            if (booking != null) {
                // Revert car status if booking was active
                if ("Approved".equalsIgnoreCase(booking.getStatus())) {
                    Car car = carDao.getCarById(booking.getCarId());
                    if (car != null) {
                        car.setStatus("Available");
                        carDao.updateCar(car);
                    }
                }
                
                boolean success = bookingDao.deleteBooking(bookingId);
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Booking cancelled and removed successfully.", "Cancellation Complete", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    
                    // Refresh dashboards
                    if (userDashboard != null) {
                        loadUserBookingsTable();
                        refreshUserDashboardMetrics();
                    }
                    if (adminDashboard != null) {
                        loadAdminBookingsTable();
                        refreshAdminDashboardMetrics();
                        // Refresh fleet table
                        new CarController().loadAdminCarTable(adminDashboard.getCarPanel());
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Database error: failed to cancel booking.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private int getOrCreateLocationId(String city, String address) {
        List<Location> list = locationDao.getAllLocations();
        for (Location loc : list) {
            if (loc.getCity().equalsIgnoreCase(city) && loc.getAddress().equalsIgnoreCase(address)) {
                return loc.getId();
            }
        }
        
        // Not found, create it
        Location newLoc = new Location(0, city, address);
        boolean added = locationDao.addLocation(newLoc);
        if (added) {
            // Find newly generated ID
            list = locationDao.getAllLocations();
            for (Location loc : list) {
                if (loc.getCity().equalsIgnoreCase(city) && loc.getAddress().equalsIgnoreCase(address)) {
                    return loc.getId();
                }
            }
        }
        return 1; // Fallback to first branch
    }

    private void showSelectionDialog(JFrame parent, String title, String[] headers, Object[][] data, java.util.function.Consumer<Integer> onSelected) {
        javax.swing.JDialog selectDialog = new javax.swing.JDialog(parent, title, true);
        selectDialog.setLayout(new java.awt.BorderLayout(10, 10));
        
        JTable table = new JTable(new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setRowHeight(25);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        table.setBackground(new Color(50, 50, 50));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(70, 70, 70));
        table.setSelectionBackground(new Color(0, 102, 153));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(74, 83, 97));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(table);
        selectDialog.add(scroll, java.awt.BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        JButton btnSelect = new JButton("Select");
        btnSelect.setBackground(new Color(43, 124, 245));
        btnSelect.setForeground(Color.WHITE);
        btnSelect.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBackground(new Color(241, 238, 236));
        btnCancel.setForeground(new Color(51, 51, 51));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        bottomPanel.add(btnSelect);
        bottomPanel.add(btnCancel);
        selectDialog.add(bottomPanel, java.awt.BorderLayout.SOUTH);
        
        btnSelect.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                onSelected.accept(table.convertRowIndexToModel(selectedRow));
                selectDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(selectDialog, "Please select a row.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnCancel.addActionListener(e -> selectDialog.dispose());
        
        selectDialog.setSize(600, 350);
        selectDialog.setLocationRelativeTo(parent);
        selectDialog.setVisible(true);
    }

    private void openInvoiceDialog(JFrame parent, boolean isAdmin) {
        JTable table = isAdmin ? adminBookingsTable : userBookingsTable;
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(parent, "Please select a booking to view its invoice.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId = (Integer) table.getValueAt(selectedRow, 0);
        Booking booking = bookingDao.getBookingById(bookingId);
        if (booking == null) {
            JOptionPane.showMessageDialog(parent, "Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        view.InvoiceDialog dialog = new view.InvoiceDialog(parent, booking);
        dialog.getBtnExportHtml().addActionListener(e -> {
            exportInvoiceToHtml(dialog, booking);
        });
        dialog.getBtnClose().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    /*
    private void openReviewDialog(JFrame parent) {
        int selectedRow = userBookingsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(parent, "Please select a booking to rate.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId = (Integer) userBookingsTable.getValueAt(selectedRow, 0);
        Booking booking = bookingDao.getBookingById(bookingId);
        if (booking == null) {
            JOptionPane.showMessageDialog(parent, "Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!"Completed".equalsIgnoreCase(booking.getStatus())) {
            JOptionPane.showMessageDialog(parent, "Only completed bookings can be rated.", "Invalid Action", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dao.ReviewDao reviewDao = new dao.ReviewDao();
        if (reviewDao.hasUserReviewed(bookingId)) {
            JOptionPane.showMessageDialog(parent, "You have already submitted a review for this booking.", "Already Reviewed", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        view.ReviewDialog dialog = new view.ReviewDialog(parent, booking);
        dialog.getBtnSubmit().addActionListener(e -> {
            int rating = (Integer) dialog.getCbRating().getSelectedItem();
            String comment = dialog.getTaComment().getText().trim();
            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please write a comment.", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            model.Review review = new model.Review(bookingId, booking.getUserId(), booking.getCarId(), rating, comment);
            boolean success = reviewDao.addReview(review);
            if (success) {
                JOptionPane.showMessageDialog(dialog, "Thank you for your feedback!", "Review Submitted", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to submit review.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.getBtnCancel().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    */

    private void exportInvoiceToHtml(javax.swing.JDialog parent, Booking booking) {
        try {
            java.io.File dir = new java.io.File("invoices");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            java.io.File file = new java.io.File(dir, "Invoice_" + booking.getId() + ".html");

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n<html>\n<head>\n");
            html.append("<title>Invoice #" + booking.getId() + "</title>\n");
            html.append("<style>\n");
            html.append("  body { font-family: 'Segoe UI', Arial, sans-serif; background: #1e1e1e; color: #ffffff; padding: 40px; margin: 0; }\n");
            html.append("  .invoice-card { background: #2d2d2d; max-width: 600px; margin: 0 auto; border-radius: 12px; box-shadow: 0 8px 24px rgba(0,0,0,0.5); padding: 40px; border: 1px solid #404040; }\n");
            html.append("  .header { text-align: center; margin-bottom: 30px; }\n");
            html.append("  .header h1 { margin: 0; font-size: 28px; color: #33ccff; letter-spacing: 1px; }\n");
            html.append("  .header p { margin: 5px 0 0 0; color: #a0a0a0; font-size: 14px; }\n");
            html.append("  .separator { height: 2px; background: #404040; margin: 20px 0; }\n");
            html.append("  .details-table { width: 100%; border-collapse: collapse; margin-top: 10px; }\n");
            html.append("  .details-table td { padding: 12px 6px; font-size: 15px; border-bottom: 1px solid #3d3d3d; }\n");
            html.append("  .details-table td.label { font-weight: bold; color: #b0b0b0; width: 40%; }\n");
            html.append("  .details-table td.value { color: #ffffff; width: 60%; }\n");
            html.append("  .price-container { display: flex; justify-content: space-between; align-items: center; margin-top: 30px; background: #242424; padding: 20px; border-radius: 8px; border: 1px solid #33ccff; }\n");
            html.append("  .price-label { font-size: 18px; font-weight: bold; color: #b0b0b0; }\n");
            html.append("  .price-value { font-size: 26px; font-weight: bold; color: #2ecc71; }\n");
            html.append("  .footer { text-align: center; margin-top: 40px; font-size: 12px; color: #707070; }\n");
            html.append("</style>\n</head>\n<body>\n");

            html.append("<div class='invoice-card'>\n");
            html.append("  <div class='header'>\n");
            html.append("    <h1>RENTAL INVOICE</h1>\n");
            html.append("    <p>Group-6-39-A Car Rental System</p>\n");
            html.append("  </div>\n");
            html.append("  <div class='separator'></div>\n");

            html.append("  <table class='details-table'>\n");
            html.append("    <tr><td class='label'>Booking ID:</td><td class='value'>#" + booking.getId() + "</td></tr>\n");
            html.append("    <tr><td class='label'>Customer Name:</td><td class='value'>" + (booking.getUsername() != null ? booking.getUsername() : "N/A") + "</td></tr>\n");
            html.append("    <tr><td class='label'>Vehicle Details:</td><td class='value'>" + (booking.getCarDetails() != null ? booking.getCarDetails() : "N/A") + "</td></tr>\n");
            html.append("    <tr><td class='label'>Pickup Location:</td><td class='value'>" + (booking.getPickupLocationName() != null ? booking.getPickupLocationName() : "N/A") + "</td></tr>\n");
            html.append("    <tr><td class='label'>Return Location:</td><td class='value'>" + (booking.getReturnLocationName() != null ? booking.getReturnLocationName() : "N/A") + "</td></tr>\n");
            html.append("    <tr><td class='label'>Start Date:</td><td class='value'>" + (booking.getStartDate() != null ? booking.getStartDate().toString() : "N/A") + "</td></tr>\n");
            html.append("    <tr><td class='label'>End Date:</td><td class='value'>" + (booking.getEndDate() != null ? booking.getEndDate().toString() : "N/A") + "</td></tr>\n");
            html.append("    <tr><td class='label'>Booking Status:</td><td class='value'>" + (booking.getStatus() != null ? booking.getStatus() : "Pending") + "</td></tr>\n");
            html.append("  </table>\n");

            html.append("  <div class='price-container'>\n");
            html.append("    <div class='price-label'>Total Amount Paid:</div>\n");
            html.append("    <div class='price-value'>$" + String.format("%.2f", booking.getTotalPrice()) + "</div>\n");
            html.append("  </div>\n");

            html.append("  <div class='footer'>\n");
            html.append("    <p>Thank you for choosing Group-6-39-A Car Rental System!</p>\n");
            html.append("    <p>Generated on " + new java.util.Date().toString() + "</p>\n");
            html.append("  </div>\n");
            html.append("</div>\n");

            html.append("</body>\n</html>");

            java.nio.file.Files.write(file.toPath(), html.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));

            JOptionPane.showMessageDialog(parent, "Invoice successfully saved to:\n" + file.getAbsolutePath(), "Invoice Exported", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.err.println("[BookingController] Error exporting invoice: " + e.getMessage());
            JOptionPane.showMessageDialog(parent, "Failed to export invoice: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

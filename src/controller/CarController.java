package controller;

import dao.CarDao;
import dao.BrandDao;
import model.Car;
import model.Brand;
import model.User;
import view.CarPanel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller class coordinating interactions between dashboards/panels (view layer) 
 * and database fleet operations (DAO layer).
 * Normalized to support brand IDs mapping and the modular visual CarPanel design.
 * 
 * @author dipes
 */
public class CarController {

    private final CarDao carDao;
    private final BrandDao brandDao;
    private final CarPanel view;
    private User currentUser;
    private Runnable onBookingComplete;

    public CarController(CarPanel view) {
        this.carDao = new CarDao();
        this.brandDao = new BrandDao();
        this.view = view;

        // Initial loading
        populateBrandCombo(this.view);
        loadAdminCarTable(this.view);

        // Wire listeners
        this.view.getBtnAdd().addActionListener(new AddCarListener());
        this.view.getBtnUpdate().addActionListener(new UpdateCarListener());
        this.view.getBtnDelete().addActionListener(new DeleteCarListener());
        this.view.getBtnSearch().addActionListener(new SearchCarListener());
        this.view.getBtnRent().addActionListener(new RentCarListener());
        this.view.getBtnCarsList().addActionListener(new CarsListListener());

        // Wire catalog filters search button
        this.view.getBtnFilterSearch().addActionListener(e -> {
            applyCarFilters();
        });

        // Wire list selection model selection listener
        this.view.getCarTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = this.view.getCarTable().getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int carId = Integer.parseInt(this.view.getCarTable().getValueAt(selectedRow, 0).toString());
                        Car car = getCarById(carId);
                        if (car != null) {
                            loadCarReviews(carId);
                            if (this.view.isAdminMode()) {
                                this.view.populateEditorFields(car);
                                // Flip card back to form for direct editing in admin mode
                                this.view.getCardLayout().show(this.view.getCardPanel(), "form");
                            }
                        }
                    } catch (Exception ex) {
                        System.err.println("[CarPanel] Row selection mapping error: " + ex.getMessage());
                    }
                }
            }
        });
    }

    /**
     * Helper default constructor for cross-controller updates.
     */
    public CarController() {
        this.carDao = new CarDao();
        this.brandDao = new BrandDao();
        this.view = null;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setOnBookingComplete(Runnable callback) {
        this.onBookingComplete = callback;
    }

    /**
     * Dynamically populates the Brand dropdown in the custom CarPanel.
     */
    public void populateBrandCombo(CarPanel targetView) {
        if (targetView == null) return;

        List<Brand> brands = brandDao.getAllBrands();
        targetView.getBrandCombo().removeAllItems();

        for (Brand b : brands) {
            targetView.getBrandCombo().addItem(b);
        }
    }

    /**
     * Retrieves a single car record by its database ID (DAO bridge).
     */
    public Car getCarById(int id) {
        return carDao.getCarById(id);
    }

    // ==================== CarPanel Visual Actions ====================

    /**
     * Fetches all cars from the database and loads them into the modular JTable.
     */
    public void loadAdminCarTable(CarPanel targetView) {
        loadAdminCarTable(targetView, null);
    }

    /**
     * Fetches all cars from the database, filters by brand if specified, and loads them into the JTable.
     */
    public void loadAdminCarTable(CarPanel targetView, String brandFilter) {
        if (targetView == null) return;
        
        List<Car> cars = targetView.isAdminMode() ? carDao.getAllCars() : carDao.getAvailableCars();
        DefaultTableModel model = (DefaultTableModel) targetView.getCarTable().getModel();
        model.setRowCount(0); // Clear existing table data

        for (Car car : cars) {
            if (brandFilter != null && !brandFilter.isEmpty() && !car.getBrand().equalsIgnoreCase(brandFilter)) {
                continue;
            }
            model.addRow(new Object[]{
                car.getId(),
                car.getBrand(), // Preserved brand name string
                car.getModel(),
                car.getCategory(),
                String.format("$%.2f", car.getPricePerDay()),
                car.getStatus()
            });
        }
    }

    public void handleIdSearch() {
        if (view == null) return;
        String idText = view.getIdField().getText().trim();
        if (idText.isEmpty() || "0".equals(idText)) {
            String input = JOptionPane.showInputDialog(view, "Enter Car ID to Search:", "Search Car", JOptionPane.QUESTION_MESSAGE);
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int carId = Integer.parseInt(input.trim());
                    Car car = getCarById(carId);
                    if (car != null) {
                        view.populateEditorFields(car);
                        JOptionPane.showMessageDialog(view, "Car record found and loaded!", "Search Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(view, "No car found with ID: " + carId, "Record Not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Please enter a valid numeric integer ID.", "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            try {
                int carId = Integer.parseInt(idText);
                Car car = getCarById(carId);
                if (car != null) {
                    view.populateEditorFields(car);
                }
            } catch (Exception e) {
                view.clearCarInputs();
            }
        }
    }

    class AddCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;

            Brand selectedBrand = (Brand) view.getBrandCombo().getSelectedItem();
            String carModel = view.getModelInput().trim();
            String category = view.getCategoryInput();
            String priceText = view.getPriceInput().trim();
            String status = view.getStatusInput();
            String fuel = view.getFuelInput();
            String color = view.getColorInput();
            int passengers = view.getPassengersInput();
            String gearbox = view.getGearboxInput();
            String features = view.getFeaturesInput();

            if (selectedBrand == null) {
                JOptionPane.showMessageDialog(view, "Please select or add a Brand first.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (carModel.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all fields (Model, Price).", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText);
                if (price <= 0) {
                    JOptionPane.showMessageDialog(view, "Price per day must be a positive number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Please enter a valid numeric value for Price.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Car car = new Car(selectedBrand.getId(), carModel, category, price, status, fuel, color, passengers, gearbox, features);
            boolean success = carDao.addCar(car);

            if (success) {
                JOptionPane.showMessageDialog(view, "Car added successfully to fleet!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearCarInputs();
                loadAdminCarTable(view);
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add car. Database error occurred.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class UpdateCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;

            String idText = view.getCarIdInput();
            int id;
            try {
                id = Integer.parseInt(idText);
                if (id == 0) {
                    JOptionPane.showMessageDialog(view, "Please select a car from the table list first to edit.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Brand selectedBrand = (Brand) view.getBrandCombo().getSelectedItem();
            String carModel = view.getModelInput().trim();
            String category = view.getCategoryInput();
            String priceText = view.getPriceInput().trim();
            String status = view.getStatusInput();
            String fuel = view.getFuelInput();
            String color = view.getColorInput();
            int passengers = view.getPassengersInput();
            String gearbox = view.getGearboxInput();
            String features = view.getFeaturesInput();

            if (selectedBrand == null) {
                JOptionPane.showMessageDialog(view, "Please select a Brand.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (carModel.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all fields to perform an update.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText.replace("$", "").trim());
                if (price <= 0) {
                    JOptionPane.showMessageDialog(view, "Price must be positive.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Please enter a valid numeric value for Price.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Car car = new Car(id, selectedBrand.getId(), selectedBrand.getName(), carModel, category, price, status, fuel, color, passengers, gearbox, features);
            boolean success = carDao.updateCar(car);

            if (success) {
                JOptionPane.showMessageDialog(view, "Car record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearCarInputs();
                loadAdminCarTable(view);
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update car. Database error.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DeleteCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;

            String idText = view.getCarIdInput();
            int id;
            try {
                id = Integer.parseInt(idText);
                if (id == 0) {
                    JOptionPane.showMessageDialog(view, "Please select a car to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Brand selectedBrand = (Brand) view.getBrandCombo().getSelectedItem();
            String carModel = view.getModelInput().trim();

            int confirm = JOptionPane.showConfirmDialog(view, 
                "Are you sure you want to delete the car: " + (selectedBrand != null ? selectedBrand.getName() : "") + " " + carModel + " (ID: " + id + ")?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = carDao.deleteCar(id);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Car removed from fleet successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.clearCarInputs();
                    loadAdminCarTable(view);
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete car. Database error.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class SearchCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleIdSearch();
        }
    }

    class RentCarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;
            
            int selectedRow = view.getCarTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Please select a car from the table list first to book.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int carId = Integer.parseInt(view.getCarTable().getValueAt(selectedRow, 0).toString());
                Car car = carDao.getCarById(carId);
                if (car != null) {
                    if (currentUser == null) {
                        JOptionPane.showMessageDialog(view, "No active user session found to complete reservation.", "Session Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
                    BookingController.openBookingDialog(frame, car, currentUser, () -> {
                        loadAdminCarTable(view);
                        if (onBookingComplete != null) {
                            onBookingComplete.run();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(view, "Selected vehicle details could not be found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error preparing booking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class CarsListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;
            loadAdminCarTable(view);
        }
    }

    private void applyCarFilters() {
        if (view == null) return;
        
        String gearbox = view.getCbFilterGearbox().getSelectedItem().toString();
        String fuel = view.getCbFilterFuel().getSelectedItem().toString();
        String priceText = view.getTxtFilterPrice().getText().trim();
        
        double maxPrice = -1;
        if (!priceText.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Please enter a valid number for max price.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
        DefaultTableModel model = (DefaultTableModel) view.getCarTable().getModel();
        model.setRowCount(0);
        
        List<Car> filteredCars = carDao.getFilteredCars(gearbox, fuel, maxPrice);
        for (Car car : filteredCars) {
            model.addRow(new Object[]{
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getCategory(),
                String.format("$%.2f", car.getPricePerDay()),
                car.getStatus()
            });
        }
    }

    public void loadCarReviews(int carId) {
        if (view == null) return;
        
        dao.ReviewDao reviewDao = new dao.ReviewDao();
        List<model.Review> reviews = reviewDao.getReviewsByCarId(carId);
        
        DefaultTableModel model = (DefaultTableModel) view.getReviewsTable().getModel();
        model.setRowCount(0);
        
        for (model.Review r : reviews) {
            model.addRow(new Object[]{
                r.getUsername(),
                r.getRating() + " / 5",
                r.getComment(),
                r.getCreatedAt() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(r.getCreatedAt()) : "N/A"
            });
        }
    }
}

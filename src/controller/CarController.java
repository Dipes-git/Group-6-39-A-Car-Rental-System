package controller;

import dao.CarDao;
import dao.CarDaoImpl;
import dao.BrandDao;
import dao.BrandDaoImpl;
import model.Car;
import model.Brand;
import view.UserDashboard;
import view.CarPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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

    public CarController() {
        this.carDao = new CarDaoImpl();
        this.brandDao = new BrandDaoImpl();
    }

    /**
     * Dynamically populates the Brand dropdown in the custom CarPanel.
     */
    public void populateBrandCombo(CarPanel view) {
        if (view == null) return;

        List<Brand> brands = brandDao.getAllBrands();
        view.getBrandCombo().removeAllItems();

        for (Brand b : brands) {
            view.getBrandCombo().addItem(b);
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
    public void loadAdminCarTable(CarPanel view) {
        loadAdminCarTable(view, null);
    }

    /**
     * Fetches all cars from the database, filters by brand if specified, and loads them into the JTable.
     */
    public void loadAdminCarTable(CarPanel view, String brandFilter) {
        if (view == null) return;
        
        List<Car> cars = view.isAdminMode() ? carDao.getAllCars() : carDao.getAvailableCars();
        DefaultTableModel model = (DefaultTableModel) view.getCarTable().getModel();
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

    /**
     * Handles the "Add Car" action from the modular CarPanel view.
     */
    public boolean handleAddCar(CarPanel view) {
        if (view == null) return false;

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
            return false;
        }
        if (carModel.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields (Model, Price).", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) {
                JOptionPane.showMessageDialog(view, "Price per day must be a positive number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric value for Price.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        Car car = new Car(selectedBrand.getId(), carModel, category, price, status, fuel, color, passengers, gearbox, features);
        boolean success = carDao.addCar(car);

        if (success) {
            JOptionPane.showMessageDialog(view, "Car added successfully to fleet!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.clearCarInputs();
            loadAdminCarTable(view);
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Failed to add car. Database error occurred.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Handles the "Update Car" action from the modular CarPanel view.
     */
    public boolean handleUpdateCar(CarPanel view) {
        if (view == null) return false;

        String idText = view.getCarIdInput();
        int id;
        try {
            id = Integer.parseInt(idText);
            if (id == 0) {
                JOptionPane.showMessageDialog(view, "Please select a car from the table list first to edit.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
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
            return false;
        }
        if (carModel.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields to perform an update.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        double price;
        try {
            price = Double.parseDouble(priceText.replace("$", "").trim());
            if (price <= 0) {
                JOptionPane.showMessageDialog(view, "Price must be positive.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric value for Price.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        Car car = new Car(id, selectedBrand.getId(), selectedBrand.getName(), carModel, category, price, status, fuel, color, passengers, gearbox, features);
        boolean success = carDao.updateCar(car);

        if (success) {
            JOptionPane.showMessageDialog(view, "Car record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.clearCarInputs();
            loadAdminCarTable(view);
            return true;
        } else {
            JOptionPane.showMessageDialog(view, "Failed to update car. Database error.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Handles the "Delete Car" action from the modular CarPanel view.
     */
    public boolean handleDeleteCar(CarPanel view) {
        if (view == null) return false;

        String idText = view.getCarIdInput();
        int id;
        try {
            id = Integer.parseInt(idText);
            if (id == 0) {
                JOptionPane.showMessageDialog(view, "Please select a car to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
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
                return true;
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete car. Database error.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    // ==================== User Dashboard Actions ====================
    
    /**
     * Handles visual reservation request action from the modular CarPanel.
     */
    public void handleRentRequest(CarPanel view) {
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
                String details = String.format(
                    "Vehicle: %s %s (%s)\n" +
                    "Class: %s\n" +
                    "Specification:\n" +
                    "  - Gearbox: %s\n" +
                    "  - Fuel Type: %s\n" +
                    "  - Color: %s\n" +
                    "  - Capacity: %d Passengers\n" +
                    "  - Features: %s\n\n" +
                    "Rental Price: $%.2f/day\n\n" +
                    "Booking request has been sent for approval in Sprint 4!",
                    car.getBrand(), car.getModel(), car.getStatus(),
                    car.getCategory(),
                    car.getGearbox(),
                    car.getFuel(),
                    car.getColor(),
                    car.getPassengers(),
                    (car.getFeatures() == null || car.getFeatures().isEmpty()) ? "Standard Accessories" : car.getFeatures(),
                    car.getPricePerDay()
                );
                
                JOptionPane.showMessageDialog(view, details, "Reservation Request Sent", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Selected vehicle details could not be found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error preparing booking request details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

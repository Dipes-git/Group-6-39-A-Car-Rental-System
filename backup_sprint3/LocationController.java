package controller;

import dao.LocationDao;
import dao.LocationDaoImpl;
import model.Location;
import view.LocationPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller class managing the Location/Branch operations.
 * Strictly implements the MVC architecture: handles input validations,
 * displays standard JOptionPane alerts, and delegates DB operations to LocationDao.
 * Keeps the LocationPanel UI-only and free of business logic.
 * 
 * @author dipes
 */
public class LocationController {

    private final LocationDao locationDao;

    public LocationController() {
        this.locationDao = new LocationDaoImpl();
    }

    /**
     * Loads all location records from the database and renders them in the table.
     */
    public void loadLocationsTable(Object viewObj) {
        LocationPanel view = (LocationPanel) viewObj;
        DefaultTableModel model = (DefaultTableModel) view.getLocationsTable().getModel();
        model.setRowCount(0); // Clear existing records

        List<Location> locations = locationDao.getAllLocations();
        for (Location loc : locations) {
            model.addRow(new Object[]{
                loc.getId(),
                loc.getCity(),
                loc.getAddress()
            });
        }
    }

    /**
     * Handles inserting a new location/branch record.
     */
    public void handleAddLocation(Object viewObj) {
        LocationPanel view = (LocationPanel) viewObj;
        String city = view.getCityInput();
        String address = view.getAddressInput();

        if (city == null || city.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a City.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter an Address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Location newLoc = new Location(city, address);
        boolean success = locationDao.addLocation(newLoc);

        if (success) {
            JOptionPane.showMessageDialog(view, "Location successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.clearInputs();
            loadLocationsTable(view);
        } else {
            JOptionPane.showMessageDialog(view, "Database error. Could not add location.", "Execution Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles updating an existing location/branch record.
     */
    public void handleEditLocation(Object viewObj) {
        LocationPanel view = (LocationPanel) viewObj;
        int id = view.getIdInput();
        String city = view.getCityInput();
        String address = view.getAddressInput();

        if (id <= 0) {
            JOptionPane.showMessageDialog(view, "Invalid Location ID selected.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (city == null || city.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a City.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter an Address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify location exists first
        Location existing = locationDao.getLocationById(id);
        if (existing == null) {
            JOptionPane.showMessageDialog(view, "Location with ID " + id + " does not exist.", "Execution Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Location loc = new Location(id, city, address);
        boolean success = locationDao.updateLocation(loc);

        if (success) {
            JOptionPane.showMessageDialog(view, "Location successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.clearInputs();
            loadLocationsTable(view);
        } else {
            JOptionPane.showMessageDialog(view, "Database error. Could not update location.", "Execution Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles permanently deleting a location/branch record.
     */
    public void handleRemoveLocation(Object viewObj) {
        LocationPanel view = (LocationPanel) viewObj;
        int id = view.getIdInput();

        if (id <= 0) {
            JOptionPane.showMessageDialog(view, "Please select or input a valid Location ID to remove.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if location exists
        Location existing = locationDao.getLocationById(id);
        if (existing == null) {
            JOptionPane.showMessageDialog(view, "Location with ID " + id + " does not exist.", "Execution Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
            view,
            "Are you sure you want to permanently delete Location ID " + id + " (" + existing.getCity() + ")?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = locationDao.deleteLocation(id);
            if (success) {
                JOptionPane.showMessageDialog(view, "Location successfully removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearInputs();
                loadLocationsTable(view);
            } else {
                JOptionPane.showMessageDialog(view, "Database error. Could not delete location.", "Execution Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

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
}
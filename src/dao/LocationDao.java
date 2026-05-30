package dao;

import model.Location;
import java.util.List;

/**
 * Data Access Object (DAO) interface for Location operations.
 * Declares database interaction contracts for managing car rental branches.
 * 
 * @author dipes
 */
public interface LocationDao {
    
    /**
     * Inserts a new location into the database.
     * 
     * @param location The Location model containing city and address
     * @return true if insertion succeeded, false otherwise
     */
    boolean addLocation(Location location);
    
    /**
     * Updates an existing location's parameters in the database.
     * 
     * @param location The Location model containing updated city and address
     * @return true if update succeeded, false otherwise
     */
    boolean updateLocation(Location location);
    
    /**
     * Permanently deletes a specific location from the database.
     * 
     * @param id The unique ID of the location to delete
     * @return true if deletion succeeded, false otherwise
     */
    boolean deleteLocation(int id);
    
    /**
     * Retrieves all locations registered in the database.
     * 
     * @return A list of Location entities
     */
    List<Location> getAllLocations();
    
    /**
     * Retrieves a single location by its unique identifier.
     * 
     * @param id The unique ID of the location
     * @return The populated Location entity if found, null otherwise
     */
    Location getLocationById(int id);
}

package dao;

import model.Car;
import java.util.List;

/**
 * Data Access Object (DAO) Interface for Car management.
 * Defines standard CRUD database operations for the car fleet.
 * 
 * @author dipes
 */
public interface CarDao {
    
    /**
     * Adds a new car to the fleet.
     * @param car The car model containing brand, model, category, price, and status.
     * @return true if insertion was successful, false otherwise.
     */
    boolean addCar(Car car);

    /**
     * Updates an existing car record in the database.
     * @param car The car model containing updated properties matching the target ID.
     * @return true if update was successful, false otherwise.
     */
    boolean updateCar(Car car);

    /**
     * Deletes a car from the database by ID.
     * @param id The ID of the target car.
     * @return true if deletion was successful, false otherwise.
     */
    boolean deleteCar(int id);

    /**
     * Retrieves a single car record by its database primary key.
     * @param id The target car ID.
     * @return The Car object if found, null otherwise.
     */
    Car getCarById(int id);

    /**
     * Fetches all cars present in the database inventory (for Admin view).
     * @return A list of all Car records.
     */
    List<Car> getAllCars();

    /**
     * Fetches only available cars in the inventory (for User catalog).
     * @return A list of available Cars.
     */
    List<Car> getAvailableCars();
}

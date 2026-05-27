package dao;

import model.Brand;
import java.util.List;

/**
 * Data Access Object (DAO) Interface for Brand management.
 * Defines standard CRUD database operations for car brands.
 * 
 * @author dipes
 */
public interface BrandDao {
    
    /**
     * Adds a new brand to the system.
     * @param brand The brand model containing name and logo path.
     * @return true if successful, false otherwise.
     */
    boolean addBrand(Brand brand);

    /**
     * Updates an existing brand record in the database.
     * @param brand The brand model with updated fields.
     * @return true if successful, false otherwise.
     */
    boolean updateBrand(Brand brand);

    /**
     * Deletes a brand from the database by ID.
     * @param id The target brand ID.
     * @return true if successful, false otherwise.
     */
    boolean deleteBrand(int id);

    /**
     * Retrieves a single brand record by its primary key ID.
     * @param id The target brand ID.
     * @return The Brand object if found, null otherwise.
     */
    Brand getBrandById(int id);

    /**
     * Fetches all brands present in the system database.
     * @return A list of all Brand records.
     */
    List<Brand> getAllBrands();
}

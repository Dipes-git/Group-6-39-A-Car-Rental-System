package dao;

import database.MySqlConnector;
import model.Car;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Data Access Object (DAO) class managing Car database fleet operations.
 * Strictly isolates SQL operations from View layers, supporting normalized brand associations.
 * 
 * Includes database self-healing configurations to automatically add specs columns.
 * 
 * @author dipes
 */
public class CarDao {

    private final MySqlConnector connector;

    public CarDao() {
        this.connector = new MySqlConnector();
        initializeDatabaseSchema();
    }

    /**
     * Natively checks table columns and dynamically adds missing specs columns
     * at startup to achieve a fully seamless self-healing database configuration.
     */
    private void initializeDatabaseSchema() {
        Connection conn = null;
        try {
            conn = connector.openConnection();
            if (conn != null) {
                addColumnIfMissing(conn, "fuel", "VARCHAR(50) DEFAULT 'Gas'");
                addColumnIfMissing(conn, "color", "VARCHAR(50) DEFAULT 'White'");
                addColumnIfMissing(conn, "passengers", "INT DEFAULT 5");
                addColumnIfMissing(conn, "gearbox", "VARCHAR(50) DEFAULT 'Automatic'");
                addColumnIfMissing(conn, "features", "VARCHAR(500) DEFAULT ''");
                addColumnIfMissing(conn, "location_id", "INT DEFAULT NULL");
                
                // Add foreign key constraint if missing
                try {
                    String fkQuery = "ALTER TABLE cars ADD CONSTRAINT fk_cars_location FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE SET NULL";
                    try (PreparedStatement stmt = conn.prepareStatement(fkQuery)) {
                        stmt.executeUpdate();
                        System.out.println("[CarDao] Successfully added foreign key constraint fk_cars_location");
                    }
                } catch (SQLException e) {
                    // Safe to ignore if already exists
                    System.out.println("[CarDao] Constraint verification notice: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("[CarDao] Self-healing DB check encountered an error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    connector.closeConnection(conn);
                } catch (SQLException e) {
                    System.err.println("[CarDao] Error closing check connection: " + e.getMessage());
                }
            }
        }
    }

    private void addColumnIfMissing(Connection conn, String columnName, String columnDefinition) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getColumns(null, null, "cars", columnName)) {
                if (!rs.next()) {
                    String query = "ALTER TABLE cars ADD COLUMN " + columnName + " " + columnDefinition;
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.executeUpdate();
                        System.out.println("[CarDao] Successfully added column: " + columnName);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[CarDao] Failed to verify/add column '" + columnName + "': " + e.getMessage());
        }
    }

    /**
     * Adds a new car to the fleet.
     */
    public boolean addCar(Car car) {
        String query = "INSERT INTO cars (brand_id, location_id, model, category, price_per_day, status, fuel, color, passengers, gearbox, features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[CarDao] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, car.getBrandId());
            if (car.getLocationId() != null) {
                stmt.setInt(2, car.getLocationId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, car.getModel());
            stmt.setString(4, car.getCategory());
            stmt.setDouble(5, car.getPricePerDay());
            stmt.setString(6, car.getStatus());
            stmt.setString(7, car.getFuel());
            stmt.setString(8, car.getColor());
            stmt.setInt(9, car.getPassengers());
            stmt.setString(10, car.getGearbox());
            stmt.setString(11, car.getFeatures());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("[CarDao] Error occurred while adding car: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Updates an existing car record in the database.
     */
    public boolean updateCar(Car car) {
        String query = "UPDATE cars SET brand_id = ?, location_id = ?, model = ?, category = ?, price_per_day = ?, status = ?, fuel = ?, color = ?, passengers = ?, gearbox = ?, features = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[CarDao] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, car.getBrandId());
            if (car.getLocationId() != null) {
                stmt.setInt(2, car.getLocationId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, car.getModel());
            stmt.setString(4, car.getCategory());
            stmt.setDouble(5, car.getPricePerDay());
            stmt.setString(6, car.getStatus());
            stmt.setString(7, car.getFuel());
            stmt.setString(8, car.getColor());
            stmt.setInt(9, car.getPassengers());
            stmt.setString(10, car.getGearbox());
            stmt.setString(11, car.getFeatures());
            stmt.setInt(12, car.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("[CarDao] Error occurred while updating car: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Deletes a car from the database by ID.
     */
    public boolean deleteCar(int id) {
        String query = "DELETE FROM cars WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[CarDao] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("[CarDao] Error occurred while deleting car: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Retrieves a single car record by its database primary key.
     */
    public Car getCarById(int id) {
        String query = "SELECT c.*, b.name AS brand_name FROM cars c INNER JOIN brands b ON c.brand_id = b.id WHERE c.id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[CarDao] Connection failed: openConnection() returned null.");
                return null;
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setBrandId(rs.getInt("brand_id"));
                car.setBrand(rs.getString("brand_name"));
                car.setModel(rs.getString("model"));
                car.setCategory(rs.getString("category"));
                car.setPricePerDay(rs.getDouble("price_per_day"));
                car.setStatus(rs.getString("status"));
                car.setFuel(rs.getString("fuel"));
                car.setColor(rs.getString("color"));
                car.setPassengers(rs.getInt("passengers"));
                car.setGearbox(rs.getString("gearbox"));
                car.setFeatures(rs.getString("features"));
                car.setLocationId(rs.getObject("location_id") != null ? rs.getInt("location_id") : null);
                return car;
            }

        } catch (SQLException e) {
            System.err.println("[CarDao] Error occurred while fetching car by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return null;
    }

    /**
     * Fetches all cars present in the database inventory (for Admin view).
     */
    public List<Car> getAllCars() {
        String query = "SELECT c.*, b.name AS brand_name FROM cars c INNER JOIN brands b ON c.brand_id = b.id ORDER BY c.id ASC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Car> carList = new ArrayList<>();

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[CarDao] Connection failed: openConnection() returned null.");
                return carList;
            }
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setBrandId(rs.getInt("brand_id"));
                car.setBrand(rs.getString("brand_name"));
                car.setModel(rs.getString("model"));
                car.setCategory(rs.getString("category"));
                car.setPricePerDay(rs.getDouble("price_per_day"));
                car.setStatus(rs.getString("status"));
                car.setFuel(rs.getString("fuel"));
                car.setColor(rs.getString("color"));
                car.setPassengers(rs.getInt("passengers"));
                car.setGearbox(rs.getString("gearbox"));
                car.setFeatures(rs.getString("features"));
                car.setLocationId(rs.getObject("location_id") != null ? rs.getInt("location_id") : null);
                carList.add(car);
            }

        } catch (SQLException e) {
            System.err.println("[CarDao] Error occurred while fetching all cars: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return carList;
    }

    /**
     * Fetches only available cars in the inventory (for User catalog).
     */
    public List<Car> getAvailableCars() {
        String query = "SELECT c.*, b.name AS brand_name FROM cars c INNER JOIN brands b ON c.brand_id = b.id WHERE c.status = 'Available' ORDER BY c.id ASC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Car> carList = new ArrayList<>();

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[CarDao] Connection failed: openConnection() returned null.");
                return carList;
            }
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setBrandId(rs.getInt("brand_id"));
                car.setBrand(rs.getString("brand_name"));
                car.setModel(rs.getString("model"));
                car.setCategory(rs.getString("category"));
                car.setPricePerDay(rs.getDouble("price_per_day"));
                car.setStatus(rs.getString("status"));
                car.setFuel(rs.getString("fuel"));
                car.setColor(rs.getString("color"));
                car.setPassengers(rs.getInt("passengers"));
                car.setGearbox(rs.getString("gearbox"));
                car.setFeatures(rs.getString("features"));
                car.setLocationId(rs.getObject("location_id") != null ? rs.getInt("location_id") : null);
                carList.add(car);
            }

        } catch (SQLException e) {
            System.err.println("[CarDao] Error occurred while fetching available cars: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return carList;
    }

    /**
     * Fetches cars filtered by Gearbox, Fuel, and Max Price.
     */
    public List<Car> getFilteredCars(String gearbox, String fuel, double maxPrice) {
        List<Car> carList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT c.*, b.name AS brand_name FROM cars c INNER JOIN brands b ON c.brand_id = b.id WHERE c.status = 'Available'");
        
        List<Object> params = new ArrayList<>();
        
        if (gearbox != null && !"All".equalsIgnoreCase(gearbox)) {
            query.append(" AND c.gearbox = ?");
            params.add(gearbox);
        }
        
        if (fuel != null && !"All".equalsIgnoreCase(fuel)) {
            query.append(" AND c.fuel = ?");
            params.add(fuel);
        }
        
        if (maxPrice >= 0) {
            query.append(" AND c.price_per_day <= ?");
            params.add(maxPrice);
        }
        
        query.append(" ORDER BY c.id ASC");
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return carList;
            
            stmt = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            rs = stmt.executeQuery();
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setBrandId(rs.getInt("brand_id"));
                car.setBrand(rs.getString("brand_name"));
                car.setModel(rs.getString("model"));
                car.setCategory(rs.getString("category"));
                car.setPricePerDay(rs.getDouble("price_per_day"));
                car.setStatus(rs.getString("status"));
                car.setFuel(rs.getString("fuel"));
                car.setColor(rs.getString("color"));
                car.setPassengers(rs.getInt("passengers"));
                car.setGearbox(rs.getString("gearbox"));
                car.setFeatures(rs.getString("features"));
                car.setLocationId(rs.getObject("location_id") != null ? rs.getInt("location_id") : null);
                carList.add(car);
            }
        } catch (SQLException e) {
            System.err.println("[CarDao] Error filtering cars: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return carList;
    }

    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                connector.closeConnection(conn);
            }
        } catch (SQLException e) {
            System.err.println("[CarDao] Error closing JDBC resources: " + e.getMessage());
        }
    }
}

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
        String query = "INSERT INTO cars (brand_id, model, category, price_per_day, status, fuel, color, passengers, gearbox, features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getCategory());
            stmt.setDouble(4, car.getPricePerDay());
            stmt.setString(5, car.getStatus());
            stmt.setString(6, car.getFuel());
            stmt.setString(7, car.getColor());
            stmt.setInt(8, car.getPassengers());
            stmt.setString(9, car.getGearbox());
            stmt.setString(10, car.getFeatures());

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
        String query = "UPDATE cars SET brand_id = ?, model = ?, category = ?, price_per_day = ?, status = ?, fuel = ?, color = ?, passengers = ?, gearbox = ?, features = ? WHERE id = ?";
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
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getCategory());
            stmt.setDouble(4, car.getPricePerDay());
            stmt.setString(5, car.getStatus());
            stmt.setString(6, car.getFuel());
            stmt.setString(7, car.getColor());
            stmt.setInt(8, car.getPassengers());
            stmt.setString(9, car.getGearbox());
            stmt.setString(10, car.getFeatures());
            stmt.setInt(11, car.getId());

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

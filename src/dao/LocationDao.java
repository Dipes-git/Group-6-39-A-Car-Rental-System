package dao;

import database.MySqlConnector;
import model.Location;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Data Access Object (DAO) class managing Location database operations.
 * Coordinates all rental branches, inputs validation, and JTable renders.
 * 
 * Features an automatic self-healing database builder and initial seeder at startup.
 * 
 * @author dipes
 */
public class LocationDao {

    private final MySqlConnector connector;

    public LocationDao() {
        this.connector = new MySqlConnector();
        initializeDatabaseSchema();
    }

    /**
     * Natively checks if the locations table exists at startup.
     * If not, automatically creates the table and seeds it with default data
     * from the visual mockup to achieve a self-contained premium user experience.
     */
    private void initializeDatabaseSchema() {
        Connection conn = null;
        try {
            conn = connector.openConnection();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                try (ResultSet rs = meta.getTables(null, null, "locations", null)) {
                    if (!rs.next()) {
                        System.out.println("[LocationDao] Locations table not found. Building schema natively...");
                        String createTableSQL = "CREATE TABLE locations ("
                                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                                + "city VARCHAR(100) NOT NULL,"
                                + "address TEXT NOT NULL"
                                + ")";
                        try (Statement stmt = conn.createStatement()) {
                            stmt.executeUpdate(createTableSQL);
                            System.out.println("[LocationDao] Table 'locations' successfully created.");
                            seedMockData(conn);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[LocationDao] Error during self-healing initialization: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    connector.closeConnection(conn);
                } catch (SQLException e) {
                    System.err.println("[LocationDao] Error closing schema verification connection: " + e.getMessage());
                }
            }
        }
    }

    private void seedMockData(Connection conn) {
        String seedSQL = "INSERT INTO locations (city, address) VALUES (?, ?)";
        String[][] mockLocations = {
            {"City 1", "address 1"},
            {"City 2", "address 22"},
            {"City 3", "address 33"},
            {"City 4", "address 44"},
            {"City 1", "address 100"},
            {"City 3", "bbbbbbbbbbbbbbbb"},
            {"City 1", "aaaaaaaaaa"}
        };

        try (PreparedStatement pstmt = conn.prepareStatement(seedSQL)) {
            for (String[] loc : mockLocations) {
                pstmt.setString(1, loc[0]);
                pstmt.setString(2, loc[1]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("[LocationDao] Successfully seeded mockup locations!");
        } catch (SQLException e) {
            System.err.println("[LocationDao] Failed to seed mockup locations: " + e.getMessage());
        }
    }

    /**
     * Inserts a new location into the database.
     */
    public boolean addLocation(Location location) {
        String query = "INSERT INTO locations (city, address) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query);
            stmt.setString(1, location.getCity());
            stmt.setString(2, location.getAddress());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[LocationDao] Error adding location: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Updates an existing location's parameters in the database.
     */
    public boolean updateLocation(Location location) {
        String query = "UPDATE locations SET city = ?, address = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query);
            stmt.setString(1, location.getCity());
            stmt.setString(2, location.getAddress());
            stmt.setInt(3, location.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[LocationDao] Error updating location: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Permanently deletes a specific location from the database.
     */
    public boolean deleteLocation(int id) {
        String query = "DELETE FROM locations WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[LocationDao] Error deleting location: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Retrieves all locations registered in the database.
     */
    public List<Location> getAllLocations() {
        List<Location> list = new ArrayList<>();
        String query = "SELECT * FROM locations ORDER BY id ASC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    list.add(new Location(
                        rs.getInt("id"),
                        rs.getString("city"),
                        rs.getString("address")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("[LocationDao] Error retrieving all locations: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return list;
    }

    /**
     * Retrieves a single location by its unique identifier.
     */
    public Location getLocationById(int id) {
        String query = "SELECT * FROM locations WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Location(
                        rs.getInt("id"),
                        rs.getString("city"),
                        rs.getString("address")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[LocationDao] Error retrieving location by ID: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return null;
    }

    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) connector.closeConnection(conn);
        } catch (SQLException e) {
            System.err.println("[LocationDao] Error closing resources: " + e.getMessage());
        }
    }
}

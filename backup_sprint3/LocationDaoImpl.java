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
 * Concrete implementation of the LocationDao interface.
 * Coordinates all location database operations using secure parameterized queries.
 * Features an automatic self-healing database builder and initial seeder at startup.
 * 
 * @author dipes
 */
public class LocationDaoImpl implements LocationDao {

    private final MySqlConnector connector;

    public LocationDaoImpl() {
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
                        System.out.println("[LocationDaoImpl] Locations table not found. Building schema natively...");
                        String createTableSQL = "CREATE TABLE locations ("
                                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                                + "city VARCHAR(100) NOT NULL,"
                                + "address TEXT NOT NULL"
                                + ")";
                        try (Statement stmt = conn.createStatement()) {
                            stmt.executeUpdate(createTableSQL);
                            System.out.println("[LocationDaoImpl] Table 'locations' successfully created.");
                            seedMockData(conn);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[LocationDaoImpl] Error during self-healing initialization: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    connector.closeConnection(conn);
                } catch (SQLException e) {
                    System.err.println("[LocationDaoImpl] Error closing schema verification connection: " + e.getMessage());
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
            System.out.println("[LocationDaoImpl] Successfully seeded mockup locations!");
        } catch (SQLException e) {
            System.err.println("[LocationDaoImpl] Failed to seed mockup locations: " + e.getMessage());
        }
    }

    @Override
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
            System.err.println("[LocationDaoImpl] Error adding location: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
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
            System.err.println("[LocationDaoImpl] Error updating location: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
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
            System.err.println("[LocationDaoImpl] Error deleting location: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
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
            System.err.println("[LocationDaoImpl] Error retrieving all locations: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return list;
    }

    @Override
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
            System.err.println("[LocationDaoImpl] Error retrieving location by ID: " + e.getMessage());
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
            System.err.println("[LocationDaoImpl] Error closing resources: " + e.getMessage());
        }
    }
}

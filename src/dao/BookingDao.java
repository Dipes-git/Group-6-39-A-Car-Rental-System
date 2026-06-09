package dao;

import database.MySqlConnector;
import model.Booking;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Data Access Object (DAO) class managing Booking database operations.
 * Isolates SQL operations for bookings, implementing self-healing schema creation.
 * 
 * @author dipes
 */
public class BookingDao {

    private final MySqlConnector connector;

    public BookingDao() {
        this.connector = new MySqlConnector();
        initializeDatabaseSchema();
    }

    /**
     * Natively checks if the bookings table exists at startup.
     * If not, automatically creates the table.
     */
    private void initializeDatabaseSchema() {
        Connection conn = null;
        try {
            conn = connector.openConnection();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                try (ResultSet rs = meta.getTables(null, null, "bookings", null)) {
                    if (!rs.next()) {
                        System.out.println("[BookingDao] Bookings table not found. Building schema natively...");
                        String createTableSQL = "CREATE TABLE bookings ("
                                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                                + "user_id INT NOT NULL,"
                                + "car_id INT NOT NULL,"
                                + "pickup_location_id INT NOT NULL,"
                                + "return_location_id INT NOT NULL,"
                                + "start_date DATE NOT NULL,"
                                + "end_date DATE NOT NULL,"
                                + "total_price DECIMAL(10, 2) NOT NULL,"
                                + "status VARCHAR(20) NOT NULL DEFAULT 'Pending',"
                                + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,"
                                + "FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE,"
                                + "FOREIGN KEY (pickup_location_id) REFERENCES locations(id) ON DELETE CASCADE,"
                                + "FOREIGN KEY (return_location_id) REFERENCES locations(id) ON DELETE CASCADE"
                                + ")";
                        try (Statement stmt = conn.createStatement()) {
                            stmt.executeUpdate(createTableSQL);
                            System.out.println("[BookingDao] Table 'bookings' successfully created.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error during self-healing initialization: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    connector.closeConnection(conn);
                } catch (SQLException e) {
                    System.err.println("[BookingDao] Error closing schema verification connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Inserts a new booking into the database.
     */
    public boolean createBooking(Booking booking) {
        String query = "INSERT INTO bookings (user_id, car_id, pickup_location_id, return_location_id, start_date, end_date, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getCarId());
            stmt.setInt(3, booking.getPickupLocationId());
            stmt.setInt(4, booking.getReturnLocationId());
            stmt.setDate(5, booking.getStartDate());
            stmt.setDate(6, booking.getEndDate());
            stmt.setDouble(7, booking.getTotalPrice());
            stmt.setString(8, booking.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        booking.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error creating booking: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Updates the status of an existing booking.
     */
    public boolean updateBookingStatus(int bookingId, String status) {
        String query = "UPDATE bookings SET status = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
}
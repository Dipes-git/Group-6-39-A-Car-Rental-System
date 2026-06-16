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
                
                try (ResultSet rs = meta.getTables(null, null, "reviews", null)) {
                    if (!rs.next()) {
                        System.out.println("[BookingDao] Reviews table not found. Building schema natively...");
                        String createReviewsSQL = "CREATE TABLE reviews ("
                                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                                + "booking_id INT NOT NULL,"
                                + "user_id INT NOT NULL,"
                                + "car_id INT NOT NULL,"
                                + "rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),"
                                + "comment TEXT,"
                                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                                + "FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,"
                                + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,"
                                + "FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE"
                                + ")";
                        try (Statement stmt = conn.createStatement()) {
                            stmt.executeUpdate(createReviewsSQL);
                            System.out.println("[BookingDao] Table 'reviews' successfully created.");
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
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error updating booking status: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Retrieves all bookings for the admin view.
     */
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String query = "SELECT b.*, u.username, br.name AS brand_name, c.model AS car_model, "
                + "lp.city AS pickup_city, lp.address AS pickup_address, "
                + "lr.city AS return_city, lr.address AS return_address "
                + "FROM bookings b "
                + "JOIN users u ON b.user_id = u.id "
                + "JOIN cars c ON b.car_id = c.id "
                + "JOIN brands br ON c.brand_id = br.id "
                + "JOIN locations lp ON b.pickup_location_id = lp.id "
                + "JOIN locations lr ON b.return_location_id = lr.id "
                + "ORDER BY b.id DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Booking b = mapRowToBooking(rs);
                    list.add(b);
                }
            }
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error retrieving all bookings: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return list;
    }

    /**
     * Retrieves all bookings associated with a specific user.
     */
    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> list = new ArrayList<>();
        String query = "SELECT b.*, u.username, br.name AS brand_name, c.model AS car_model, "
                + "lp.city AS pickup_city, lp.address AS pickup_address, "
                + "lr.city AS return_city, lr.address AS return_address "
                + "FROM bookings b "
                + "JOIN users u ON b.user_id = u.id "
                + "JOIN cars c ON b.car_id = c.id "
                + "JOIN brands br ON c.brand_id = br.id "
                + "JOIN locations lp ON b.pickup_location_id = lp.id "
                + "JOIN locations lr ON b.return_location_id = lr.id "
                + "WHERE b.user_id = ? "
                + "ORDER BY b.id DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, userId);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Booking b = mapRowToBooking(rs);
                    list.add(b);
                }
            }
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error retrieving bookings by user: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return list;
    }

    /**
     * Retrieves a single booking by ID.
     */
    public Booking getBookingById(int id) {
        String query = "SELECT b.*, u.username, br.name AS brand_name, c.model AS car_model, "
                + "lp.city AS pickup_city, lp.address AS pickup_address, "
                + "lr.city AS return_city, lr.address AS return_address "
                + "FROM bookings b "
                + "JOIN users u ON b.user_id = u.id "
                + "JOIN cars c ON b.car_id = c.id "
                + "JOIN brands br ON c.brand_id = br.id "
                + "JOIN locations lp ON b.pickup_location_id = lp.id "
                + "JOIN locations lr ON b.return_location_id = lr.id "
                + "WHERE b.id = ?";

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
                    return mapRowToBooking(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error retrieving booking by ID: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return null;
    }

    private Booking mapRowToBooking(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setCarId(rs.getInt("car_id"));
        b.setPickupLocationId(rs.getInt("pickup_location_id"));
        b.setReturnLocationId(rs.getInt("return_location_id"));
        b.setStartDate(rs.getDate("start_date"));
        b.setEndDate(rs.getDate("end_date"));
        b.setTotalPrice(rs.getDouble("total_price"));
        b.setStatus(rs.getString("status"));
        
        b.setUsername(rs.getString("username"));
        b.setCarDetails(rs.getString("brand_name") + " " + rs.getString("car_model"));
        b.setPickupLocationName(rs.getString("pickup_city") + " (" + rs.getString("pickup_address") + ")");
        b.setReturnLocationName(rs.getString("return_city") + " (" + rs.getString("return_address") + ")");
        return b;
    }

    /**
     * Updates an existing booking's records.
     */
    public boolean updateBooking(Booking booking) {
        String query = "UPDATE bookings SET car_id = ?, pickup_location_id = ?, return_location_id = ?, start_date = ?, end_date = ?, total_price = ?, status = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, booking.getCarId());
            stmt.setInt(2, booking.getPickupLocationId());
            stmt.setInt(3, booking.getReturnLocationId());
            stmt.setDate(4, booking.getStartDate());
            stmt.setDate(5, booking.getEndDate());
            stmt.setDouble(6, booking.getTotalPrice());
            stmt.setString(7, booking.getStatus());
            stmt.setInt(8, booking.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error updating booking: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Deletes a booking from the database.
     */
    public boolean deleteBooking(int id) {
        String query = "DELETE FROM bookings WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error deleting booking: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Natively retrieves aggregated statistics for the Admin Dashboard summary cards.
     * Keeps code strictly MVC aligned by executing SQL COUNT and SUM aggregates.
     */
    public model.DashboardMetrics getDashboardMetrics() {
        int totalCars = 0;
        int activeBookings = 0;
        int pendingRequests = 0;
        double totalEarnings = 0.0;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn != null) {
                // 1. Get total cars
                String sqlCars = "SELECT COUNT(*) FROM cars";
                try (PreparedStatement pst = conn.prepareStatement(sqlCars);
                     ResultSet r = pst.executeQuery()) {
                    if (r.next()) totalCars = r.getInt(1);
                }

                // 2. Get active bookings (Approved)
                String sqlActive = "SELECT COUNT(*) FROM bookings WHERE status = 'Approved'";
                try (PreparedStatement pst = conn.prepareStatement(sqlActive);
                     ResultSet r = pst.executeQuery()) {
                    if (r.next()) activeBookings = r.getInt(1);
                }

                // 3. Get pending requests
                String sqlPending = "SELECT COUNT(*) FROM bookings WHERE status = 'Pending'";
                try (PreparedStatement pst = conn.prepareStatement(sqlPending);
                     ResultSet r = pst.executeQuery()) {
                    if (r.next()) pendingRequests = r.getInt(1);
                }

                // 4. Get total earnings (Approved or Completed)
                String sqlEarnings = "SELECT SUM(total_price) FROM bookings WHERE status = 'Approved' OR status = 'Completed'";
                try (PreparedStatement pst = conn.prepareStatement(sqlEarnings);
                     ResultSet r = pst.executeQuery()) {
                    if (r.next()) totalEarnings = r.getDouble(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error loading dashboard metrics: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return new model.DashboardMetrics(totalCars, activeBookings, pendingRequests, totalEarnings);
    }

    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) connector.closeConnection(conn);
        } catch (SQLException e) {
            System.err.println("[BookingDao] Error closing resources: " + e.getMessage());
        }
    }
}


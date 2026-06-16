package dao;

import database.MySqlConnector;
import model.Review;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {

    private final MySqlConnector connector;

    public ReviewDao() {
        this.connector = new MySqlConnector();
    }

    public boolean addReview(Review review) {
        String query = "INSERT INTO reviews (booking_id, user_id, car_id, rating, comment) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, review.getBookingId());
            stmt.setInt(2, review.getUserId());
            stmt.setInt(3, review.getCarId());
            stmt.setInt(4, review.getRating());
            stmt.setString(5, review.getComment());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("[ReviewDao] Error adding review: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    public boolean hasUserReviewed(int bookingId) {
        String query = "SELECT 1 FROM reviews WHERE booking_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookingId);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("[ReviewDao] Error checking reviewed status: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    public List<Review> getReviewsByCarId(int carId) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.*, u.username FROM reviews r JOIN users u ON r.user_id = u.id WHERE r.car_id = ? ORDER BY r.created_at DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return reviews;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, carId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Review r = new Review();
                r.setId(rs.getInt("id"));
                r.setBookingId(rs.getInt("booking_id"));
                r.setUserId(rs.getInt("user_id"));
                r.setCarId(rs.getInt("car_id"));
                r.setRating(rs.getInt("rating"));
                r.setComment(rs.getString("comment"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                r.setUsername(rs.getString("username"));
                reviews.add(r);
            }
        } catch (SQLException e) {
            System.err.println("[ReviewDao] Error retrieving reviews by carId: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return reviews;
    }

    public double getAverageRating(int carId) {
        String query = "SELECT AVG(rating) FROM reviews WHERE car_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return 0.0;
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, carId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("[ReviewDao] Error calculating average rating: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }
        return 0.0;
    }

    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) connector.closeConnection(conn);
        } catch (SQLException e) {
            System.err.println("[ReviewDao] Error closing resources: " + e.getMessage());
        }
    }
}
package dao;

import database.MySqlConnector;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Concrete implementation of the UserDao interface.
 * Implements authentication, registration, and recovery operations using JDBC PreparedStatement
 * to ensure robust protection against SQL Injection vulnerabilities.
 * 
 * @author dipes
 */
public class UserDaoImpl implements UserDao {

    private final MySqlConnector connector;

    public UserDaoImpl() {
        this.connector = new MySqlConnector();
    }

    @Override
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDaoImpl] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error occurred while registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
    public User loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDaoImpl] Connection failed: openConnection() returned null.");
                return null;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error occurred during user login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return null;
    }

    @Override
    public boolean checkUserExists(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDaoImpl] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            return rs.next(); // Returns true if a record exists

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error occurred while checking user existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public boolean updatePassword(String username, String email, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ? AND email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDaoImpl] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.setString(3, email);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error occurred while resetting password: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Helper method to clean up JDBC resources safely.
     */
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
            System.err.println("[UserDaoImpl] Error closing JDBC resources: " + e.getMessage());
        }
    }
}

package dao;

import database.MySqlConnector;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Concrete implementation of the UserDao interface.
 * Basic implementation with registration and login operations.
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
        String query = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error registering user: " + e.getMessage());
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
                user.setRole(rs.getString("role"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error logging in user: " + e.getMessage());
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
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error checking user existence: " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public String getSecurityQuestion(String username) {
        return null;
    }

    @Override
    public boolean verifyAnswerAndUpdatePassword(String username, String answer, String newPassword) {
        return false;
    }

    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) connector.closeConnection(conn);
        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error closing JDBC resources: " + e.getMessage());
        }
    }
}

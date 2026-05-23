package dao;

import database.MySqlConnector;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Concrete implementation of the UserDao interface supporting security questions.
 * Handles user account operations, using prepared parameterized queries
 * to secure input parsing and prevent SQL injection.
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
        String query = "INSERT INTO users (username, email, password, security_question, security_answer) VALUES (?, ?, ?, ?, ?)";
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
            stmt.setString(4, user.getSecurityQuestion());
            stmt.setString(5, user.getSecurityAnswer());

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
                user.setSecurityQuestion(rs.getString("security_question"));
                user.setSecurityAnswer(rs.getString("security_answer"));
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
            return rs.next();

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error occurred while checking user existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public String getSecurityQuestion(String username) {
        String query = "SELECT security_question FROM users WHERE username = ?";
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

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("security_question");
            }

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error occurred while retrieving security question: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return null;
    }

    @Override
    public boolean verifyAnswerAndUpdatePassword(String username, String answer, String newPassword) {
        // Case-insensitive match on the security answer for a premium user experience
        String query = "UPDATE users SET password = ? WHERE username = ? AND LOWER(security_answer) = LOWER(?)";
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
            stmt.setString(3, answer);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("[UserDaoImpl] Error occurred while verifying answer and updating password: " + e.getMessage());
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

package dao;

import database.MySqlConnector;
import model.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Concrete Data Access Object (DAO) class managing User database operations.
 * Coordinates user credentials, role-based login/signup, security question recovery,
 * and administrative customer account status updates.
 * 
 * Strict MVC compliance: Houses database statements, isolating them from View layers.
 * 
 * @author dipes
 */
public class UserDao {

    private final MySqlConnector connector;

    public UserDao() {
        this.connector = new MySqlConnector();
        initializeDatabaseSchema();
    }

    /**
     * Natively checks users table columns and dynamically adds missing columns
     * at startup to achieve a fully seamless self-healing database configuration.
     */
    private void initializeDatabaseSchema() {
        Connection conn = null;
        try {
            conn = connector.openConnection();
            if (conn != null) {
                addColumnIfMissing(conn, "role", "VARCHAR(20) NOT NULL DEFAULT 'user'");
                addColumnIfMissing(conn, "status", "VARCHAR(20) NOT NULL DEFAULT 'Active'");
            }
        } catch (Exception e) {
            System.err.println("[UserDao] Self-healing DB check encountered an error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    connector.closeConnection(conn);
                } catch (SQLException e) {
                    System.err.println("[UserDao] Error closing check connection: " + e.getMessage());
                }
            }
        }
    }

    private void addColumnIfMissing(Connection conn, String columnName, String columnDefinition) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getColumns(null, null, "users", columnName)) {
                if (!rs.next()) {
                    String query = "ALTER TABLE users ADD COLUMN " + columnName + " " + columnDefinition;
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.executeUpdate();
                        System.out.println("[UserDao] Successfully added column: " + columnName);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDao] Failed to verify/add column '" + columnName + "': " + e.getMessage());
        }
    }

    /**
     * Registers a new user into the database, including their security question details.
     */
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, email, password, security_question, security_answer, role) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDao] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getSecurityQuestion());
            stmt.setString(5, user.getSecurityAnswer());
            stmt.setString(6, user.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("[UserDao] Error occurred while registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Authenticates a user based on their credentials.
     */
    public User loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDao] Connection failed: openConnection() returned null.");
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
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("[UserDao] Error occurred during user login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return null;
    }

    /**
     * Checks if a username already exists in the database.
     */
    public boolean checkUserExists(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDao] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.err.println("[UserDao] Error occurred while checking user existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    /**
     * Retrieves the security question associated with a specific username.
     */
    public String getSecurityQuestion(String username) {
        String query = "SELECT security_question FROM users WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDao] Connection failed: openConnection() returned null.");
                return null;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("security_question");
            }

        } catch (SQLException e) {
            System.err.println("[UserDao] Error occurred while retrieving security question: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return null;
    }

    /**
     * Verifies the user's security answer. If correct, updates their password to a new one.
     */
    public boolean verifyAnswerAndUpdatePassword(String username, String answer, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ? AND LOWER(security_answer) = LOWER(?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.err.println("[UserDao] Connection failed: openConnection() returned null.");
                return false;
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.setString(3, answer);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("[UserDao] Error occurred while verifying answer and updating password: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    /**
     * Retrieves all registered customer accounts (role = 'user') from the database.
     */
    public java.util.List<User> getAllCustomers() {
        java.util.List<User> customers = new java.util.ArrayList<>();
        String query = "SELECT * FROM users WHERE role = 'user' ORDER BY id ASC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setSecurityQuestion(rs.getString("security_question"));
                    user.setSecurityAnswer(rs.getString("security_answer"));
                    user.setRole(rs.getString("role"));
                    user.setStatus(rs.getString("status"));
                    customers.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDao] Error retrieving all customers: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return customers;
    }

    /**
     * Updates the status (Active/Suspended) of a specific user account.
     */
    public boolean updateUserStatus(int userId, String status) {
        String query = "UPDATE users SET status = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                stmt.setString(1, status);
                stmt.setInt(2, userId);
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            System.err.println("[UserDao] Error updating user status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        return false;
    }

    /**
     * Permanently deletes a specific user account from the database.
     */
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, userId);
                int rowsDeleted = stmt.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            System.err.println("[UserDao] Error deleting user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
        return false;
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
            System.err.println("[UserDao] Error closing JDBC resources: " + e.getMessage());
        }
    }
}
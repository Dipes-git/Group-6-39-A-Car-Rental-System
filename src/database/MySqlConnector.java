package database;

import java.sql.*;

public class MySqlConnector implements Db {

    private final String url = "jdbc:mysql://localhost:3306/hello?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String username = "root";
    private final String password = "1234";
    @Override
    public Connection openConnection() {
        try {
            // 🔥 Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        } catch (ClassNotFoundException ex) {
            System.getLogger(MySqlConnector.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }

    @Override
    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    @Override
    public int executeUpdate(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(query);
    }
}
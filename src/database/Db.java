package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Db {
    Connection openConnection() throws SQLException;
    void closeConnection(Connection conn) throws SQLException;
    ResultSet runQuery(Connection conn, String query) throws SQLException;
    int executeUpdate(Connection conn, String query) throws SQLException;
}

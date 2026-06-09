package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTester {
    public static void main(String[] args) {
        System.out.println("=== Database Configuration Tester ===");
        MySqlConnector connector = new MySqlConnector();
        Connection conn = null;
        try {
            conn = connector.openConnection();
            if (conn == null) {
                System.out.println("Error: Connection returned null!");
                return;
            }
            System.out.println("Connection successful!");

            System.out.println("Instantiating BookingDao to trigger table creation...");
            new dao.BookingDao();
            System.out.println("BookingDao instantiated successfully.");

            DatabaseMetaData meta = conn.getMetaData();
            String[] tables = {"users", "locations", "brands", "cars", "bookings"};
            for (String table : tables) {
                try (ResultSet rs = meta.getTables(null, null, table, null)) {
                    if (rs.next()) {
                        // Table exists, query count
                        try (Statement stmt = conn.createStatement();
                             ResultSet countRs = stmt.executeQuery("SELECT COUNT(*) FROM " + table)) {
                            if (countRs.next()) {
                                System.out.println("Table '" + table + "' exists. Row count: " + countRs.getInt(1));
                            }
                        }
                    } else {
                        System.out.println("Table '" + table + "' does NOT exist!");
                    }
                } catch (Exception ex) {
                    System.out.println("Error querying table '" + table + "': " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    connector.closeConnection(conn);
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }
}

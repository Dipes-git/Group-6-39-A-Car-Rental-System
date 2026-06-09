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
}
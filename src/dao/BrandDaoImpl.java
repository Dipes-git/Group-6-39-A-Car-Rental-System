package dao;

import database.MySqlConnector;
import model.Brand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the BrandDao interface.
 * Handles database operations for car brands using prepared statements.
 * 
 * @author dipes
 */
public class BrandDaoImpl implements BrandDao {

    private final MySqlConnector connector;

    public BrandDaoImpl() {
        this.connector = new MySqlConnector();
    }

    @Override
    public boolean addBrand(Brand brand) {
        String query = "INSERT INTO brands (name, logo_path) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(query);
            stmt.setString(1, brand.getName());
            stmt.setString(2, brand.getLogoPath());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("[BrandDaoImpl] Error adding brand: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
    public boolean updateBrand(Brand brand) {
        String query = "UPDATE brands SET name = ?, logo_path = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(query);
            stmt.setString(1, brand.getName());
            stmt.setString(2, brand.getLogoPath());
            stmt.setInt(3, brand.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("[BrandDaoImpl] Error updating brand: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
    public boolean deleteBrand(int id) {
        String query = "DELETE FROM brands WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("[BrandDaoImpl] Error deleting brand: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
    public Brand getBrandById(int id) {
        String query = "SELECT * FROM brands WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connector.openConnection();
            if (conn == null) return null;
            
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("id"));
                brand.setName(rs.getString("name"));
                brand.setLogoPath(rs.getString("logo_path"));
                return brand;
            }

        } catch (SQLException e) {
            System.err.println("[BrandDaoImpl] Error fetching brand by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return null;
    }

    @Override
    public List<Brand> getAllBrands() {
        String query = "SELECT * FROM brands ORDER BY name ASC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Brand> brandList = new ArrayList<>();

        try {
            conn = connector.openConnection();
            if (conn == null) return brandList;
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand();
                brand.setId(rs.getInt("id"));
                brand.setName(rs.getString("name"));
                brand.setLogoPath(rs.getString("logo_path"));
                brandList.add(brand);
            }

        } catch (SQLException e) {
            System.err.println("[BrandDaoImpl] Error fetching all brands: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return brandList;
    }

    /**
     * Helper method to clean up JDBC resources safely.
     */
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) connector.closeConnection(conn);
        } catch (SQLException e) {
            System.err.println("[BrandDaoImpl] Error closing JDBC resources: " + e.getMessage());
        }
    }
}

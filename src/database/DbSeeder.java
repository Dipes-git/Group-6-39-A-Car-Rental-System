package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class DbSeeder {
    public static void main(String[] args) {
        System.out.println("=== Starting Comprehensive Database Seeder ===");
        MySqlConnector connector = new MySqlConnector();
        
        try (Connection conn = connector.openConnection()) {
            if (conn == null) {
                System.err.println("Error: Could not open database connection!");
                return;
            }
            System.out.println("Connected to the database. Clearing existing records in reverse foreign-key order...");
            
            try (Statement stmt = conn.createStatement()) {
                // Temporarily disable foreign key checks to safely clear all tables and reset auto-increment
                stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                stmt.executeUpdate("TRUNCATE TABLE bookings");
                stmt.executeUpdate("TRUNCATE TABLE cars");
                stmt.executeUpdate("TRUNCATE TABLE brands");
                stmt.executeUpdate("TRUNCATE TABLE locations");
                stmt.executeUpdate("TRUNCATE TABLE users");
                stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
                System.out.println("All existing database tables cleared and auto-increment values reset.");
            }

            // 1. Seed Users
            System.out.println("\nSeeding users...");
            seedUser(conn, "admin", "admin@carrental.com", "admin123", "What is my role?", "admin", "admin", "Active");
            seedUser(conn, "dakshata", "dakshata@email.com", "pass123", "Favorite Color?", "Blue", "user", "Active");
            seedUser(conn, "john_doe", "john@email.com", "pass123", "First Pet?", "Rex", "user", "Active");
            seedUser(conn, "jane_smith", "jane@email.com", "pass123", "Favorite Food?", "Pizza", "user", "Suspended");
            seedUser(conn, "alice_williams", "alice@email.com", "pass123", "High School?", "Central", "user", "Active");
            seedUser(conn, "bob_brown", "bob@email.com", "pass123", "Mother's Maiden Name?", "Smith", "user", "Active");

            // 2. Seed Locations
            System.out.println("\nSeeding locations...");
            seedLocation(conn, "New York", "123 Broadway Ave");
            seedLocation(conn, "Los Angeles", "456 Sunset Blvd");
            seedLocation(conn, "San Francisco", "789 Market St");
            seedLocation(conn, "Miami", "101 Ocean Dr");
            seedLocation(conn, "Chicago", "202 Michigan Ave");

            // 3. Seed Brands
            System.out.println("\nSeeding brands...");
            seedBrand(conn, "Tesla", "/images/tesla.png");
            seedBrand(conn, "Ford", "/images/ford.png");
            seedBrand(conn, "Toyota", "/images/toyota.png");
            seedBrand(conn, "BMW", "/images/bmw.png");
            seedBrand(conn, "Audi", "/images/audi.png");
            seedBrand(conn, "Porsche", "/images/porsche.png");

            // 4. Seed Cars
            System.out.println("\nSeeding cars...");
            // Tesla Model S (Luxury, Electric, Red)
            seedCar(conn, 1, 1, "Model S", "Luxury", 150.00, "Available", "Electric", "Red", 5, "Automatic", "GPS, Autopilot, Premium Audio, Heated Seats");
            // Ford Mustang GT (Sports, Gas, Blue)
            seedCar(conn, 2, 2, "Mustang GT", "Sports", 110.00, "Available", "Gas", "Blue", 4, "Manual", "Leather Seats, Sports Mode, Bluetooth, Rear Camera");
            // Toyota RAV4 (SUV, Hybrid, Gray)
            seedCar(conn, 3, 3, "RAV4", "SUV", 70.00, "Rented", "Hybrid", "Gray", 5, "Automatic", "All-Wheel Drive, Backup Camera, Lane Assist, Apple CarPlay");
            // BMW 3 Series (Sedan, Diesel, Black)
            seedCar(conn, 4, 4, "3 Series", "Sedan", 90.00, "Available", "Diesel", "Black", 5, "Automatic", "Sunroof, Heated Seats, Navigation, Park Assist");
            // Audi e-tron (Luxury, Electric, Silver)
            seedCar(conn, 5, 1, "e-tron", "Luxury", 140.00, "Maintenance", "Electric", "Silver", 5, "Automatic", "Panoramic Sunroof, Virtual Cockpit, Wireless Charger");
            // Porsche 911 Carrera (Sports, Gas, Yellow)
            seedCar(conn, 6, 2, "911 Carrera", "Sports", 250.00, "Available", "Gas", "Yellow", 2, "Automatic", "Sport Chrono, Bose Surround Sound, Active Suspension");

            // 5. Seed Bookings
            System.out.println("\nSeeding bookings...");
            LocalDate today = LocalDate.now();

            // Booking 1: Pending (User: dakshata, Car: Tesla Model S)
            int userId1 = getUserId(conn, "dakshata");
            int carId1 = getCarId(conn, "Model S");
            seedBooking(conn, userId1, carId1, 1, 1, 
                    java.sql.Date.valueOf(today.plusDays(1)), 
                    java.sql.Date.valueOf(today.plusDays(3)), 
                    300.00, "Pending");

            // Booking 2: Approved (User: john_doe, Car: BMW 3 Series)
            int userId2 = getUserId(conn, "john_doe");
            int carId2 = getCarId(conn, "3 Series");
            seedBooking(conn, userId2, carId2, 4, 4, 
                    java.sql.Date.valueOf(today), 
                    java.sql.Date.valueOf(today.plusDays(4)), 
                    360.00, "Approved");

            // Booking 3: Completed (User: bob_brown, Car: Toyota RAV4)
            int userId3 = getUserId(conn, "bob_brown");
            int carId3 = getCarId(conn, "RAV4");
            seedBooking(conn, userId3, carId3, 3, 3, 
                    java.sql.Date.valueOf(today.minusDays(10)), 
                    java.sql.Date.valueOf(today.minusDays(5)), 
                    350.00, "Completed");

            // Booking 4: Rejected (User: alice_williams, Car: Mustang GT)
            int userId4 = getUserId(conn, "alice_williams");
            int carId4 = getCarId(conn, "Mustang GT");
            seedBooking(conn, userId4, carId4, 2, 2, 
                    java.sql.Date.valueOf(today.minusDays(5)), 
                    java.sql.Date.valueOf(today.minusDays(3)), 
                    220.00, "Rejected");

            // Booking 5: Completed (User: dakshata, Car: 911 Carrera)
            int carId5 = getCarId(conn, "911 Carrera");
            seedBooking(conn, userId1, carId5, 2, 5, 
                    java.sql.Date.valueOf(today.minusDays(20)), 
                    java.sql.Date.valueOf(today.minusDays(15)), 
                    1250.00, "Completed");

            System.out.println("\n=== Database Seeding Completed Successfully! ===");
        } catch (Exception e) {
            System.err.println("Seeding encountered an error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void seedUser(Connection conn, String username, String email, String password,
                                 String question, String answer, String role, String status) throws Exception {
        String query = "INSERT INTO users (username, email, password, security_question, security_answer, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, question);
            stmt.setString(5, answer);
            stmt.setString(6, role);
            stmt.setString(7, status);
            stmt.executeUpdate();
            System.out.println("Seeded user: " + username + " (" + role + ", status=" + status + ")");
        }
    }

    private static void seedLocation(Connection conn, String city, String address) throws Exception {
        String query = "INSERT INTO locations (city, address) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, city);
            stmt.setString(2, address);
            stmt.executeUpdate();
            System.out.println("Seeded location: " + city + " (" + address + ")");
        }
    }

    private static void seedBrand(Connection conn, String name, String logoPath) throws Exception {
        String query = "INSERT INTO brands (name, logo_path) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, logoPath);
            stmt.executeUpdate();
            System.out.println("Seeded brand: " + name);
        }
    }

    private static void seedCar(Connection conn, int brandId, int locationId, String model, String category,
                                double pricePerDay, String status, String fuel, String color, int passengers,
                                String gearbox, String features) throws Exception {
        String query = "INSERT INTO cars (brand_id, location_id, model, category, price_per_day, status, fuel, color, passengers, gearbox, features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, brandId);
            stmt.setInt(2, locationId);
            stmt.setString(3, model);
            stmt.setString(4, category);
            stmt.setDouble(5, pricePerDay);
            stmt.setString(6, status);
            stmt.setString(7, fuel);
            stmt.setString(8, color);
            stmt.setInt(9, passengers);
            stmt.setString(10, gearbox);
            stmt.setString(11, features);
            stmt.executeUpdate();
            System.out.println("Seeded car: " + model + " (Class: " + category + ", Status: " + status + ")");
        }
    }

    private static void seedBooking(Connection conn, int userId, int carId, int pickupLocId, int returnLocId,
                                    java.sql.Date startDate, java.sql.Date endDate, double totalPrice, String status) throws Exception {
        String query = "INSERT INTO bookings (user_id, car_id, pickup_location_id, return_location_id, start_date, end_date, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, carId);
            stmt.setInt(3, pickupLocId);
            stmt.setInt(4, returnLocId);
            stmt.setDate(5, startDate);
            stmt.setDate(6, endDate);
            stmt.setDouble(7, totalPrice);
            stmt.setString(8, status);
            stmt.executeUpdate();
            System.out.println("Seeded booking: UserID=" + userId + ", CarID=" + carId + ", Status=" + status + ", Price=" + totalPrice);
        }
    }

    private static int getUserId(Connection conn, String username) throws Exception {
        String query = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new RuntimeException("User not found: " + username);
    }

    private static int getCarId(Connection conn, String model) throws Exception {
        String query = "SELECT id FROM cars WHERE model = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, model);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new RuntimeException("Car not found: " + model);
    }
}

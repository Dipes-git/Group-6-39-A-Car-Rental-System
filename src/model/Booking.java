package model;

import java.sql.Date;

/**
 * POJO Model representing a Booking entity.
 * Supports relational foreign keys (user, car, locations) and 
 * display fields for table rendering.
 * 
 * @author dipes
 */
public class Booking {
    private int id;
    private int userId;
    private int carId;
    private int pickupLocationId;
    private int returnLocationId;
    private Date startDate;
    private Date endDate;
    private double totalPrice;
    private String status; // Pending, Approved, Rejected, Completed

    // Convenience fields for display/GUI binding
    private String username;
    private String carDetails; // e.g. "Toyota RAV4"
    private String pickupLocationName; // e.g. "City 1 - address 1"
    private String returnLocationName; // e.g. "City 2 - address 2"

    // Default Constructor
    public Booking() {}

    // Constructor without ID
    public Booking(int userId, int carId, int pickupLocationId, int returnLocationId, Date startDate, Date endDate, double totalPrice, String status) {
        this.userId = userId;
        this.carId = carId;
}
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
        this.pickupLocationId = pickupLocationId;
        this.returnLocationId = returnLocationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Full Constructor
    public Booking(int id, int userId, int carId, int pickupLocationId, int returnLocationId, Date startDate, Date endDate, double totalPrice, String status) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.pickupLocationId = pickupLocationId;
        this.returnLocationId = returnLocationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getPickupLocationId() {
        return pickupLocationId;
    }

    public void setPickupLocationId(int pickupLocationId) {
        this.pickupLocationId = pickupLocationId;
    }

    public int getReturnLocationId() {
        return returnLocationId;
    }

    public void setReturnLocationId(int returnLocationId) {
        this.returnLocationId = returnLocationId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(String carDetails) {
        this.carDetails = carDetails;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public void setPickupLocationName(String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }

    public String getReturnLocationName() {
        return returnLocationName;
    }

    public void setReturnLocationName(String returnLocationName) {
        this.returnLocationName = returnLocationName;
    }
}

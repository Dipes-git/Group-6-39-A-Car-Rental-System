package model;

/**
 * POJO class representing a Car entity in the Car Rental System inventory.
 * Contains properties mapped to the database structure, including brand ID and brand name.
 * Upgraded to include Fuel, Color, Passengers, Gearbox, and Features checklist.
 * 
 * @author dipes
 */
public class Car {
    private int id;
    private int brandId;     // Relational Brand ID (Foreign Key)
    private String brand;    // Brand Name (for table displays and backward compatibility)
    private String model;
    private String category; // Maps to "Class" in the new visual design
    private double pricePerDay;
    private String status;   // Available, Rented, Maintenance
    
    // New premium specification columns
    private String fuel;     // Gas, Diesel, Electric, Hybrid
    private String color;    // White, Black, Red, Blue, Grey, etc.
    private int passengers;  // Passenger count, e.g. 2, 4, 5, 7
    private String gearbox;  // Automatic, Manual
    private String features; // Comma-separated list of active accessories

    // Default Constructor
    public Car() {}

    // Constructor for registering a new car (without ID) - Backward Compatible
    public Car(int brandId, String model, String category, double pricePerDay, String status) {
        this.brandId = brandId;
        this.model = model;
        this.category = category;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.fuel = "Gas";
        this.color = "White";
        this.passengers = 5;
        this.gearbox = "Automatic";
        this.features = "";
    }

    // Constructor with new specs (without ID)
    public Car(int brandId, String model, String category, double pricePerDay, String status, 
               String fuel, String color, int passengers, String gearbox, String features) {
        this.brandId = brandId;
        this.model = model;
        this.category = category;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.fuel = fuel;
        this.color = color;
        this.passengers = passengers;
        this.gearbox = gearbox;
        this.features = features;
    }

    // Full Constructor (with ID, brandName, and new specs)
    public Car(int id, int brandId, String brand, String model, String category, double pricePerDay, String status,
               String fuel, String color, int passengers, String gearbox, String features) {
        this.id = id;
        this.brandId = brandId;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.fuel = fuel;
        this.color = color;
        this.passengers = passengers;
        this.gearbox = gearbox;
        this.features = features;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}

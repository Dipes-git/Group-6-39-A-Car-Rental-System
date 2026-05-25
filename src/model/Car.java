package model;

/**
 * POJO class representing a Car entity in the Car Rental System inventory.
 * Contains base properties mapped to the database structure.
 * 
 * @author dipes
 */
public class Car {
    private int id;
    private int brandId;     // Relational Brand ID (Foreign Key)
    private String brand;    // Brand Name (for table displays)
    private String model;
    private String category; // Maps to "Class" in the visual design
    private double pricePerDay;
    private String status;   // Available, Rented, Maintenance

    // Default Constructor
    public Car() {}

    public Car(int brandId, String model, String category, double pricePerDay, String status) {
        this.brandId = brandId;
        this.model = model;
        this.category = category;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    public Car(int id, int brandId, String brand, String model, String category, double pricePerDay, String status) {
        this.id = id;
        this.brandId = brandId;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

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
}

package model;

/**
 * POJO class representing a Brand entity in the Car Rental System.
 * Contains properties for id, brand name, and logo path.
 * 
 * @author dipes
 */
public class Brand {
    private int id;
    private String name;
    private String logoPath;

    // Default Constructor
    public Brand() {}

    // Constructor without ID (for adding new brand)
    public Brand(String name, String logoPath) {
        this.name = name;
        this.logoPath = logoPath;
    }

    // Full Constructor
    public Brand(int id, String name, String logoPath) {
        this.id = id;
        this.name = name;
        this.logoPath = logoPath;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Override
    public String toString() {
        return this.name; // Crucial for loading JComboBox dynamically
    }
}

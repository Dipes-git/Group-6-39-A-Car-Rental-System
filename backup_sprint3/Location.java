package model;

/**
 * POJO Model representing a Location/Branch entity.
 * Holds attributes for id, city, and address.
 * 
 * @author dipes
 */
public class Location {
    private int id;
    private String city;
    private String address;

    public Location() {
    }

    public Location(String city, String address) {
        this.city = city;
        this.address = address;
    }

    public Location(int id, String city, String address) {
        this.id = id;
        this.city = city;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

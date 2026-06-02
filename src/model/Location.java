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
}
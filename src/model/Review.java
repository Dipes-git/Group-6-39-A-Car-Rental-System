package model;

import java.sql.Timestamp;

public class Review {
    private int id;
    private int bookingId;
    private int userId;
    private int carId;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private String username;

    public Review() {}

    public Review(int bookingId, int userId, int carId, int rating, String comment) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.carId = carId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
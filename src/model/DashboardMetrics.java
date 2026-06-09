package model;

/**
 * Model class encapsulating the system summary metrics for the Admin Dashboard.
 * 
 * @author dipes
 */
public class DashboardMetrics {
    private int totalCars;
    private int activeBookings;
    private int pendingRequests;
    private double totalEarnings;

    public DashboardMetrics() {
    }

    public DashboardMetrics(int totalCars, int activeBookings, int pendingRequests, double totalEarnings) {
        this.totalCars = totalCars;
        this.activeBookings = activeBookings;
        this.pendingRequests = pendingRequests;
        this.totalEarnings = totalEarnings;
    }

    public int getTotalCars() {
        return totalCars;
    }

    public void setTotalCars(int totalCars) {
        this.totalCars = totalCars;
    }

    public int getActiveBookings() {
        return activeBookings;
    }

    public void setActiveBookings(int activeBookings) {
        this.activeBookings = activeBookings;
    }

    public int getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(int pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }
}

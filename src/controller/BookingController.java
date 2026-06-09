package controller;

import dao.BookingDao;
import dao.CarDao;
import dao.LocationDao;
import model.Booking;
import model.Car;
import model.Location;
import model.User;
import view.AdminDashboard;
import view.BookingDialog;
import view.UserDashboard;
import view.BookingPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * Controller class coordinating Booking operations across view and data layers.
 * Implements strict MVC separation.
 * 
 * @author dipes
 */
public class BookingController {

    private final BookingDao bookingDao;
    private final CarDao carDao;
    private final LocationDao locationDao;

    // Optional view references
    private UserDashboard userDashboard;
    private User currentUser;
    private JTable userBookingsTable;

    private AdminDashboard adminDashboard;
    private JTable adminBookingsTable;

    public BookingController() {
        this.bookingDao = new BookingDao();
        this.carDao = new CarDao();
        this.locationDao = new LocationDao();
    }

    /**
     * User dashboard constructor.
     */
    public BookingController(UserDashboard userDashboard, User currentUser) {
        this();
        this.userDashboard = userDashboard;
        this.currentUser = currentUser;

        BookingPanel bookingPanel = userDashboard.getBookingPanel();
}
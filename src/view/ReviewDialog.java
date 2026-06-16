package view;

import model.Booking;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ReviewDialog extends JDialog {

    private final Booking booking;
    private JComboBox<Integer> cbRating;
    private JTextArea taComment;
    private JButton btnSubmit;
    private JButton btnCancel;

    public ReviewDialog(Frame parent, Booking booking) {
        super(parent, "Rate Rental - Booking #" + booking.getId(), true);
        this.booking = booking;
        initComponents();
        setSize(400, 360);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        JLabel lblHeader = new JLabel("RATE YOUR RENTAL");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setHorizontalAlignment(JLabel.CENTER);
        lblHeader.setBounds(0, 20, 400, 30);
        mainPanel.add(lblHeader);

        JLabel lblCar = new JLabel(booking.getCarDetails());
        lblCar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCar.setForeground(new Color(180, 180, 180));
        lblCar.setHorizontalAlignment(JLabel.CENTER);
        lblCar.setBounds(0, 45, 400, 20);
        mainPanel.add(lblCar);

        JLabel lblRating = new JLabel("Rating (1-5 Stars):");
        lblRating.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRating.setForeground(Color.WHITE);
        lblRating.setBounds(40, 90, 120, 25);
        mainPanel.add(lblRating);

        Integer[] ratings = {5, 4, 3, 2, 1};
        cbRating = new JComboBox<>(ratings);
        cbRating.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbRating.setBackground(new Color(50, 50, 50));
        cbRating.setForeground(Color.WHITE);
        cbRating.setBounds(180, 90, 180, 25);
        mainPanel.add(cbRating);

        JLabel lblComment = new JLabel("Comments:");
        lblComment.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblComment.setForeground(Color.WHITE);
        lblComment.setBounds(40, 130, 120, 25);
        mainPanel.add(lblComment);

        taComment = new JTextArea();
        taComment.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        taComment.setBackground(new Color(50, 50, 50));
        taComment.setForeground(Color.WHITE);
        taComment.setLineWrap(true);
        taComment.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(taComment);
        scroll.setBounds(40, 160, 320, 90);
        mainPanel.add(scroll);

        btnSubmit = new JButton("Submit Review");
        btnSubmit.setBackground(new Color(39, 174, 96));
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBounds(40, 270, 140, 35);
        mainPanel.add(btnSubmit);

        btnCancel = new JButton("Cancel");
        btnCancel.setBackground(new Color(192, 57, 43));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBounds(220, 270, 140, 35);
        mainPanel.add(btnCancel);
    }

    public JComboBox<Integer> getCbRating() { return cbRating; }
    public JTextArea getTaComment() { return taComment; }
    public JButton getBtnSubmit() { return btnSubmit; }
    public JButton getBtnCancel() { return btnCancel; }
}
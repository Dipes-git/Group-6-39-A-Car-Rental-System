package view;

import model.Booking;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class InvoiceDialog extends JDialog {

    private final Booking booking;
    private JButton btnExportHtml;
    private JButton btnClose;

    public InvoiceDialog(Frame parent, Booking booking) {
        super(parent, "Invoice / Receipt - Booking #" + booking.getId(), true);
        this.booking = booking;
        initComponents();
        setSize(450, 520);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        JLabel lblHeader = new JLabel("RENTAL INVOICE");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeader.setForeground(new Color(255, 255, 255));
        lblHeader.setHorizontalAlignment(JLabel.CENTER);
        lblHeader.setBounds(0, 20, 450, 30);
        mainPanel.add(lblHeader);

        JLabel lblSubHeader = new JLabel("Group-6-39-A Car Rental System");
        lblSubHeader.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubHeader.setForeground(new Color(180, 180, 180));
        lblSubHeader.setHorizontalAlignment(JLabel.CENTER);
        lblSubHeader.setBounds(0, 45, 450, 20);
        mainPanel.add(lblSubHeader);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(70, 70, 70));
        sep.setBounds(30, 75, 390, 2);
        mainPanel.add(sep);

        int startY = 90;
        int rowHeight = 30;

        addInvoiceField(mainPanel, "Booking ID:", "#" + booking.getId(), startY);
        addInvoiceField(mainPanel, "Customer:", booking.getUsername() != null ? booking.getUsername() : "N/A", startY + rowHeight);
        addInvoiceField(mainPanel, "Vehicle Details:", booking.getCarDetails() != null ? booking.getCarDetails() : "N/A", startY + rowHeight * 2);
        addInvoiceField(mainPanel, "Pickup Branch:", booking.getPickupLocationName() != null ? booking.getPickupLocationName() : "N/A", startY + rowHeight * 3);
        addInvoiceField(mainPanel, "Return Branch:", booking.getReturnLocationName() != null ? booking.getReturnLocationName() : "N/A", startY + rowHeight * 4);
        addInvoiceField(mainPanel, "Start Date:", booking.getStartDate() != null ? booking.getStartDate().toString() : "N/A", startY + rowHeight * 5);
        addInvoiceField(mainPanel, "End Date:", booking.getEndDate() != null ? booking.getEndDate().toString() : "N/A", startY + rowHeight * 6);
        addInvoiceField(mainPanel, "Status:", booking.getStatus() != null ? booking.getStatus() : "Pending", startY + rowHeight * 7);

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(70, 70, 70));
        sep2.setBounds(30, startY + rowHeight * 8 + 10, 390, 2);
        mainPanel.add(sep2);

        JLabel lblTotalLabel = new JLabel("Total Paid / Due:");
        lblTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalLabel.setForeground(new Color(255, 255, 255));
        lblTotalLabel.setBounds(50, startY + rowHeight * 8 + 20, 150, 30);
        mainPanel.add(lblTotalLabel);

        JLabel lblTotalPrice = new JLabel("$" + String.format("%.2f", booking.getTotalPrice()));
        lblTotalPrice.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotalPrice.setForeground(new Color(39, 174, 96));
        lblTotalPrice.setHorizontalAlignment(JLabel.RIGHT);
        lblTotalPrice.setBounds(200, startY + rowHeight * 8 + 20, 200, 30);
        mainPanel.add(lblTotalPrice);

        btnExportHtml = new JButton("Export HTML");
        btnExportHtml.setBackground(new Color(41, 128, 185));
        btnExportHtml.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExportHtml.setForeground(Color.WHITE);
        btnExportHtml.setBounds(50, 420, 160, 35);
        mainPanel.add(btnExportHtml);

        btnClose = new JButton("Close");
        btnClose.setBackground(new Color(74, 83, 97));
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnClose.setForeground(Color.WHITE);
        btnClose.setBounds(240, 420, 160, 35);
        mainPanel.add(btnClose);
    }

    private void addInvoiceField(JPanel panel, String label, String value, int y) {
        JLabel lblName = new JLabel(label);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblName.setForeground(new Color(180, 180, 180));
        lblName.setBounds(50, y, 130, 25);
        panel.add(lblName);

        JLabel lblVal = new JLabel(value);
        lblVal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblVal.setForeground(Color.WHITE);
        lblVal.setBounds(180, y, 220, 25);
        panel.add(lblVal);
    }

    public JButton getBtnExportHtml() { return btnExportHtml; }
    public JButton getBtnClose() { return btnClose; }
}
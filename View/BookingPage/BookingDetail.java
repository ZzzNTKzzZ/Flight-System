package src.View.BookingPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.main;
import src.Controller.UserController;
import src.Model.UserModel;
import src.View.UserPage.UserProfile;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BookingDetail {
    public static JPanel bookingDetail = new JPanel();
    private static String flightId;

    public static void setFlightId(String id) {
        flightId = id;
        refreshTable(); // Refresh the table when flight ID is set
    }

    private static JTable table;
    private static DefaultTableModel model;
    private static JScrollPane scrollPane;

    static {
        bookingDetail.setLayout(new BorderLayout());
        bookingDetail.setBackground(Color.WHITE);
        bookingDetail.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Top panel with back and title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel backPanel = main.createBackPanel(() -> main.setCardLayout("flightCurrent"));

        JLabel title = new JLabel("Booking Details", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        title.setForeground(new Color(30, 30, 60));

        topPanel.add(backPanel, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        bookingDetail.add(topPanel, BorderLayout.NORTH);

        // Table
        createBookingTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        bookingDetail.add(scrollPane, BorderLayout.CENTER);
    }

    private static void createBookingTable() {
        String[] columns = {"Booking ID", "Passenger Name", "Flight ID", "Seat", "Date"};
        Object[][] rowData = {};

        model = new DefaultTableModel(rowData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setGridColor(new Color(200, 200, 200));

        // Popup Menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem detailItem = new JMenuItem("Detail");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        popupMenu.add(detailItem);
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        // Mouse listener for popup
        table.addMouseListener(new MouseAdapter() {
            private void showMenu(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    table.setRowSelectionInterval(row, row);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }
        });

        // Actions
        detailItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String userId = table.getValueAt(selectedRow, 0).toString();
                UserProfile.setIdFlight(flightId);
                UserProfile.setUserId(userId);
                main.setCardLayout("userProfile");
            }
        });

        editItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String bookingDetailId = table.getValueAt(selectedRow, 0).toString();
                JOptionPane.showMessageDialog(bookingDetail, "Edit bookingDetail: " + bookingDetailId);
                // Call controller to edit if needed
            }
        });

        deleteItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(bookingDetail, "Delete this bookingDetail?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                    // Call controller to delete in DB if needed
                }
            }
        });
    }

    public static void refreshTable() {
        if (model == null) return;

        model.setRowCount(0); // Clear old data

        List<UserModel> users = UserController.getAllUser(flightId);
        for (UserModel user : users) {
            model.addRow(new Object[]{
                    user.getId(),
                    user.getFullName(),
                    user.getPhone(),
                    user.getAge(),
                    user.getDate()
            });
        }
    }
}

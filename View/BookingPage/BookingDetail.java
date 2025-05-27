package src.View.BookingPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.main;
import src.Controller.UserController;
import src.View.UserProfile;
import src.Model.UserModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BookingDetail {
    public static JPanel bookingDetail = new JPanel();
    private static String flightId;

    public static void setFlightId(String flightId) {
        BookingDetail.flightId = flightId;
    }

    
    static {
        bookingDetail.setLayout(new BorderLayout());
        bookingDetail.setBackground(Color.WHITE);
        bookingDetail.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Back + Title layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel backPanel = main.createBackPanel(() -> main.setCardLayout("home"));

        JLabel title = new JLabel("Booking Details", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        title.setForeground(new Color(30, 30, 60));

        topPanel.add(backPanel, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        bookingDetail.add(topPanel, BorderLayout.NORTH);

        // Table with bookingDetail data
        String[] columns = {"Booking ID", "Passenger Name", "Flight ID", "Seat", "Date"};

        List<UserModel> users = UserController.getAllUser(flightId); // Sửa lại nếu cần lọc theo flight

        Object[][] rowData = new Object[users.size()][5];
        for (int i = 0; i < users.size(); i++) {
            UserModel user = users.get(i);
            rowData[i][0] = user.getId();
            rowData[i][1] = user.getFullName();
            rowData[i][2] = user.getPhone(); // giả định có flight_id, có thể lấy từ ticket nếu cần
            rowData[i][3] = user.getAge();    // giả định có seat, cần sửa lại theo ticket thực tế
            rowData[i][4] = user.getDate(); // nhớ thêm getter cho Date nếu chưa có
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho chỉnh sửa trực tiếp trên bảng
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setGridColor(new Color(200, 200, 200));

        // Popup menu
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
                String bookingDetailId = table.getValueAt(selectedRow, 0).toString();
                String seatId = table.getValueAt(selectedRow, 3).toString();
                UserProfile.setIdFlight(bookingDetailId);
                UserProfile.setSeatId(seatId);
                main.setCardLayout("userProfile");
            }
        });

        editItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String bookingDetailId = table.getValueAt(selectedRow, 0).toString();
                JOptionPane.showMessageDialog(bookingDetail, "Edit bookingDetail: " + bookingDetailId);
                // Gọi hàm sửa từ controller nếu cần
            }
        });

        deleteItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(bookingDetail, "Delete this bookingDetail?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                    // Gọi controller để xóa trong database nếu cần
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        bookingDetail.add(scrollPane, BorderLayout.CENTER);
    }
}

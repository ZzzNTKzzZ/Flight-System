package src.View.UserPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import src.Controller.UserController;
import src.Controller.TicketController;
import src.Model.UserModel;
import src.Model.FlightModel;
import src.Model.SeatModel;
import src.Model.TicketModel;

public class UserTicketsListPanel {
    public static JPanel userProfileListPage = new JPanel();

    private static JTable table;
    private static DefaultTableModel tableModel;

    static {
        userProfileListPage.setLayout(new BorderLayout());
        userProfileListPage.setBackground(Color.WHITE);
        userProfileListPage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Users with Upcoming Flight Tickets", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        userProfileListPage.add(title, BorderLayout.NORTH);

        // Tạo bảng với các cột
        String[] columnNames = { "User ID", "Full Name", "Flight ID", "Seat Number", "Ticket Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        userProfileListPage.add(scrollPane, BorderLayout.CENTER);

        // Load dữ liệu
        reloadData();
    }

    public static void reloadData() {
        // Xóa dữ liệu cũ
        tableModel.setRowCount(0);

        // Giả sử TicketController có method lấy tất cả vé sắp bay
        List<TicketModel> tickets = TicketController.getTicketsFlight();

        for (TicketModel ticket : tickets) {
            UserModel user = ticket.getUser();
            FlightModel flight = ticket.getFlight();
            SeatModel seat = ticket.getSeat();

            Object[] row = {
                    user.getId(),
                    user.getFullName(),
                    flight.getId(),
                    seat.getSeatId(),
                    flight.getDeparture() // hoặc format thành String nếu muốn hiển thị đẹp
            };

            tableModel.addRow(row);
        }
    }
}

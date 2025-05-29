package src.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private final Connection conn;

    public TicketDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Insert a new ticket record into the database.
     */
    public void createTicket(TicketModel ticket) throws SQLException {
        String sql = "INSERT INTO Ticket (Id, UserId, FlightId, SeatId, `Time`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String ticketId = createTicketId();
            String userId = ticket.getUser().getId();
            String flightId = ticket.getFlight().getId();
            String seatId = ticket.getSeat().getSeatId();
            java.sql.Timestamp time = new java.sql.Timestamp(ticket.getFlight().getDeparture().getTime());

            // Print all data before executing
            stmt.setString(1, ticketId);
            stmt.setString(2, userId);
            stmt.setString(3, flightId);
            stmt.setString(4, seatId);
            stmt.setTimestamp(5, time);
            stmt.executeUpdate();

        }
    }

    public String createTicketId() throws SQLException {
        String newId = "T001"; // T001
        String sql = "SELECT Id FROM Ticket ORDER BY Id DESC LIMIT 1";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastId = rs.getString("Id"); // e.g. "T000123"
                int numericPart = Integer.parseInt(lastId.substring(1));
                numericPart++;
                newId = String.format("T%03d", numericPart);
            }
        }

        return newId;
    }

    public static List<TicketModel> getTicketsFlight() throws SQLException {
        List<TicketModel> tickets = new ArrayList<>();

        String sql = "SELECT t.Id AS TicketId, t.UserId, t.FlightId, t.SeatId, t.Time, " +
                "u.FullName, u.Id AS UserId, u.gender, u.age, u.Phone, " +
                "f.`From`, f.`To`, f.Departure, f.Arrival, f.Status, " +
                "s.Class, s.Price " +
                "FROM Ticket t " +
                "JOIN Flight f ON t.FlightId = f.Id " +
                "JOIN User u ON t.UserId = u.Id " +
                "JOIN Seat s ON t.SeatId = s.SeatId " +
                "WHERE f.Status = 'Scheduled' " +
                "ORDER BY f.Departure ASC";

        try (Statement stmt = DBConnection.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                // Tạo UserModel
                UserModel user = new UserModel();
                user.setId(rs.getString("UserId"));
                user.setFullName(rs.getString("FullName"));
                user.setGender(rs.getString("gender"));
                user.setAge(rs.getInt("age"));
                user.setPhone(rs.getString("Phone"));

                // Tạo FlightModel
                FlightModel flight = new FlightModel();
                flight.setId(rs.getString("FlightId"));
                flight.setFrom(rs.getString("From"));
                flight.setTo(rs.getString("To"));
                flight.setDeparture(rs.getTimestamp("Departure"));
                flight.setArrival(rs.getTimestamp("Arrival"));
                flight.setStatus(FlightStatus.valueOf(rs.getString("Status")));

                // Tạo SeatModel
                SeatModel seat = new SeatModel();
                seat.setSeatId(rs.getString("SeatId"));
                seat.setSeatClass(rs.getString("Class"));
                seat.setPrice(rs.getInt("Price"));

                // Tạo TicketModel
                TicketModel ticket = new TicketModel();
                ticket.setTicketId(rs.getString("TicketId"));
                ticket.setUser(user);
                ticket.setFlight(flight);
                ticket.setSeat(seat);

                tickets.add(ticket);
            }
        }

        return tickets;
    }
}

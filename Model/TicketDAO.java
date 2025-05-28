package src.Model;

import java.sql.*;

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
        System.out.println("Inserting Ticket:");
        System.out.println("  Ticket ID: " + ticketId);
        System.out.println("  User ID: " + userId);
        System.out.println("  Flight ID: " + flightId);
        System.out.println("  Seat ID: " + seatId);
        System.out.println("  Departure Time: " + time);

        stmt.setString(1, ticketId);
        stmt.setString(2, userId);
        stmt.setString(3, flightId);
        stmt.setString(4, seatId);
        stmt.setTimestamp(5, time);
        stmt.executeUpdate();

        System.out.println("✅ Ticket inserted successfully.");
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

}

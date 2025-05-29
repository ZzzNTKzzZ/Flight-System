package src.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightSeatDAO {
    private Connection conn;

    public FlightSeatDAO(Connection conn) {
        this.conn = conn;
    }

    // Get one available seat for a given flight
    public FlightSeatModel getOneAvailableSeat(String flightId) throws SQLException {
        String sql = "SELECT FlightId, SeatId, SeatStatus " +
                "FROM FlightSeat " +
                "WHERE FlightId = ? AND SeatStatus = 'Available' " +
                "LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flightId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new FlightSeatModel(
                            rs.getString("FlightId"),
                            rs.getString("SeatId"),
                            rs.getString("SeatStatus"));
                }
            }
        }

        return null; // No available seat found
    }

    public FlightSeatModel getOneAvailableSeatByClass(String flightId, String seatClass) throws SQLException {
        String sql = "SELECT FlightSeat.FlightId, FlightSeat.SeatId, FlightSeat.SeatStatus " +
                "FROM FlightSeat " +
                "JOIN Seat ON FlightSeat.SeatId = Seat.SeatId " +
                "WHERE FlightSeat.FlightId = ? " +
                "AND FlightSeat.SeatStatus = 'Available' " +
                "AND Seat.Class = ? " +
                "LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flightId); // Set flightId
            stmt.setString(2, seatClass); // Set seatClass

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new FlightSeatModel(
                            rs.getString("FlightId"),
                            rs.getString("SeatId"),
                            rs.getString("SeatStatus"));
                }
            }
        }

        return null; // No matching available seat found
    }

    public boolean updateSeatStatus(String flightId, String seatId, String newStatus) throws SQLException {
        String sql = "UPDATE FlightSeat SET SeatStatus = ? WHERE FlightId = ? AND SeatId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setString(2, flightId);
            stmt.setString(3, seatId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public void createFlightSeat(String flightId, String seatId, String seatStatus) throws SQLException {
    String sql = "INSERT INTO FlightSeat (FlightId, SeatId, SeatStatus) VALUES (?, ?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, flightId);
        stmt.setString(2, seatId);
        stmt.setString(3, seatStatus); // Must be one of: "Available", "Booked", "Unavailable"

        stmt.executeUpdate();
    }
}
}

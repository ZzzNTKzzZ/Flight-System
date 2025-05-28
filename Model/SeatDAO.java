package src.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
    private Connection conn;

    public SeatDAO(Connection conn) {
        this.conn = conn;
    }

    public SeatModel getSeat(String flightId) throws SQLException {
        String sql = "SELECT s.SeatId, s.Class, s.Price " +
                "FROM Seat s " +
                "JOIN Ticket t ON s.SeatId = t.SeatId " +
                "WHERE t.FlightId = ? " +
                "LIMIT 1"; // only return one booked seat

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flightId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new SeatModel(
                            rs.getString("SeatId"),
                            rs.getString("Class"),
                            rs.getInt("Price"));
                }
            }
        }

        return null; // No booked seat found
    }
}

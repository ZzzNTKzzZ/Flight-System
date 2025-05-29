package src.Model;

import java.sql.*;

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
        return null;
    }

    public String createSeatId() throws SQLException {
        String newId = "S001";
        String sql = "SELECT SeatId FROM Seat ORDER BY SeatId DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastId = rs.getString("SeatId"); // ví dụ: S111
                int num = Integer.parseInt(lastId.substring(1));
                num++;
                newId = String.format("S%03d", num);
            }
        }
        return newId;
    }

    public void createSeat(SeatModel seat) throws SQLException {
        String sql = "INSERT INTO Seat (SeatId, Class, Price) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, seat.getSeatId());
            stmt.setString(2, seat.getSeatClass());
            stmt.setInt(3, seat.getPrice());

            stmt.executeUpdate();
        }
    }

}

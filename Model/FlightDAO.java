package src.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {
    private Connection conn;

    public FlightDAO(Connection conn) {
        this.conn = conn;
    }

    public List<FlightModel> getAllFlights() throws SQLException {
        List<FlightModel> flights = new ArrayList<>();
        String sql = "SELECT * FROM Flight";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            FlightModel flight = new FlightModel(
                    rs.getString("Id"),
                    rs.getString("From"),
                    rs.getString("To"),
                    rs.getTimestamp("Departure"),
                    rs.getTimestamp("Arrival"),
                    FlightStatus.valueOf(rs.getString("Status")), // Assuming you use enum
                    rs.getInt("SeatAvailable"));
            flights.add(flight);
        }

        rs.close();
        stmt.close();

        return flights;
    }

    public void createNewFlight(FlightModel flight) throws SQLException {
        String sql = "INSERT INTO Flight (Id, `From`, `To`, Departure, Arrival, Status, SeatAvailable) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, flight.getId());
        stmt.setString(2, flight.getFrom());
        stmt.setString(3, flight.getTo());
        stmt.setTimestamp(4, new Timestamp(flight.getDeparture().getTime()));
        stmt.setTimestamp(5, new Timestamp(flight.getArrival().getTime()));
        stmt.setString(6, flight.getStatus().name());
        stmt.setInt(7, flight.getSeatAvailable());

        int rowsInserted = stmt.executeUpdate();
        System.out.println("Rows inserted: " + rowsInserted);

        stmt.close();
    }

    public void deleteFlight(String flightId) throws SQLException {
        String sql = "DELETE FROM Flight WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, flightId);

        int rowsDeleted = stmt.executeUpdate();

        System.out.println("Row deleted " + rowsDeleted);
        stmt.close();
    }

    public boolean updateFlight(FlightModel flight) throws SQLException {
        String sql = "UPDATE Flight SET `from` = ?, `to` = ?, departure = ?, arrival = ? WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, flight.getFrom());
        stmt.setString(2, flight.getTo());
        stmt.setTimestamp(3, new Timestamp(flight.getDeparture().getTime()));
        stmt.setTimestamp(4, new Timestamp(flight.getArrival().getTime()));
        stmt.setString(5, flight.getId());

        int rowsUpdated = stmt.executeUpdate();

        return rowsUpdated > 0;

    }
}
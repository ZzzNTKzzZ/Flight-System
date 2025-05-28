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

    public List<TripModel> searchFlight(String from, String to, String departure, String returnDate)
        throws SQLException {
    List<TripModel> flights = new ArrayList<>();

   String sql = "SELECT \n" +
        "    outbound.Id AS OutboundId,\n" +
        "    outbound.`From` AS OutboundFrom,\n" +
        "    outbound.`To` AS OutboundTo,\n" +
        "    outbound.Departure AS OutboundDeparture,\n" +
        "    outbound.Arrival AS OutboundArrival,\n" +
        "    outbound.Status AS OutboundStatus,\n" +
        "    outbound.SeatAvailable AS OutboundSeatAvailable,\n" +
        "    returnF.Id AS ReturnId,\n" +
        "    returnF.Departure AS ReturnDeparture,\n" +
        "    returnF.Arrival AS ReturnArrival,\n" +
        "    returnF.Status AS ReturnStatus,\n" +
        "    returnF.SeatAvailable AS ReturnSeatAvailable\n" +
        "FROM Flight outbound\n" +
        "JOIN Flight returnF \n" +
        "    ON outbound.`From` = returnF.`To`\n" +
        "   AND outbound.`To` = returnF.`From`\n" +
        "   AND returnF.Departure > outbound.Arrival\n" +
        "WHERE outbound.`From` = ?\n" +
        "  AND outbound.`To` = ?\n" +
        "  AND returnF.`From` = ?\n" +
        "  AND returnF.`To` = ?\n" +
        "  AND DATE(outbound.Departure) = ?\n" +
        "  AND DATE(returnF.Departure) = ?\n" +
        "  AND outbound.Status = 'Scheduled'\n" +
        "  AND returnF.Status = 'Scheduled'\n" +
        "ORDER BY outbound.Id, returnF.Departure";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, from);
    stmt.setString(2, to);
    stmt.setString(3, to);
    stmt.setString(4, from);
    stmt.setString(5, departure);
    stmt.setString(6, returnDate);

    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
        FlightModel outbound = new FlightModel();
        outbound.setId(rs.getString("OutboundId"));
        outbound.setFrom(rs.getString("OutboundFrom"));
        outbound.setTo(rs.getString("OutboundTo"));
        outbound.setDeparture(rs.getTimestamp("OutboundDeparture"));
        outbound.setArrival(rs.getTimestamp("OutboundArrival"));
        outbound.setStatus(FlightStatus.Scheduled);
        outbound.setSeatAvailable(rs.getInt("OutboundSeatAvailable"));

        FlightModel returnFlight = new FlightModel();
        returnFlight.setId(rs.getString("ReturnId"));
        returnFlight.setFrom(to);
        returnFlight.setTo(from);
        returnFlight.setDeparture(rs.getTimestamp("ReturnDeparture"));
        returnFlight.setArrival(rs.getTimestamp("ReturnArrival"));
        returnFlight.setStatus(FlightStatus.Scheduled);
        returnFlight.setSeatAvailable(rs.getInt("ReturnSeatAvailable"));

        flights.add(new TripModel(outbound, returnFlight));
    }

    return flights;
}
}
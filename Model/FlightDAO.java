package src.Model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<TripModel> searchFlight(String from, String to, String departure, String returnDate) throws Exception {
        List<TripModel> flights = new ArrayList<>();

        // SQL query with join to find matching outbound and return flights
        String sql = "SELECT " +
                "    outbound.Id AS OutboundId, " +
                "    outbound.`From` AS OutboundFrom, " +
                "    outbound.`To` AS OutboundTo, " +
                "    outbound.Departure AS OutboundDeparture, " +
                "    outbound.Arrival AS OutboundArrival, " +
                "    outbound.Status AS OutboundStatus, " +
                "    outbound.SeatAvailable AS OutboundSeatAvailable, " +
                "    returnF.Id AS ReturnId, " +
                "    returnF.Departure AS ReturnDeparture, " +
                "    returnF.Arrival AS ReturnArrival, " +
                "    returnF.Status AS ReturnStatus, " +
                "    returnF.SeatAvailable AS ReturnSeatAvailable " +
                "FROM Flight outbound " +
                "JOIN Flight returnF ON outbound.`From` = returnF.`To` " +
                "    AND outbound.`To` = returnF.`From` " +
                "    AND returnF.Departure > outbound.Arrival " +
                "WHERE outbound.`From` = ? " +
                "  AND outbound.`To` = ? " +
                "  AND returnF.`From` = ? " +
                "  AND returnF.`To` = ? " +
                "  AND DATE(outbound.Departure) = ? " +
                "  AND DATE(returnF.Departure) = ? " +
                "  AND outbound.Status = 'Scheduled' " +
                "  AND returnF.Status = 'Scheduled' " +
                "ORDER BY outbound.Id, returnF.Departure";

        PreparedStatement stmt = conn.prepareStatement(sql);

        // Parse input dates and print them for debugging
        java.sql.Date depSqlDate = parseAndPrintDate(departure);
        java.sql.Date retSqlDate = parseAndPrintDate(returnDate);

        // Bind parameters with debug print
        System.out.println("Setting SQL parameters:");
        System.out.println("1: from = " + from);
        System.out.println("2: to = " + to);
        System.out.println("3: to (return from) = " + to);
        System.out.println("4: from (return to) = " + from);
        System.out.println("5: departure date = " + depSqlDate);
        System.out.println("6: return date = " + retSqlDate);

        stmt.setString(1, from);
        stmt.setString(2, to);
        stmt.setString(3, to);
        stmt.setString(4, from);
        stmt.setDate(5, depSqlDate);
        stmt.setDate(6, retSqlDate);

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

        rs.close();
        stmt.close();

        System.out.println("Flights found: " + flights.size());
        return flights;
    }

    public List<TripModel> searchFlightOneWay(String from, String to, String departureDate) {
        List<TripModel> flights = new ArrayList<>();
        String sql = "SELECT * FROM Flight WHERE `from` = ? AND `to` = ? AND DATE(`departure`) = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, from);
            stmt.setString(2, to);
            stmt.setString(3, departureDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FlightModel flight = new FlightModel();
                flight.setId(rs.getString("Id"));
                flight.setFrom(rs.getString("From"));
                flight.setTo(rs.getString("To"));
                flight.setDeparture(rs.getTimestamp("Departure"));
                flight.setArrival(rs.getTimestamp("Arrival"));
                flight.setStatus(FlightStatus.valueOf(rs.getString("Status")));
                flight.setSeatAvailable(rs.getInt("SeatAvailable"));

                flights.add(new TripModel(flight, null)); // returnFlight = null for one-way
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public FlightModel searchFlight(String flightId) throws SQLException {
        String sql = "SELECT * FROM flight WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FlightModel flight = new FlightModel();
                flight.setId(rs.getString("Id"));
                flight.setFrom(rs.getString("From"));
                flight.setTo(rs.getString("To"));
                flight.setDeparture(rs.getTimestamp("Departure"));
                flight.setArrival(rs.getTimestamp("Arrival"));
                flight.setStatus(FlightStatus.valueOf(rs.getString("Status")));
                flight.setSeatAvailable(rs.getInt("SeatAvailable"));
                return flight;
            } else {
                return null;
            }
        }
    }

    public List<String> getFromFlight() throws SQLException {
        List<String> fromList = new ArrayList<>();
        String sql = "SELECT DISTINCT `from` FROM flight";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            fromList.add(rs.getString("from"));
        }
        return fromList;
    }

    public List<String> getToFlight() throws SQLException {
        List<String> toList = new ArrayList<>();
        String sql = "SELECT DISTINCT `to` FROM flight";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            toList.add(rs.getString("to"));
        }

        return toList;
    }

    private java.sql.Date parseAndPrintDate(String dateStr) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = inputFormat.parse(dateStr);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("Parsed date string '" + dateStr + "' as SQL Date: " + sqlDate);
        return sqlDate;
    }

}
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
            flights.add(new FlightModel(
                    rs.getString("Id"),
                    rs.getString("From"),
                    rs.getString("To"),
                    rs.getTimestamp("Departure"), 
                    rs.getTimestamp("Arrival"),
                    rs.getString("Status"),
                    rs.getInt("SeatAvailable")));
        }
        return flights;
    }
}

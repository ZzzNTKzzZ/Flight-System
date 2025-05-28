package src.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;

import src.Model.DBConnection;
import src.Model.FlightDAO;
import src.Model.FlightModel;
import src.Model.TripModel;

public class FlightController {
    public static List<FlightModel> getAllFlights() {
        List<FlightModel> flights = null; // khai báo danh sách để trả về

        try {
            Connection conn = DBConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            flights = flightDAO.getAllFlights();
            conn.close(); // Đóng kết nối sau khi dùng xong
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flights;
    }

    public static void createNewFlights(FlightModel flight) {
        try {
            Connection conn = DBConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            flightDAO.createNewFlight(flight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFlight(String flightId) {
        try {
            Connection conn = DBConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            flightDAO.deleteFlight(flightId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateFlight(FlightModel flight) {
        try {
            Connection conn = DBConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            flightDAO.updateFlight(flight);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static List<TripModel> searchFlight(String from, String to, String departure, String returnDate) {
        List<TripModel> flights = new ArrayList<>();
        
        // Trim and validate inputs
        if (from == null || to == null || departure == null || returnDate == null ||
                from.trim().isEmpty() || to.trim().isEmpty() ||
                departure.trim().isEmpty() || returnDate.trim().isEmpty()) {

            JOptionPane.showMessageDialog(null,
                    "All fields must be filled.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return flights;
        }

        SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdfSql = new SimpleDateFormat("yyyy-MM-dd"); // SQL format
        sdfInput.setLenient(false);

        String depDateStr;
        String retDateStr;

        try {
            // Parse input strings to java.util.Date
            java.util.Date utilDepDate = sdfInput.parse(departure.trim());
            java.util.Date utilRetDate = sdfInput.parse(returnDate.trim());

            // Convert to java.sql.Date
            java.sql.Date depDate = new java.sql.Date(utilDepDate.getTime());
            java.sql.Date retDate = new java.sql.Date(utilRetDate.getTime());

            // Format to SQL-style string
            depDateStr = sdfSql.format(depDate);
            retDateStr = sdfSql.format(retDate);

            // Validate dates
            if (!retDate.after(depDate)) {
                JOptionPane.showMessageDialog(null,
                        "Return date must be after departure date.",
                        "Date Error",
                        JOptionPane.WARNING_MESSAGE);
                return flights;
            }

            // DB call inside same try block
            Connection conn = DBConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            flights = flightDAO.searchFlight(from.trim(), to.trim(), depDateStr, retDateStr);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "An error occurred while searching flights or parsing dates.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return flights;
    }

    public static List<String> getFlightFrom() {
        List<String> fromList = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            fromList = flightDAO.getFromFlight();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fromList;
    }

    public static List<String> getFligthTo() {
        List<String> toList = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            toList = flightDAO.getToFlight();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toList;
    }

    public static FlightModel getFlight(String flightId) {
        FlightModel flight = new FlightModel();

        try {
            Connection conn = DBConnection.getConnection();
            FlightDAO flightDAO = new FlightDAO(conn);
            flight = flightDAO.searchFlight(flightId);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flight;
    }
}

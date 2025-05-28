package src.Controller;

import java.util.List;
import java.sql.Connection;

import src.Model.DBConnection;
import src.Model.FlightDAO;
import src.Model.FlightModel;

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
            // TODO: handle exception
        }
    }
}

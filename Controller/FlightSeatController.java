package src.Controller;

import src.Model.DBConnection;
import src.Model.FlightSeatDAO;
import src.Model.FlightSeatModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FlightSeatController {

    public static FlightSeatModel getAvailableSeat(String flightId) {
        Connection conn = null;
        try {
            conn = new DBConnection().getConnection();
            FlightSeatDAO flightSeatDAO = new FlightSeatDAO(conn);
            return flightSeatDAO.getOneAvailableSeat(flightId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static FlightSeatModel getAvailableSeatByClass(String flightId, String seatClass) {
        Connection conn = null;
        try {
            conn = new DBConnection().getConnection();
            FlightSeatDAO flightSeatDAO = new FlightSeatDAO(conn);
            return flightSeatDAO.getOneAvailableSeatByClass(flightId, seatClass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean updateSeatStatus(String flightId, String seatId, String newStatus) {
        Connection conn = null;
        try {
            conn = new DBConnection().getConnection();
            FlightSeatDAO flightSeatDAO = new FlightSeatDAO(conn);
            return flightSeatDAO.updateSeatStatus(flightId, seatId, newStatus);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createAllFlightSeat(List<FlightSeatModel> flightSeatList) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            FlightSeatDAO flightSeatDAO = new FlightSeatDAO(conn);
            for (FlightSeatModel flightSeat : flightSeatList) {
                flightSeatDAO.createFlightSeat(flightSeat.getFlightId(), flightSeat.getSeatId(), flightSeat.getSeatStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

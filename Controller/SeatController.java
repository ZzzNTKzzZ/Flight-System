package src.Controller;

import java.sql.Connection;
import java.util.List;

import src.Model.DBConnection;
import src.Model.SeatDAO;
import src.Model.SeatModel;

public class SeatController {
    public static SeatModel getSeat(String flightId) {
        Connection conn = null;
        SeatModel seat = null;
        try {
            conn = DBConnection.getConnection();
            SeatDAO seatDAO = new SeatDAO(conn);
            seat = seatDAO.getSeat(flightId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return seat;
    }

    public static String createSeatId() {
        Connection conn = null;
        String seatId = null;
        try {
            conn = DBConnection.getConnection();
            SeatDAO seatDAO = new SeatDAO(conn);
            seatId = seatDAO.createSeatId();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return seatId;
    }

    public static void createAllSeat(List<SeatModel> seats) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            SeatDAO seatDAO = new SeatDAO(conn);
            for (SeatModel seat : seats) {
                seatDAO.createSeat(seat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

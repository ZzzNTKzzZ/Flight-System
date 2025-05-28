package src.Controller;

import java.sql.Connection;

import src.Model.DBConnection;
import src.Model.SeatDAO;
import src.Model.SeatModel;

public class SeatController {
    public static SeatModel getSeat(String flightId) {
        SeatModel seat = null;

        try {
            Connection conn = DBConnection.getConnection();
            SeatDAO seatDAO = new SeatDAO(conn);
            seat = seatDAO.getSeat(flightId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seat;
    }

}

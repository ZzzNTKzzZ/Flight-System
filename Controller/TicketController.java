package src.Controller;

import java.sql.Connection;
import java.util.List;

import src.Model.DBConnection;
import src.Model.FlightModel;
import src.Model.TicketDAO;
import src.Model.TicketModel;
import src.Model.UserModel;

public class TicketController {
    
    public static void createTicket(TicketModel ticket) {
        try {
            Connection conn = DBConnection.getConnection();
            TicketDAO ticketDAO = new TicketDAO(conn);
            ticketDAO.createTicket(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<TicketModel> getTicketsFlight() {
        List<TicketModel> list = null;
        try {
            Connection conn = DBConnection.getConnection();
            TicketDAO ticketDAO = new TicketDAO(conn);
            list = ticketDAO.getTicketsFlight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

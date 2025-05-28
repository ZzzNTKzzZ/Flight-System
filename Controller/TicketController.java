package src.Controller;

import java.sql.Connection;

import src.Model.DBConnection;
import src.Model.FlightModel;
import src.Model.TicketDAO;
import src.Model.TicketModel;
import src.Model.UserModel;

public class TicketController {
    public static TicketModel createTicket(UserModel user, FlightModel flight) {
        TicketModel ticket = new TicketModel(null, user, flight, null, 0);
        return ticket;
    }
    public static String createTicketId() {
        String ticketId = "";

        try {
            Connection conn = DBConnection.getConnection();
            TicketDAO ticketDAO = new TicketDAO(conn);
            ticketId = ticketDAO.createTicketId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ticketId;
    }

    public static void createTicket(TicketModel ticket) {

        try {
            Connection conn = DBConnection.getConnection();
            TicketDAO ticketDAO = new TicketDAO(conn);
            ticketDAO.createTicket(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

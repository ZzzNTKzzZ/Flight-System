package src.Model;

public class TicketModel {
    private String ticketId;
    private UserModel user;
    private FlightModel flight;
    private SeatModel seat;
    private double price;

    // Constructor
    public TicketModel(String ticketId, UserModel user, FlightModel flight, SeatModel seat, double price) {
        this.ticketId = ticketId;
        this.user = user;
        this.flight = flight;
        this.seat = seat;
        this.price = price;
    }

    public TicketModel() {
        //TODO Auto-generated constructor stub
    }

    // Getters and Setters
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public FlightModel getFlight() {
        return flight;
    }

    public void setFlight(FlightModel flight) {
        this.flight = flight;
    }

    public SeatModel getSeat() {
        return seat;
    }

    public void setSeat(SeatModel seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString for display
    @Override
    public String toString() {
        return "Ticket ID: " + ticketId + "\n" +
               "Passenger Info:\n" + user + "\n\n" +
               "Flight Info:\n" + flight + "\n" +
               "Seat Info: " + seat + "\n" +
               "Price: $" + price;
    }
}

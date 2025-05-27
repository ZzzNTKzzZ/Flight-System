package src.Model;

public class TicketModel {
    private String ticketId;
    private UserModel user;
    private FlightModel flight;
    private String seatNumber;
    private double price;

    // Constructor
    public TicketModel(String ticketId, UserModel user, FlightModel flight, String seatNumber, double price) {
        this.ticketId = ticketId;
        this.user = user;
        this.flight = flight;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    // Getters and Setters
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public UserModel getPassenger() {
        return user;
    }

    public void setPassenger(UserModel user) {
        this.user = user;
    }

    public FlightModel getFlight() {
        return flight;
    }

    public void setFlight(FlightModel flight) {
        this.flight = flight;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
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
               "Seat Number: " + seatNumber + "\n" +
               "Price: $" + price;
    }
}

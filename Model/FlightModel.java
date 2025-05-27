package src.Model;

import java.util.Date;

public class FlightModel {
    private String id;
    private String from;
    private String to;
    private Date departure;
    private Date arrival;
    private String status;
    private int seatAvailable;

    // Constructor
    public FlightModel(String id, String from, String to, Date departure, Date arrival, String status, int seatAvailable) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
        this.status = status;
        this.seatAvailable = seatAvailable;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public Date getDeparture() { return departure; }
    public void setDeparture(Date departure) { this.departure = departure; }

    public Date getArrival() { return arrival; }
    public void setArrival(Date arrival) { this.arrival = arrival; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getSeatAvailable() { return seatAvailable; }
    public void setSeatAvailable(int seatAvailable) { this.seatAvailable = seatAvailable; }

    @Override
    public String toString() {
        return "Flight: " + id + "\n" +
               "From: " + from + "\n" +
               "To: " + to + "\n" +
               "Departure: " + departure + "\n" +
               "Arrival: " + arrival + "\n" +
               "Status: " + status + "\n" +
               "Seats Available: " + seatAvailable;
    }
}

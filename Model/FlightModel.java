package src.Model;

import java.util.Date;

public class FlightModel {
    private String id;
    private String from;
    private String to;
    private Date departure;
    private Date arrival;
    private FlightStatus status; // Use enum
    private int seatAvailable;
    // Default Constructor
    public FlightModel() {
        
    }

    // Constructor
    public FlightModel(String id, String from, String to, Date departure, Date arrival, FlightStatus status,
            int seatAvailable) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
        this.status = status;
        this.seatAvailable = seatAvailable;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public int getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(int seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    @Override
    public String toString() {
        return "Flight: " + id + "\n" +
                "From: " + from + "\n" +
                "To: " + to + "\n" +
                "Departure: " + departure + "\n" +
                "Arrival: " + arrival + "\n" +
                "Status: " + status.name() + "\n" +
                "Seats Available: " + seatAvailable;
    }
}

package src.Model;

public class FlightSeatModel {
    private String flightId;
    private String seatId;
    private String seatStatus;

    public FlightSeatModel(String flightId, String seatId, String seatStatus) {
        this.flightId = flightId;
        this.seatId = seatId;
        this.seatStatus = seatStatus;
    }

    // Getters
    public String getFlightId() {
        return flightId;
    }

    public String getSeatId() {
        return seatId;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    // Setters
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    @Override
    public String toString() {
        return "FlightSeatModel{" +
                "flightId='" + flightId + '\'' +
                ", seatId='" + seatId + '\'' +
                ", seatStatus='" + seatStatus + '\'' +
                '}';
    }
}

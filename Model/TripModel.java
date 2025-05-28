package src.Model;

public class TripModel {
    private FlightModel outbound;
    private FlightModel returnFlight;

    // Constructor, getters, setters
    public TripModel(FlightModel outbound, FlightModel returnFlight) {
        this.outbound = outbound;
        this.returnFlight = returnFlight;
    }

    public FlightModel getOutbound() { return outbound; }
    public FlightModel getReturnFlight() { return returnFlight; }
    @Override
    public String toString() {
        return "TripModel{" +
                "Outbound Flight=" + (outbound != null ? outbound.toString() : "N/A") +
                ", Return Flight=" + (returnFlight != null ? returnFlight.toString() : "N/A") +
                '}';
    }
}

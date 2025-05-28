package src.Model;

public class SeatModel {
    private String seatId;
    private String seatClass; // economy, business, first class
    private int price;

    public SeatModel(String seatId, String seatClass, int price) {
        this.seatId = seatId;
        setSeatClass(seatClass);  // use setter to validate class
        this.price = price;
    }

    // Getters and setters
    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        if (seatClass == null) {
            throw new IllegalArgumentException("Seat class cannot be null");
        }
        String s = seatClass.toLowerCase();
        if (!s.equals("economy") && !s.equals("business") && !s.equals("first class")) {
            throw new IllegalArgumentException("Invalid seat class: " + seatClass);
        }
        this.seatClass = s;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "SeatModel{" +
                "seatId='" + seatId + '\'' +
                ", seatClass='" + seatClass + '\'' +
                ", price=" + price +
                '}';
    }
}

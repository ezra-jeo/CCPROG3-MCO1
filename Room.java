public class Room {
    private String name;
    private double basePrice = 1299.0;
    private boolean available;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.basePrice;
    }

    public boolean getAvailability() {
        return this.available;
    }

    public void setPrice(double price) {
        this.basePrice = price;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

}

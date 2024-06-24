import java.util.ArrayList;

public class Room {
    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31;

    private String name;
    private double basePrice = 1299.0;
    private ArrayList<Boolean> availability;

    public Room(String name) {
        int i;

        this.name = name;
        this.availability = new ArrayList<Boolean>();

        for (i = MIN_DATE; i <= MAX_DATE; i++)
            this.availability.add(true);
    }

    public int getMinDate() {
        return MIN_DATE;
    }

    public int getMaxDate() {
        return MAX_DATE;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.basePrice;
    }

    public ArrayList<Boolean> getAvailability() {
        return this.availability;
    }

    public void setPrice(double price) {
        this.basePrice = price;
    }

    public void setAvailability(int checkInDate, int checkOutDate, boolean available) {
        int i;

        for (i = checkInDate - 1; i < checkOutDate - 1; i++) 
            this.availability.set(i, available);
    }
}

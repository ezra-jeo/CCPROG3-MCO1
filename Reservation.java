public class Reservation {
    private String guestName;
    private int checkInDate;
    private int checkOutDate;
    private double nightPrice;
    private double totalPrice;
    private Room room;
    
    public Reservation(String guestName, int checkInDate, int checkOutDate, double nightPrice, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.nightPrice = nightPrice;
        this.room = room;
        this.totalPrice = this.nightPrice * (this.checkOutDate - this.checkInDate);
    }

    public String getGuestName() {
        return this.guestName;
    }

    public int getCheckInDate() {
        return this.checkInDate;
    }

    public int getCheckOutDate() {
        return this.checkOutDate;
    }

    public Room getRoom() {
        return this.room;
    }

    public double getNightPrice() {
        return this.nightPrice;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

}

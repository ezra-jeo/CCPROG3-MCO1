public class Reservation {
    private String guestName;
    private int checkInDate;
    private int checkOutDate;
    private Room room;
    
    public Reservation(String guestName, int checkInDate, int checkOutDate, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;

        room.setAvailability(checkInDate, checkOutDate, false);
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
        return this.room.getPrice();
    }

    public double getTotalPrice() {
        double total = 0;
        int i;

        for (i = this.checkInDate; i < this.checkOutDate; i++)
            total += this.room.getPrice();

        return total;
    }

}

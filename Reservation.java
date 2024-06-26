/**
 * The Reservation class represents the reservation entity in the hotel reservation system.
 * It has the guest name, the check-in and check-out dates, and the information on the reserved room.
 */
public class Reservation {
    private String guestName;
    private int checkInDate;
    private int checkOutDate;
    private Room room;
    
    /**
     * Creates a new Reservation object for the given guest name, check-in and check-out dates, and the room to be reserved.
     * 
     * @param guestName the name of the guest under the reservation object.
     * @param checkInDate the starting date to be considered under the reservation object.
     * @param checkOutDate the ending date to be considered under the reservation object.
     * @param room the room to be reserved under the reservation object.
     */
    public Reservation(String guestName, int checkInDate, int checkOutDate, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;

        // Reserves the room
        room.setAvailability(checkInDate, checkOutDate, false);
    }


    /**
     * Returns the guest name under the reservation object.
     * 
     * @return the guest name under the reservation.
     */
    public String getGuestName() {
        return this.guestName;
    }

    /**
     * Returns the check-in date under the reservation object.
     * 
     * @return the check-in date under the reservation.
     */
    public int getCheckInDate() {
        return this.checkInDate;
    }

    /**
     * Returns the check-out date under the reservation object.
     * 
     * @return the check-out date under the reservation.
     */
    public int getCheckOutDate() {
        return this.checkOutDate;
    }

    /**
     * Returns the room object under the reservation object.
     * 
     * @return the room object under the reservation.
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Returns the price of the reservation per night.
     * 
     * @return the price of the reservation per night.
     */
    public double getNightPrice() {
        return this.room.getPrice();
    }

    /**
     * Gets and returns the total price for the whole reservation.
     * 
     * @return the total price for the reservation.
     */
    public double getTotalPrice() {
        double total = 0;
        int i;

        for (i = this.checkInDate; i < this.checkOutDate; i++)
            total += this.room.getPrice();

        return total;
    }
}

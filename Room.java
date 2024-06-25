/**
 * The Room class represents the hotel room entity in the hotel reservation system.
 * It has a name, which is unique to the hotel, a base price, which is the cost per night, 
 * and a list of availability, which represents its availability across the entire month,
 * with respect to the minimum and maximum date reservation value.
 */

import java.util.ArrayList;

public class Room {
    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31; 

    private String name;
    private double basePrice = 1299.0;
    private ArrayList<Boolean> availability;

    /**
     * Creates a new Room object for the given name and sets its availability to true across the entire month.
     * 
     * @param name the name of the room.
     */
    public Room(String name) {
        int i;

        this.name = name;
        this.availability = new ArrayList<Boolean>();

        for (i = MIN_DATE; i <= MAX_DATE; i++)
            this.availability.add(true);
    }

    /**
     * Returns the minimum date reservation value of the room class.
     * 
     * @return the minimum date reservation value.
     */
    public static int getMinDate() {
        return MIN_DATE;
    }

    /**
     * Returns the maximum date reservation value of the room class.
     * 
     * @return the maximum date reservation value.
     */
    public static int getMaxDate() {
        return MAX_DATE;
    }

    /**
     * Returns the name of the room object.
     * 
     * @return the name of the room.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the base price of the room object.
     * 
     * @return the base price of the room.
     */
    public double getPrice() {
        return this.basePrice;
    }

    /**
     * Returns the list of availability of the room object across the entire month.
     * 
     * @return the arraylist of availability of the room.
     */
    public ArrayList<Boolean> getAvailability() {
        return this.availability;
    }

    /**
     * Replaces and sets the first character of the name of the room object with given letter.
     * 
     * @param letter the letter to use to replace the first character of the name of the room.
     */
    public void setName(char letter) {
        this.name = this.name.replace(this.name.charAt(0), letter);
    }

    /**
     * Sets the base price of the room object to the given price value.
     * 
     * @param price the price to use to set the base price of the room.
     */
    public void setPrice(double price) {
        this.basePrice = price;
    }

    /**
     * Sets the availability status of the room object for a range of dates in a list based on the provided start and end dates.
     * 
     * @param startDate the starting date for which availability of the room object needs to be set.
     * @param endDate the ending date for which availability of the room object needs to be considered.
     * @param available the availability status to set the availability of the room object.
     */
    public void setAvailability(int startDate, int endDate, boolean available) {
        int i;

        // If the endDate is not 31 (MAX_DATE), the availability of the endDate should not be set to the given availability status.
        if (endDate < MAX_DATE) {
            for (i = startDate - 1; i < endDate - 1; i++) 
                this.availability.set(i, available);
        }
        // Otherwise, the availability of the endDate should be set to the given availability status.
        else {
            for (i = startDate - 1; i < endDate; i++) 
                this.availability.set(i, available);
        }
    }
}

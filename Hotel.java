/**
 * The Hotel class represents the hotel entity in the hotel reservation system.
 * It has a name, which is unique from the other hotels in the system, a list of rooms, with
 * respect to the minimum and maximum number of rooms in a hotel, and a list of reservations.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Hotel {
    private static final int MIN_ROOM = 1;
    private static final int MAX_ROOM = 50;

    private String name;
    private ArrayList<Room> roomList;
    private ArrayList<Reservation> reservationList;

    /**
     * Creates a new Hotel object for the given name and creates a number of room/s for the hotel based on the given number.
     * 
     * @param name the name of the hotel.
     * @param numRoom the number of rooms to be created.
     */
    public Hotel(String name, int numRoom) {
        String roomName;
        Room room;
        int i;

        this.name = name;
        this.roomList = new ArrayList<Room>();
        this.reservationList = new ArrayList<Reservation>();

        for (i = 1; i <= numRoom; i++) {
            roomName = name.substring(0,1) + i;
            room = new Room(roomName);
            this.roomList.add(room);
        }
    }

    /**
     * Sorts the rooms in the list of rooms under the hotel object based on their room numbers.
     */
    private void sortRoom() {
        int size = this.roomList.size();
        int i, j;
        int minIndex;
        int minNum;
        int roomNum;
        Room room;

        // Executes a selection sort algorithm to sort the rooms by their room numbers.
        for (i = 0; i < size - 1; i++) {
            minIndex = i;
            for (j = i + 1; j < size; j++) {
                minNum = Integer.parseInt(this.roomList.get(minIndex).getName().substring(1));
                roomNum = Integer.parseInt(this.roomList.get(j).getName().substring(1));

                if (minNum > roomNum)
                    minIndex = j;
            }

            if (minIndex != i) {
                room = this.roomList.remove(minIndex);
                this.roomList.add(i, room);
            }
        } 
    }

    /**
     * Checks if the given room is available within the given range of check-in and check-out date.
     * 
     * @param room the room object to be checked whether is available or not.
     * @param checkInDate the starting date to consider the availability of the room object.
     * @param checkOutDate the ending date to consider the availability of the room object.
     * @return true if the given room is available within the given range and false otherwise.
     * @pre checkInDate is before checkOut, and checkInDate and checkOutDate is within the range of the minimum and maximum date reservation values.
     */
    public boolean checkAvailability(Room room, int checkInDate, int checkOutDate) {
        boolean available = true;
        int i = checkInDate - 1;

        // Loops through the concerned dates and checks whether a date is not available.
        while (i < checkOutDate - 1 && available) {
            if (room.getAvailability().get(i) == false)
                available = false;

            i++;
        }

        return available;
    }

    /**
     * Checks the availability of all the rooms in the hotel object within the entire month.
     * 
     * @return true if all the rooms are available and false otherwise.
     */
    public boolean checkAllRoomAvailability() {
        int size = this.roomList.size();
        int i = 0;
        boolean available = true;

        // Loops through all the rooms in the list and checks their availability within the entire month.
        while (i < size && available) {
            if (!checkAvailability(this.roomList.get(i), Room.getMinDate(), Room.getMaxDate()))
                available = false;

            i++;
        }

        return available;
    }

    /**
     * Changes the name of all the rooms under the hotel object based on the initial letter of the name of the hotel object.
     */
    public void changeAllRoomName() {
        for (Room room : this.roomList) {
            room.setName(this.name.charAt(0));
        }
    }
    
    /**
     * Searches and gets the room index of the given room name based on the list of rooms under the hotel object.
     * 
     * @param name the room name to be searched on from the list of rooms.
     * @return the index of the room if found and -1 if not found.
     */
    public int getRoomIndex(String name) {
        int size = this.roomList.size();
        int i = 0;
        boolean found = false;

        // Loops through the list of rooms and checks whether it is the same with the given name.
        while (i < size && !found) {
            if (name.equals(this.roomList.get(i).getName()))
                found = true;

            i++;
        }

        if (found)
            return --i;
        else
            return -1;
    }

    /**
     * Creates and adds a number of rooms to the list of rooms under the hotel object based on the given number.
     * 
     * @param numRoom the number of rooms to create and add to the list of rooms.
     * @pre adding numRoom to the list will not exceed the maximum number of rooms for the hotel object.
     */
    public void addRoom(int numRoom) {
        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;
        boolean roomExists;
        int size = this.roomList.size();
        int roomNum = 1;
        int i = 1, j = 0;
        int ctr = 1;
        ArrayList<String> roomNameList = new ArrayList<String>();
        String input;
        Room room;

        sortRoom();

        // Automatically creates room names based on the number of rooms to be created and based on the sequential room naming of the rooms.
        for (i = 1; i <= numRoom; i++) {
            roomExists = true;
            while (j < size && roomExists) {
                if (roomNum != Integer.parseInt(this.roomList.get(j).getName().substring(1)))
                    roomExists = false;
                else 
                    j++;
                
                roomNum++;
            }

            if (roomExists) {
                roomNameList.add(this.name.substring(0,1) + (Integer.parseInt(this.roomList.get(this.roomList.size()-1).getName().substring(1)) + ctr));
                ctr++;
            }
            else
                roomNameList.add(this.name.substring(0,1) + (roomNum-1));            
        }

        // Asks user for confirmation regarding the addition of rooms.
        do {
            System.out.print("Add room/s ");

            for (String roomName : roomNameList)
                System.out.print("\"" + roomName + "\", ");

            System.out.print("\b\b to hotel \"" + this.name + "\" room list? (Yes/No): ");
            input = scanner.nextLine();   

            if (input.equalsIgnoreCase("Yes")) {
                // Creates room objects based on the created names and adds to the list of rooms.
                for (String roomName : roomNameList) {
                    room = new Room(roomName);
                    this.roomList.add(room);
                }
                System.out.println("Room/s has/have been successfully added to hotel \"" + this.name + "\" room list.");
                repeat = false;
            }
            else if (input.equalsIgnoreCase("No"))
                repeat = false;
            else
                System.out.println("Invalid input.");
        } while (repeat);
    }

    /**
     * Removes a number of available rooms based on the given list of room index from the list of rooms.
     * 
     * @param indexList the list of index of the rooms to be removed.
     * @return true if the list of index is valid and false otherwise.
     * @pre removing a number of available rooms will not make the hotel room list empty.
     */
    public boolean removeRoom(ArrayList<Integer> indexList) {
        Scanner scanner = new Scanner(System.in);
        boolean result = true;
        boolean repeat = true;
        boolean available = true;
        boolean valid = true;
        String input;
        int size = this.roomList.size();
        int i = 0;
        ArrayList<Room> roomList = new ArrayList<Room>();

        // Loops and checks whether the given list of index is valid.
        while (i < indexList.size() && valid) {
            if (!(indexList.get(i) >= 0 && indexList.get(i) < size))
                valid = false;

            i++;
        }

        if (valid) {
            // Loops and checks the availability of the rooms to be removed. 
            i = 0;
            while (i < indexList.size() && available) {
                if (!(checkAvailability(this.roomList.get(indexList.get(i)), Room.getMinDate(), Room.getMaxDate())))
                    available = false;

                i++;
            }

            // Checks whether all rooms to be removed are available.
            if (available) {
                // Asks user for confirmation regarding the removal of rooms.
                do {
                    System.out.print("Remove room/s ");

                    for (Integer index : indexList)
                        System.out.print("\"" + this.roomList.get(index).getName() + "\", ");
        
                    System.out.print("\b\b from hotel \"" + this.name + "\" room list? (Yes/No): ");
                    input = scanner.nextLine();   

                    if (input.equalsIgnoreCase("Yes")) {
                        // Removes all concerned rooms from the list.
                        for (Integer index : indexList)
                            roomList.add(this.roomList.get(index)); // Adds the concerned rooms to a list.

                        this.roomList.removeAll(roomList); // Using the list to removed the concerned rooms.
                        System.out.println("Room/s has/have been successfully removed from hotel \"" + this.name + "\" room list.");
                        repeat = false;
                    }
                    else if (input.equalsIgnoreCase("No"))
                        repeat = false;
                    else
                        System.out.println("Invalid input.");
                } while (repeat);
            }
            else {
                System.out.println("Chosen room/s has/have an active reservation.");
                result = false;
            }
        }
        else {
            System.out.println("Invalid input.");
            result = false;
        }

        return result;
    }

    /**
     * Updates the prices of all the rooms under the hotel object.
     * 
     * @param price the new price to be set across all the rooms.
     * @return true if the given price is valid and false otherwise.
     * @pre all of the rooms under the hotel object is available.
     */
    public boolean updateRoomPrice(double price) {
        Scanner scanner = new Scanner(System.in);
        boolean result = true;
        boolean repeat = true;
        String input;

        // Checks whether the price is valid.
        if (price >= 100) {
            // Asks user for confirmation regarding the modification.
            do {
                System.out.print("Change room price to " + price + " for hotel \"" + this.name + "\"? (Yes/No): ");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("Yes")) {
                    // Changes the price of all the rooms.
                    for (Room room : this.roomList)
                        room.setPrice(price);
                    System.out.println("Room price has been successfuly changed to " + price + " for hotel \"" + this.name + "\".");
                    repeat = false;
                }
                else if (input.equalsIgnoreCase("No"))
                    repeat = false;
                else
                    System.out.println("Invalid input.");
            } while (repeat);
        }
        else {
            System.out.println("Invalid input.");
            result = false;
        }

        return result;
    }

    /**
     * Creates and adds a reservation to the list of reservations under the hotel object given the guest name and check-in and check-out dates.
     * 
     * @param guestName the name of the guest for the reservation.
     * @param checkInDate the starting date for the reservation.
     * @param checkOutDate the ending date for the reservation.
     * @return true if all parameters are valid and false otherwise.
     */
    public boolean addReservation(String guestName, int checkInDate, int checkOutDate) {
        Scanner scanner = new Scanner(System.in);
        boolean result = true;
        boolean repeat = true;
        boolean available = false;
        int i = 0;
        String input;
        Room room;

        // Checks whether the date values are valid.
        if ((checkInDate >= 1 && checkInDate < 31) && (checkOutDate > 1 && checkOutDate <= 31) && (checkInDate < checkOutDate)) {
            // Looks for an available room for the specified range of dates.
            while (i < this.roomList.size() && !available) {
                if (checkAvailability(this.roomList.get(i), checkInDate, checkOutDate))
                    available = true;

                i++;
            }

            // Checks whether there is an available room.
            if (available) {
                room = this.roomList.get(i-1);

                // Asks user for confirmation regarding the reservation.
                do {
                    System.out.print("Confirm reservation of \"" + guestName + "\" in room \"" + room.getName() + "\" from " + checkInDate + " to " + checkOutDate + "? (Yes/No): ");
                    input = scanner.nextLine();

                    if (input.equalsIgnoreCase("Yes")) {
                        // Creates a reservation and adds to the list of reservations.
                        Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate, room);
                        this.reservationList.add(reservation);
                        System.out.println("Reservation of \"" + guestName + "\" in room \"" + room.getName() + "\" from " + checkInDate + " to " + checkOutDate + " has been successfuly confirmed.");
                        repeat = false;
                    }
                    else if (input.equalsIgnoreCase("No"))
                        repeat = false;
                    else
                        System.out.println("Invalid input.");
                } while (repeat);
            }
            else {
                System.out.println("There is no room available for " + checkInDate + " to " + checkOutDate + ".");
                result = false;
            }
        }
        else {
            System.out.println("Invalid input.");
            result = false;
        }

        return result;
    }

    /**
     * Removes a reservation from the list of reservations under the hotel object given the reservation index from the list.
     * 
     * @param index the index of the reservation from the list of reservations.
     * @return true if index value is valid and false otherwise.
     * @pre there is an existing reservation under the hotel object.
     */
    public boolean removeReservation(int index) {
        Scanner scanner = new Scanner(System.in);
        boolean result = true;
        boolean repeat = true;
        String input;
        Reservation reservation;

        // Checks whether the index value is valid
        if (index >= 0 && index < this.reservationList.size()) {
            reservation = this.reservationList.get(index);
            
            // Asks user for confirmation regarding the modification.
            do {
                System.out.print("Remove reservation of \"" + reservation.getGuestName() + "\" in room \"" + reservation.getRoom().getName() + "\" from " + reservation.getCheckInDate()+ " to " + reservation.getCheckOutDate() + "? (Yes/No): ");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("Yes")) {
                    // Removes the reservation from the room.
                    reservation.getRoom().setAvailability(reservation.getCheckInDate(), reservation.getCheckOutDate(), true);
                    // Removes the reservation from the list.
                    this.reservationList.remove(index);
                    System.out.print("Reservation of \"" + reservation.getGuestName() + "\" in room \"" + reservation.getRoom().getName() + "\" from " + reservation.getCheckInDate()+ " to " + reservation.getCheckOutDate() + " has been successfuly removed.");
                    repeat = false;
                }
                else if (input.equalsIgnoreCase("No"))
                    repeat = false;
                else
                    System.out.println("Invalid input.");
            } while (repeat);
        }
        else {
            System.out.println("Invalid input.");
            result = false;
        }

        return result;
    }

    /**
     * Gets the total number of available rooms under the hotel object based on the given date.
     * 
     * @param date the date to be considered in getting the number of available rooms.
     * @return the total number of available rooms with the given date and -1 if the date input is invalid.
     */
    public int getTotalAvailableRooms(int date) {
        int total = 0;

        if (date >= Room.getMinDate() && date < Room.getMaxDate()) {
            // Loops through the list of rooms and counts the number of available rooms with the specified date.
            for (Room room : this.roomList) {
                if (room.getAvailability().get(date-1) == true)
                    total++;
            }
        }
        else if (date == Room.getMaxDate()) {
            System.out.println("No rooms are available for checking-in on the date 31.");
            total = -1;
        }
        else {
            System.out.println("Invalid input.");
            total = -1;
        }

        return total;
    }

    /**
     * Gets the total number of booked rooms under the hotel object based on the given date.
     * 
     * @param date the date to be considered in getting the number of booked rooms.
     * @return the total number of booked rooms with the given date and -1 if the date input is invalid.
     */
    public int getTotalBookedRooms(int date) {
        int total = 0;

        if (date >= Room.getMinDate() && date <= Room.getMaxDate()) {
            // Loops through the list of rooms and counts the number of booked rooms with the specified date.
            for (Room room : this.roomList) {
                if (room.getAvailability().get(date-1) == false)
                    total++;
            }
        }
        else {
            System.out.println("Invalid input.");
            total = -1;
        }

        return total;
    }

    /**
     * Gets the information on the room based on the given index from the list of rooms under the hotel object.
     * 
     * @param index the index of the room to be considered from the list of rooms.
     * @return the string room information and null if index input is invalid.
     */
    public String getRoomInfo(int index) {
        String roomInfo;
        Room room;
        ArrayList<Boolean> availability;
        int i;

        if (index >= 0 && index < this.roomList.size()) {
            room = this.roomList.get(index);

            roomInfo = "\nRoom Information : ";
            roomInfo += "\nRoom Name : " + room.getName();
            roomInfo += "\nPrice per Night : " + room.getPrice();
            roomInfo += "\nAvailability : ";

            availability = room.getAvailability();
            
            // Checks if there is an avaiable date.
            if (availability.size() > 0) {
                // Loops through the list of availability of the room and stores the available dates.
                for (i = 0; i < availability.size(); i++) {
                    if (availability.get(i))
                        roomInfo += (i+1) + ", ";
                }

                roomInfo += "\b\b ";
            }
            else 
                roomInfo += "None";
        }
        else {
            System.out.println("Invalid input.");
            roomInfo = null;
        }

        return roomInfo;
    }

    /**
     * Gets the information on the reservation based on the given index from the list of reservations under the hotel object.
     * 
     * @param index the index of the reservation to be considered from the list of reservations.
     * @return the string reservation information and null if index input is invalid.
     */
    public String getReservationInfo(int index) {
        String reservationInfo;
        Reservation reservation;
        int roomIndex;

        if (index >= 0 && index < this.reservationList.size()) {
            reservation = this.reservationList.get(index);

            reservationInfo = "\nGuest Information : " + reservation.getGuestName();
            reservationInfo += "\nCheck-In Date : " + reservation.getCheckInDate();
            reservationInfo += "\nCheck-Out Date : " + reservation.getCheckOutDate();
            reservationInfo += "\nTotal Price : " + reservation.getTotalPrice();
            reservationInfo += "\nPrice per Night : " + reservation.getNightPrice();

            roomIndex = getRoomIndex(reservation.getRoom().getName());
            reservationInfo += "\n" + getRoomInfo(roomIndex);
        }
        else {
            System.out.println("Invalid input.");
            reservationInfo = null;
        }

        return reservationInfo;
    }

    /**
     * Returns the minimum number of rooms value for the hotel class.
     * 
     * @return the minimum number of rooms value.
     */
    public static int getMinRoom() {
        return MIN_ROOM;
    }

    /**
     * Returns the maximum number of rooms value for the hotel class.
     * 
     * @return the maximum number of rooms value.
     */
    public static int getMaxRoom() {
        return MAX_ROOM;
    }

    /**
     * Returns the name of the hotel object.
     * 
     * @return the name of the hotel.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the sorted list of room object/s under the hotel object.
     * 
     * @return the arraylist of room object/s under the hotel.
     */
    public ArrayList<Room> getRoomList() {
        sortRoom();

        return this.roomList;
    }

    /**
     * Returns the list of reservation object/s under the hotel object.
     * 
     * @return the arraylist of reservation object/s under the hotel.
     */
    public ArrayList<Reservation> getReservationList() {
        return this.reservationList;
    }

    /**
     * Gets and returns the total estimated earnings of the hotel object based on its reservation list.
     * 
     * @return the total estimated earnings of the hotel.
     */
    public double getEarnings() {
        double earnings = 0;
        int size = this.reservationList.size();
        int i;
        
        for (i = 0; i < size; i++)
            earnings += this.reservationList.get(i).getTotalPrice();

        return earnings;
    }

    /**
     * Sets the name of the hotel object with the given name.
     * 
     * @param name the name to be used to replace the name of the hotel.
     * @pre name is unique from the other hotels in the system.
     */
    public void setName(String name) {
        this.name = name;
    }
}
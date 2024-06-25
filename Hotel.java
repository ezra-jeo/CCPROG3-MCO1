import java.util.ArrayList;
import java.util.Scanner;

public class Hotel {
    private static final int MIN_ROOM = 1;
    private static final int MAX_ROOM = 50;

    private String name;
    private ArrayList<Room> roomList;
    private ArrayList<Reservation> reservationList;

    // Constructors

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

    public Hotel(String name) {
        String roomName;
        Room room;

        this.name = name;
        this.roomList = new ArrayList<Room>();
        this.reservationList = new ArrayList<Reservation>();

        roomName = name.substring(0,1) + 1;
        room = new Room(roomName);
        this.roomList.add(room);
    }

    // Methods

    private void sortRoom() {
        int size = this.roomList.size();
        int minIndex;
        int minNum;
        int roomNum;
        int i, j;
        Room room;

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

    public boolean checkAvailability(Room room, int checkInDate, int checkOutDate) {
        boolean available = true;
        int i = checkInDate;

        while (i < checkOutDate - 1 && available) {
            if (room.getAvailability().get(i) == false)
                available = false;

            i++;
        }

        return available;
    }
    
    public int getRoomIndex(String name) {
        int size = this.roomList.size();
        int i = 0;
        boolean found = false;

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

    public int getReservationIndex(String guestName, int checkInDate, int checkOutDate, String roomName) {
        int size = this.reservationList.size();
        int i = 0;
        boolean found = false;
        Reservation reservation;

        while (i < size && !found) {
            reservation = this.reservationList.get(i);
            if (guestName.equals(reservation.getGuestName()) &&
                checkInDate == reservation.getCheckInDate() &&
                checkOutDate == reservation.getCheckOutDate() &&
                roomName.equals(reservation.getRoom().getName()))
                found = true;

            i++;
        }

        if (found)
            return --i;
        else
            return -1;
    }

    public boolean addRoom() {
        Scanner scanner = new Scanner(System.in);
        boolean res = true;
        boolean repeat = true;
        boolean roomExists = true;
        int roomNum = 1;
        int size = this.roomList.size();
        String roomName;
        String input;

        sortRoom();

        if (size < MAX_ROOM) {
            while (roomNum <= size && roomExists) {
                if (roomNum != Integer.parseInt(this.roomList.get(roomNum - 1).getName().substring(1)))
                    roomExists = false;
                
                roomNum++;
            }

            if (roomExists) 
                roomName = this.name.substring(0,1) + (size+1);
            else
                roomName = this.name.substring(0,1) + (--roomNum);
            
            do {
                System.out.println("Add room \"" + roomName + "\" to hotel \"" + this.name + "\" room list? (Yes/No) : ");
                input = scanner.nextLine();   

                if (input.equalsIgnoreCase("Yes")) {
                    Room room = new Room(roomName);
                    this.roomList.add(room);
                    System.out.println("Room \"" + roomName + "\" has been successfully added to hotel \"" + this.name + "\" room list!");
                    repeat = false;
                }
                else if (input.equalsIgnoreCase("No")) {
                    System.out.println("Going back to system menu...");
                    res = false;
                    repeat = false;
                }
                else
                    System.out.println("Invalid input!");
            } while (repeat);
        }
        else {
            System.out.println("\"" + this.name + "\" has reached full hotel room capacity!");
            res = false;
        }

        
        return res;
    }

    public boolean removeRoom(int index) {
        Scanner scanner = new Scanner(System.in);
        boolean res = true;
        boolean repeat = true;
        Room room;
        String input;
        int size = this.roomList.size();

        if (size > MIN_ROOM && index >= 0 && index < size && 
            checkAvailability(this.roomList.get(index), 1, 31)) {
            room = this.roomList.get(index);

            do {
                System.out.println("Remove room \"" + room.getName() + "\" from hotel \"" + this.name + "\" room list? (Yes/No) : ");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("Yes")) {
                    this.roomList.remove(index);
                    System.out.println("Room \"" + room.getName() + "\" has been successfully removed from hotel \"" + this.name + "\" room list!");
                    repeat = false;
                }
                else if (input.equalsIgnoreCase("No")) {
                    System.out.println("Going back to system menu...");
                    res = false;
                    repeat = false;
                }
                else {
                    System.out.println("Invalid input!");
                }
            } while (repeat);
        }
        else if (this.roomList.size() == MIN_ROOM) {
            System.out.println("Hotel \"" + this.name + "\" has only one existing room!");
            res = false; 
        }
        else if (!checkAvailability(this.roomList.get(index), 1, 31)) {
            System.out.println("\"" + this.roomList.get(index).getName() + "\" has an active reservation!");
            res = false;
        }
        else {
            System.out.println("Invalid input!");
            res = false;
        }

        
        return res;
    }

    public boolean updateRoomPrice(double price) {
        Scanner scanner = new Scanner(System.in);
        boolean res = true;
        boolean repeat = true;
        String input;
        boolean available = true;
        int size = this.roomList.size();
        int i = 0;

        while (i < size && available) {
            if (!checkAvailability(this.roomList.get(i), this.roomList.get(i).getMinDate(), this.roomList.get(i).getMaxDate()))
                available = false;
            i++;
        }

        if (price >= 100 && available) {
            do {
                System.out.println("Change room price to " + price + " for hotel \"" + this.name + "\"? (Yes/No) : ");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("Yes")) {
                    for (Room room : this.roomList)
                        room.setPrice(price);
                    System.out.println("Room price has been successfuly changed to " + price + " from hotel \"" + this.name + "\"!");
                    repeat = false;
                }
                else if (input.equalsIgnoreCase("No")) {
                    System.out.println("Going back to system menu...");
                    res = false;
                    repeat = false;
                }
                else {
                    System.out.println("Invalid input!");
                }
            } while (repeat);
        }
        else if (!available) {
            System.out.println("Hotel \"" + this.name + "\" has existing reservation/s!");
            res = false;
        }
        else {
            System.out.println("Invalid input!");
            res = false;
        }

        
        return res;
    }

// To make the calendar simpler, assume that we’re working with a single month with 31 days. No
// reservations can be made when the check-out is on the 1st of the month or when the check-in is
// on the 31st of the month. Bookings cannot be made outside of the defined period for the month

    public boolean addReservation(String guestName, int checkInDate, int checkOutDate) {
        Scanner scanner = new Scanner(System.in);
        boolean res = true;
        boolean repeat = true;
        boolean available = false;
        int i = 0;
        String input;
        Room room;

        if ((checkInDate >= 1 && checkInDate < 31) && (checkOutDate > 1 && checkOutDate <= 31)) {
            while (i < this.roomList.size() && !available) {
                if (checkAvailability(this.roomList.get(i), checkInDate, checkOutDate))
                    available = true;

                i++;
            }

            if (available) {
                room = this.roomList.get(i-1);

                do {
                    System.out.println("Confirm reservation of \"" + guestName + "\" in room \"" + room.getName() + "\" from " + checkInDate + " to " + checkOutDate + "? (Yes/No) : ");
                    input = scanner.nextLine();

                    if (input.equalsIgnoreCase("Yes")) {
                        room.setAvailability(checkInDate, checkOutDate, false);
                        Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate, room);
                        this.reservationList.add(reservation);
                        System.out.println("Reservation of \"" + guestName + "\" in room \"" + room.getName() + "\" from " + checkInDate + " to " + checkOutDate + " has been successfuly confirmed!");
                        repeat = false;
                    }
                    else if (input.equalsIgnoreCase("No")) {
                        System.out.println("Going back to system menu...");
                        res = false;
                        repeat = false;
                    }
                    else {
                        System.out.println("Invalid input!");
                    }
                } while (repeat);
            }
            else {
                System.out.println("There is no room available for " + checkInDate + " to " + checkOutDate + ".");
                res = false;
            }
        }
        else {
            System.out.println("Invalid input!");
            res = false;
        }

        
        return res;
    }

    public boolean removeReservation(int index) {
        Scanner scanner = new Scanner(System.in);
        boolean res = true;
        boolean repeat = true;
        String input;
        Reservation reservation;

        if (index >= 0 && index < this.reservationList.size()) {
            reservation = this.reservationList.get(index);
            
            do {
                System.out.println("Remove reservation of \"" + reservation.getGuestName() + "\" in room \"" + reservation.getRoom().getName() + "\" from " + reservation.getCheckInDate()+ " to " + reservation.getCheckOutDate() + "? (Yes/No) : ");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("Yes")) {
                    reservation.getRoom().setAvailability(reservation.getCheckInDate(), reservation.getCheckOutDate(), true);
                    this.reservationList.remove(index);
                    System.out.println("Reservation of \"" + reservation.getGuestName() + "\" in room \"" + reservation.getRoom().getName() + "\" from " + reservation.getCheckInDate()+ " to " + reservation.getCheckOutDate() + " has been successfuly removed!");
                    repeat = false;
                }
                else if (input.equalsIgnoreCase("No")) {
                    System.out.println("Going back to system menu...");
                    res = false;
                    repeat = false;
                }
                else {
                    System.out.println("Invalid input!");
                }
            } while (repeat);
        }
        else {
            System.out.println("Invalid input!");
            res = false;
        }

        
        return res;
    }

    public int getTotalAvailableRooms(int date) {
        int total = 0;

        if (date >= 1 && date <= 31) {
            for (Room room : this.roomList) {
                if (room.getAvailability().get(date - 1) == true)
                    total++;
            }
        }
        else {
            System.out.println("Invalid input!");
            total = -1;
        }

        return total;
    }

    public int getTotalBookedRooms(int date) {
        int total = 0;

        if (date >= 1 && date <= 31) {
            for (Room room : this.roomList) {
                if (room.getAvailability().get(date - 1) == false)
                    total++;
            }
        }
        else {
            System.out.println("Invalid input!");
            total = -1;
        }

        return total;
    }

    public String getRoomInfo(int index) {
        String roomInfo;
        Room room;
        ArrayList<Boolean> availability;
        int i;

        if (index >= 0 && index < this.roomList.size()) {
            room = this.roomList.get(index);

            roomInfo = "\nRoom Name : " + room.getName();
            roomInfo += "\nPrice per Night : " + room.getPrice();
            roomInfo += "\nAvailability : ";

            availability = room.getAvailability();

            for (i = 0; i < availability.size(); i++) {
                if (availability.get(i))
                    roomInfo += (i+1) + ", ";
            }
        }
        else {
            System.out.println("Invalid input!");
            roomInfo = null;
        }

        return roomInfo;
    }

    public String getReservationInfo(int index) {
        String reservationInfo;
        Reservation reservation;
        int roomIndex;

        if (index >= 0 && index < this.reservationList.size()) {
            reservation = this.reservationList.get(index);

            reservationInfo = "\nGuest Information : " + reservation.getGuestName();

            roomIndex = getRoomIndex(reservation.getRoom().getName());

            reservationInfo += getRoomInfo(roomIndex);
            reservationInfo += "\nCheck-In Date : " + reservation.getCheckInDate();
            reservationInfo += "\nCheck-Out Date : " + reservation.getCheckOutDate();
            reservationInfo += "\nTotal Price : " + reservation.getTotalPrice();
            reservationInfo += "\nPrice per Night : " + reservation.getNightPrice();
        }
        else {
            System.out.println("Invalid input!");
            reservationInfo = null;
        }

        return reservationInfo;
    }

    // Getters and Setters

    public int getMinRoom() {
        return MIN_ROOM;
    }

    public int getMaxRoom() {
        return MAX_ROOM;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Room> getRoomList() {
        sortRoom();

        return this.roomList;
    }

    public ArrayList<Reservation> getReservationList() {
        return this.reservationList;
    }

    public double getEarnings() {
        double earnings = 0;
        int size = this.reservationList.size();
        
        for (int i = 0; i < size; i++)
            earnings += this.reservationList.get(i).getTotalPrice();

        return earnings;
    }

    public void setName(String name) {
        this.name = name;
    }
}
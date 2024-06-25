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
        int i, j;
        int minIndex;
        int minNum;
        int roomNum;
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
        int i = checkInDate - 1;

        while (i < checkOutDate - 1 && available) {
            if (room.getAvailability().get(i) == false)
                available = false;

            i++;
        }

        return available;
    }

    public boolean checkAllRoomAvailability() {
        int size = this.roomList.size();
        int i = 0;
        boolean available = true;

        while (i < size && available) {
            if (!checkAvailability(this.roomList.get(i), Room.getMinDate(), Room.getMaxDate()))
                available = false;
            i++;
        }

        return available;
    }

    public void changeAllRoomName() {
        for (Room room : this.roomList) {
            room.setName(this.name.charAt(0));
        }
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

    public boolean addRoom(int numRoom) {
        Scanner scanner = new Scanner(System.in);
        boolean res = true;
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

        do {
            System.out.print("Add room/s ");

            for (String roomName : roomNameList)
                System.out.print("\"" + roomName + "\", ");

            System.out.print("\b\b to hotel \"" + this.name + "\" room list? (Yes/No) : ");
            input = scanner.nextLine();   

            if (input.equalsIgnoreCase("Yes")) {
                for (String roomName : roomNameList) {
                    room = new Room(roomName);
                    this.roomList.add(room);
                }
                System.out.println("Room/s has/have been successfully added to hotel \"" + this.name + "\" room list!");
                repeat = false;
            }
            else if (input.equalsIgnoreCase("No")) {
                System.out.println("Going back to system menu...");
                repeat = false;
            }
            else
                System.out.println("Invalid input!");
        } while (repeat);
       
        return res;
    }

    public boolean removeRoom(ArrayList<Integer> indexList) {
        Scanner scanner = new Scanner(System.in);
        boolean res = true;
        boolean repeat = true;
        boolean available = true;
        boolean valid = true;
        String input;
        int size = this.roomList.size();
        int i = 0;
        ArrayList<Room> roomList = new ArrayList<Room>();


        while (i < indexList.size() && valid) {
            if (!(indexList.get(i) >= 0 && indexList.get(i) < size))
                valid = false;

            i++;
        }

        if (valid) {
                i = 0;
                while (i < indexList.size() && available) {
                    if (!(checkAvailability(this.roomList.get(indexList.get(i)), Room.getMinDate(), Room.getMaxDate())))
                        available = false;

                    i++;
                }

                if (available) {
                    do {
                        System.out.print("Remove room/s ");

                        for (Integer index : indexList)
                            System.out.print("\"" + this.roomList.get(index).getName() + "\", ");
            
                        System.out.print("\b\b from hotel \"" + this.name + "\" room list? (Yes/No) : ");
                        input = scanner.nextLine();   

                        if (input.equalsIgnoreCase("Yes")) {
                            for (Integer index : indexList)
                                roomList.add(this.roomList.get(index));

                            this.roomList.removeAll(roomList);
                            System.out.println("Room/s has/have been successfully removed from hotel \"" + this.name + "\" room list!");
                            repeat = false;
                        }
                        else if (input.equalsIgnoreCase("No")) {
                            System.out.println("Going back to system menu...");
                            repeat = false;
                        }
                        else {
                            System.out.println("Invalid input!");
                        }
                    } while (repeat);
                }
                else {
                    System.out.println("Chosen room/s has/have an active reservation!");
                    res = false;
                }
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

        if (price >= 100) {
            do {
                System.out.print("Change room price to " + price + " for hotel \"" + this.name + "\"? (Yes/No) : ");
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("Yes")) {
                    for (Room room : this.roomList)
                        room.setPrice(price);
                    System.out.println("Room price has been successfuly changed to " + price + " for hotel \"" + this.name + "\"!");
                    repeat = false;
                }
                else if (input.equalsIgnoreCase("No")) {
                    System.out.println("Going back to system menu...");
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

    public boolean addReservation(String guestName, int checkInDate, int checkOutDate) {
        Scanner scanner = new Scanner(System.in);
        boolean res = true;
        boolean repeat = true;
        boolean available = false;
        int i = 0;
        String input;
        Room room;

        if ((checkInDate >= 1 && checkInDate < 31) && (checkOutDate > 1 && checkOutDate <= 31) && (checkInDate < checkOutDate)) {
            while (i < this.roomList.size() && !available) {
                if (checkAvailability(this.roomList.get(i), checkInDate, checkOutDate))
                    available = true;

                i++;
            }

            if (available) {
                room = this.roomList.get(i-1);

                do {
                    System.out.print("Confirm reservation of \"" + guestName + "\" in room \"" + room.getName() + "\" from " + checkInDate + " to " + checkOutDate + "? (Yes/No) : ");
                    input = scanner.nextLine();

                    if (input.equalsIgnoreCase("Yes")) {
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

        if (date >= Room.getMinDate() && date < Room.getMaxDate()) {
            for (Room room : this.roomList) {
                if (room.getAvailability().get(date-1) == true)
                    total++;
            }
        }
        else if (date == Room.getMaxDate()) {
            System.out.println("No rooms are available for checking-in on the date : 31");
            total = -1;
        }
        else {
            System.out.println("Invalid input!");
            total = -1;
        }

        return total;
    }

    public int getTotalBookedRooms(int date) {
        int total = 0;

        if (date >= Room.getMinDate() && date <= Room.getMaxDate()) {
            for (Room room : this.roomList) {
                if (room.getAvailability().get(date-1) == false)
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

            if (availability.size() > 0) {
                for (i = 0; i < availability.size(); i++) {
                    if (availability.get(i))
                        roomInfo += (i+1) + ", ";
                }

                roomInfo += "\b\b ";
            }
            else 
                System.out.println("None");
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

    public static int getMinRoom() {
        return MIN_ROOM;
    }

    public static int getMaxRoom() {
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
        int i;
        
        for (i = 0; i < size; i++)
            earnings += this.reservationList.get(i).getTotalPrice();

        return earnings;
    }

    public void setName(String name) {
        this.name = name;
    }
}
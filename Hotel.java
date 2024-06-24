import java.util.ArrayList;
import java.util.Scanner;

public class Hotel {
    private final int MAX_ROOM = 50;
    private final int MIN_ROOM = 1;

    private String name;
    private ArrayList<Room> roomList;
    private ArrayList<Reservation> reservationList;

    // Constructors

    public Hotel(String name, int numRoom) {
        this.name = name;
        this.roomList = new ArrayList<Room>();
        this.reservationList = new ArrayList<Reservation>();

        for (int i = 1; i <= numRoom; i++) {
            String roomName = name.substring(0,1) + i;
            Room room = new Room(roomName);
            this.roomList.add(room);
        }
    }

    public Hotel(String name) {
        this.name = name;
        this.roomList = new ArrayList<Room>();
        this.reservationList = new ArrayList<Reservation>();

        String roomName = name.substring(0,1) + 1;
        Room room = new Room(roomName);
        this.roomList.add(room);
    }

    // Methods

    private void sortRoom() {
        int size = this.roomList.size();
        int minIndex;
        int minNum;
        int roomNum;

        for (int i = 0; i < size - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < size; j++) {
                minNum = Integer.parseInt(this.roomList.get(minIndex).getName().substring(1));
                roomNum = Integer.parseInt(this.roomList.get(i).getName().substring(1));

                if (minNum > roomNum)
                    minIndex = i;
            }

            if (minIndex != i) {
                Room room = this.roomList.remove(minIndex);
                this.roomList.add(i, room);
            }
        } 
    }
    
    public boolean addRoom() {
        boolean res = true;

        sortRoom();

        if (this.roomList.size() < MAX_ROOM) {
            int roomNum = 1;
            boolean roomExists = true;
            String roomName;
            Scanner scanner = new Scanner(System.in);
            String input;
            boolean repeat = true;

            while (roomExists) {
                if (roomNum == Integer.parseInt(this.roomList.get(roomNum - 1).getName().substring(1)))
                    roomExists = false;
                
                roomNum++;
            }

            roomName = this.name.substring(0,1) + (--roomNum);
            
            do {
                System.out.println(repeat);
                System.out.println("Add Room " + roomName + " to hotel " + this.name + " room list? (Yes/No) : ");
                input = scanner.nextLine();   

                if (input.equalsIgnoreCase("Yes")) {
                    Room room = new Room(roomName);
                    this.roomList.add(room);
                    System.out.println("Room " + roomName + " has been successfully added to hotel " + this.name + " room list!");
                    repeat = false;
                    System.out.println(repeat);
                }
                else if (input.equalsIgnoreCase("No")) {
                    System.out.println("Going back to menu...");
                    res = false;
                    repeat = false;
                    System.out.println(repeat);
                }
                else {
                    System.out.println("Invalid input!");
                }
            } while (false);

            scanner.close();
        }
        else {
            System.out.println(this.name + " has reached full hotel room capacity!");
            res = false;
        }
        return res;
    }

    // Getters and Setters

    public int getMaxRoom() {
        return this.MAX_ROOM;
    }

    public int getMinRoom() {
        return this.MIN_ROOM;
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
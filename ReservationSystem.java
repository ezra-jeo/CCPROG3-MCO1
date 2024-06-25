import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReservationSystem {
    // Attributes
    private ArrayList<Hotel> hotelList;
    Scanner sc = new Scanner(System.in);

    // Constructors
    public ReservationSystem() {
        this.hotelList = new ArrayList<Hotel>();
    }

    // Methods
    public void createHotel(String name) {  

        String verify;

        System.out.print("Create hotel \"" + name + "\"? (Yes/No) : ");
        verify = sc.nextLine();

        if (verify.equalsIgnoreCase("Yes")) {
            Hotel hotel = new Hotel(name);
            this.hotelList.add(hotel);
        }
    }

    public void viewHotel(int index) {
        String roomName;
        String choiceInfo;
        String input;
        String roomInfo;
        String reservationInfo;
        int reservationIndex = -1;
        int date;
        int totalAvailable;
        int totalBooked;
        boolean error = false;
        Hotel hotel = hotelList.get(index);
        
        System.out.println("High Level Information: ");
        System.out.println("Hotel : " + hotel.getName());
        System.out.println("No. of Rooms : " + hotel.getRoomList().size());
        System.out.println("Estimated Earnings : " + hotel.getEarnings());
        
        // Low level information prompt
            
        System.out.println("Low Level Information:\n[1] Total Available and Booked Rooms\n[2] Room Information\n[3] Reservation Information\n[Quit] Exit View");
        System.out.print("Enter the number of the information to display : ");

        choiceInfo = sc.nextLine();

        if (choiceInfo.equals("1")) {
            do {    
                error = false;
                try {
                    System.out.print("\nEnter the date to see available and booked rooms (Quit to exit): ");
                    input = sc.nextLine();
    
                    if (!input.equalsIgnoreCase("Quit")) {      
                        date = Integer.parseInt(input);
                        totalAvailable = hotel.getTotalAvailableRooms(date);
                        totalBooked = hotel.getTotalBookedRooms(date);
    
                        if (totalAvailable != -1 && totalBooked != -1) {
                            System.out.println("Available Rooms : " + totalAvailable);
                            System.out.println("Booked Rooms : " + totalBooked);
                        }
                        else 
                            throw new IndexOutOfBoundsException();
                    }
                    
                }
                catch (NumberFormatException exception) {
                    System.out.println("Invalid Input. Enter a new input.");
                    error = true;
                }
                catch (IndexOutOfBoundsException exception) {
                    error = true;
                }
            
            } while (error) ;
    
        }

        // Rooms

        // Display
        else if (choiceInfo.equals("2")) {
            System.out.println("\nList of Rooms in \"" + hotel.getName() + "\" : ");

            for (Room room : hotel.getRoomList()) {
                System.out.println(room.getName());     
            }
    
            // Input
            do {
                error = false;
                try {
                    
                    System.out.print("\nEnter the room to get room information (Quit to exit): ");
                    roomName = sc.nextLine();
                    if ( !roomName.equalsIgnoreCase("Quit")) {
                        roomInfo = hotel.getRoomInfo(hotel.getRoomIndex(roomName));
                        if (roomInfo != null)
                            System.out.println(roomInfo);
                        else
                            throw new IndexOutOfBoundsException();
                        
                    }                    
                }
                catch (IndexOutOfBoundsException exception) {
                    error = true;
                }
            } while (error);
    
        }
        // Reservation
        else if (choiceInfo.equals("3")) {
            if (hotel.getReservationList().size() > 0) {
                // Display
                System.out.println("\nList of Rooms in " + hotel.getName() + " with reservations : ");
    
                for (int i = 0; i < hotel.getReservationList().size(); i++) {
                    System.out.println("["+(i+1)+"] " + hotel.getReservationList().get(i).getRoom().getName() + " : " + hotel.getReservationList().get(i).getGuestName() + "\nCheck In: " +
                       hotel.getReservationList().get(i).getCheckInDate() + "\nCheck Out: " + hotel.getReservationList().get(i).getCheckOutDate());
                }
                
                // Input
                do {
                    error = false;
                    try {
                    
                        System.out.print("\nEnter the room to get reservation information (Quit to exit): ");
                        input = sc.nextLine();
                        
                        if (!input.equalsIgnoreCase("Quit")) {
                            reservationIndex = Integer.parseInt(input)-1;
                            reservationInfo = hotel.getReservationInfo(reservationIndex);
    
                            if (reservationInfo != null) {
                                System.out.println(reservationInfo);
                            }
                            else {
                                throw new IndexOutOfBoundsException();
                            }
                        }
                    }
                    catch (NumberFormatException exception) {
                        System.out.println("Invalid Integer Input. Enter New Input.");
                        error = true;
                    }
                    catch (IndexOutOfBoundsException exception) {
                        error = true;
                    }
                } while (error);
            }
        }

    }

    public void renameHotel(int index) {
        // Assume index is valid.
        
        String verify;
        String newName;
        boolean invalid = false;
        Hotel hotel = this.hotelList.get(index);

        System.out.print("\nEnter the new name for hotel \"" + hotel.getName() + "\" : ");
        newName = sc.nextLine();

        if (isExisting(newName) == -1) {
            do {
                invalid = false;
                System.out.print("Rename hotel \"" + hotel.getName() + "\" to \"" + newName + "\"? (Yes/No) : ");
                verify = sc.nextLine();

                if (!verify.equalsIgnoreCase("Yes") && !verify.equalsIgnoreCase("No")) {
                    invalid = true;
                    System.out.println("Invalid Input. Enter a new Input.");
                }
            } while (invalid);

            if (verify.equalsIgnoreCase("Yes")) {
                this.hotelList.get(index).setName(newName);
                //this.hotelList.get(index).changeAllRoomName();
            }
        }

    }

    public void removeHotel(int index) {
        // Assume index is valid.

        String verify;
        boolean invalid = false;
        Hotel hotel = this.hotelList.get(index);


        do {
            invalid = false;
            System.out.print("Delete hotel \"" + hotel.getName() + "\"? (Yes/No) : ");
            verify = sc.nextLine();

            if (!verify.equalsIgnoreCase("Yes") && !verify.equalsIgnoreCase("No")) {
                System.out.println("Invalid Input. Enter a new Input.");
                invalid = true;
            }
        } while (invalid);


        if (verify.equalsIgnoreCase("Yes")) {
            this.hotelList.remove(index);
        }
    }

    public void manageHotel(int index) {
        // Assume index is valid.

        String choice;
        boolean quit = false;
        Hotel hotel = this.hotelList.get(index);
        
        do {
            System.out.println("\nManage Hotel: \n");
            System.out.println("Options: ");
            System.out.println("[1] Rename Hotel\n[2] Add Room\n[3] Remove Room/s\n[4] Update base price\n[5] Remove Reservation\n[6] Remove Hotel\n[Quit] to exit");
            System.out.print("\nEnter the command to run: ");
    
            choice = sc.nextLine();

            if (choice.equals("1")) {
                renameHotel(index);
            }

            // accommodate multiinput.
            else if (choice.equals("2")) {
                String input;
                int roomNum = 1;
                boolean error = false;
                boolean result = false;

                if (hotel.getRoomList().size() < 50) {
                    do {
                        error = false;
                        try {
                            System.out.print("Enter the amount of rooms to add (Quit to exit): ");
                            input = sc.nextLine();
                            
                            if (!input.equalsIgnoreCase("Quit")) {
                                roomNum = Integer.parseInt(input);
                                if (roomNum + hotel.getRoomList().size() < 50) {
                                    result = hotel.addRoom(roomNum);
                                    if (result == false) {
                                        throw new IndexOutOfBoundsException();
                                    }
                                }

                            }
                        }   
                        catch (InputMismatchException exception) {
                            System.out.println("Invalid Integer Input. Enter New Input");
                            error = true;
                        }
                        catch (IndexOutOfBoundsException exception) {
                            error = true;
                        }
                    } while (error);
                }
            }
            else if (choice.equals("3")) {
                String roomName;
                String repeatChoice;
                boolean result = false;
                boolean error = false;
                boolean repeat = false;
                ArrayList<Room> roomList = hotel.getRoomList();
                ArrayList<Integer> roomToRemove = new ArrayList<Integer>();

                if (roomList.size() > 1) {
                    // Display 
                    System.out.println("Rooms in " + hotel.getName() + ":");
        
                    for (Room room : roomList) {
                        System.out.println(room.getName());
                    }
        
                    // Input
                    do {
                        error = false;
                        try {
                            do {
                                repeat = false;
                                System.out.print("\nEnter the room/s to remove (Quit to exit): ");
                                roomName = sc.nextLine();
                
                                if (!roomName.equalsIgnoreCase("Quit")) {
                                    roomToRemove.add(hotel.getRoomIndex(roomName));
                                    do {
                                        System.out.print("\nEnter the room/s to remove (Yes/No): ");
                                        repeatChoice = sc.nextLine();
                                    } while (!repeatChoice.equalsIgnoreCase("Yes") && !repeatChoice.equalsIgnoreCase("No"));
    
                                    if (repeatChoice.equalsIgnoreCase("Yes"))
                                        repeat = true;
                                }    
                            } while (repeat && hotel.getRoomList().size() - roomToRemove.size() > 0);
                            
                            if (!roomName.equalsIgnoreCase("Quit")) {
                                if (hotel.getRoomList().size() - roomToRemove.size() > 0) {
                                    result = hotel.removeRoom(roomToRemove);
                                    if (result == false)
                                        throw new IndexOutOfBoundsException();
                                }
                                else
                                    System.out.println("Rooms to remove exceeds the limit.");
                            }
                        }
                        catch (IndexOutOfBoundsException exception) {
                            error = true;
                        }
                    } while (error);
                }
                else
                {
                    System.out.println("Hotel has only 1 room. It can not be removed.");
                }
            }
            else if (choice.equals("4")) {
                String input;
                boolean result;
                boolean error;
                double roomPrice = 0;
                
                // Get price input.
                if (!hotel.checkAllRoomAvailability()) {
                    do {
                        error = false;
                        try {
                            System.out.print("\nEnter the new price (Quit to exit): ");
                            input = sc.nextLine();

                            if (!input.equalsIgnoreCase("Quit")) {
                                roomPrice = Double.parseDouble(input);
                                result = hotel.updateRoomPrice(roomPrice);

                                if (result == false) {
                                    throw new InputMismatchException();
                                }
                            }
                                
                        }
                        catch (NumberFormatException exception) {
                            System.out.println("Invalid Double Input. Enter a new Input.");
                            error = true;
                        }
                        catch (InputMismatchException exception) {
                            error = true;
                        }

                    } while (error);
                }
                else
                    System.out.println("Hotel \"" + hotel.getName() + "\" has existing reservation/s!");
            }
            else if (choice.equals("5")) {
                String input;
                boolean result;
                boolean error;
                int reservationIndex = -1;
                if (hotel.getReservationList().size() > 0) {
                    System.out.println("\nList of Rooms in " + hotel.getName() + " with reservations : ");
                    for (int i = 0; i < hotel.getReservationList().size(); i++) {
                        System.out.println("["+i+1+"] " + hotel.getReservationList().get(i).getRoom().getName() + " : " + hotel.getReservationList().get(i).getGuestName() + "\nCheck In: " +
                        hotel.getReservationList().get(i).getCheckInDate() + "\nCheck Out: " + hotel.getReservationList().get(i).getCheckOutDate());
                    }     
                        
                    do {
                        error = false;
                        try {   
                            System.out.print("\nEnter the number of the reservation to remove : ");
                            input = sc.nextLine();
                            
                            if (!input.equalsIgnoreCase("Quit")) {
                                reservationIndex = Integer.parseInt(input)-1; 
                                result = hotel.removeReservation(reservationIndex);
                                if (result == false)
                                    throw new IndexOutOfBoundsException();
                            }
                        }
                        catch (IndexOutOfBoundsException exception) {
                            error = true;
                        }
    
                    } while (error);
                }
            }
            else if (choice.equals("6")) {
                removeHotel(index);
            }
            else if (choice.equalsIgnoreCase("Quit")) {
                quit = true;
            }

        } while (!quit);

    }

    public void simulateBooking(int index) {
        Hotel hotel = this.hotelList.get(index);
        String input = "";
        String guestName;
        boolean error;
        boolean result = false;
        int checkInDate = -1;
        int checkOutDate = -1;
        
        System.out.print("\nEnter your name : ");
        guestName = sc.nextLine();

        do {

            do { 
                error = false;

                try {
                    System.out.print("\nEnter your check in date (Quit to exit): ");
                    input = sc.nextLine();
                    if (!input.equalsIgnoreCase("Quit")) {
                        checkInDate = Integer.parseInt(input);
                    }
                }
                catch (NumberFormatException exception) {
                    System.out.println("Invalid Integer Input. Enter New Input.");
                    error = true;
                }
            } while (error);

            if (!input.equalsIgnoreCase("Quit")) {
                do {
                    error = false;
        
                    try {
                        System.out.print("\nEnter your check out date (Quit to exit): ");
                        input = sc.nextLine();
                        if (!input.equalsIgnoreCase("Quit")) {
                            checkOutDate = Integer.parseInt(input);
                        }
                    }
                    catch (NumberFormatException exception) {
                        System.out.println("Invalid Integer Input. Enter New Input.");
                        error = true;
                    }
                } while (error);
            }

            if (!input.equalsIgnoreCase("Quit")) {
                result = hotel.addReservation(guestName, checkInDate, checkOutDate);
            }
        
        } while (result == false && !input.equalsIgnoreCase("Quit"));
    }

    public int isExisting(String name) {
        
        int result = -1;

        for (int i = 0; i < this.hotelList.size() && result == -1; i++) {
            if (this.hotelList.get(i).getName().equals(name))
                result = i;
        }

        return result;
    }

    public int isExisting(int index) {
        
        int result = -1;

        if (index < this.hotelList.size())
            result = index;

        return result;
    }

    public void runSystem() {
        String choice;

        do {

            System.out.println("\n----Hotel Reservation System----");
            System.out.println("Processes: "); 
            System.out.println("[1] Create Hotel\n[2] View Hotel\n[3] Manage Hotel\n[4] Simulate Booking\n[Quit] Exit Reservation System");
            System.out.print("\nEnter the number of the process to run : ");
    
            choice = sc.nextLine();
    
            if (choice.equals("1")) {
                
                String hotelName;
                int hotelIndex = -1;
                
                do {    
                    System.out.print("\nEnter a hotel name : ");
                    hotelName = sc.nextLine();
    
                    if (!hotelName.equalsIgnoreCase("Quit")) {
                        hotelIndex = isExisting(hotelName);
                        
                        if (hotelIndex == -1)
                            createHotel(hotelName);
                        else
                            System.out.println("Hotel " + hotelName + " already exists. Enter New Input.");

                    }
                } while (hotelIndex != -1);
            }
            else if (choice.equals("2") || choice.equals("3") || choice.equals("4")) {
                if (this.hotelList.size() > 0) {
                    String hotelName;
                    int hotelIndex = -1;
    
                    System.out.println("\nList of Hotels: ");
                    for (int i = 0; i < this.hotelList.size(); i++) {
                        System.out.println(this.hotelList.get(i).getName());
                    }
                    
                    do {    
                        System.out.print("\nEnter a hotel name : ");
                        hotelName = sc.nextLine();
        
                        if (!hotelName.equalsIgnoreCase("Quit")) {
                            hotelIndex = isExisting(hotelName);
                            
                            if (hotelIndex != -1) {
                                if (choice.equals("2")) 
                                    viewHotel(hotelIndex);
                                else if (choice.equals("3"))
                                    manageHotel(hotelIndex);
                                else if (choice.equals("4"))
                                    simulateBooking(hotelIndex);
                            }
                            else
                                System.out.println("Hotel " + hotelName + " does not exists. Enter New Input.");
    
                        }
                    } while (hotelIndex == -1);
                }
                else 
                    System.out.println("There are no hotels in the system. Create hotels.");
            }
        } while (!choice.equalsIgnoreCase("Quit"));
    }

    public ArrayList<Hotel> getHotelList() {
        return this.hotelList;
    }

    public ArrayList<String> getHotelNames() {
        ArrayList<String> hotelNames = new ArrayList<String>();
        
        for (Hotel hotel : hotelList) {
            hotelNames.add(hotel.getName());
        }

        return hotelNames;
    }

}
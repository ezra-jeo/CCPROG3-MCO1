import java.util.ArrayList;
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
        // else if (verify.equalsIgnoreCase("No")) {
        //     System.out.println("Quit");
        // }
    }

    public void viewHotel(int index) {
        String roomName;
        int reservationIndex = -1;
        int date;
        Hotel hotel = hotelList.get(index);

        System.out.println("Hotel : " + hotel.getName());
        System.out.println("No. of Rooms : " + hotel.getRoomList().size());
        System.out.println("Estimated Earnings : " + hotel.getEarnings());
        
        do {
            System.out.print("\nEnter the date to see available and booked rooms : ");
            date = sc.nextInt();
            sc.nextLine();

            if (date < 1 && date > 31)
                System.out.println("Invalid date input. Enter new input.");

        } while (date < 1 && date > 31);

        
        System.out.println("Available Rooms : " + hotel.getTotalAvailableRooms(date));
        System.out.println("Booked Rooms : " + hotel.getTotalBookedRooms(date));
        
        // Rooms
        System.out.println("\nList of Rooms in " + hotel.getName() + " : ");
        for (Room room : hotel.getRoomList()) {
            System.out.println(room.getName());     
        }

        do {
            System.out.print("\nEnter the room to get room information (Quit to Exit): ");
            roomName = sc.nextLine();

            if (hotel.getRoomIndex(roomName) == -1) 
                System.out.println("The room entered is not in hotel \"" + hotel.getName() + "\".\nEnter a new input.");
            

        } while (hotel.getRoomIndex(roomName) == -1 && !roomName.equalsIgnoreCase("Quit"));
        
        System.out.println(hotel.getRoomInfo(hotel.getRoomIndex(roomName)));

        // Reservation
        if (hotel.getReservationList().size() > 0) {
            System.out.println("\nList of Rooms in " + hotel.getName() + " with reservations : ");

            for (int i = 0; i < hotel.getReservationList().size(); i++) {
                System.out.println(i+1 + hotel.getReservationList().get(i).getRoom().getName() + " : " + hotel.getReservationList().get(i).getGuestName() + "\nCheck In: " +
                   hotel.getReservationList().get(i).getCheckInDate() + "\nCheck Out: " + hotel.getReservationList().get(i).getCheckOutDate());
            }
    
            try {
                String input;
                do {
                    System.out.print("\nEnter the room to get reservation information (Quit to Exit): ");
                    input= sc.nextLine();
                    
                    if (!input.equalsIgnoreCase("Quit")) {
                        reservationIndex = Integer.parseInt(input);
    
                        if (reservationIndex-1 > hotel.getReservationList().size() && reservationIndex > -1) {
                            System.out.println("The room Entered is not reserved in hotel \"" + hotel.getName() + "\".\nEnter a new input.");
                        }
                    }
                } while (reservationIndex-1 > hotel.getReservationList().size() && reservationIndex > -1);
                
                System.out.println(hotel.getReservationInfo(reservationIndex-1));
            }
            catch (NumberFormatException exception) {
                System.out.println("Invalid integer input. Enter new input.");
    
            }
    
        }

    }

    public void renameHotel(int index) {
        // Assume index is valid.
        
        String verify;
        String newName;
        Hotel hotel = this.hotelList.get(index);

        System.out.print("\nEnter the new name for hotel \"" + hotel.getName() + "\" : ");
        newName = sc.nextLine();

        if (isExisting(newName) == -1) {
            System.out.print("Rename hotel \"" + hotel.getName() + "\" to \"" + newName + "\"? (Yes/No) : ");
            verify = sc.nextLine();
            
            if (verify.equalsIgnoreCase("Yes")) {
                this.hotelList.get(index).setName(newName);
            }
        }

    }

    public void removeHotel(int index) {
        // Assume index is valid.

        String verify;
        Hotel hotel = this.hotelList.get(index);

        System.out.print("Delete hotel \"" + hotel.getName() + "\"? (Yes/No)");
        verify = sc.nextLine();

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
            System.out.println("Manage Hotel: \n");
            System.out.println("Options: ");
            System.out.println("[1] Rename Hotel\n[2] Add Room\n[3] Remove Room/s\n[4] Update base price\n[5] Remove Reservation\n[6] Remove Hotel\n[Quit] to exit");
            System.out.print("\nEnter the command to run: ");
    
            choice = sc.nextLine();

            if (choice.equals("1")) {
                renameHotel(index);
            }
            else if (choice.equals("2")) {
                hotel.addRoom();
            }
            else if (choice.equals("3")) {
                String roomName;
                String verify;
                ArrayList<Room> roomList = hotel.getRoomList();
                boolean res = true;
                
                // Display the rooms
                System.out.println("Rooms in " + hotel.getName() + ":");
    
                for (Room room : roomList) {
                    System.out.println(room.getName());
                }

                if (roomList.size() > 1) {
                    do {
                        System.out.println("\nEnter the room to remove (Quit to exit): ");
                        roomName = sc.nextLine();

                        if (!roomName.equalsIgnoreCase("Quit"))
                            res = hotel.removeRoom(hotel.getRoomIndex(roomName));
                        
                    } while (res == false && !roomName.equalsIgnoreCase("Quit"));
                }
                else {
                    System.out.println("Hotel has only one room.");
                    System.out.println("Returning to system menu.");
                }
                // Ask room input
                // do {
                //     System.out.println("\nEnter the room to remove (Quit to exit): ");
                //     roomName = sc.nextLine();
    
                //     if (hotel.getRoomIndex(roomName) == -1)
                //         System.out.println("The room entered is not in hotel " + hotel.getName() + ".\nEnter a new input.");

                // } while (hotel.getRoomIndex(roomName) == -1 && !roomName.equalsIgnoreCase("Quit"));
                
                // Run removeRoom
                // if (!roomName.equalsIgnoreCase("Quit")) {
                //     System.out.print("Remove room \"" + roomName + "\"?");
                //     verify = sc.nextLine();

                //     if (verify.equalsIgnoreCase("Yes"))
                //         hotel.removeRoom(hotel.getRoomIndex(roomName));
                // }
            }
            else if (choice.equals("4")) {
                try{
                    String input;
                    String verify;
                    double roomPrice = 0;
                    
                    // Get price input.
                    do {
                        System.out.print("\nEnter the new price (Quit to exit): ");
                        input = sc.nextLine();

                        if (!input.equalsIgnoreCase("Quit"))
                            roomPrice = Double.parseDouble(input);

                        if (roomPrice < 100.0)
                            System.out.println("Insufficient price. Enter new input.");

                    } while (roomPrice < 100.0);
                    
                    if (!input.equalsIgnoreCase("Quit")) {
                        System.out.print("Update room price to \"" + roomPrice + "\"?");
                        verify = sc.nextLine();
    
                        if (verify.equalsIgnoreCase("Yes"))
                            hotel.updateRoomPrice(roomPrice);
                    }
                        
                }
                catch (NumberFormatException exception) {
                    System.out.println("Invalid float input. Enter new input.");
                }
            }
            else if (choice.equals("5")) {
                int reservationIndex = -1;
                String verify;

                System.out.println("List of Rooms in " + hotel.getName() + " with reservations : ");
                for (int i = 0; i < hotel.getReservationList().size(); i++) {
                    System.out.println(i+1 + " . " + hotel.getReservationList().get(i).getRoom().getName() + " : " + hotel.getReservationList().get(i).getGuestName() + "\nCheck In: " +
                    hotel.getReservationList().get(i).getCheckInDate() + "\nCheck Out: " + hotel.getReservationList().get(i).getCheckOutDate());
                }
                try {
                    String input;
                    
                    do {
                        System.out.print("\nEnter the number of the reservation to remove : ");
                        input = sc.nextLine();

                        if (!input.equalsIgnoreCase("Quit")) {
                            reservationIndex = Integer.parseInt(input);

                            if (reservationIndex-1 > hotel.getReservationList().size() && reservationIndex > -1)
                                System.out.println("The room entered is not reserved in hotel \"" + hotel.getName() + "\".\nEnter a new input.");
                        }

                    } while (reservationIndex-1 > hotel.getReservationList().size() && reservationIndex > -1);

                }
                catch (NumberFormatException exception) {
                    System.out.println("Invalid integer input. Enter new input.");
                }

                if (reservationIndex < hotel.getReservationList().size() && reservationIndex > -1) {
                    System.out.print("Remove reservation #" + reservationIndex + "? (Yes/No) : ");
                    verify = sc.nextLine();

                    if (verify.equalsIgnoreCase("Yes"))
                        hotel.removeReservation(reservationIndex);

                }
            }
            else if (choice.equals("6")) {
                String verify;
                
                System.out.print("Remove hotel \"" + this.hotelList.get(index).getName() + "\"? (Yes/No) : ");
                verify = sc.nextLine();

                if (verify.equalsIgnoreCase("Yes")) {
                    removeHotel(index); 
                }
            }
            else if (choice.equalsIgnoreCase("Quit")) {
                quit = true;
            }

        } while (!quit);

    }

    public void simulateBooking(int index) {
        Hotel hotel = this.hotelList.get(index);
        String guestName;
        int checkInDate;
        int checkOutDate;

        System.out.print("\nEnter your name : ");
        guestName = sc.nextLine();
    
        do {
            System.out.print("\nEnter your check in date : ");
            checkInDate = sc.nextInt();
            sc.nextLine();

            System.out.print("\nEnter your check out date : ");
            checkOutDate = sc.nextInt();
            sc.nextLine();


        } while (!hotel.addReservation(guestName, checkInDate, checkOutDate));
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
            System.out.println("[1] Create Hotel\n[2] View Hotel\n[3] Manage Hotel\n[4] Simulate Booking\n[5] Quit");
            System.out.print("\nEnter the number of the process to run : ");
    
            choice = sc.nextLine();
    
            if (choice.equals("1")) {
                
                String hotelName;
                int hotelIndex;
                
                do {    
                    System.out.print("\nEnter a hotel name : ");
                    hotelName = sc.nextLine();
                    hotelIndex = isExisting(hotelName);
    
                    if (hotelIndex != -1 && !hotelName.equalsIgnoreCase("Quit")) {
                        System.out.println("Hotel " + hotelName + " already exists. Enter new input.");
                    }
                } while (hotelIndex != -1 && !hotelName.equalsIgnoreCase("Quit"));
    
                if (!hotelName.equalsIgnoreCase("Quit")) {
                    createHotel(hotelName);
                }
            }
            else if (choice.equals("2") || choice.equals("3") || choice.equals("4")) {
                String hotelName;
                int hotelIndex;

                System.out.println("List of Hotels: ");
                for (int i = 0; i < this.hotelList.size(); i++) {
                    System.out.println(this.hotelList.get(i).getName());
                }
                
                do {    
                    System.out.print("\nEnter a hotel name : ");
                    hotelName = sc.nextLine();
                    hotelIndex = isExisting(hotelName);
    
                    if (hotelIndex == -1 && !hotelName.equalsIgnoreCase("Quit")) {
                        System.out.println("Hotel " + hotelName + " does not exists. Enter new input.");
                    }
                } while (hotelIndex == -1 && !hotelName.equalsIgnoreCase("Quit"));
    
                if (!hotelName.equalsIgnoreCase("Quit")) {
                    if (choice.equals("2")) {
                        viewHotel(hotelIndex);
                    }
                    else if (choice.equals("3"))
                        manageHotel(hotelIndex);
                    else if (choice.equals("4"))
                        simulateBooking(hotelIndex);
                }
            }

        } while (!choice.equals("5"));
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
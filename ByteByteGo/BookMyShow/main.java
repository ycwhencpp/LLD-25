
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class main {

    public static void main(String[] args) throws InterruptedException {
        // 1. Setup Environment (Independent Rooms to avoid reference bugs)
        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(new Cinema("anurag-1", createRooms(1, 10))); 

        BookingService bookingService = new BookingService();
        BMS bms = new BMS(cinemas, bookingService);

        Cinema targetCinema = bms.searchByName("anurag-1");
        Room targetRoom = targetCinema.getRooms().get(0); 
        Show show1 = new Show(new Movie(), targetRoom, 1800); 

        List<ShowSeat> allSeats = show1.gShowSeats();

        // 2. Setup the Clash Scenario
        // User A wants Seat 0 and Seat 1
        List<ShowSeat> userA_Seats = Arrays.asList(allSeats.get(0), allSeats.get(1));
        
        // User B wants Seat 1 and Seat 2 (Seat 1 is the conflict!)
        List<ShowSeat> userB_Seats = Arrays.asList(allSeats.get(1), allSeats.get(2));

        // CountDownLatch(1) acts like a starting gun for a race.
        CountDownLatch startingGun = new CountDownLatch(1);

        // 3. Create User A's Thread
        Thread threadA = new Thread(() -> {
            try {
                startingGun.await(); // Wait for the gun to fire
                System.out.println("User A trying to book...");
                Ticket ticket = bms.bookTicket(targetCinema, show1, userA_Seats);
                if (ticket != null) {
                    System.out.println("✅ User A got the tickets!");
                } else {
                    System.out.println("❌ User A failed (Rollback executed).");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 4. Create User B's Thread
        Thread threadB = new Thread(() -> {
            try {
                startingGun.await(); // Wait for the gun to fire
                System.out.println("User B trying to book...");
                Ticket ticket = bms.bookTicket(targetCinema, show1, userB_Seats);
                if (ticket != null) {
                    System.out.println("✅ User B got the tickets!");
                } else {
                    System.out.println("❌ User B failed (Rollback executed).");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 5. Start the threads (they will pause at the await() line)
        threadB.start();

        threadA.start();

        System.out.println("Threads ready. Firing starting gun in 3... 2... 1...");
        Thread.sleep(1000); 

        // 6. Fire the starting gun! Both threads hit the ReentrantLock simultaneously.
        startingGun.countDown(); 

        // Wait for both threads to finish before exiting
        threadA.join();
        threadB.join();

        // 7. Verify Final State of Seat 1
       System.out.println("--- Final System State ---");
        System.out.println("Seat 0 Booked? " + allSeats.get(0).isSeatBooked()); 
        System.out.println("Seat 1 Booked? " + allSeats.get(1).isSeatBooked());
        System.out.println("Seat 2 Booked? " + allSeats.get(2).isSeatBooked());
    }

    private static List<Room> createRooms(int numberOfRooms, int seatsPerRoom) {
        List<Room> distinctRooms = new ArrayList<>();
        for (int i = 0; i < numberOfRooms; i++) {
            List<Seat> distinctSeats = new ArrayList<>();
            for (int j = 0; j < seatsPerRoom; j++) {
                distinctSeats.add(new Seat()); 
            }
            distinctRooms.add(new Room(i, distinctSeats));
        }
        return distinctRooms;
    }
}

class Movie {
    private int id;
    private String name;
    private Genre genre;
}
enum Genre{
    SCI_FI,
    ROM_COM,
    MYSTERY
}

class Cinema {
    private String name;
    private List<Room> rooms;

    public Cinema (String name , List<Room> rooms){
        this.name = name;
        this.rooms = rooms;
    }

    public boolean addRoom(Room room){
        rooms.add(room);
        return true;
    }

    public String getName(){
        return this.name;
    }
    public List<Room> getRooms() {
    return this.rooms;
}
    
}

class Room {
    private int id;
    private List<Seat> seats;

    public Room (int id , List<Seat> seats){
        this.id = id;
        this.seats = seats;
    }

    public List<Seat> getSeats(){
        return this.seats;
    }

}

class Seat {
    private int id;
    private SeatType seatType;


}
enum SeatType{
    NORMAL,
    VIP, 
    RECLINER
}

class ShowSeat{
    public int id;
    private Show show;
    private Seat seat;
    private boolean isBooked;
    private ReentrantLock lock;
    private int amount;

    public ShowSeat(int id, Show show, Seat seat, int amount){
        this.id = id;
        this.show = show;
        this.seat = seat;
        this.isBooked = false;
        this.lock = new ReentrantLock();
        this.amount= amount;
    }

    public boolean bookSeat(){
        if(lock.tryLock()){
            try {
                if(this.isBooked) return false;
                this.isBooked = true;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
        
    }

    public boolean unbook(){
        try {
            lock.lock();
            if(!this.isBooked) return false;
            this.isBooked = false;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int getAmount(){
        return this.amount;
    }
    public boolean isSeatBooked() {
    return this.isBooked;
}

}

class Show {
    private Movie movie;
    private Room room;
    private int time;
    private List<ShowSeat> showSeats;


    public Show (Movie movie, Room room, int time){
        this.movie = movie;
        this.room = room;
        this.time = time;
        createShowSeat();
    }

    public Room getRoom() {
        return this.room;
    }
    public List<ShowSeat> gShowSeats(){
        return this.showSeats;
    }

    private void createShowSeat(){
        this.showSeats = new ArrayList<>();
        int id=0;
        for(Seat seat : this.room.getSeats()){
            
            ShowSeat showSeat = new ShowSeat(id,this,seat,10);
            id++;
            showSeats.add(showSeat);
        }
    }
}

class BookingService{

    public Ticket bookShowTicket(Cinema cinema, Show show , List<ShowSeat> selectedSeats){

        List<ShowSeat> bookedSeats = new ArrayList<>();
        int amount =0;
        for (ShowSeat showSeat : show.gShowSeats()){
            for (ShowSeat selectedSeat : selectedSeats){
                if(showSeat.id ==selectedSeat.id){
                    boolean status = showSeat.bookSeat();
                    if(status == false){
                        revertChanges(bookedSeats);
                        return null;
                    }
                    bookedSeats.add(showSeat);
                    amount+= showSeat.getAmount();
                } 
            }     
        }
        Ticket ticket = new Ticket(show, bookedSeats, 0, amount);

        return ticket;
    }

    private void revertChanges(List<ShowSeat> bookedSeats){
        for (ShowSeat seat : bookedSeats){
            seat.unbook();
        }
    }
}

class Ticket{
    private Show show;
    private List<ShowSeat> selectedseats;
    private int id;
    private int amount;


    public Ticket(Show show, List<ShowSeat> selectedseats, int id, int amount){
        this.show = show;
        this.selectedseats = selectedseats;
        this.id= id;
        this.amount= amount;
    }
    public int getAmount() {
    return this.amount;
}
    
}



class BMS {
    private List<Cinema> cinemas;
    private BookingService bookingService;

    public BMS (List<Cinema> cinema , BookingService bookingService) {
        this.cinemas= cinema;
        this.bookingService = bookingService;
    }

    public Cinema searchByName(String name){
        for (Cinema cinema : cinemas){
            if(cinema.getName().equals(name)){
                return cinema;
            }
        }
        return null;
    }

    public Ticket bookTicket(Cinema cinema, Show show, List<ShowSeat> selectedSeats){
        return this.bookingService.bookShowTicket(cinema, show, selectedSeats);
    }
}
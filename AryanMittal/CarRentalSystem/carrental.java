package AryanMittal.CarRentalSystem;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class carrental {
    
}


class CarRentalService{
    private List<Store> stores;
    private ReservationManager reservationManager;

	public CarRentalService(List<Store> stores, ReservationManager reservationManager) {
		this.stores = stores;
		this.reservationManager = reservationManager;
	}


    public boolean createBooking(Reservation reservation){
        return this.reservationManager.createBooking(reservation);
    }

	public List<Store> getStores() {
		return stores;
	}
	public ReservationManager getReservationManager() {
		return reservationManager;
	}
    
}

class ReservationManager{
    private RentalPricingStrategy rentalPricingStrategy;
    private ReservationSerivce reservationSerivce;


	public ReservationManager(RentalPricingStrategy rentalPricingStrategy, ReservationService reservationSerivce) {
		this.rentalPricingStrategy = rentalPricingStrategy;
		this.reservationSerivce = reservationSerivce;
	}

    public boolean createBooking(Reservation reservation){
        

        ReentrantLock lock = this.reservationSerivce.getVechileLock(reservation.getVechile());

        int finalPrice = this.rentalPricingStrategy.calculatePrice(reservation);
        try {
            lock.lock();
            boolean isConflict = this.reservationSerivce.checkConflict(reservation);
            if(isConflict) return false;
            reservation.setPrice(finalPrice);
            this.reservationSerivce.addBooking(reservation);
        } finally {
            lock.unlock();

        }
        return true;
        
    }
}



class ReservationRepo{
    private HashMap<Integer, Reservation> reservationToIdMap;
    private HashMap<Integer, List<Reservation>> vechileToReservationMap;

    public List<Reservation> getReservations(Vechile vechile){
        return this.vechileToReservationMap.get(vechile.getId());
    }

    public void addBooking(Reservation reservation){
        this.reservationToIdMap.put(reservation.getId(), reservation);
        this.vechileToReservationMap.get(reservation.getVechile()).add(reservation);
    }

}

class Store{
    private List<Vechile> vechiles;
    private Location location;

	public Store(List<Vechile> vechiles, Location location) {
		this.vechiles = vechiles;
		this.location = location;
	}
	public List<Vechile> getVechiles() {
		return vechiles;
	}
	public Location getLocation() {
		return location;
	}
}

class ReservationSerivce{
    private VechileLockManager vechileLockManager;
    private ReservationRepo reservationRepo;

	public ReservationSerivce(VechileLockManager vechileLockManager, ReservationRepo reservationRepo) {
		this.vechileLockManager = vechileLockManager;
		this.reservationRepo = reservationRepo;
	}

    public ReentrantLock getVechileLock(Vechile vechile){
        return vechileLockManager.getLock(vechile);
    }
    public List<Reservation> getReservations(Vechile vechile){
        return this.reservationRepo.getReservations(vechile);
    }

    public boolean checkConflict(Reservation reservation){
        return false;
    }

    public void addBooking(Reservation reservation){
        this.reservationRepo.addBooking(reservation);
    }
    
}

class VechileLockManager{
    private HashMap<Vechile, ReentrantLock> vechileToLockMap;

	public VechileLockManager(HashMap<Vechile, ReentrantLock> vechileToLockMap) {
		this.vechileToLockMap = vechileToLockMap;
	}

    public ReentrantLock getLock(Vechile vechile){
        return this.vechileToLockMap.get(vechile);
    }

    
}


class Reservation{
    private int id;
    private Vechile vechile;
    private User user;
    private Store pickUpStore;
    private Store dropStore;
    private int amount;
    private int startDate;
    private int endDate;
	public Reservation(int id, Vechile vechile, User user, Store pickUpStore, Store dropStore, int amount, int startDate,
			int endDate) {
        this.id = id;
		this.vechile = vechile;
		this.user = user;
		this.pickUpStore = pickUpStore;
		this.dropStore = dropStore;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Vechile getVechile() {
		return vechile;
	}
	public User getUser() {
		return user;
	}
	public Store getPickUpStore() {
		return pickUpStore;
	}
	public Store getDropStore() {
		return dropStore;
	}
	public int getAmount() {
		return amount;
	}
	public int getStartDate() {
		return startDate;
	}
	public int getEndDate() {
		return endDate;
	}
	public int getId() {
		return id;
	}

    public void setPrice(int amount){
        this.amount = amount;
    }

    
}


interface RentalPricingStrategy{
    public int calculatePrice(Reservation reservation);
}

class WeekEndRentalPricingStartegy implements  RentalPricingStrategy{
    private int mux = 1;


    public WeekEndRentalPricingStartegy(int mux) {
		this.mux = mux;
	}

	public int calculatePrice(Reservation reservation){

        return reservation.getVechile().getPrice() * mux;

    }
}

class Location{
    int lat;
    int lon;

	public Location(int lat, int lon) {
		this.lat = lat;
		this.lon = lon;
	}


	public int getLat() {
		return lat;
	}
	public int getLon() {
		return lon;
	} 

}

class Vechile{
    private int id;
    private VechileType vechileType;
    private int price;


	public Vechile(int id, VechileType vechileType, int price) {
		this.id = id;
		this.vechileType = vechileType;
		this.price = price;
	}


	public int getId() {
		return id;
	}


	public VechileType getVechileType() {
		return vechileType;
	}


	public int getPrice() {
		return price;
	}

    

    
}

enum VechileType{
    CAR,
    BIKE,
    SCOOTY
}

class User{
    private int id;
    private List<Reservation> reservations;
	public User(int id, List<Reservation> reservations) {
		this.id = id;
		this.reservations = reservations;
	}
	public int getId() {
		return id;
	}
	public List<Reservation> getReservations() {
		return reservations;
	}

    
}
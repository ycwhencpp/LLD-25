
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;
class main{
    public static void main(String[] args) {
        
        //only main and half is implemented

    }
  
}
class CarRentalSystem{
    HashMap<Integer, Vechile> idToVechileMap;
    BookingSystem bookingSystem = new BookingSystem();

    public Booking bookVechile(int startDate, int endDate, int vechileId, User user) throws Exception{
        Vechile vechile = idToVechileMap.get(vechileId);
        if(vechile == null){
            throw new Exception("Vechile not found");
        }
        return bookingSystem.createBooking(startDate, endDate, vechile, user);
    }
}

class User{
    int id;
}
enum UserStatus{
    BLOCKED, 
    ACTIVE
}
enum VechileStatus{
    AVAIABLED, 
    DISABLED,
    MAINTAINCE
}
enum VechileType{
    CAR, 
    BIKE, 
    SCOOTER
}
enum VechileCategory{
    HATCHBACK, 
    SEDAN, 
    SUV
}
interface PricingStrategy {
    public double calculatePrice(Booking booking);
}

class sedanPricingStrategy {
    double basePay;
    public sedanPricingStrategy(double basePay){
        this.basePay = basePay;
    }
    public double calculatePrice(Booking booking){
        int time = booking.bookingDetials.endtime - booking.bookingDetials.starttime;
        
        return time * basePay;
    }
}
class  Vechile{
    VechileType vechileType;
    VechileCategory vechileCategory;
    PricingStrategy pricingStrategy;
    VechileStatus vechileStatus;
    ReentrantLock lock;
    TreeMap<Integer,Booking> timeToBookingmap = new TreeMap<>();

    // basic getters setters


}


class Booking{
    User user;
    Vechile vechile;
    BookingDetials bookingDetials;
    BookingStaus bookingStaus;
}

class BookingDetials{
    int starttime;
    int id;
    int endtime;
    int expiryAt;
}
enum BookingStaus{
    CREATED, 
    RESERVED, 
    ACTIVE, 
    COMPLETED, 
    CANCELLED, 
    EXPIRED
}
interface PaymentStartegy{
    public boolean deductmoney();
}

class UPI implements PaymentStartegy{
    @Override
    public boolean deductmoney(){
        return true;
    }
}

class BookingSystem {
    public Booking createBooking(int startDate, int endDate, Vechile vechile, User user) throws Exception{
        vechile.lock.lock();
        try {
            validateAvialablity(startDate,endDate, vechile);
            Booking booking = new Booking();
            booking.bookingDetials.expiryAt = booking.bookingDetials.starttime + 10*60;
            booking.bookingStaus = BookingStaus.RESERVED;
            return booking;
        } catch (Exception e) {
            throw e;
        } finally {
            vechile.lock.unlock();
        }
    }

    private void validateAvialablity(int startDate, int endDate, Vechile vechile) throws Exception{
        TreeMap<Integer, Booking> timeToBookingmap = vechile.timeToBookingmap;

        Entry<Integer, Booking> floorEntry = timeToBookingmap.floorEntry(startDate);
        Entry<Integer, Booking> ceilEntry = timeToBookingmap.ceilingEntry(endDate);

        if(floorEntry != null && floorEntry.getValue() != null && overlaps(startDate, endDate, floorEntry.getValue())){
            throw new Exception("Bookig not Avaiable");
        }
        if(ceilEntry != null && ceilEntry.getValue() != null && overlaps(startDate, endDate, ceilEntry.getValue())){
            throw new Exception("Bookig not Avaiable");
        }


    }

    private boolean overlaps(int start, int end, Booking booking){
        return (booking.bookingDetials.starttime <= end && start<= booking.bookingDetials.expiryAt);
    }
}
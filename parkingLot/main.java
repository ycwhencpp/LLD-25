import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

class main{
    public static void main(String[] args) {
        
        //only main and half is implemented

    }
  
}

class ParkingLot{
    HashMap<Integer, List<ParkingSpot>> floorToSpotMap;
    HashMap<VechileType, Integer> vechileTypeToFreeSpotMap;
}

class ParkingService{
    ParkingStartegy parkingStartegy;

    public ParkingSpot assignParkingSpot(Vechile vechile){
        return null;
    }
    public void freeSpot(ParkingSpot spot) {
        spot.parkingSpotState = ParkingSpotState.AVIALABLE;
        spot.vechile = null;
    }
}

class Vechile {
    int vechileId;
    VechileType vechileType;

}
class ParkingFloor{
    int floorId;
    List<ParkingSpot> parkingSpots;
    HashMap<VechileType, Integer> freeSpotPerVechileMap;
}

interface ParkingStartegy {
    public ParkingSpot findSpot(Vechile vechile, List<ParkingSpot> floor);
}

class ClosesetParkingStartegy implements ParkingStartegy{
    @Override
    public ParkingSpot findSpot(Vechile vechile, List<ParkingSpot> floor){
        for (ParkingSpot spot : floor) {
            if (spot.vechileType == vechile.vechileType
                    && spot.parkingSpotState == ParkingSpotState.AVIALABLE) {
                return spot;
            }
        }
        return null;
    }
}

class ParkingSpot{
    int parkingSpotId;
    VechileType vechileType;
    ParkingSpotState parkingSpotState;
    Vechile vechile;
    ReentrantLock lock;

    public boolean assignVechile(Vechile vechile){
        lock.lock();
        try {
            if(this.parkingSpotState != parkingSpotState.AVIALABLE){
                return false;
            }
            this.vechile = vechile;
            this.parkingSpotState = parkingSpotState.OCCUPIED;
            return true;
        } finally{
            lock.unlock();
        }
    }

    public boolean freeSpot(){
        lock.lock();
        try {
            if(this.parkingSpotState != parkingSpotState.OCCUPIED){
                return false;
            }
            this.vechile = null;
            this.parkingSpotState = parkingSpotState.AVIALABLE;
            return true;
        } finally{
            lock.unlock();
        }
    }
}

class Ticket{
    int ticketId;
    int startTime;
    int exitTime;
    TicketStatus ticketStatus;
    ParkingSpot parkingSpot;
}


class EntryGate {
    int gateId;
    ParkingService parkingService;
    TicketSerivce ticketSerivce;
    public ParkingSpot assignSpot(Vechile vechile){
        return null;
    }

    public Ticket enter(Vechile vechile){
         ParkingSpot spot = parkingService.assignParkingSpot(vechile);
         return ticketSerivce.createTicket(vechile, 123, spot);
    }
}

class ExitGate {
    int gateId;
    ParkingService parkingService;
    TicketSerivce ticketSerivce;
    public void exit(Ticket ticket){

    }
}

class PaymentProcessor{
    PaymentStartegy paymentStartegy;
    public boolean pay(){
        return true;
    }
}
interface PaymentStartegy{
    public boolean pay();
}
class UPI implements PaymentStartegy{
    @Override
    public boolean pay(){
        return true;
    }
}

interface PricingStrategy{
    public int calculateFee(Ticket ticket);
}
class CarPricingStrategy implements PricingStrategy{
    int basePrice;
    @Override
    public int calculateFee(Ticket ticket){
        return basePrice;
    }
}
class TicketSerivce{
    HashMap<VechileType, PricingStrategy> pricingStrategy;
    public Ticket createTicket(Vechile vechile, int entryTime, ParkingSpot parkingSpot){
        return null;
    }
    public int calculateFee(Ticket ticket){
        return pricingStrategy.get(ticket.parkingSpot.vechile.vechileType).calculateFee(ticket);
    }
}

enum TicketStatus{
    ACTIVE, 
    PAID, 
    CLOSED
}
enum ParkingSpotState{
    AVIALABLE, 
    OCCUPIED, 
}
enum VechileType {
    CAR, 
    BUS, 
    TRUCK, 
    BIKE
}
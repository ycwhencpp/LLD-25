import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 
 * 
 * Task 1: The State Manager & Concurrency
1. ParkingSpot

State: ID, SpotType (Small, Medium, Large), SpotStatus (Available, Booked, Out_of_Service).

Methods: bookSpot() and freeSpot().

2. ParkingFloor

State: Floor Number, a collection of ParkingSpots. (Think carefully about what data structure is best here for fast lookups based on SpotType).

Methods: getAvailableSpot(SpotType type).

3. ParkingLot (The Singleton)

State: A collection of ParkingFloors.

Methods: * public ParkingSpot reserveSpot(SpotType type) -> This is the critical method. Multiple gates will call this at the exact same time. How do you ensure two cars aren't assigned the same spot?

public void releaseSpot(ParkingSpot spot)
 */
public class main {

    class ParkingSpot{
        int id;
        AvailibilityState state;
        SpotType spotType;
        ReentrantLock lock;

        public boolean bookSpot() {
            lock.lock();
            try {
                if (state != AvailibilityState.AVAILIABLE){
                    return false;
                }
                state = AvailibilityState.BOOKED;
            } finally {
                lock.unlock();
            }

            return true;
            
 
        }

        public boolean freeSpot() {
            lock.lock();
            try {
                if (state != AvailibilityState.BOOKED){
                    return false;
                }
                state = AvailibilityState.AVAILIABLE;
            } finally {
                lock.unlock();
            }

            return true;
        }
    }
    enum SpotType{
        LARGE, 
        MEDIUM, 
        SMALL
    }
    enum AvailibilityState{
        AVAILIABLE, 
        BOOKED, 
        OUT_OF_SERVICE
    }

  
    class ParkingFloor{
        HashMap<SpotType, List<ParkingSpot>> spotTypeToSpot = new HashMap<>();


        public ParkingSpot getAvailableSpot(SpotType type) {
            if(!spotTypeToSpot.containsKey(type)){
                return null;
            }
            List<ParkingSpot> ParkingSpots = spotTypeToSpot.get(type);

            for(ParkingSpot spot : ParkingSpots){
                if(spot.state == AvailibilityState.AVAILIABLE){
                    return spot;
                }
            }
            return null;
        }
        
    }

    class EntryGate {
       
        public ParkingSpot reserveSpot(SpotType spotType, ParkingFloor parkingFloor){
            ParkingSpot parkingSpot = parkingFloor.getAvailableSpot(spotType);
            if(parkingSpot.state != AvailibilityState.AVAILIABLE){
                return null;
            } 
            boolean sucess  = parkingSpot.bookSpot();
            if(sucess) return parkingSpot;
            return null;
        }

        public void releaseSpot(ParkingSpot spot) throws Exception{
            if(spot.state != AvailibilityState.BOOKED){
               throw new Exception("spot is already availiable");
            } 
            boolean sucess  = spot.freeSpot();
            if(!sucess) throw new Exception("not able to free spot try again");
        }   

    }
}
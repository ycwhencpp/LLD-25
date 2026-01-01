import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class main{
    public static void main(String[] args) {
        
        //only main and half is implemented

    }
  
}

class Uber{
    List<TripAssignmentStrategy> strategies;

    public List<Driver> findDrivers(Trip trip){
        return new ArrayList<>();
    }

    public Driver AssignDriver(Trip trip, PriorityQueue<DriverContext> availableDrivers) throws Exception{
        synchronized (this) {
            while(!availableDrivers.isEmpty()){
                DriverContext driverContext = availableDrivers.poll();
                if(driverContext.driver.driverStatus != DriverStatus.IDLE ) continue;
                driverContext.driver.driverStatus = DriverStatus.BUSY;
                trip.tripStatus = TripStatus.CONFIRMED;
                trip.driver = driverContext.driver;
                return driverContext.driver;

            }
            throw new RuntimeException("no driver available");
        }
    }
}

class User {
    int id;
    UserStatus userStatus;
    List<Trip> trips;
    Location location;
}

enum UserStatus{
    PREMIUM, 
    REGULAR
}

class Trip {
    User user;
    Driver driver;
    TripStatus tripStatus;
    int tripWaitingTime;
    int tripStartedAt;
    int tripCompletedAt;
    Location pickupLocation;
    Location dropLocation;

}

class Location{
    int id;
    int lat;
    int lon;
}

class Driver{
    int id;
    DriverStatus driverStatus;
    int idletime;
    Location location;
}

enum DriverStatus{
    ONLINE, 
    OFFLINE, 
    BUSY,
    IDLE
}

enum TripStatus{
    REQUESTED, 
    ACCEPTED, 
    ONGOING, 
    COMPLETED, 
    CONFIRMED
}

class DriverContext{
    Driver driver;
    int score;
}

interface TripAssignmentStrategy{
    public int calculateScore(Driver driver, Trip trip);
}

class WaitingTimeTripAssignmentStartegy implements TripAssignmentStrategy{
    public int calculateScore(Driver driver, Trip trip){
        return 0;
    }
}
class DriverLocationTripAssignmentStartegy implements TripAssignmentStrategy{
    public int calculateScore(Driver driver, Trip trip){
        return 0;
    }
}

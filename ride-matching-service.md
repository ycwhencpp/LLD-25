# question 
Design a Ride matching Service.

# requirement gathering 
1. user should be able to request ride 
    - ride requested 
    - find drivers 
    - assign driver
2. drivers should be able to view ride request 
    - reject/accept ride 
        - change state 
3. algos to fine driver (interchangeable algo based on user tier, service)
4. error handling 
    - 2 driver accepting same ride 
    - handling connucrrent ride accept request 

# questions 
should i focus on these things, or u want me to focus on fare calcualtion, trip ETA and all 


# entities 
1. User 
2. Ride 
3. Driver
4. findDriver (diff interchangeable algo based on conditions)
5. ride_status 
6. driver_status 
7. RideService 

# class diagram 
1. RideService 
    - List<Driver>
    - List<FindDriverStrategy>

    + assignRide(Ride)
        - loop over list of driver to Driver status in [IDLE]
        - loop over startegy to send(ride, List<Driver> potential drivers)
        - now again loop over returned list of potential driver and check status of them if its still ideal
        - if ideal assign ride and return ride object lock ride object when assign order and check ride_staus as well it should be WAITING and wait for driver accept/reject for 5s 

        
2. User 
    - id 
    - List<Ridde> history
3. Driver 
    - id 
    - Driver_Status[OFFLINE, IDEAL, IN_RIDE]
    - Location
    - ReentrantLock
    
    + acceptRide(Ride)
    + declineRide(Ride)
4. FindDriver <<interface>>
    + List<Driver> find(Ride, List<Driver>)
5. Ride_Status[WAITING, COMPLETED, DRIVER_ASSIGNED, STARTED]
6. Ride
    - User
    - Driver 
    - MetaData[pickup, droplocatio]
    - Ride_Status
    - ReentrantLock






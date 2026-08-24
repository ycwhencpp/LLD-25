# question 
design parking lot system 

primary capabilities 
error handling 
scope boundaries 


# requirement gathering 
1. should alot desried parking spot to vechile 
2. generate parking ticket with enter time 
3. calculate price on exit gate
4. no two vechile should park on same spot 
5. vechile should not enter if parking lot is full
6. out of scope 
    - concurrent vechile assignment for the same spot 


7. extensiblity 
 - multiple strategy for finding parking spots
 - multiple gates 


# entities (look for noun or something that standout)
1. vechile 
2. parking spot 
3. parking ticket
4. exit gate 
5. entry gate 
7. parking Lot 

# class design(define state and methods it exposes)
- top down
- scrape requirements and derive state and methods

1. parking lot
    - map of parking spot per floor 
    - list of entry gates 
    - list of exit gates 
    



    # offload to other classes 
    - spot = findParkingspot(vechile, parking spot data) //since we ca have nuktiple entry gates 
    - parkingTicket = generateParkingTicket(vechile, spot, Entrytime)
    - parkingTicket = parkVechile(vechile, time)
    - boolean = exitVechile(parkingTicket) // it should be here or in exit gate

2. Parking spot 
    - state enum (AVAILIABLE, BOOKED, OUT_OF_SERVICE)
    - type (large, medium, small)

    - assignVehcile()
    - removeVechile()

3. parking ticker 
    - parking spot 
    - vechile 
    - entry time 

4. parking ticker generator (just a wrapper to offload resposnibility)
    - parkingTicket = generateParkingTicket(vechile, spot, Entrytime)

5. findParkingSpotStrategy 
    - spot = findParking spot(vechile, parking spot data)

6. entry gate 
    - id
    - state enum (AVAILIABLE, OUT_OF_SERVICE)
    - findParkingSpotStrategy 
    - parking ticket generator



    - parkingTicket = parkVechile(vechile, time)


7. exit gate 
    - id 
    - state enum (AVAILIABLE, OUT_OF_SERVICE)
   
    - fee generator 

    - boolean = exitVechile(parkingTicket) // it should be here or in exit gate

8. fee genrator 
    - caclulateFeeStartegy 
    - caluclate fee(parking ticket)

9. vechile 
    - id 
    - type (large, medium, small)




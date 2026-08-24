# question 
car rental system 

# requirement gathering
1. user should be able to view all the cars , based on prefernce 
2. user should be able to book the car, bike , truck 
3. system should correctly update state of vechile 
4. system should be able to integrate diff payments 
5. car lifecycle : AVIABLE -> DISABLED-> MAINTAINCE
6. booking lifecycle : CREATED-> RESERVED -> ACTIVE-> COMPLETED/CANCELLED/EXPIRED
7. user status : BLOCKED, ACTIVE, PENDING
8. track vechile availiabluty 
9. search vechile by time rnage 

# question to ask 
1. can user book multiple cars while state is ONGOING/BOOKED 
2. do we have to show car even if its reserved but not started
3. Is this station-based rental or free-floating rental? 


# happy flow 
1. user comes to the platform , searches for car (can view car state , type and price config)
2. create booking 
3. he reerves the car for x-y date 
4. he starts the ride and car jounrey starts, 
5. store information about journey 
6. once trip is completed extract joureny information and deduct money and reset states 

# entites 
1. user 
2. vechile 
3. enum(car type) or should we make interface for car types, ()
4. car rental system 
5. TRIP
6. payment 
7. car config
8. car state 
9. user state 
10. transcation system 
11. Booking

# confusion 
1. should i make diff interface for car types ? why cause ? how to incorporate diff base amount ? 

# clear ups
1. enum is best since interface is used when behaiour differs 
2. we just want diff price so vechile can have one call pricing startegy to diff that 
3. user reserves a car but never starts the trip,
  - we can do 3 things 
    - reseravtion expiry serice(backhround job)
    - lazy expire on read
    - reservationExpiryTime add this field(grace period)
4. a car can have mutilple booking in line 
   - so store map of car id to list of booking(sorted) for fast validaiton check 
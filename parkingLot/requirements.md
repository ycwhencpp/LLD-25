# question 
design parking lot 

# requirement gathering 
1. parking lot has multiple floors 
2. space assigned for vechile type can be occupied by that vechile only 
3. multiple payment startegy 
4. do we need to display empty spaces as well 
5. dynamic pricing based on car ttype 
6. Assign nearest available spot
7. generate ticket on entry 
8. caclulate payment on exit 
9. spot lifecyle : AVAILAIBLE -> OCCUPIED -> AVAIlABLE 
10. vechile type enum -> car, truck, bike 
11. check free spots on entry 
12. Ticket Lifecycle : ACTIVE -> PAID -> CLOSED 


# question to ask 
1. do we have to show nearest spots based on floor or entire lot ?
2. what if parking is full 
3. Do different vehicle types have dedicated spots only?
4. do we support reservation or, real-time oarking 


# happy flow 
1. vechile comes into system (allowed if spots avaialble rejected if not)
2. find nearest parking spot based on gate entry using pq and similary nearest exit 
3. block parking space and assign ticket , with starttime and vechile and user 
4. on exit , check ticket assigned to vechile , calculate fare 
5. free parking space, change ticket state 

# entities 
1. Parking lot  
    - list of floors
    - ticket service
    - map of vehcile type to spot (free spot count)

2. vechile 
  - id 
  - vechile type
3. vechile type enum
4. parking spot 
    - state 
    - type
    - vechile 
    - lock
4. parking spot state enum
5. ticket 
   - id 
   - entry time
   - vechile
   - ticket status
   - parking spot 
   - exit time 
6. gates interface 
7. entry gate , exit gate (since diff behaviour)
    - id 
    - type 
    - entry gate 
        - assignspot()
        - createticket()
    - exit gate 
        - free spot()
        - close ticket() and process payment()
8. payment processor 
    - payment startegy
    - pay()
9. parking floor 
    - list of parking spots
10. Ticket status enum

11. Parking startegy
    - findspot(vechile , gate)
    - closest parking 
    - cheapest parking

12. ticket service 
   - create ticket()
   - close ticket()
   - calculate fee()
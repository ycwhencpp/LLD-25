# question 
Design a Restaurant Booking System.

# requirement gathering 
1. user should be able to resever tables 
    - no of pax 
    - reservation timing 
2. alogrithm to find potential table 
    - if current size not availiable check one size above until found 
    - or strict size matching 
3. error boundaries 
    - no double booking 
    - no concurrent booking for same table for same time (locking)
4. questions i have 
 what if i have booked for 4-5 now should the table be availiavle exactly at 5 or some buffer ?

5. also ignoring searching as of now , will extend if u want me to build 

# entities 
1. Restutant Booking System 
2. User 
3. Table 
4. Reservation
5. findTable (multiple interchangable alogrithms so most probably startegy)


# class daigram 
1. Resturant bOoking System 
    - HashMap<Id, Resturant> resturants


    + reserveTable(User, Request)
    + cancelReservation(User, request)

2. User 
    - id
    - name 
    // history if needed in future 

3. Resturant 
    - id
    - name
    - HashMap<TableSize, HashMap<Id, Table>>
    - HashMap<TableId, HashMap<ResevationId, Reservation>> table resveation ledger

    + reserveTable(User, Request)
    + cancelReservation(User, request)
    + findNextBestTable(Request)


4. Table 
    - id 
    - TABLE_SiZE
    - grace Time 
    - List<Reservation> reservation (will contain active reservation only)
    - ReentrantLock

    + reserveTable(User, Request)
    + cancelReservation(User, Request)

5. Rservation 
    - id 
    - User 
    - startTime
    - endTime 
    - graceTime 
    - staus[Canclled, reserved, completed ]

6. findNextBestTable<<interface>>

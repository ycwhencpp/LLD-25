# question
Design a movie booking system 

# requirement gathering 
- user should be able to search and book movie tickets 
- cinema hall contains multiple rooms 
- a movie can have multiple shows 
- a room have multiple seats with support of diff type 
- seats can have diff structure based on the room 
- user can book multiple seats at once 
- user searches for show 
    - seletcs shows time and seat 
    - generates ticket 
- no double booking 
    - handle invalid show search (out of scope)


# entities (noun or things that standout )
- movie 
- cinema 
- room 
- seat 
- ticket 
- show 
- showseat
- bookingservice

# class design (what entitie a class hold and what method it exposes)

- movie
    - id 
    - name
    - genre
- cinema 
    <!-- - bookingservice -->
    - list <room>

    + boolean addRoom/removeRoom()
    <!-- + Ticket createBooking() -->

- room 
    - list<seat>

- seat 
    - id 
    - type 

- showSeat 
    - id 
    - seat 
    - show 
    - isBooked 

    + bookSeat()


- show 
    - movie 
    - room 
    - time 
    - List<ShowSeat>


- bookingService 
    
    + Ticket bookShowTicket(Cinema, Show, list<showSeat>)
       Ticket 
       List<showseat> bookedseat
        for showSeat in show.room.showseat
            for showseatbyuser in list of showseat 
                if not booked add in bookedseat
                else 
                    return error 
        
        for showseat in bookedseat
            showseat.book()
        return ticket 

- ticket 
    - show 
    - list<showseat>
    - userid 
    - amount

-BMS 
    - list<cinema>
    - bookingService 

    + searchByName()
    + bookTicket(...)
        bookingService.bookShowTicket(...)




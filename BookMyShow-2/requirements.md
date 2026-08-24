# question 
Design BMS

- primary capapablities 
- error handlignn
- scope boundarie s
# requirement gathering 
 - should be able to search for show based on name/genre/
 - able to choose seat , and book ticket 
 - system should complete booking by assign seat/ audi/ screen/ show 
 - error handling 
    - booking already booked seat
    - booking invalid/show
- extensibilty 
    - concurrent seat booking 
    - search show startegy 
    - multiple screens ?
    - multiple audis
    - multiple theraters

# entities (noun or something that matter)
 - user
 - show 
 - searchEngine
 - searchStartegy
 - Booking 
 - seat 
 - audi 
 - BMS
 - theater
 - BookingRequest


# class design (where we define what state a entitie holds and what methods it exposes )
 - BMS 
    - SearchEngine
    - booking manager 
    - Map of <movie, list<theater>>>

    + seachMovie(movie) -> list<theater>
    + seachMovie(bookingRequest) -> booking

- booking manager 
    - createBooking(user, show , list<showSeat>) -> booking

- SearchEnginer 
    - SearchStartegy 
    
    + seachMovie(movie, list<theater>)

- theater 
    - list<show>
    - id
    - location

- show 
    - movie 
    - audi 
    - start
    - end 

- audi 
    - id
    - list<seats>

- seat 
    - id
    - type

- movie 
    - name 
    - id 
    - location
    
- showSeat 
    - show 
    - seat 
    - id 
    - status
    - reeentrant lock

- bookig 
    - id 
    - show 
    - list<showSeat>
    - sattus 

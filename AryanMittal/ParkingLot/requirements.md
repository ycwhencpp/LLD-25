# question 
design parking lot 

# requirement gathering 
1. system should provide receipt on entry gate
2. system should charge money and return receipt on exit gate
3. system should support multiple vechile types and payment method
4. system supports multiple floors
5. error handling
    - system should check for parking spot based on car type first before giving ticket
    - system should not assign parked spot to someone else


# thinking out load 
- system has to support multiple payment startgey 
    - so we can user strategy pattern here 
- system should support multiple parking fee calculator strategy and multiple algo to find parking spots 
    - we can use startegy here 
- we can use observer as well to notify user whenever there vechile leaves 
- think of supproting multiple dynamic fare based on car type 


# entites 
- parking lot 
- vechile 
- receipt
- entry gate
- exit gate
- vechile Type 
- parking floor
- parking spot 
- spotType 
- PaymentCollector
- paymentStartegy interface 
- ParkingFeeCalculator interface
- ParkingSpotFinder interface 
- TicketBuilder

// before writing class design i would like to do mental mode of who will own what do top down approach 

parking lot 
    -> EntryGate
    -> Exit Gate
    -> floors 
        -> spot 
    -> TicketBuilder
    
entry gate
    -> parkingSpotFinder 

exit gate 
    -> ParkingFeeCalculator
    -> PaymentCollector

parkingSpotFinder
    -> parkingSpotFinderStartegy

ParkingFeeCalculator
    -> ParkingFeeStartegy


# class design
- Parking Lot 
    - List<Floor>
    - List<Entrygate> etnry
    - List<ExitGate> exit
    - TicketBuilder 

    + generateTicket()
    + getFloors()
    + getGate(Entry/Exit) 
    + updateTicket()
    + enterVechile()
    + exitVechile()

- Floor 
    - List<ParkingSpot>
    
    + getSpots()

- ParkingSpot 
    - spotStatus [OCCUPIED,FREE,NOT_SERVICABLE]
    - vechile
    
    + getSpotStatus()
    + isSpotFree()
    + setSpotFree()
    + setSpotOccupied()

- Vechile 
    - vechileType[CAR,SUV,BIKE] 
    - number
    - Ticket

    + getVechileType()
    + getTicket()

- EntryGate
    - ParkingSpotFiner
    - id 

    + getParkingSpot()
    + fillParkingSpot()
    + generateTicket()

- ExitGate 
    - ParkingFeeCalculator
    - PaymentCollector

    + calculateParkingFee()
    + debitAmount()
    + updateTicket()

- TicketBuilder 
    - Vechile 
    - inTime 
    - outTime 
    - ticketType[PRIORITY,NORMAL,VIP]
    - ParkingSpot

- ParkingSpotFinder
    - SpotFinderAlgo 

    + findSpot()

- SpotFinderAlgo interface
    + findSpot()

- ParkingFeeCalculator 
    - ParkingFeeStartegy

    + calculateFee()

- ParkingFeeStartegy interface
    + calculateFee()

- PaymentCollector
    - paymentStartegy

    + deduct()

- paymentStartegy interface
    + deduct()
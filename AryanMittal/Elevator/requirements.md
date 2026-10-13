# question 
design elevator

# requirement gathering 
1. user should be able to call the elevator from outside 
2. user should be able to choose destination floor from inside 
3. elevator scheduling logic can have multiple type of interchangable algorithms 
4. error handling 
    - elevator should cater all the request 
    - no request catered twice 


# entities 
1. elevator 
2. building 
3. ElevatorSchedulingStrategy 
4. Floor 
5. Request 
6. elevatorState
7. ElevatorRoutingStartegy


# thinking out loud 
- since we can have multiple algo and each elevator can have diff way of behaving to request so startegy should be inside elevator 

# class design 
1. Elevator 
    - ElevatorRoutingStrategy
    - elevatorState[IDLE|MOVING|MAINTAINCE]
    - direction[UP|DOWN|IDLE]
    - Queue<Request>

    + step()

2. Building 
    - List<Floor>
    - ElevatorController 

3. ElevatorContoller 
    - List<Elevator>
    - Queue<Request>
    - ElevatorSchedulingStartegy

    + step();
4. Floor 
    - id 

5. ElevatorRoutingStrategy
    - getNextFloor(Queue<Request>)

6. Request 

7. InternalRequest
    - Floor

8. ExternalRequest
    - floor 
    - direction

9. ElevatorSchedulingStartegy
    - getBestElevator()
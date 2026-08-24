# question 
Design a Train-Platform Management System with functionalities:

    Assign trains to platforms based on input.
    Query which train is at a given platform at a specific time.
    Query which platform a train is at, at a specific time.



- fucntional requirements
- error handling


# requirement gathering 
 - can this assignment change ?
 - system should assign train to platofrms from input(time)
 - should able to return platofrm of train at given time or vice versa 
 - error handling 
    - return error if 2 train comes on same platofmr at same time 
 - extensibility scope 
    - concurrent addition of trains to platorm 

# entities (nouns or something that matters)
- Platform Management System
    - map of <Integer,Platform>
    - map of <train id, treeset<platformLocation>>
    - platform manager 
    - assignmentStartegy

    + assignTrain (stopageRequest , assignmentStartegy) -> boolean
    + private updateTrainStatus()
    + getTrainInfo(TrainCheckrequest)
    + getPlatform info(Stopagerequest);

- platformLocation 
    - platoform platform
    - arrives at 
    - departs at 

 

- stopage
    - train 
    - arrivesAt
    - departsAt

- interface Assignment Startegy
    - assign()


- platform 
    - int id
    - TreeSet<Stopage>

    + isAviaible(request) -> boolean 
    + assignTrain(request) -> boolean

- train 
    - int id





































/** HINTS

Designed classes: Train, Platform, Scheduler, Schedule Manager.
coded using minHeap Startegy and then explained random Assigning Startegy , wrote extensible code
Focused on time-based queries and mapping train schedules.
Did not added all class variables , just explained and just added variables requried for getting answer ( for example , for trian I jus added trian id , not capacity or type )
The interviewer continuously cross-questioned choices and pushed for clean design + working code in 60–75 minutes.


**/
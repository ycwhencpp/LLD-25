# question 
Design Meeting scheduler. There are n meeting rooms. We will keep getting requests for bookings - start time and end time. Allocate any room that is available.

There are N rooms.

We are given a stream of meeting requests (start time, end time, capacity).

We have to assign a room to the meeting if available, considering:

The room must be free during the requested time.

The room must have at least the required capacity.

We must minimize spillage of free time (i.e., use the room that has the least free time that can accommodate the meeting).

We have to store audit logs for each room (when a meeting is scheduled, etc.) and delete audit logs after X days.

- primary capablities
- error handeling 
- scope boundaries 

# requirement gathering 
- should able to schedule meetings at a time if room is availaible  
- keep track of meeting end time and make room free 
- keep 2 queue , one pq and one normal queue 
- use pq to check when meeting ends on request of new meeting and queue to store availaible room 
- multiple time booking for a same room 
- flow :
    - booking request -> aviliable room queue -> ongoing meeting queue -> acknowledge or reject
- store booking information against room for availbility(think about it)
- if meeting room is not avaiable reject the meeting or add in queue?
- store logs 
- check for capacity
- extensibilty
    - priority to meetings or rooms 
    - concurrent booking handling 
    - delete meeting/edit meeting
    - algo to find and assign room 
- *always ask* (what to do if concurrenct modification occurs , try next or return error )
    - if try next 
        - then just return potential data so that u can keep on trying 
    - else 
        - just return single and throw error 

# entities (look for noun or anything that standout)
- Room 
- Meeting
- Meeting Scheduler 
- meeting request

# class diagram (define state and methods that a class holds)
- meeting scheduler 
    - map of id to room
    - auditLogSerice
    - findMeetingRoom Startegy

    + createMeeting(Meeting request) -> booking/boolean
    + isRoomAvaialble(Meeting request) -> boolean
    + updateRoomAvailibiltiy(current Time) -> void

    + getHistory(room id ) -> list of booking

- AuditLogService 
    - Map of <Room, TreeSet<Booking>> history 
    - retentionDays

    + purgeOldLogs(time now)
    + addHistroy(booking)
    + 


- Booking 
    - Meeting 
    - bookingDate 
- Room 
    - int id
    - int capacity
    - TreeSet<Meeting>
    - Reentrant Lock

    + updateStatus() -> void/boolean
    + isAvaiable(Meeting request)

- Meeting
    - start time 
    - end time 
    - Room 
    - id
    - list<user>

    + updateRoom(Room or null) -> void/boolean

- Meeting Request
    - start time 
    - end time 
    - userCount

- findRoomStrategy
    - earliest()
    - largest()
    - mostUsed()
    - frequentlyUsed()

- user 
    - id
    - name 
    -

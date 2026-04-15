import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class main {

}

class MeetingScheduler{
    HashMap<Integer, Room> availiableRooms;
    AuditLogService auditLogService;
    FindRoomStrategy findRoomStrategy;

    public MeetingScheduler(HashMap<Integer, Room> rooms, AuditLogService auditLogService, FindRoomStrategy dFindRoomStrategy){
        this.availiableRooms= rooms;
        this.auditLogService = auditLogService;
        this.findRoomStrategy = dFindRoomStrategy;
    }


    public Meeting creteaMeeting(MeetingRequest request) throws Exception{
       PriorityQueue<Pair> avPairs = findRoomStrategy.findRoom(request, new ArrayList<>(availiableRooms.values()));
       if(avPairs.isEmpty()){
            throw new Exception("no rooms fiund");
       }
       while(!avPairs.isEmpty()){
            Pair pair = avPairs.poll();
            Meeting meeting = pair.room.createMeeting(request);
            if(meeting == null){
               continue;
            }
            Booking booking = new Booking(meeting, 1);

            auditLogService.addHistory(pair.room.id, booking);

            return meeting;

       }

       throw new Exception("no rooms found");       

    }

    public boolean isRoomAvailiable(MeetingRequest request, int id) {
        Room room = availiableRooms.getOrDefault(id, null);
        if(room == null){
            return false;
        }

        return room.isAvailiable(request);

    }

    public List<Booking> getHistory(int roomId){
        return auditLogService.getLogs(roomId);
    }


}

class Room {
    int id;
    int capacity;
    TreeSet<Meeting> meetingsList;
    ReentrantReadWriteLock readWriteLock;

    public Room (int id, int capacity){
        this.id = id;
        this.capacity= capacity;
        this.meetingsList = new TreeSet<>((a,b)->a.startTime - b.endTime);
        readWriteLock = new ReentrantReadWriteLock();


    }

    public Meeting createMeeting(MeetingRequest request){
        if(!this.isAvailiable(request)){
            return null;
        }
        try {
            readWriteLock.writeLock().lock();
            if(!this.isAvailiable(request)){
                return null;
            }
            Meeting meeting = new Meeting(request.startTime, request.endTime, this, 1, request.userCount);
            this.meetingsList.add(meeting);
            return meeting;
        } finally {
            readWriteLock.writeLock().unlock();
        }
        
    }

    public boolean isAvailiable (MeetingRequest request){
        try {
            readWriteLock.readLock().lock();
            if(this.capacity < request.userCount){
                return false;
            }
            Meeting dummy = new Meeting(request.startTime, request.endTime, this, -1, 0);
            Meeting floor = meetingsList.floor(dummy);
            Meeting ceieling = meetingsList.ceiling(dummy);

            if(floor != null && floor.endTime>= request.startTime){
                return false;
            }

            if(ceieling != null && ceieling.startTime<= request.endTime){
                return false;
            }
            return true;
        } finally {
            readWriteLock.readLock().unlock();
        }
        

    }

    public int timeBetweenNextMeeting(MeetingRequest request){
        boolean isValid = this.isAvailiable(request);
        if(!isValid) return Integer.MAX_VALUE;

        Meeting dummy = new Meeting(request.startTime, request.endTime, this, -1, 0);
        Meeting floor = meetingsList.floor(dummy);
        Meeting ceieling = meetingsList.ceiling(dummy);

        int boundStart = (floor != null) ? floor.endTime : request.startTime;
        int boundEnd = (ceieling != null) ? ceieling.startTime : request.endTime;

       
        // idk treeset methods 
         return boundEnd - boundStart;
    }
}

class Meeting {
    int startTime;
    int endTime;
    Room room;
    int id;
    int userCount;

    public Meeting(int startTime, int endTime, Room room, int id , int userCount){
        this.startTime= startTime;
        this.endTime = endTime;
        this.room = room;
        this.id = id;
        this.userCount = userCount;
    }

    //getter setters
}

class MeetingRequest {
    int startTime;
    int endTime;
    int userCount;

    public MeetingRequest(int startTime, int endTime, int userCount){
        this.startTime= startTime;
        this.endTime = endTime;
        this.userCount = userCount;
    }
    
}

class Booking {
    Meeting meeting;
    int scheduledOn;

    public Booking (Meeting meeting, int scheduledOn){
        this.meeting = meeting;
        this.scheduledOn = scheduledOn;
    }
}

interface FindRoomStrategy {
    public PriorityQueue<Pair> findRoom(MeetingRequest request, List<Room> rooms);
}

class Pair{
    int gapTime;
    Room room;
    public Pair(int gapTime, Room room){
        this.gapTime = gapTime;
        this.room = room;
    }
}
class BestFitRoomStartegy implements FindRoomStrategy {
    @Override
    public PriorityQueue<Pair> findRoom(MeetingRequest request, List<Room> rooms){
        PriorityQueue<Pair> availiableRooms = new PriorityQueue<>((a,b)->a.gapTime - b.gapTime);

        for(Room room: rooms){
            if(room.isAvailiable(request)){
                int gapTime = room.timeBetweenNextMeeting(request);
                availiableRooms.add(new Pair(gapTime, room));
            }
        }

        if(availiableRooms.isEmpty()){
            return new PriorityQueue<>();
        }

        return availiableRooms;
    }
}

class AuditLogService{
    HashMap<Integer, TreeSet<Booking>> history;
    int retentionDays;

    public AuditLogService(int retentionDays){
        this.retentionDays = retentionDays;
        this.history = new HashMap<>();
    }


    private void purgeOldLogs(){
        int today = 5; // now
        for(TreeSet<Booking> bookings : history.values()){
            for(Booking booking : bookings){
                if(booking.scheduledOn + retentionDays >= today){
                    bookings.remove(booking);
                }
            }
            
        }   
    }

    public void addHistory(Integer roomId, Booking booking){
        history.get(roomId).add(booking);
    }

    public List<Booking> getLogs(int roomId){
        return new ArrayList<>(history.get(roomId));
    }
}

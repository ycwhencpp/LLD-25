
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


class main{
    public static void main(String[] args) {
        
        List<orderAssignmentStrategy> strategies = new ArrayList<>(List.of(new closestOrderAssignmentStrategy(), new riderIdleTimeAssignmentStrategy(), new orderDelayTimeAssignmentStrategy()));
        List<rider> riders = new ArrayList<>(List.of(new rider(), new rider(), new rider()));
        orderAssigmnetSystem orderAssigmnetSystem = new orderAssigmnetSystem(riders, strategies);
        order o = new order();
        PriorityQueue<riderContext> riderslist =  orderAssigmnetSystem.computeRiderForAssignment(o);
        System.out.println(riderslist);

        rider r = orderAssigmnetSystem.assignRiderToOrder(riderslist, o);
        System.out.println(r);


    }
  
}

class orderAssigmnetSystem{
    List<rider> availiableriders;
    List<orderAssignmentStrategy> strategies;

    public orderAssigmnetSystem( List<rider>  ar,List<orderAssignmentStrategy> s){
        this.availiableriders =ar;
        this.strategies =s;
    }

    public PriorityQueue<riderContext> computeRiderForAssignment(order o) {
        PriorityQueue<riderContext> eligibleRiders = new PriorityQueue<>((a,b)->b.score-a.score);
        for(rider rider : availiableriders){
            if (rider.riderStatus != riderStatus.IDLE) continue;
            int score =0;
            for(orderAssignmentStrategy strategy : strategies){
                score+= strategy.calculateScore(rider, o);
            }
            eligibleRiders.offer(new riderContext(rider,score));
        }
        return eligibleRiders;
    }

    public rider assignRiderToOrder(PriorityQueue<riderContext> eligibleRiders, order o){
        synchronized (this) {
            while(!eligibleRiders.isEmpty()){
                riderContext rc = eligibleRiders.poll();
                if (rc.rider.riderStatus != riderStatus.IDLE) continue;
                rc.rider.riderStatus = riderStatus.BOOKED;
                o.riderAssigned = rc.rider;
                o.orderStatus = orderStatus.ON_THE_WAY;
                return rc.rider;
            }
        }
        return null;
        
    }
}

class riderContext{
    rider rider;
    int score;

    public riderContext(rider r, int s){
        this.rider = r;
        this.score =s;
    }
}

class rider {
    int id ;
    riderStatus riderStatus;
    location location;
    int idletime;
}

enum riderStatus{
    BOOKED,
    OFFLINE,
    IDLE,
}

class location{
    int lat;
    int lon;
}

class order {
    int id;
    rider riderAssigned;
    orderStatus orderStatus;
    location location;
    int waitingtime;
}

enum orderStatus{
    PREPARING,
    CONFIRMED,
    ON_THE_WAY,
    WAITING,
}

interface orderAssignmentStrategy{
    public int calculateScore(rider r, order o);
}

class closestOrderAssignmentStrategy implements  orderAssignmentStrategy{

    @Override
   public int calculateScore(rider r, order o) {
        return 0;
    }


}
class riderIdleTimeAssignmentStrategy implements  orderAssignmentStrategy{

    @Override
   public int calculateScore(rider r, order o) {
        return 0;
    }


}
class orderDelayTimeAssignmentStrategy implements  orderAssignmentStrategy{

    @Override
   public int calculateScore(rider r, order o) {
        return 0;
    }
}


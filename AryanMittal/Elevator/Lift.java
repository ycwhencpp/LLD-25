package AryanMittal.Elevator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import java.util.ArrayList;
import java.util.Arrays;

public class Lift {

    public static void main(String[] args) {
        // 1. Initialize the Arena (Floors 0 to 10)
        List<Floor> floors = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            floors.add(new Floor(i));
        }

        // 2. Initialize Strategies
        ElevatorRoutingStartegy routingStrategy = new FIFO();
        ElevatorSchedulingStrategy schedulingStrategy = new ClosesetElevatorSchedulingStrategy();

        // 3. Initialize Actors (Elevator 1 at Ground Floor, Elevator 2 at Floor 5)
        Elevator e1 = new Elevator(1, routingStrategy, Direction.IDLE, floors.get(0));
        Elevator e2 = new Elevator(2, routingStrategy, Direction.IDLE, floors.get(5));
        List<Elevator> elevators = Arrays.asList(e1, e2);

        // 4. Initialize Orchestrators
        ElevatorController controller = new ElevatorController(elevators, schedulingStrategy);
        Building building = new Building(floors, controller);

        // ==========================================
        // SIMULATION
        // ==========================================
        System.out.println("--- System Initialized ---");
        System.out.println("Elevator 1 is at Floor: " + e1.getElevatorContext().getFloor().getId());
        System.out.println("Elevator 2 is at Floor: " + e2.getElevatorContext().getFloor().getId());

        // Event: Someone on Floor 3 presses the UP button
        System.out.println("\n[EVENT] User on Floor 3 calls the elevator...");
        ExternalRequest request = new ExternalRequest(floors.get(3), Direction.UP);
        building.callElevator(request);

        // Verify the Dispatch Strategy worked (Elevator 2 should get it because |5-3|=2 vs |0-3|=3)
        System.out.println("Elevator 1 pending requests: " + e1.getElevatorContext().getInternalRequest().size());
        System.out.println("Elevator 2 pending requests: " + e2.getElevatorContext().getInternalRequest().size());

        // Event: The Elevator processes its queue
        System.out.println("\n[EVENT] Elevator 2 routing strategy triggers step...");
        Floor arrivedFloor = e2.nextFloor();
        
        System.out.println("Elevator 2 arrived at Floor: " + arrivedFloor.getId());
        System.out.println("Elevator 2 is now: " + e2.getElevatorContext().getElevatoState());
    }
}

class Building {
    private List<Floor> floors;
    private ElevatorController elevatorController;
	public Building(List<Floor> floors, ElevatorController elevatorController) {
		this.floors = floors;
		this.elevatorController = elevatorController;
	}

	public void callElevator(ExternalRequest request){
		this.elevatorController.step(request);
	}

    
    
}

class ElevatorController{
    private List<Elevator> elevators;
    private ElevatorSchedulingStrategy elevatorSchedulingStartegy;


	public ElevatorController(List<Elevator> elevators, ElevatorSchedulingStrategy elevatorSchedulingStartegy) {
		this.elevators = elevators;
		this.elevatorSchedulingStartegy = elevatorSchedulingStartegy;
	}

    public void step(ExternalRequest request){
        Elevator elevator = this.getBestElevator(request);
		elevator.acceptExternalRequest(new InternalRequest(request.getFloor()));
    }

    private Elevator getBestElevator(ExternalRequest request){
        return this.elevatorSchedulingStartegy.getElevator(request, elevators);
    }

    
}

interface ElevatorSchedulingStrategy{
    public Elevator getElevator(ExternalRequest request, List<Elevator> elevators);
}

class ClosesetElevatorSchedulingStrategy implements  ElevatorSchedulingStrategy{

    public Elevator getElevator(ExternalRequest request, List<Elevator> elevators){
		int closesetDistance = Integer.MAX_VALUE;
		Elevator closest = null;
		Floor floor = request.getFloor();
        for(Elevator elevator : elevators){
            ElevatorContext context = elevator.getElevatorContext();
			if(context.getElevatoState() == ElevatorState.MAINTAINANCE) continue;

			if(Math.abs(floor.getId() - context.getFloor().getId()) < closesetDistance){
				closest = elevator;
				closesetDistance = Math.abs(floor.getId() - context.getFloor().getId());
			}

        }

		return closest;
    }


}


class Elevator{
    private int id;
    private ElevatorRoutingStartegy elevatorRoutingStartegy;
    private ElevatorContext elevatorContext;


	public Elevator(int id, ElevatorRoutingStartegy elevatorRoutingStartegy, Direction direction, Floor floor) {
        this.id = id;
        this.elevatorRoutingStartegy = elevatorRoutingStartegy;
        
        this.elevatorContext = new ElevatorContext(floor);
        this.elevatorContext.setDirection(direction);
        this.elevatorContext.setElevatoState(ElevatorState.IDLE);
    }


    public Floor nextFloor(){
		Floor floor = this.elevatorRoutingStartegy.nextFloor(this.elevatorContext);
		this.elevatorContext.setFloor(floor);
		return floor;
    }

	public void acceptExternalRequest(InternalRequest request){
		this.elevatorContext.getInternalRequest().offer(request);
	}

    public void registerRequest(InternalRequest request){
        this.elevatorContext.getInternalRequest().offer(request);
    }


	public int getId() {
		return id;
	}


	public ElevatorRoutingStartegy getElevatorRoutingStartegy() {
		return elevatorRoutingStartegy;
	}


	public ElevatorContext getElevatorContext() {
		return elevatorContext;
	}

	

    
}

interface ElevatorRoutingStartegy{
    public Floor nextFloor(ElevatorContext elevatorContext);
}

class FIFO implements  ElevatorRoutingStartegy{

    public Floor nextFloor(ElevatorContext elevatorContext){
        Direction direction = elevatorContext.getDirection();
        Floor floor = elevatorContext.getFloor();

        Queue<InternalRequest> requests = elevatorContext.getInternalRequest();
        if(requests.isEmpty()) {
            elevatorContext.setDirection(Direction.IDLE);
			elevatorContext.setElevatoState(ElevatorState.IDLE);
            return floor;
        }

        InternalRequest request = requests.poll();
        Floor targetFloor = request.getFloor();

        if(targetFloor == floor){
            elevatorContext.setDirection(Direction.IDLE);
			elevatorContext.setElevatoState(ElevatorState.IDLE);
            return floor;
        }

        if(targetFloor.getId() > floor.getId()){
            elevatorContext.setDirection(Direction.UP);
        } else {
            elevatorContext.setDirection(Direction.DOWN);
        }
		elevatorContext.setElevatoState(ElevatorState.MOVING);
        return targetFloor;

    }
}

class ElevatorContext{
    private ElevatorState elevatoState;
    private Direction direction;
    private Floor floor;
    private Queue<InternalRequest> internalRequest;


	public ElevatorContext(Floor floor) {
		this.floor = floor;
		this.internalRequest = new LinkedList<>();
	}


	public ElevatorState getElevatoState() {
		return elevatoState;
	}


	public Direction getDirection() {
		return direction;
	}


	public Floor getFloor() {
		return floor;
	}


	public Queue<InternalRequest> getInternalRequest() {
		return internalRequest;
	}


	public void setElevatoState(ElevatorState elevatoState) {
		this.elevatoState = elevatoState;
	}


	public void setDirection(Direction direction) {
		this.direction = direction;
	}


	public void setFloor(Floor floor) {
		this.floor = floor;
	}


	public void setInternalRequest(Queue<InternalRequest> internalRequest) {
		this.internalRequest = internalRequest;
	}


    

    
}

class InternalRequest  {
    private Floor floor;

    public InternalRequest(Floor floor) {
		this.floor = floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	public Floor getFloor() {
		return floor;
	}


    
}

class ExternalRequest{
    private Floor floor;
    private Direction direction;

	public ExternalRequest(Floor floor, Direction direction) {
		this.floor = floor;
		this.direction = direction;
	}

	public Floor getFloor() {
		return floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

    
}

class Floor{
    private int id;

	public Floor(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

    
    
}
enum Direction {
    UP,
    DOWN,
    IDLE
}
enum ElevatorState{
    IDLE,
    MOVING,
    MAINTAINANCE
}
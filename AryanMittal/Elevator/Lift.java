package AryanMittal.Elevator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Lift {

	public static void main(String[] args) {
		
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
	}


    public void nextFloor(){
		Floor floor = this.elevatorRoutingStartegy.nextFloor(this.elevatorContext);
		this.elevatorContext.setFloor(floor);
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
            return floor;
        }

        InternalRequest request = requests.poll();
        Floor targetFloor = request.getFloor();

        if(targetFloor == floor){
            elevatorContext.setDirection(Direction.IDLE);
            return floor;
        }

        if(targetFloor.getId() > floor.getId()){
            elevatorContext.setDirection(Direction.UP);
        } else {
            elevatorContext.setDirection(Direction.DOWN);
        }
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
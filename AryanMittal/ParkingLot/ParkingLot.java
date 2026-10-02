package AryanMittal.ParkingLot;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParkingLot {
    
}

class ParkingLotManager{
    private List<Floor> floors;
    private List<EntryGate> entryGates;
    private List<ExitGate> exitGates;
    private TicketManager ticketManager;


    public ParkingLotManager(List<Floor> floors, List<EntryGate> entryGates, List<ExitGate> exitGates, TicketManager ticketManager) {
		this.floors = floors;
		this.entryGates = entryGates;
		this.exitGates = exitGates;
		this.ticketManager = ticketManager;
	}


	public List<Floor> getFloors(){
        return this.floors;
        
    }


	public List<EntryGate> getEntryGates() {
		return entryGates;
	}


	public List<ExitGate> getExitGates() {
		return exitGates;
	}


	public TicketManager getTicketManager() {
		return ticketManager;
	}

    
}

class Floor{
    private int id;
    private List<ParkingSpot> parkingSpots;

    public Floor(int id, List<ParkingSpot> parkingSpots) {
		this.id = id;
		this.parkingSpots = parkingSpots;
	}

	public int getId() {
		return id;
	}

	public List<ParkingSpot> getParkingSpots() {
		return parkingSpots;
	}

    
}

class ParkingSpot{
    private int id;
    private SpotStatus spotStatus;
    private Vechile vechile;
    private ReentrantReadWriteLock readWriteLock;
    private SpotType spotType;


    
	public ParkingSpot(int id, SpotStatus spotStatus, Vechile vechile) {
		this.id = id;
		this.spotStatus = spotStatus;
		this.vechile = vechile;
	}

    public boolean isCompatible(VechileType vechileType){
        if(vechileType == vechileType.CAR && this.spotType == spotType.CAR) return true;
        if(vechileType == vechileType.BIKE && this.spotType == spotType.BIKE) return true;
        if(vechileType == vechileType.SUV && this.spotType == spotType.SUV) return true;

        return false;

    }


	public SpotStatus getSpotStatus() {

        readWriteLock.readLock();
        return spotStatus;

	}

    public boolean isFree(){
        readWriteLock.readLock();
        return this.spotStatus == SpotStatus.FREE;
    }
    
	public Vechile getVechile() {
        readWriteLock.readLock();
		return vechile;
	}

    public boolean setSpotOcupied(Vechile vechile){
        readWriteLock.writeLock();
        if(this.spotStatus != SpotStatus.FREE){
            return false;
        }
        this.vechile = vechile;
        this.spotStatus = SpotStatus.OCCUPIED;
        return true;
        
    }

    public void setSpotFree(){
        readWriteLock.writeLock();
        this.spotStatus = SpotStatus.FREE;
    }

    public SpotType getSpotType(){
        return this.spotType;
    }
    
}

class Vechile{
    private int id;
    private VechileType vechileType;
    private Ticket ticket;


    
	public Vechile(int id, VechileType vechileType) {
		this.id = id;
		this.vechileType = vechileType;
	}


	public int getId() {
		return id;
	}


	public VechileType getVechileType() {
		return vechileType;
	}

    public Ticket getTicket(){
        return this.ticket;
    }

    public void setTicket(Ticket ticket){
        this.ticket = ticket;
    }

    
}

class EntryGate{
    private ParkingSpotFinder parkingSpotFinder;

    public EntryGate(ParkingSpotFinder parkingSpotFinder) {
		this.parkingSpotFinder = parkingSpotFinder;
	}




    public Queue<ParkingSpot> getParkingSpots(List<Floor> floors, VechileType vechileType){
        return this.parkingSpotFinder.findParkingSpot(floors, vechileType);
    }


    public Ticket parkVechile(Vechile vechile, TicketManager ticketManager, List<Floor> floors){
        Queue<ParkingSpot> parkingSpots = getParkingSpots(floors, vechile.getVechileType());


        while(!parkingSpots.isEmpty()){
            ParkingSpot parkingspot = parkingSpots.poll();

            if(!parkingspot.isFree()){
                continue;
            }

            boolean success = fillParkingSpot(vechile, parkingspot);

            if(!success){
                continue;
            }

            Ticket ticket = generateTicket(ticketManager, vechile, parkingspot);
            if(ticket == null){
                continue;
            }

            return ticket;
        }
        System.out.println("Not able to find/generateTicket please try again");
        return null;
    }

    private boolean fillParkingSpot(Vechile vechile, ParkingSpot parkingSpot){
        return parkingSpot.setSpotOcupied(vechile);
    }

    private Ticket generateTicket(TicketManager ticketManager, Vechile vechile, ParkingSpot parkingSpot){
        return ticketManager.createTicket(parkingSpot, vechile, 123);
    }


    

}

enum VechileType{
    CAR,
    BIKE,
    SUV,
}

enum SpotType{
    CAR,
    BIKE,
    SUV
}

enum SpotStatus{
    FREE,
    OCCUPIED,
    NOT_SERVICABLE
}

class ExitGate{
    private ParkingFeeManager parkingFeeManager;
    private PaymentManager paymentManager;


	public ExitGate(ParkingFeeManager parkingFeeManager, PaymentManager paymentManager) {
		this.parkingFeeManager = parkingFeeManager;
		this.paymentManager = paymentManager;
	}

    public Ticket exitVechile(Ticket ticket, TicketManager ticketManager){
    
        boolean success = this.paymentManager.deduct(ticket.getParkingFee()); //maybe payment method in args 

        if(!success){
            System.out.println("Payment failed try again");
            return null;
        }

        ParkingSpot spot = ticket.getParkingSpot();

        spot.setSpotFree();

        ticketManager.closeTicket(ticket);

        return ticket;
    }

    public int calculateFee(Ticket ticket, TicketManager ticketManager){
        ticketManager.updateExit(ticket, 1234);
        int parkingFee = this.parkingFeeManager.calculate(ticket);
        ticketManager.updateParkingFee(ticket, parkingFee);

        return parkingFee;
    }
    
}


class TicketManager{

    public Ticket createTicket(ParkingSpot parkingSpot, Vechile vechile, int entryTime){
        return new Ticket(vechile,parkingSpot, entryTime);
    }

    public void closeTicket(Ticket ticket){
        ticket.setStatus(Status.CLOSED);
    }

    public void updateParkingFee(Ticket ticket, int parkingFee){
        ticket.updateParkingFee(parkingFee);
    }

    public void updateExit(Ticket ticket, int time){
        ticket.setExitTime(time);
    }
}

class Ticket{
    private Vechile vechile;
    private ParkingSpot parkingSpot;
    private int entryTime;
    private int exitTime;
    private Status status;
    private int parkingFee;

    

	public Ticket(Vechile vechile, ParkingSpot parkingSpot, int entryTime) {
		this.vechile = vechile;
		this.parkingSpot = parkingSpot;
		this.entryTime = entryTime;
        this.status = Status.OPEN;
	}


	public Vechile getVechile() {
		return vechile;
	}
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}
	public int getEntryTime() {
		return entryTime;
	}
	public Status getStatus() {
		return status;
	}
	public int getParkingFee() {
		return parkingFee;
	}


	public void setExitTime(int exitTime) {
		this.exitTime = exitTime;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public void updateParkingFee(int parkingFee) {
		this.parkingFee = parkingFee;
	}

    public int getDuration(){
        return exitTime - entryTime;
    }
    
    

    
}


enum Status{
    OPEN, 
    CLOSED
}


class ParkingFeeManager{
    private List<ParkingFeeStrategy> parkingFeeStrategy;


    public int calculate(Ticket ticket){
        int amount =0;
        for(ParkingFeeStrategy startgey : parkingFeeStrategy){
            amount+= this.parkingFeeStrategy.calculate(ticket);
        }
        return amount;
    }

}

interface ParkingFeeStrategy{
    public int calculate(Ticket ticket);
}

class VechileParkingFeeStartegy implements  ParkingFeeStrategy{
    public int calculate(Ticket ticket){
        VechileType vechileType = ticket.getVechile().getVechileType();
        switch(vechileType){
            case VechileType.CAR:
                return ticket.getDuration() * 10;
            case VechileType.BIKE:
                return ticket.getDuration() * 5;
            case VechileType.SUV:
                return ticket.getDuration() * 20;
            default:
                return 0;
        }
    }
}

class PaymentManager{
    private PaymentStartegy paymentStrategy;


    public boolean deduct(int amount){
        return this.paymentStrategy.deduct(amount);
    }

}


interface PaymentStartegy{
    public boolean deduct(int amount);
}

class UPI implements  PaymentStartegy{

    public boolean deduct(int amount){
        return true;
    }
}


interface ParkingSpotFinder{
    public Queue<ParkingSpot> findParkingSpot(List<Floor> floors,VechileType vechileType);
}

class NearestParkingSpotFinder implements ParkingSpotFinder{
    public Queue<ParkingSpot> findParkingSpot(List<Floor> floors, VechileType vechileType){
        Queue<ParkingSpot> spots = new LinkedList<>();

        for(Floor floor : floors){
            for(ParkingSpot parkingSpot : floor.getParkingSpots()){
                if(parkingSpot.isFree() && parkingSpot.isCompatible(vechileType)){
                    spots.offer(parkingSpot);
                }
            }
        }
        return spots;
    }
}
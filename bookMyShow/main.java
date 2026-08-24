import java.util.*;

class main{
    public static void main(String[] args) {
        
        

    }
  
}

class bookMyShow{
    List<theater> theaters;
    searchCriteria searchCriteria;
    bookingSystem bookingSystem;

   
}

class bookingSystem{
    public List<seat> selectSeats(show s , user u, List<seat> seats) throws Exception{
        List<seat> selectedSeats = new ArrayList<>();
        synchronized (this) {
            for (seat seat : seats){
                if (seat.seatStatus != seatStatus.AVAILABLE){
                    throw new Exception("seat already booked");
                }
                seat.seatStatus = seatStatus.BLOCKED;
                seat.user = u;
                selectedSeats.add(seat);
            }
        }
        return selectedSeats;
    }

    public booking bookSeats(show s , List<seat> seats, user u) throws Exception{
        synchronized (this) {
            for(seat seat : seats){
                if (seat.seatStatus != seatStatus.BLOCKED || seat.user.id != u.id){
                    throw new Exception("seat already booked try again ");
                }
                // paymentObj.pay()
                seat.seatStatus = seatStatus.BOOKED;
                seat.user = u;
            }
            return new booking();
        }
    }
}


class user {
    int id;
    userType userType;
    List<booking> bookings;
}

enum userType{
    PREMIUM, 
    NORMAL
}
class booking {
    int id;
    List<seat> seats;
    user user;
    show show;

}

class seat {
    int id;
    seatType seatType;
    seatStatus seatStatus;
    user user;
    int lockedAt;
}

enum seatStatus {
    BOOKED,
    AVAILABLE,
    BLOCKED
}

enum seatType{
    RECLINER,
    NORMAL,
    CORNER
}

class show {
    int id;
    movie movie;
    List<seat> seats;
    int startTime;
    int endTime;

    
}

class movie {
    int id;
    String name;
    movieType movieType;
    int length;
}

enum movieType{
    SCI_FI,
    ROMANCE,
    FICTION
}

class theater {
    int id;
    location location;
    List<show> shows;

}

class location{
    int id;
    String city;
}

class searchCriteria {
    public List<show> findMovies(List<theater> allTheaters, movie movie){
        List<show> avaiableShows = new ArrayList<>();
        for(theater theater: allTheaters){
            for(show show : theater.shows){
                if (show.movie.id == movie.id){
                    avaiableShows.add(show);
                }
            }
        }
        return avaiableShows;
    }

    public Set<movie> findMovieByCity(List<theater> allTheaters, location location){
        Set<movie> avaiableMovies = new HashSet<>();
        for(theater theater: allTheaters){
            if (theater.location.id != location.id) continue;
            for(show show : theater.shows){
                avaiableMovies.add(show.movie);
            }
        }
        return avaiableMovies;
    }

}

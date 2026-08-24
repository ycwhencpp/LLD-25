import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class main{
    public static void main(String[] args) {
        
        
      


    }
  
}

class filterService{
    List<resturant> allResturants;
    usercontext usercontext;
    List<filter> appliedFilters;

    public List<resturant> applyFilter(){
        List<resturant> eligibleResturants = new ArrayList<>();
        for(resturant r : allResturants){
            boolean is_valid = false;
            for(filter f : appliedFilters){
                if (f.matches(r, usercontext)){
                    is_valid = true;
                    break;
                }
            }
            if(is_valid){
                eligibleResturants.add(r);       
            }
        }
        return eligibleResturants;   
    }
}

class user {
    int id;
    userType userType;
    location location;
}

class location {
    String city;
}

enum userType{
    NEW,
    PREMIUM,
}

class resturant{
    int id;
}
class usercontext{
    user user;
    location location;
    Date time;
}

abstract  class filter{
    String name;
    public abstract  boolean matches(resturant r , usercontext uc);
}



enum foodtype{
    VEG,
    NONVEG
}


class vegornonvegFilter extends filter{
    foodtype type;
    public vegornonvegFilter(foodtype type){
        this.type = type;
    }
    @Override
    public boolean matches(resturant r , usercontext uc) {
        return true;
    }
}

class ratingfilter extends filter{
    int minRating;

    public ratingfilter(int rating){
        this.minRating =rating;
    }
    @Override
    public boolean matches(resturant r , usercontext uc) {
        return true;
    }
}


class cityFilter extends filter{

    location location;


    public cityFilter(location location){
        this.location = location;
    }
    @Override
    public boolean matches(resturant r , usercontext uc) {
        return true;
    }

}


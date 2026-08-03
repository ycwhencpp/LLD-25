package CaseStudy;

import java.util.HashMap;
import java.util.List;

public class main {

    public static void main(String[] args) {
        
    }

    
    
}
// usage, user, request, ratelimiter, tier , middleware 

class Usage{
    UsageType usageType;
    int count;
    int lastUpdatedat;
}

enum UsageType{
    DAILY, 
    HOURLY,
}

enum Tier{
    FREE,
    PRO,
    ENTERPRISE,
}

class User{
    Tier tier;
    List<Usage> usage;
}

class RateLimiter{
    HashMap<Tier, HashMap<UsageType, Integer>> tierToUsageMap;
    HashMap<UsageType, rateLimitStartegy> usageTypeToStrategy;

    boolean allowUser(User user){
        for(Usage usage : user.usage){
            boolean isRateLimited = usageTypeToStrategy.get(usage).isRateLimited(user, tierToUsageMap);
            if(isRateLimited) return false;
        }
    }
    
}

interface rateLimitStartegy{
    public boolean isRateLimited(User user, HashMap<Tier, HashMap<UsageType, Integer>> tierToUsageMap);
}

class HourlyRateLimitStrategy implements rateLimitStartegy{
     public boolean isRateLimited(User user, HashMap<Tier, HashMap<UsageType, Integer>> tierToUsageMap){
        synchronized (user) {
            int currenthour =0;
            int maxlimit = tierToUsageMap.get(user.tier).get(UsageType.HOURLY);
            if(user.lastUpdatedat != currenthour){
                user.usage =0;
            }

            if(user.usage >maxlimit){
                return false;
            }

            user.lastUpdatedat = currenthour;
            user.usage++;
            return true;
        }
     }
}


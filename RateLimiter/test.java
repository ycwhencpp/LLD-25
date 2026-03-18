import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

class test{
    public static void main(String[] args) {
        
        RateLimiterService service = RateLimiterService.getInstance();

        RateLimiter fixedWindow = service.getRateLimiter(UserTier.FREE);
        System.out.println("System 1");
        for(int i=0; i<25; i++){
            boolean status = fixedWindow.isAllowed(1);
            System.out.println(status);
        }
        System.out.println("System 2");
        RateLimiter slidingWindow = service.getRateLimiter(UserTier.PREMIUM);
        for(int i=0; i<15; i++){
            try {
                boolean status = slidingWindow.isAllowed(2);
                System.out.println(status);
                Thread.sleep(100);
            } catch (Exception e) {
            }
            
        }

    }
  
}


class RateLimiterService{
    HashMap<UserTier, RateLimiter> userToRateLimiter = new HashMap<>();
    private static RateLimiterService rateLimiterServiceInstance = null;
    RateLimiterFactory rateLimiterFactory = new RateLimiterFactory();
    private RateLimiterService(){
        try {
            RateLimiter fixedWindow = rateLimiterFactory.getRateLimiter(UserTier.FREE, new RateLimiterConfig(1000, 10));
            userToRateLimiter.put(UserTier.FREE, fixedWindow);

            RateLimiter slidingWindow = rateLimiterFactory.getRateLimiter(UserTier.PREMIUM, new RateLimiterConfig(10000, 10));
            userToRateLimiter.put(UserTier.PREMIUM, slidingWindow);
        } catch (Exception e) {
            
        }
        
    }

    public static RateLimiterService getInstance(){
        if(rateLimiterServiceInstance == null){
            synchronized (RateLimiterService.class) {
                if(rateLimiterServiceInstance == null){
                    rateLimiterServiceInstance = new RateLimiterService();
                }
                return rateLimiterServiceInstance;
            } 
        }
        return rateLimiterServiceInstance;
        
    }
    public RateLimiter getRateLimiter(UserTier userType){
        return userToRateLimiter.get(userType);
    }

}

class RateLimiterFactory{

    public RateLimiter getRateLimiter(UserTier userTier, RateLimiterConfig rateLimiterConfig) throws Exception{
        switch(userTier){
            case UserTier.FREE:
                return new FixedWindowRateLimiter(rateLimiterConfig);
            case UserTier.PREMIUM:
                return new SlidingWindowRateLimiter(rateLimiterConfig);
            default:
                throw new InputMismatchException("Invalid inout");
        }
    }
}

abstract class RateLimiter{
    RateLimiterConfig rateLimiterConfig;

    public RateLimiter(RateLimiterConfig rateLimiterConfig){
        this.rateLimiterConfig = rateLimiterConfig;
    }

    public abstract boolean isAllowed(int userId);
}

class SlidingWindowRateLimiter extends RateLimiter{
    ConcurrentHashMap<Integer, Queue<Long>> requestLog = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(RateLimiterConfig rateLimiterConfig){
        super(rateLimiterConfig);
    }
    @Override
    public boolean isAllowed(int userId){
        AtomicBoolean isValid = new AtomicBoolean(false);

        if(!requestLog.containsKey(userId)){
            requestLog.put(userId, new LinkedList<>());
        }
        long curTime = System.currentTimeMillis();
        Queue<Long> logs = requestLog.get(userId);
        while(!logs.isEmpty() && curTime - logs.peek() >= rateLimiterConfig.timeInMs){
            logs.poll();
        }
        if(logs.size()<= rateLimiterConfig.requestCount){
            logs.offer(curTime);
            isValid.set(true);
            return isValid.get();
        }
        return isValid.get();
    }
}

class FixedWindowRateLimiter extends RateLimiter{
    private final ConcurrentHashMap<Integer,Integer> userTorequestCount = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer,Long> userToLastRequestTime = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(RateLimiterConfig rateLimiterConfig){
        super(rateLimiterConfig);
    }
    @Override
    public boolean isAllowed(int userId){
        AtomicBoolean isValid = new AtomicBoolean(false);
        long curTime = System.currentTimeMillis();
        if(!userToLastRequestTime.containsKey(userId)){
            userToLastRequestTime.put(userId, 0L);
        }
        if(curTime - userToLastRequestTime.get(userId) >= rateLimiterConfig.timeInMs){
            userToLastRequestTime.put(userId, curTime);
        }

        if(!userTorequestCount.containsKey(userId)){
            userTorequestCount.put(userId, 0);
        }        
        if(userTorequestCount.get(userId) <= rateLimiterConfig.requestCount){
            userTorequestCount.put(userId, userTorequestCount.get(userId)+1);
            isValid.set(true);
            return isValid.get();
        }
        return isValid.get();
    }
}

class RateLimiterConfig{
    int timeInMs;
    int requestCount;

    public RateLimiterConfig(int time, int requestCount){
        this.timeInMs= time;
        this.requestCount= requestCount;
    }
}


enum UserTier{
    FREE, 
    PREMIUM
}
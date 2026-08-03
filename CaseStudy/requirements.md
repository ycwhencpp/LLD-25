user to tier map 

tier to {
    daily, 
    monthly 
}


user to usage map


// update this map whenever dynamic tier update or user update comes 
// 2 ways clear all user cache whenever tier data changes (makes sense)
user(in cache) -> {

    tier -> 
    usage -> 
    maxcap ->
}



usage, user, request, ratelimiter, tier , middleware 


fucntion(request){
    user = request.user;
    boolean isuserRateLimited = isuserRateLimmited()
    if(isuserRateLimited) return {
        429,
        reached max limit for daily/monthly quota 
    }
    user->{
        usage;
        maxcap;
        tier;
    }
}
boolean isuserRateLimmited(user){
    for(duration : maxcap){
        if(usage>duration) return false;
        user.maxcap.duration++;
        
    }
    return true;
}



TABLES 
would have used these tbales 



User 

-id 

- tier_id 

- status

- type





user_usage_limit 

- id 

- user_id

- count 

- usage_type

- last_updated_at

- api_id (if limit was per api) not needed as of now 



tier 

- id

- type 



tier_max_limit

- id 

- tier_id

- limit_type 

- max_count


relationship 

user to user_usage_limit will be 1 : N 

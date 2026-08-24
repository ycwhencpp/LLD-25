# question 
Design rate limiter 

# requirement gathering 
1. should suport multiple configs
2. should support diff rate limiter startegy
3. should support concurrent requests 
4. supports diff type of user and respective rate limiter 

# happy flow 
1. requests comes into system , gets redirected to rate limiter based on user tier
2. check if request is valid , else reject(429)


# entities 
1. rate limiter service
    - singelton 
    - facory
2. rate limiter config 
3. abstract rate limiter 
    - config
       - sliding widow 
       - fixed window 
       - leaky bucket 
       - token bucker 
4. rate limiter factory 


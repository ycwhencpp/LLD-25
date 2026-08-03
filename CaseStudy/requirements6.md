Design schema for API Rate Limiter.

Questions

How would you support

free tier
premium tier
enterprise

How would you support

per user

per API

per IP

per Organization

How would you avoid joins?


# fucntional requirements 
 - per user rate limit applies  
    - per api
    - per ip 
    - per org 
 - based on subscription

# non functional requirements 
    - should be fast 
    - should be durable 
    - concurrent request 

# entites 
- user 
    - id
    - tier 
    - ip
    - org_id

- Tier 
    - id 
    - enum [premuium, free, enterpirs]

- TierRateLimit 
    - tier_id
    - api_id | nullable 
    - type[user, ip, org]
    - windowsize in seconds 
    - maxLimit

- API 
    - name 
    - path
    - method

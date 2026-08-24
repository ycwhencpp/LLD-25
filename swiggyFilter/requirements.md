# question 
Write APIs and LLD for the filters in Swiggy application (both visible by default and those generated on demand)

# requirement gathering 
1. 2types of filter default and on demand 
2. what are default filters 
    a.veg or non veg 
    b. rating 4+
3. what are dynamic filter 
    a. based on city (distance from ur location)
    b. time of day
    c. cusinine availaiblity
    d. new resturants around me 
4. what are functional requierments 
    1. fetch default and dynamic filters 
    2. apply together 
    3. order independent (ask or think)
    4. are filter AND or OR


# happy flow 
1. we have user info (type, location) for now 
2. we will fetch default and on demand (based on user context )
3. user can apply filters (together)
4. we can have mock resturant data and then we compute eligible restuant based on (OR or AND) condtion 

# entities 
1. user 
2. location 
3. user type 
4. filters (abstract) -> [on demand, default] (they will vary as they all will have diff logic )
5. filter type abstract (checkbox, mutliselect) (they will vary as they all will have diff logic )
6. restutnat for now concrete (coz we need just to mock)
7. filter serivce contains user and filters and have api apply filters and return eligible resturant data also have list of applied filters 


# Design schema for Uber.



# functional requirements 
- store user data
- trip data 
- driver data
- rating
- payment


# entites 
- user 
    - id 
    - name 
    - type( user, admin)

- driver 
    - user_id
    - status[online | offline | in_ride]
    - vechile_number 
    - rating 

- user_trips
    - user_id
    - trip_id 
- trips 
    - id
    - user_id
    - driver_id | null
    - status(waiting, searching, driver assigned, completed, cancelled)
    - started_at
    - completed_at
    - driver_assigned_at

- trip_estimate
    - trip_id
    - ETA
    - amount 
    - distance
    - pickup
    - dropoff

- driver_trips 
    - user_id
    - trip_id

- trip_payment
    - trip_id
    - payment_type
    - status

- driver_rating 
    - trip_id
    - rating
    - driver_id
    - ratedby user id

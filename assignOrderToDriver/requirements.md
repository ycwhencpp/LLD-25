# question 
Build an auto assignment system for Swiggy's delivery fleet. Given list of orders and delivery executives, output assignments optimized for: First mile distance, DE waiting time, and Order delay time.

# requirement gathering 
1. assign rider to orders 
2. various optimzation technique 
    a. closeset one 
    b. rider waiting time or idle time 
    c. order delay time 
3. we have to use all optimzation or anyone or dynamic ?
4. no same driver should be assigned 2 orders in one go 
5. return driver to order map ?

# happy flow 
1. user places order (not releavnt here) so we will have list of orders and list of riders 
2. then we have to compute each order to rider weight based on optimation technique and then choose the best one for each order 
3. what we can do we can create order to priority queue in a map so that even if de rejects we ca pick next one for that order instance , 
4. we can have multiple startegy for optimzation and then we can compute weight based on each of them 
5. to handel no driver assigned 2 orders when consturcted final map and choosing rider we can check his status if (booked) we can ignore him and choose next one 

# entities 
1. rider 
2. ridestatus 
3. order 
4. order status 
5. order assignment startegy 
6. order assignment serivce 
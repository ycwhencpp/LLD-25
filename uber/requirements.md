# question 
desing LLD for uber 

# requirement gathering 
1. user should be able to book cab/auto/bike
2. driver should be able to see the booking and accept or decline 
3. show driver booking sorted by diff startegy(distance b/w driver and user , wait time )
4. no 2 driver accpets same booking 
5. user lifecycle : REQUESTED -> ACCEPTED -> ONGOING -> COMPLETED/CANCELLED
6. rider lifecycle : OFFLINE->ONLINE->BUSY/IDLE 

# question to ask 
1. surge pricing ?
2. same city or multi city ?
3. one rider per trip ?

# happy flow 
1. user requests ride , new trip comes into system 
2. use diff strategies or combination of statrigies to pick driver for the trip , 
3. use driver context (driver, score) and insert it in Priority queue , and then try to assign driver 
4. once assigned change state of trip and driver 

# entities 
1. user 
2. userState 
3. driver 
4. driverState
5. trip
6. tripAssignmentStrategy
7. uber system 
8. driver context 
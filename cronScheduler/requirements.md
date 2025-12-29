# entities
1. cron scheduler 
2. job 
3. user 
4. job repo 
5. executor (interface currently on unix based) 
6. jobstatus 


# plan 
 --for plan go from top to bottom 
 we have cron schudler which will contains job repo and gives us job based on timings 
 and also we have executor (dicey a lil)
 then inside job we will have command and time 
 
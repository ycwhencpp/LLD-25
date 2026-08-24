# question 
Design a Job scheduling strategy to schedule and execute jobs based on priority(LOW,MEDIUM,HIGH) and based on request(adhoc/periodic).
The scheduling need to accept both background requests and forerunning requests.

# requirement Gathering 
1. system should be able to schedule/excute job 
    - based on priority
    - based on request[adhoc/periodic] (manual/cron)
    Question -> job is scheduled at 9PM and we executed manully at 8:58 so do we have to choose it as diff job or it will overwrite 9PM job ?
    also what is meant by need to accept both background requests and forerunning requests.
    - should route request for forerunning and bg request
2. error handling 
    - jobs should be routed accordingligly to their request type 
    - idempotent jobs (on demand)

# entities 
1. Job Scheduling platform 
2. Job Scheduler 
3. Job 
4. JOb_TYPE 
5. metdata 
6. priority

# class diagarm 
1. JobSchedulingPlatofm
    - pq<Job> (sorted by priortiy and then timestamp) scheduledJobs 
    - pq<job> (sorted by priortiy and then timestamp) active jobs 

    + scheduleJob(Job)
    + executeJob(Job) check on pq.peek() do we have same job id and timestamp (idempotent key)

    + private function excute()
        - excuteAll()
        - exutee(current)
    + private executeCurrent()
        -> while(!activejobs is Emty)
    + private executeAll()
        -> while(!scheduledJobs is Empty)
    
2. Job 
    - JOb_Type 
    - scheduled_at
    - metdata 
    - idempotent key
3. metdata 
    - id 
    - name 


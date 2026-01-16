# question 
Design a Notification System
    Supports Email, SMS, Push

    User preferences (enable/disable per channel)

    Retry on failure

    Easy to add new channels


# requirement gathering 

1. system should be able to trigger diff type of notification based on their own logic 
2. check user prefernce brfore sending notification (maybe commoon for validations)?
3. add in queue or something if !sucess
4. extensible by strategy simply 
5. startegy will have send functuon whihc will trigger notification 

# questions to ask 
1. should this follow any order ?
2. do they have to go through multiple channels or just one ?
3. retry how many times ?
4. per channel retry or global ?
5. prefernce channel wise or notification type wise ?



# happy flow 

1. system sends signal for notification generation with list of users 
2. system will have list of notification and will trigger send fucntion for each 
3. validate user setttings/ prefernce for the same 
4. add it in queue which will be processed later 


# entities 
1. notification system 
2. notification startegy (interface)
3. user 
4. notiicationType
5. validation system 
6. user prefernce 
7. notification status 
8. retry system ;
9. notification Factory





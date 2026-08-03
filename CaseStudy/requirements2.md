design in memory pub-sub 


# functional requirements 
- there should be publishers 
- there should be subscibes 
- subscribers can subscribe to multiple topics 
- topics can have multiple subscribers  
- on change/something publishers publish msg to topics and subscribers subsribed to topics can see it  
- pub/sub is fire and forget so no retrying 



# non functional 
- low latency 
- fire and forget atleas once delivery 
- concurrent , thread safe 
- publisher speed is greater than subscriber consuming it ?? handle 

just for this i will draw menatal model 

publishers -> [pub/sub] -> subscribers 
topic 1 -> s1, s2
topic 2 -> s2, s3


# entites 
- Topic 
- Publisher 
- Subscriber 
- pub/sub 
- message 

 thinking out loud (pub/sub will have infor about subscribers susbcibed to topics )
 and publishers publishing to topics 

# class diagram 
 - publisher 
    + publish(Message, topic)

- subscribers 
    + Event() 

- Topic 
    - name
    - blocking concurrent queue<Message >
    + brodcast(Message) -> always running while true

- pub/Sub
  - Map of topic to subscribers 
  
  + publish(Message, topic)

- message 
    - data 




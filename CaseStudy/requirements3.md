design a mini Redis

# functional requirements 
- system should be able to store, retrive and expire keys 
- should it have eviction policy on max lengt reahc ?

# non functional requirements 
- low latency 
- support concurrent updates


# entities 

MiniRedis 

    - concurrent map of dll node to key
    - map of lock to 
    - DLL service
    - bg cleaner serice

    + get()
    + set()
    + expire()

DLL service 
    - DLL 
    - reentrant lock
    + map of node to reentratn lock
    + update()
    + delete()
    + append()

bg cleanaer service 
    + expire(DLL head)

DLL 
    - next 
    - prev 
    - key
    - value
    - expireAt
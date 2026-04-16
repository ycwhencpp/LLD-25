# question 
Design an In-Memory Pub-Sub (Publisher-Subscriber) System.

Publishers should be able to publish messages to a specific "Topic".

Subscribers should be able to subscribe to specific Topics. When a message is published to a Topic, all its subscribers should receive the message.

Multithreading Requirement: The system must be highly concurrent.

A Publisher should not be blocked from publishing just because a Subscriber is slow at processing the message.

Multiple Publishers and Subscribers will interact with the system simultaneously on different threads.

Provide a working, executable solution.


# requirement 
    - publisher publish msg and to a topic and it will sent to subscriber through broker 
    - basic validation check 
    - what if publishing speed is greater as compare to subcribiver pulling it 

# entities 
    - publisher 
    - topic 
    - message 
    - subsciber 
    - broker 

# class diagram 
    - publisher 
        - list<topic> // might not be
        + publish(mesage, topic)
    - broker 
        - topic to subsriber map // or deligate 
        - id to topic map 

        + publish(topic, message)

    - topic 
        - hashmap of id to subcriber 
        - blocking concurrent queue <message>

        + start() -> always running while true 
    - message 
        - data 
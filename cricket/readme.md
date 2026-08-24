# questions 
- cricket score engine 
- embed 

- i/p vs o/p 
- differncet games 
- library 
- might be for developers as well 

# primary capablties, error handling, scope boundaries 
- user/system can view update of a game 
    - state of the game 
        - in progess -> detailed ingo 
        - ended -> ended and summary
        - 

    - score 
    - status 
    - info 

    - in prrogress 
        - update score / and info 
    
    - 

intialize -> data in/out -> 
-> scoreboard 
-> observer pattern ->send info to scoreboard wheneerv

-> scorebaord 
    teams , bowing/batting , score 

    3rdapi ->

-> []
-> 

# entities 
    - scorebard 
    - teams
    - score system 
    - game 
    - game status

# class implemtiaion (mehtods, state)

 -  Scoring Engine 
    - List<Games>
    
    + getcurretnScore(GameRequest) -> scorebard 

- Game 
    - Scoreboard 
    - status 
    - UpdateScoreEngine (3rd api)

    + getScoreboard()
    + setScoreboard()
    + updateScoreboard(UpdateRequest obj)



- UpdateScoreEngine impleemt obseeevr calls 
    - update()

interface Observer{
    public void update()
}

class UpdateScoreEngine implenet Observer {
    - externalApi 
    public void update(scoreboard, update){
        //update scoreboard
    }

}



Second problem was designing Restaurant menu which contains dishes and each dish has a name, price and list of ingredients its made up of. There were 6 functionalities which were expected from me to be implemented -

Add a dish to restaurant menu

Remove a dish from restaurant menu

Add an ingredient to a dish

Given a list of dishes ordered, return the total amount of bill taking in account tax.

Given an ingredient, return the list of all the dishes containing that ingredient.

Return all the dishes served in a restaurant.



- menu 
    - map of id to dish -> fast removal and acess 

- dish 
    - name 
    - price
    - map of id to ingredient -> fast removal and acess 

- order 
    - list of dish 

    foreach dish call price() method of each 

Given an ingredient, return the list of all the dishes containing that ingredient.
 for this loop through menu 
    dish and check if they contains ingrident or not in mao is yes add 





# question 
The Reconstructed Interview Question
Design an In-Memory Pub-Sub (Publisher-Subscriber) System.

Publishers should be able to publish messages to a specific "Topic".

Subscribers should be able to subscribe to specific Topics. When a message is published to a Topic, all its subscribers should receive the message.

Multithreading Requirement: The system must be highly concurrent.

A Publisher should not be blocked from publishing just because a Subscriber is slow at processing the message.

Multiple Publishers and Subscribers will interact with the system simultaneously on different threads.

Provide a working, executable solution.

- message 
- publisher 
- susbscriber 
- topic

- pub sub 


pub-sub
    - map of id to topic


- publisher 
    - publish(message, topic)

- subsriber 
    - list <topic> subscribed 
    - on Message(topic, message)

- topic 
    - list<subscriber>
    - queue<message>


# question 
Design a Circuit Breaker. The question slighly threw me off because the requirements looked like a rate limiter combined with a circuit breaker. The breaker opened when the number of requests (NOT number of errors) exceeded a threshold. Was able to solve it, come up with test cases, show OPEN, CLOSED, HALF_OPEN states and answer concurrency questions properly.


circuit breaker should allow request if < threshold 
should close and reject if >= threshold 
states -> open -> half open -> closed 


circuit breaker {
    AtomicInteger requestCount = new AtomicInteger(0);
    state = OPEN 

    public void request(){
        if(requestCount.get() == threshold/2){
            state = Hald_closed;
            update if required // notify
        } else if (requestCOunt.get() == threshold){
            state = CLOSED
            //update
            return;
        }
        requestCount.setAndUpdate(requestCount+1);
    }
}
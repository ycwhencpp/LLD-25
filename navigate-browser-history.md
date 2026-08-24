# question 
Design a system to navigate browser history.** You need to support:
    visit(url)** — Clears forward history, visits a new URL.
    back(steps)** — Moves back up to N steps in history.
    forward(steps)** — Moves forward up to N steps in history.


# requirement gathering
1. system should store browser history 
    - temp history of current iteration
2. support visit new url 
    - log in history
    - clear temp fwd history 
3. suppport back 
    - move back upto Math.max(N, temp.size()) steps
4. suuport forward 
    - move forward Math.min(N, temp.size()) steps 
5. error handling 
    - out of bound steps 
    - proper logging of temp + immutable history 

# Entities 
1. BrowserHistory 
2. History 

# class diagram 
1. BrowserHistory 
    - List<History> ledger 
    - List<History> temp
    - int currentPointer 
    - int endPointer

    + visit(String url)
        ledger.add(url)
        if(currentpointer == endPointer){
            temp.add(url)
            currentpointer++;
            endPointer++;
        } else {
            currentpointer++;
            temp.set(currentpointer, url);
        }
        endPointer = currentpointer

    + forward(int steps)
        moved_steps = Math.min(currentPointer + steps, temp.size())
        currentPointer = moved_steps
    + backward(int steps)
         moved_steps = Math.max(0 , currentPointer-steps)
         currentPointer = moved_steps

2. History 
    - visited_at
    - url 

# now improvemtns 
 - we can create temp history movement startegy and abstarct the logic there as well (but will it be over enginnering)

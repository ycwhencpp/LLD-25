# question 
build a 2 player connect 4 game , player take turns dropping discs into 7 column 6 row board , the first one to align 4 of their own discs , horizontally, vertically or daigonally wins 

1. primary capiblities 
2. error handling 
3. scope bondaries 
# requirements

1. player should play turn by turn 
2. disc should be dropped in lowest row in the choosen column 
3. a game ends when 
    - either 4 discs of same player is aligned in (horizontal, vertical , both diagonals)
    - all cells filled or cant place more than 4 discs
4. invalid moves should be rejected clearly:
    - dropping in full column 
    - dropping in higher row of a column when lower row is still empty 
    - moving when game is over 
    - moving out of turn 
5. out of scope 
    - ui 
    - concurrent games 

6. extensibility : 
    - is it 2 player or more than 2 player (for now 2 player could be more than 2 in future)
    - fixed sized grid (for now yes might exntend in future )


# entities (look for noun or things that standout)
1. player 
2. board 
3. disc 
4. game 

# class design (where we define what state a entitie holds and what methods it exposes )
- try top down 
- go through requirements and try to derive state and methods 

1. game 
    - board 
    - game status(ACTIVE, FINISHED, DRAW)
    - queue<players>
    - switchPlayer()
    - placeDisc(int row, int col) throws error 
    - isGameCompleted() //check after every move 

2. board 
    - cell[][] grid 
    - placeDisc(int row, int col, player player)
    - isValidMove(int row, int col)
    - isGameCompleted()
    - 
3. player 
    - int id
    - disc disc

4. cell
    - disc disc 
    - setDisc()
    - isEmpty()
5. disc 
    - int id

# Implementation 
 - define the core logic 
 - consider edge cases 
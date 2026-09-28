# question 
Design Chess 

# requirement gathering 
- player should play turn by turn 
- game should be 8 X 8 board 
- 16 pawns , 2 king , 2 queen , 4 rook, 4 bishop, 4 knight (black and white equally)
- peices should move according to their capablities 
- peice should be able to kill each other
- error handling 
    - check incorrect moves/out of bound moves 
    - same player playing twice 
    - no 2 peices should stay at same cell 

# entities 
- player 
- chess game 
- board 
- peice 
- peice moving startegy 
- gameState 
- symbol
- cell 
- move

# helpers 
- startegy for peice movements 
- factory for creation 
- singelton for game

# class diagram 
- player 
    - id 
    - symbol
    - playerStartegy

    + cell makeMove()
- chessGame 
    - Deque<player>
    - board 
    - peiceFactory
    - winner 

    + startGame()
    
- board 
    - peice[][]
    - gameState

    + boolean placeMove()
    + displayBoard()
    + getGameState()

- abstract peice 
    - color 

    +cell  move()

- cell 
    - int r 
    - int c 
    - peice | nullable 

- move 
    - start cell 
    - end cell 
    - player 
    - peice killed 
    - peice moved 


- gameState[NOT_STARTED, IN_PROGRESS, COMPLETED]

- symbol [BLACK, WHITE]

- peiceType ]



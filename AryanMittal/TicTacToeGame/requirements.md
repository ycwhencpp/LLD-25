# question 
Design Tic Tac Toe 


// primary capablities, error handling 
# requirement gathering 
 - 2 user should be able to play the game 
 - game board should be configurable 
 - user should choose symbol and play turn by turn 
 - game ends when either of the symbol got N in the row (diagonal, col, row)
 - game should be draw when no cell is left to place symbol 
 - error handling 
    - placing symbol on non empty cell or cell out of bounds 
    - game stops when cells are filled 
    - no player should play twice 

# entities 
- tic tac toe Game 
- cell 
- player 
- symbol 
- board 
- game state 

# class design 

1. Tic Tac Toe Game 
    - board 
    - List<Player>
    - gameState [PROGESS, DRAW, COMPLETED]
    - currentWinner 

    + startGame()

2. Board 
    - cell[][]

    + placeMove(Cell, symbol)
    + checkWinState()
    + checkDrawState() 

3. Player 
    - Symbol
    - id 
    - playerStartegy

    + makeMove() -> Cell

4. GameState enum [[IN_PROGESS, NOT_STARTED, COMPLETED]]

5. Cell 
    - row 
    - col 
    
    + getRow()
    + getCol()

# thoughts 
- player can have multuple startegy to make move 
- game could have factory to create player 
- game should be singelton 
- game should notfify observer on state change 
- game should be interface 



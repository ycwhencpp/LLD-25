# question 
design Snake and Food 

# requirement gathering 
1. single user game 
2. snake should grow when he eats food 
3. game will over when snake either touches boundary/himself
4. food should be there one by one 
5. error handling 
    - snake not growing after eating food 
    - snake when moving should leave tail spot and qcquire head spot 
    - snake cant go in opposite direction 

# entities 
2. food 
3. game 
4. board
5. foodType
6. user 
7. GameState

# thinking out loud 
1. how to capture snake movement 
    - keep a deque 
        - if snake eats food dont remove val from tail since len+1
        - else add in head and remove in tail since displace 
2. food (could be of multiple types)
    - create interface with getNext()
        - for all food at once getNext() will give from predefined grid 
        - for one food at time will give random cordinates 
        - for expiry food with double points will have time and points as well 
3. how snake will eat food 
    - create snakeEatingStaretgy with consume(food)
        - board will have this and call this and based on food_type 
        - we can add points and check logic for food 

# class design 
1. game 
    - board
    - user 
    - score 
    - timer
    - snake 
    - FoodMachineStartegy
    - GameState



    + consumeFood()
    + moveSnake()
    + start()
    + getScore()

2. board 
    - cell[][] grid
    
    + displayBoard()

    + isGameOver(cordinates )
    + validateMove(curdirection, new direction)
    

3. abstract Food 
    - cordinates 
    - food_type
    - points 
    + consumeFood(Food, timer)

4. NormalFood 
    - points = 1
   
5. FoodwithExpiry
    - expiresAt 
    - points = 2

6. Cell
    - cordinates
    - Food 

7. Snake 
    - Deque<cordinates> snake
    - snakeEatingStaretgy








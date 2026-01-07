# question 
Design a Wallet System where users can 
 a. spend money 
 b. add money 
 c. view balance 
 d. view transcation history 


# requirements gathering 
1. add concurrency check (no negtaive balance)
2. validate money deduct logic 
3. add money through diff strategy 
    - upi
    - card
    - netbanking 
4. transcation rollback on failed 
5. * always mention IDEMPOTENCY when things are related to money like system *
6. how to view transaction history , filter logic (should we make strategy for it ) but its just filtering so sort it accordingly no startegy 

# happy flow 
1. user have wallet system 
2. he adds money
3. he spends money (payments failed so rollback )
4. he views balance 
5. he checks history 

# entities 
1. user 
2. wallet system 
3. transcation 
4. transcation status 
5. add money strategy 
6. transcation history 
7. user status 
8. wallet 
9. TranscationType


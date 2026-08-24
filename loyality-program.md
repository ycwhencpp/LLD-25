# question 
Design a loyality program system for Amazon Fresh shoppers, that rewards customersfor their shopping behaviour, manages point allocation and handles tier based benefits through a points wallet.   Vairious tierslike: Silver, Gold, Platform tier.and a redemption system

1. core responsisbilities 
2. error handling 
3. scope boundaries 

# requirement gathering 
1. loyality program system 
    - reward customer [daily, weeekly, monthly shooping baheaviour]-> reward (10%, 20% ...)
    - manage point allocation (points on conditions)
    - handle tier based benefit (if gold give 10%, if silver 5% and no delivery fee )
2. manage points wallet 
    - info about credit/debit through source /transcation
3. manage redemption system 
    - on reddem check tier and reddem or what(to be confirmed by interviewer)
4. error handliing 
    - 2 benefits at same time ??
5. scope boundaries 
    - extend to other loyality features 

# focus on by interviewer 
Point Allocation (The Rules Engine): When a user completes an order, they earn points. The number of points depends on their Tier (Silver earns 1x, Gold earns 1.5x, Platinum earns 2x).

The Wallet (Concurrency & Ledger): As you rightly pointed out, it should be a ledger of credits and debits. I want to see how you model this so that we can accurately track the balance.

Redemption (Thread Safety): Users can apply points to their Amazon Fresh cart to get a discount (e.g., 100 points = $1).


# entities 

1. Loyality Program System 
2. User 
3. Point 
4. order 
5. User Tier 
6. Wallet ->[Money Wallet , Points Wallet]
7. Transcation 
8. Transcation Type 
9. Cart 
10. Items 

# class diagram 
1. Loyality Program System 
    - List<PointAllocationStartegy>
    + [Points] computePoints(User, Order)
2. User 
    - name 
    - TierType
    - HashMap<Wallet_Type , Wallet>
    + checkBalance(Wallet_Type)

3. Wallet <<Interface>>

    - showBalance()
    - addCredit(Credit)
    - deductCredit(Credit)
    - showHistory()

4. Points Wallet implements Wallet 
    - ReentrantLock
    - List<Transcation> history
    - PointsBalance 

5. Credit <<abstract>>
    - int amount 
    - expire_at

    + is_expired()
    + getAmount()
6. PointCredit implements Credit 
7. PointAllocationStartegy <<interface>>
    + compute(User, Order)

8. TierBasedStaretgy implemets PointAllocationStartegy
9. OrderTypeBasedStatefy implemnts PointAllocationStartegy


10. Transaction 
    - type [credit, debit]
    - credit
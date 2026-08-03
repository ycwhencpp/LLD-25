design LLD of reward system with schema, API design, API logics etc.


similar to amazon/cred/swiggy

# functional requirements 
- users can earn point on order placement, refferal, payment 
- users can view history(credit/debit)
- no double credit 
- use of expire credit 
- extensibility 
    - multiple other sources
    - use as flat discount 
    - fast lookup 

# non functional
- consistent balance 
- locking during credit+debit to prevent double usage 
- expire credit on TTL 

# entities 
    - user 
    - wallet
    - RewardPoint 
    - Order 
    - Refferal
    - Rewardhistory

    - Transcation ( Credit/Debit )

# class diagram 
    - User 
      - name 
      - Map of <WalletType to Wallet>

    - WalletType 
        - Reward, Payement, AirMiles 

    -  abstract Wallet 
        - balance 
        - Map<Id, Transcation> history
        - Rentrant Lock
        + credit()
        + debit()
        + showbalance()

    - Expirable Wallet 

        - PQ of <CreditBucker> activeCredits 

        + exire() can be separted as well using another interface of maintaibleWallet
    
    - Standard wallet 

    - CreditBucker
        - remainingValue 
        - expires At
        

    - Transcation 
        - type 
        - value 
        - expiresAt (null for normal amount) which is fair since some reward points never expire as well

    - Transcation Type 
        - DEBIT, CREDIT, EXPIRED 

    
    - RewardSystem 
        - Map of <Id, User>

        + credit(walletType, TranscationRequest)
        + debit(walletType, TranscationRequest)

        + showBalance(walletType)
        + showHistory(walletType)
        + expireReward(walletType)

        
    - TranscationRequest
        - value 
        - expiresAt (null)
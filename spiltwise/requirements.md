# question 
desing Spiltwise

# requirement gathering 
1. user can add/remove friends
2. user can create/delete group
2. user can add/manage friends inside group
3. user can create expenses (group, individual)
4. user can use multiple split startegy while creating expense 
5. user can check balance or balance sheet 
    - global level
    - group level 

# happy flow 
1. user adds friends
2. ceates group 
3. create expenses 
4. system updates expenses among users and balance 
5. system maintains group level and global level balance sheet 


# entities 
1. splitwise 
    - user controller
    - group conteoller 
    - expenses controller 
    

2. user 
   - id
   - balance sheet
3. group 
    - id
    - list<user>
    - balance sheet
    - expenses controller
    - list<expenses>

4. expenses
    - id
    - list<split>
    - paid by 
    - split type 
    - total amount
5. split
    - id
    - amount
    - percentage
    - owed by (user())
6. split startegy ??
    - validate()
    - calulate()
        - percentage
        - equal
        - unqual
7. user balance sheet 
    - map of <user, balance>
    - total owe
    - total return

8. balance 
    - int owe money
    - int return money

9. group balance sheet  
    - map of <user, <user, balance>>
    - total spend


// now controllers 

8. user controller 
    - list<user>
    - crud op 
9. group controller 
    - list<group>
    - crud group op  
10. expenses controller
    - balance controller 
    - list of split strategy or have split factory
    - crud
11. balance  controller 
    - crud
    - user conteoller
    - group controller
    - update updateuserbalancesheet()
    - update update groupbalancesheet()
       - will internally call userbalancesheet()
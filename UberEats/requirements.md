# question 
Design the core classes and interfaces for a cart & pricing engine for a food delivery marketplace (e.g., Uber Eats).

The final checkout price is not simply the sum of menu items. It is dynamically computed based on:

Item customizations : add/remove ingredients, size upgrades, dietary substitutions. Each customization may add $0 or an extra cost.
Marketplace dynamics : e.g., surge pricing as a multiplier applied to a subtotal (e.g., 1.2×).
Promotions : coupon codes such as $5 off , 10% off , Buy 1 Get 1 Free (BOGO) , and possibly tiered delivery fees.
Membership benefits : e.g., subscribers get 0 delivery fee and 5% off eligible items .

- primary capablities 
- error handling 
- scope boundaries
# requirements 
 - should be able to add prodcut in cart 
 - product will have item , quantity
 - should be able to add/remove customizations (decorator)
 - should have multiple pricing startegy 
 - should have multiple coupon startegy or decorator or simple enum(type of coupon) 
 - can we combine coupons?
 - throw exception if user tries to stack coupon ?
 - membership coupon + any other coupon allowed ?
 - show receipt with final price 
 - extensibility 
    - coupon stacking
    - conurrent operations on cart (add/remove same obj)
    - new pricing rules 

# entities (noun or somethign that standout)
- cart
- menuItem
- item 
- pricing startegy
- coupon 
- user type 
- receipt 
- user 

# class design (method and state it exposes )
- cart 
    - list<coupon>
    - user 
    - list<item>
    - pricingEngine
    - discountEngine


    + applyCoupon(coupon) ->boolean
    + calculateDiscount() -> int
    + calculatePrice() -> int 
    + checkout() -> receipt
    + caclulateBasePrice() -> int 

- discountEngine
    + calculateDiscount(list<coupon>) -> int

- pricingEngine 
    - list<pricingsteps>

    + caclulcatePrice(list<items>)

- interface decorator
    - caluclate()

- menuItem extends decorators
    menuItem currItem;
    - baseprice 
    - id
    - name

- item 
    - menuItem 
    - quantity
    - amount

- user 
    - id
    - userType [regular, premium]

- receipt 
    - base price
    - final price
    - hasmap<name, int> pricing steps  
    - discount price 

    + show() -> void


/**

decorator pattern - so simplydo this 




**/
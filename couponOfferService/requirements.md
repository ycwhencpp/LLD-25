# question 
Design a coupon service with use cases: 1) given user_id, user_type, tnx_amount return list of coupons 2) given a coupon & order_amount, payment_method, validate the coupon. System should be extensible to add more filtering conditions later.




# entities 
1. coupon service 
2. coupon 
3. user 
4. coupon validator 
5. order 
6. payment type(enum)

# responsibilyt 
1. coupon service will be responsible for getting all coupons and validating coupon (through coupon validator)
2. coupon validtor will be responsible for validating coupon 
3. coupon now it could be abstract class , so all other types of coupon derived from it (flat discount, percentage, percentage with cap)
4. coupon will have list of rules 
5. rule will be interface and we will have diff coupon rule 
7. 


# plan 
we will have coupon service which will have coupon validtor , user and order
and then inside coupon validtor  

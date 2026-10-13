# question 
design inventory management system

# requirement gathering 
1. user should be able to order product from inventory 
2. system should trigger alert on low inventory
3. system should be capable of adding/removing inventory 
4. system should be able to handle multiple products 
5. system should be able to count/remove product from diff warehouse (product is scattered )
6. fullfillment of request should be done based on diff algo as needed
    - proudct from iventory where item count is greater 
7. error handling 
    - fullfling order even when stock is not there 
    - dual stock reduction problem 
    - failing order just coz total stock was not in single inventory 


# things to consider 
1. go with startegy for reduction/addition of stock based on order in diff warehouse 
2. observer pattern for notofycing user about low stock 
3. singleton for one IMS for one system 
5. product level locking for fast and corretceness 


# entities 
1. IMS 
2. product 
3. Order 
4. product_category 
5. warehouse 
6. order fullifment startegy 
7. low stock observer 

# class design 
- IMS 
    - list<warehouse>
    - order_fullfilment_startegy 

    + fulfillOrder(Order)
    + addStock(product, stock)

- warehouse
    - list<inventoryproduct>

    + addStock(Product, stock)
    + removeStock(product, stock)
    + viewStock(product, stock)

- inventoryProduct
    - product
    - count 
    - reeentrantStock()

    + reduceStock(stockcount)
    + addstock(stockcount)

- product 
    - id
    - name 
    - price 
    - product_category



- product_fullfilment_startegy

    + boolean checkproduct(order)
    + orderSucess fulffill order(order)

- order 
    - map of <product, count>
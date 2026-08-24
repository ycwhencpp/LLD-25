# question 
Design a category hierarchy system, where a category can have subcategories and products. Add a method to get all products with given filters (for ex. Category, name etc). System should be extensible for filter adding in the future

1. primary capalibities 
2. error handling 
3. scope boundaries 

# requirement gathering 
1. system should support heiearchy 
2. category can have 
    - category(sub-category)
    - list<products>
3. system should be able to filter product 
4. should suuport multiple type of filter 
5. return empty or null for invalid filter or result with zero data 

# entities(noun or something that standout)

1. HiearchySystem 
2. Category
3. product
4. category_TYPE 
5. filter startegy(since multiple interchanegable algo based on system need) seachByCategory, searhcBYName 

6. 

# class diagram 
1. HiearchySystem
    - List<Category>
    + getAllcategories()
    + filterData(filterRequest)


2. Category
    - List<product> products 
    - List<Category> categories
    - categoryType

    + getAllproducts()
    + getAllcategories()
    + filterData(filterRequest)

3. product
    - name 

4. categoryType (enum)
5. filterStaretgt interface 
6. searchByNameFilterStartegy
    - name
7. searchBycategoryFilterStartegy
    - category
8. AndFilterStartegy
    - list<strategy>
9. OrFilterStartegy
    - list<strategy>
8. filterRequest 
    - FilterStartegy
    - filterType



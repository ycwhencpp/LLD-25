# question 
Low level design for movie ticket booking system like Bookmyshow

# requirement gathering 
1. user should be able to book tickets (as much tickets as he wants for a show )
2. we should lock the seats when users selects them 
3. no double booking for same seat 
4. should be able to search for tickets maybe some strategy
    a. based on city 
    b. based on movie 
    c. or just normal API 
    (over enginnering , add startegy when behaviour changes not data filtering logic) instead create search critieria Object
5. supports multiple screens and shows 


# happy flow 
1. user comes into the system and search for shows based on theater, city or movie 
2. now assume searched for movie, he will be able to view all the shows , in all the therater 
3. now user click on any show he should see seats and select some 
4. after selecting seats lock them for X time until payment is done else release the lock (helps in avoiding double booking)
5. one more thing if user searches therater , he should be able to see all the movies and all the shows for the movie 

# entities
1. user 
2. movie
3. show 
4. seats 
5. search critiera 
6. theater 
7. location 
8. seat type 
9. user type 
10. seat status 


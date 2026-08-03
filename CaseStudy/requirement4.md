# Design database schema for an URL Shortener.



# fucntional requirements 
- user input long url 
- system output short url
- unique short url 

# non functional requirements 
- low latency
- system can accomdate billions of combination 
- short url should be less than 9
- concurren supprt

# entities 
- user 
    - id
    - name

- url_conversion_data 
    - user_id (forgein key)
    - long_url 
    - short_url (unique key)
    - status
    - expiresAt  
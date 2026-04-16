# question 
Design a Circuit Breaker. The question slighly threw me off because the requirements looked like a rate limiter combined with a circuit breaker. The breaker opened when the number of requests (NOT number of errors) exceeded a threshold. Was able to solve it, come up with test cases, show OPEN, CLOSED, HALF_OPEN states and answer concurrency questions properly.


# requirement gathering 



circuit breaker should allow request if < threshold 
should close and reject if >= threshold 
states -> closed -> half open -> open

- concurrency 
- threshold
-  cooldown time 




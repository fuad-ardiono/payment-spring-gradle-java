# Payment

### 4

4.1. If the initial pageSize has a very large value it will affect the performance of the application. Why?
- Because the size of the response will be large ex: 1mb. That's not ideal.
- Burden the sql server and api server, if there are many concurrent requests at the same time.

4.2 There are many methods that can be used, one of which is using a cache, using cursors, sharding/partitioning tables, or using an architecture that is designed for searching (ElasticSearch).
- Using elasticsearch but the cost of infrastructure becomes expensive (easy)
- Using cache is simpler to implement (easy)
- Using the cursor but will affects how the client side renders (medium)
- Using partitions requires database patch/down time and affects a lot of existing code (hard)
- In this project using the cache method using Redis

4.3 Things the redis cache method does:
- Hashing query string/parameter method, the hashing will later become the key of the index payment. ex: payment:<hash>
- Every time you create/update/delete new data, you need to delete the redis index key. Why? Because the ID position will change and affect the rendering of pagination data
- If there is cache then return cache
- If there is no cache then query the data to the db and cache the data that has been queried to redis
- Define a JPA countQuery with count()

4.4 The trade off of the cache pagination strategy is the need to maintain the cache when there are create, update or delete operations

4.5 Pseudocode

GET Pagination
- If there is a cache return cache
- If there is no cache, query to db, set cache with payment index:<hashcode>

CREATE
- Query create
- Delete redis index payment

DELETE
- Delete queries
- Delete redis index payment

UPDATES
- Update queries
- Delete redis index payment

### 6
6.1 The challenge that must be faced when large size records are resource management (responses returned to the client, cache management)

6.2
- From the application side, we can use cache, cursor, ElasticSearch strategies
- From the infrastructure side, we can use a load balancer

6.3
- Pay attention to the response returned to the client, if there are properties that are not used, delete them so that the response size is smaller
- Caches

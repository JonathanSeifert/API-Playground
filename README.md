# API - Playground

 This is a small project to practice APIs, Spring Boot and React.

### Backend
 - Spring Boot
 - JPA
 - PostgreSQL (run in a docker container)
### Frontend
 - React.js

### API Specification

|Request <br />Method|URL|Response <br />Code|Description|
|--------------|---|--------------|-----------|
|GET|*/api/user*|200|At least 1 user found.|
|||204|No users found.|
|||500|Server Error|
|GET|*/api/user/**{id}***|200|User with {id} found.|
|||400|User with {id} does not exist.|
|||500|Server Error|
|POST|*/api/user*|201|User created.|
|||500|Server Error.|
|PUT|*/api/user/{id}*|200|User changed.|
|||400|User with {id} does not exist.|
|||500|Server Error|
|DELETE|*/api/user*|204|Users deleted.|
|||500|Server Error.|
|DELETE|*/api/user/{id}*|204|User with {id} deleted.|
|||400|User with {id} does not exist.|
|||500|Server Error|



# Spring Boot Reactive POC

Creating some poc to create simple CRUD with reactive spring boot framework.

### Database 
[Mongo Atlas](https://www.mongodb.com/cloud/atlas)

_Obs: Add local ip to network policy_ 

### Tests

* Unit Tests (JUnit5)

```
./gradlew clean test
```

* Mutation Tests (Pitest)

```
./gradlew pitest
```

_Obs: Reports at ./build/reports/pitest/index.html_
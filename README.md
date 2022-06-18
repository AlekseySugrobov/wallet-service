# RUN

Use command

```
gradle clean build bootRun
```

for run application

# API

Check file src/main/resources/openapi-specification/wallet-service.yaml for describing API. You can user swagger-ui for
sending requests to described endpoints. User http://localhost:8080/swagger-ui.html for it

# Database

Application uses embedded H2 database. I used LIQUIBASE for executing migration scripts for creating pre-filled tables.
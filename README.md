# gs-gateway

## Description
This project is a simple example of a Spring Cloud Gateway.

## Version
- Java: `1.8`
- Spring Boot: `2.7.18`
- Spring Cloud: `2021.0.8`
- Spring Cloud Gateway: `3.1.8`

## Run
### simple route
```bash
curl http://localhost:8080/get
```
### circuit breaker
```bash
curl --dump-header - --header 'Host: www.circuitbreaker.com' http://localhost:8080/delay/3
```

## Reference
- https://spring.io/guides/gs/gateway
- https://github.com/spring-guides/gs-gateway/tree/boot-2.7

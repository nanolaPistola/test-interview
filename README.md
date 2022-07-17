# multicert

This application was generated using JHipster 7.8.1, you can find documentation and help at [https://www.jhipster.tech](https://www.jhipster.tech).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.
This application is configured for Service Discovery and Configuration with . On launch, it will refuse to start if it is not able to connect to .

## Development

To start your application in the dev profile, run:

```
./mvnw
```

## Apis

The application making a GET to [localhost:8081/api/swagger.json](swagger endpoint) will retrieve the swagger automatically generated in the microservice



## Database 

For simplicity the microservice will create a h2 temporary database to support quick tests and development


This database can be clean by making the command

```
./mvnw clean
```

the next run of the microservice will recreate an empty database


## Testing 

There is a postman collection on the route of the project, with all apis created to permit test
```
Multicert Test Collection.postman_collection.json
```

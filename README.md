# vehicle-management

This is an example of usage of:
* Spring boot application
* Spring cloud config
* Spring hateoas
* Spring data with MariaDB
* Self-contained H2 database for tests 

## Installation

Using maven command line

```bash
mvn clean install
```

## Running the application
#### Starting MariaDB and config-server using Docker
```bash
docker-compose up
```

#### Starting the vehicle-management application
```bash
mvn spring-boot:run
```
#### Database preparation
You must create a new database named `vehicle-management` and run the insert statement from `./db/create-table.sql`. 

#### Usage
The requests can be found at `./requests`
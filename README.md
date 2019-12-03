# Coding Challenge
This project demonstrates parsing of Json data and presenting filtered data.

# Code Structure
Standard Spring MVC based class packaging
  - web - contains AirportsListingController with single request handler to provide list of airports based on filters.
  - model - contains model classes for the API, model classes strictly follow the structure of airports Json structure.
  - service - contains a single service AirportLocationService, to provide airports list to the client.
  - com.faziz.quantas.challenge.AirportLocationApplication is used for the entry point of the application.

There are tests under src/test directory for controller, repository and service.

# Business Logic
As per the requirment, the API should list all the airports that fulfill given criteria.
The Json data fetched from the URI https://www.qantas.com.au/api/airports, and stored in a local file. This file is then used to run the filter on and get the filtered data through to the users. 

The reason for getting the data stored in a local file is so that the application is runs in a disconnected mode from the Quantas API, which makes filtering data a local process and also makes it independent of the Quantas API.

I am also cahing the results of the queries so that you don't need to go and parse the chunky Json data everytime.

In a production environment we would have a JSON based database like Mongodb that makes storage and querying of the Json data much more efficient.


# Tech

* [Spring Boot] - To quickly setup Restful API, and wireup all the dependencies.
* [JsonPath] - For querying and filtering Json data.
* [Spring Boot Test] - For end-to-end testing.
* [Java 8] - Basically for everything else.
* [Gradle] - To build the application.

# Installation

This project is based on Gradle.

To run application on Linux go to the projects directory.

```sh
$ ./gradlew clean test
$ ./gradlew run
```

To run the application just switch to gradlew.bat.

Goto the browser and type something like http://localhost:8080/api/airports/search?country.code=PK to access the application.

I have tested following Uri,
http://localhost:8080/api/airports/search?country.code=AU filters data for country code AU.
http://localhost:8080/api/airports/search?code=BZE filters data for airport code.
http://localhost:8080/api/airports/search?international_airport=true gets the list of airports with international_airport is true.
http://localhost:8080/api/airports/search?international_airport=false gets the list of airports with international_airport is false.
http://localhost:8080/api/airports/search?iregional_airport=true gets the list of airports with regional_airport is true.
http://localhost:8080/api/airports/search?regional_airport=false gets the list of airports with regional_airport is false.

The application should filter data based on any attribute in the Json data.


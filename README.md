# Clout Metrics by LATCHOUMAYA and VELU

## Introduction

This project is a web application which analyse a git repository and shows data and graphs written in Java using technologies, such as REST or database.

## Used technologies

- [IntelliJ with Java 19](https://www.jetbrains.com/fr-fr/idea/)
- [Postman](https://www.postman.com/postman)
- [JUnit 5.9.1](https://junit.org/junit5/docs/current/user-guide/)
- [Jackson 2.13.0](https://github.com/FasterXML/jackson)
- [Jgit 6.3.0.202209071007-r](https://www.eclipse.org/jgit/)
- [Spring Boot Reactive 2.7.4](https://spring.io/projects/spring-boot)
- [Jdbi 3.32.0](http://jdbi.org/)
- [H2 2.1.214](https://h2database.com/html/main.html)
- [Angular 14.1.0](https://angular.io/)
- [Tailwind 3.1.8](https://tailwindcss.com/)

## Prerequisites

- NodeJS ;
- Angular 14.1.0 ;
- Maven command.

## How to use it ?

To use this application, you need to execute several commands :

```
UNIX :
cd <project root> //should be "/.../latchoumaya-velu"
mvn clean // Clean the project
mvn build // Build the project
cd target
java -jar CloutMetrics-0.0.1-SNAPSHOT.jar
```   

And then, connect to ```http://localhost:8080``` to access the website or ```http://localhost:8080/h2-console``` for database access.

## Functionalities

- Browse a list of downloaded git repositories ;
- Add a repository by giving the URL path and a name ;
- View information about a git repositories such as languages ratio or the number of commits.
 
## Authors

This project is being made by Sonny LATCHOUMAYA and Thomas VELU as a project for the course Advanced JAVA in the second year at ESIPE.

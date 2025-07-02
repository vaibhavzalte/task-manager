### task-manager

* day-13 Integrate MongoDB
* day-14 ResponseEntity added to api response
* day-15 Lombok used & ObjectId used
* day-16 @DBRef annotation and added user wise entries
* day-17 @Transactional annotation added but it work only allowed on a replica set
* day-18 mongodb atlas added and transactional code fixed
* day-19 spring security
* day-20 spring security
* day-21 Adding Authentication to Journal Endpoints
* day-22 Role Based Authorization
* day-23 Properties | YAML | How to pass command line arguments in spring boot application
    * priority-> commandline > application.properties > YAML
* day-24 JUnit Testing
* day-25 JUnit Testing using Mockito @Mock @InjectMocks
* day-26 Master Spring Boot Profiles
    - added spring.profiles.active=dev in intellije Env. variable
    - `.\mvnw clean package -D spring.profiles.active=dev` flag `-D` used when test cases is present   (setting JVM
      properties that why used `-D`)
    - `java -jar .\taskManager-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod` running application and passing
      argument `spring.profiles.active=prod`
* day-27 Logging 
  - ```logging.level.<package-name>=DEBUG ``` in application properties 
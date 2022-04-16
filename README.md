# hierarchio

Have you ever forgotten your managers name? Have you ever forgotten your managers, managers name? Well fear not!

Introducing Hierarchio. A simple web service to solve all of your employee hierarchy needs.


## Description

Hierarchio exposes a simple restful web API over http. The follow endpoints are available to use:

* /hierarchy [POST] - create your employee hierarchy
* /hierarchy [GET] - get your employee hierarchy
* /hierarchy/[name] [GET] - get a specific employees hierarchy up to 2 levels

Example valid hierachy to post: 

```
{
    "pete": "nick",
    "barb": "nick",
    "sophie": "jonas",
    "nick": "sophie"
}
```

Server will detect the following invalid scenarios upon posting the hierarchy.

* Multiple root nodes (because there can only be one leader)

```
{
    "pete": "nick",
    "barb": "nick",
    "sophie": "jonas",
    "nick": "sophie",
    "nick": "john"
}
```

* Employee loop detection (before understanding recursion, you must first understand recursion)

```
{
    "pete": "nick",
    "barb": "nick",
    "sophie": "jonas",
    "nick": "sophie",
    "jonas": "pete"
}
```

## Getting Started

### Dependencies

* Java 12 and >
* Gradle v6.x.x and >
* [Javalin](https://javalin.io/) 
* [Junit5](https://junit.org/junit5/docs/current/user-guide/)

### Executing program

To build project, navigate to project root directory and run gradle task:
```
gradle build
```

To run tests, navigate to project root directory and run gradle task:
```
gradle test
```

To run server, navigate to project root directory and run gradle task:
```
gradle runServer
```
### Testing Program

As stated above, you can run the untit test using gradle test task. Or you can run the unit tests using any Junit5 compatible test runner.

To test the endpoints in an integration scenario, find the [Postman](https://www.postman.com/) test case collections in a folder in the root directory called "Test Cases". Import the collection into Postman and run each example test case.

### Debugging
Using any gradle compatible IDE such as Jetbrains Intellij, navigate to the project root directory (containing the gradle.build file) and open the folder in the IDE. Run the gradle build task from within the IDE and begin debugging.

### Todo
Pass port from gradle task to server as runtime arg
Save hierarchy to a data store
Simple token based authentication

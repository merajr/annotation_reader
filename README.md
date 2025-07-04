# Jar Reader Application
## Overview
The Jar Reader Assessment project is a Java application designed to scan JAR files for class entries and process them. It retrieves annotations from the classes found within the JAR files.

## Requirements
- Java Development Kit (JDK) 14 or higher (not higher than 21) (14 is needed because of usage of records).
- Maven 3.6 or higher

## Building the Project
To build the project, navigate to the root directory of the project and run the following command:

```
mvn clean install
```

This will create a new executable jar file under target. Move the jar to the root and renameit to `assessment.jar`.

## Running the Application
To run the application, run the following command:

```
java -jar assessment.jar <path-to-jar-file>
```

## Testing
Unit tests can be added in the `src/test/java` directory. To run the tests, use the following command:

```
mvn test
```

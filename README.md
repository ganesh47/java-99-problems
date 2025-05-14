# 99 Problems in Java

This is a Java implementation of the famous "99 Problems" collection, originally written in Prolog and Lisp. The problems are designed to help developers learn programming concepts through practical exercises.

## Project Structure

Each problem is implemented in its own dedicated class within the `org.nintynine.problems` package. The corresponding test class contains detailed test cases that demonstrate the expected behavior and usage.

For example:
- Problem P01: `MyListP01.java` with tests in `MyListP01Test.java`
- Problem P02: `MyListP02.java` with tests in `MyListP02Test.java`
  And so on...

## Tests

The test classes serve multiple purposes:
1. Verification of the implementation
2. Documentation through examples
3. Edge case handling demonstration

Each test class follows a consistent pattern:
- Basic functionality tests
- Edge case tests
- Performance considerations where applicable
- Example usage scenarios

To run the tests:
```bash
bash ./mvnw test
```

## Implementation Notes

- Each class builds upon previous solutions where appropriate
- Test cases are designed to be self-documenting and comprehensive
- Java generics are used extensively to ensure type safety
- Solutions aim to be both efficient and readable

## Getting Started

1. Clone the repository
2. Import as a Maven project
3. Browse the problems in order, starting from P01
4. Run tests to verify your understanding
5. Check the test cases for usage examples

Each problem's implementation and its corresponding test class can be studied independently, making it easy to focus on specific concepts or challenges.


[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=ganesh47_java-99-problems)](https://sonarcloud.io/summary/new_code?id=ganesh47_java-99-problems)  
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ganesh47_java-99-problems&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=ganesh47_java-99-problems)  
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=ganesh47_java-99-problems&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=ganesh47_java-99-problems)  
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=ganesh47_java-99-problems&metric=bugs)](https://sonarcloud.io/summary/new_code?id=ganesh47_java-99-problems)  
[![Coverage](https://img.shields.io/sonar/coverage/ganesh47_java-99-problems?server=https%3A%2F%2Fsonarcloud.io&style=plastic)](https://sonarcloud.io/summary/new_code?id=ganesh47_java-99-problems)

# Restful-api

A sample restful api implemented with [Resteasy](https://resteasy.github.io/) and have example of usage of [Junit4](https://junit.org/junit4/), [Arquillian](http://arquillian.org/) and [RestAssured](http://rest-assured.io/)

## Aplication Server

This sample use a Wildfly 11 such as ServletContainer and JEE full implementation

## Maven Build

```console
mvn install
```
## Integration Tests

The integration tests use [Arquillian Chameleon](https://github.com/arquillian/arquillian-container-chameleon) to provide a container instance in tests scope

To execute tests run the cli

```console
mvn verify
```
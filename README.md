# Restful-api

A sample restful api implemented with [Resteasy](https://resteasy.github.io/) and have example of usage of [Junit4](https://junit.org/junit4/), [Arquillian](http://arquillian.org/) and [RestAssured](http://rest-assured.io/)

## Aplication Server

This sample use a Wildfly 11 such as ServletContainer and JEE full implementation

## Maven Build

```console
mvn install
```

## Integration Tests

The integration tests use [Arquillian Chameleon](https://github.com/arquillian/arquillian-container-chameleon) to provide a container instance in tests scope and [RestAssured](http://rest-assured.io/) to verify the Rest API

To execute tests run the cli

```console
mvn verify
```

### Explainning a little bit more

When integration-tests goal starts, the [Arquillian Chameleon](https://github.com/arquillian/arquillian-container-chameleon) shall download (if not exists) a servlet container specified in ```@ChameleonTarget``` and start them, then a deploy of the application specified in ```@Deployment``` will be done, after that the junit [Junit4](https://junit.org/junit4/) will be executed. Ex...

```java
@RunWith(ArquillianChameleon.class)
@ChameleonTarget(value="wildfly:11.0.0.Final:managed")
public class ClientResourceIT {

    @Deployment
    public static WebArchive deploy() {

        // Get all dependencies in pom.xml
        File[] archives = Maven.resolver().loadPomFromFile("pom.xml")
                .importDependencies(ScopeType.COMPILE, ScopeType.TEST, ScopeType.PROVIDED, ScopeType.RUNTIME)
                .resolve()
                .withTransitivity()
                .asFile();

        // Make a war file
        WebArchive war = ShrinkWrap
                        .create(WebArchive.class, "restful-api.war")
                        .addPackages(true, "br.com.app.restful")
                        .addAsResource("META-INF/persistence.xml")
                        .addAsLibraries(archives)
                        .addAsWebInfResource("jboss-web.xml")
                        .addAsWebInfResource(new StringAsset("<beans bean-discovery-mode=\"all\" version=\"1.1\"/>"), "beans.xml");

        return war;
    }
    // RestAssured Tests
    ...
}
```

## ApiDocs

To document the api [Swagger](https://swagger.io/) was used

### Get file of Swagger

http://localhost:8080/restful-api/v1/swagger.json

### Swagger UI

http://localhost:8080/restful-api/v1/api-docs/index.html
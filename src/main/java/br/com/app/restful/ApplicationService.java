package br.com.app.restful;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@ApplicationPath("/")
public class ApplicationService extends Application {
	
	public ApplicationService(@Context ServletConfig servletConfig) {
        super();
        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .title("Restful Sample API").version("v1")
                .description("This is a sample restful api to demonstrate patterns and implementations with Resteasy")
                .contact(new Contact().email("filipesomstd@gmail.com").name("Filipe Oliveira").url("https://github.com/FilipeSoares"));

        oas.info(info);
        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true)
                .resourcePackages(Stream.of("br.com.app.restful").collect(Collectors.toSet()));

        try {
            new JaxrsOpenApiContextBuilder()
                    .servletConfig(servletConfig)
                    .application(this)
                    .openApiConfiguration(oasConfig)
                    .buildContext(true);
        } catch (OpenApiConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }
	

}

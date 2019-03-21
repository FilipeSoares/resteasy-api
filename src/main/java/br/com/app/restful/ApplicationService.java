package br.com.app.restful;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.app.restful.resources.ClientResource;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

// import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/")
public class ApplicationService extends Application {
	
	/*public ApplicationService() {
        BeanConfig config = new BeanConfig();
        config.setTitle("Restful Sample API");
        config.setDescription("A sample Restful api");
        config.setContact("http://github.com/FilipeSoares");
        config.setVersion("v1");
        config.setSchemes(new String[]{"http"});
        config.setHost("localhost:8080");
        config.setBasePath("/restful-api/v1");
        config.setResourcePackage("br.com.app.restful");
        config.setScan(true);
    }*/
	
	/*@Override
    public Set<Class<?>> getClasses() {
        return Stream.of(ClientResource.class, OpenApiResource.class, AcceptHeaderOpenApiResource.class).collect(Collectors.toSet());
    }
	*/
}

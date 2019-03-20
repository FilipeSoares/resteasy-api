package br.com.app.restful;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/")
public class ApplicationService extends Application {
	
	public ApplicationService() {
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
    }
	
}

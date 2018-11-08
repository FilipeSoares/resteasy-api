package br.com.app.restful;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.app.restful.resources.ClientsResource;
import br.com.app.restful.resources.InfoResource;

@ApplicationPath("/v1")
public class ApplicationService extends Application {
	
}

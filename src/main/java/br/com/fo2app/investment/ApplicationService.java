package br.com.fo2app.investment;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.fo2app.investment.resources.ClientsResource;

@ApplicationPath("/")
public class ApplicationService extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	 
    public ApplicationService() {
        singletons.add(new ClientsResource());
    }
 
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
	
}

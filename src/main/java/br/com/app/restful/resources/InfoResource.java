package br.com.app.restful.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class InfoResource {


	@GET
	@Path("/info")
	@Produces(MediaType.TEXT_PLAIN)
	public String info() {
		return "Clients Resource version 1.0";
	}
	
}

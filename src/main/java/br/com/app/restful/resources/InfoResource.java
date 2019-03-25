package br.com.app.restful.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.v3.oas.annotations.Hidden;

@Path("/")
public class InfoResource {


	@GET
	@Path("/info")
	@Produces(MediaType.TEXT_PLAIN)
	@Hidden
	public String info() {
		return "Restful API versão 1.0.0";
	}
	
}

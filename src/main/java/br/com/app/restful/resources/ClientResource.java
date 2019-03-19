package br.com.app.restful.resources;

import java.net.URI;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import br.com.app.restful.dao.ClientDAO;
import br.com.app.restful.model.Client;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)	
public class ClientResource {
	
	@Inject
	ClientDAO dao;

	@GET
	public Response list(@QueryParam("name") String name, @QueryParam("email") String email) {
		return Response.ok(dao.list()).build();
	}
	
	@GET
	@Path("/{id}")
	public Response fetch(@PathParam("id") Long id) {
		return Response.ok(dao.find(id)).build();
	}
	
	@POST
	public Response create(Client client) {
		
		if ( dao.findByEmail(client.getEmail()).isEmpty() ){
			URI location = UriBuilder.fromResource(ClientResource.class).path(dao.create(client).toString()).build();
			return Response.created(location).build();
		} else {
			return Response.status(Status.CONFLICT).build();
		}
		
	}
	
	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, Client client) {
		
		dao.find(id);
		if ( dao.findByEmail(client.getEmail()).stream().filter(c -> c.getId()!=id).collect(Collectors.toList()).isEmpty() ){
			URI location = UriBuilder.fromResource(ClientResource.class).path(id.toString()).build();
			client.setId(id);
			return Response.ok(dao.update(client)).header("Location", location).build();
		} else {
			return Response.status(Status.CONFLICT).build();
		}
		
	}
	
	@DELETE
	@Path("/{id}")
	public Response remover(@PathParam("id") Long id) {
		dao.remove(dao.find(id));
		return Response.noContent().build();
	}
	
}

package br.com.app.restful.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.app.restful.model.Cliente;

@Path("/clientes")
public class ClienteResource {
	
	@Context
	UriInfo uriInfo;
	
	private List<Cliente> clientes = new ArrayList<Cliente>();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		return Response.ok(this.clientes).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetch(@PathParam("id") Long id) {
		Optional<Cliente> cliente = this.clientes.stream().filter(c -> c.getId().equals(id)).findAny();
		if (cliente.isPresent()) {
			return Response.ok(cliente.get()).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Cliente cliente) {
		cliente.setId(System.currentTimeMillis());
		this.clientes.add(cliente);
		URI location = UriBuilder.fromResource(ClienteResource.class).path(cliente.getId().toString()).build();
		return Response.created(location).build();
	}	
	
}

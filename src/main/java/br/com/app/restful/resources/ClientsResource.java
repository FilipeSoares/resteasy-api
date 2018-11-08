package br.com.app.restful.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.app.restful.model.Client;

@Path("/clients")
public class ClientsResource {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		return Response.ok(this.list()).build();
	}

	private List<Client> list() {
		List<Client> list = new ArrayList<Client>();
		
		list.add(new Client("1", "Client 1"));
		list.add(new Client("2", "Client 2"));
		
		return list;
	}
	
}

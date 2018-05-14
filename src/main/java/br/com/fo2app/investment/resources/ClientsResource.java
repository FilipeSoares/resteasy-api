package br.com.fo2app.investment.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fo2app.investment.model.Client;

@Path("/clients")
public class ClientsResource {

	@GET
	@Path("/info")
	@Produces(MediaType.TEXT_PLAIN)
	public String info() {
		return "Clients Resource version 1.0";
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		return Response.ok(this.list()).build();
	}

	private List<Client> list() {
		List<Client> list = new ArrayList<Client>();
		
		list.add(new Client("1", "Oswaldo"));
		list.add(new Client("2", "Mel"));
		
		return list;
	}
	
}

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

	@Inject
	ClientDAO dao;

	@GET
	@Operation(summary = "List clients", description = "Retrieve a list of all clients in database", tags = {
			"clients" }, responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Client.class)), description = "List of all clients"))
	public Response list(
			@Parameter(description = "Name of client", example = "name=Jhon Doe", in = ParameterIn.QUERY, required = false) @QueryParam("name") String name,
			@Parameter(description = "E-mail of client", example = "email=jhon@email.com", in = ParameterIn.QUERY, required = false) @QueryParam("email") String email) {

		return Response.ok(dao.list(name, email)).build();

	}

	@GET
	@Path("/{id}")
	@Operation(summary = "Retrieve Client", description = "Retrieve a client representation by id", tags = {
			"clients" }, responses = {
					@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Client.class)), description = "Client representation"),
					@ApiResponse(responseCode = "404", description = "Client not found"), })
	public Response fetch(
			@Parameter(description = "ID of client", in = ParameterIn.PATH, required = true) @PathParam("id") Long id) {

		return Response.ok(dao.find(id)).build();

	}

	@POST
	@Operation(summary = "Create Client", description = "Create a new representation of client", tags = {
			"clients" }, responses = { @ApiResponse(responseCode = "201"),
					@ApiResponse(responseCode = "409", description = "Client already exists") })
	public Response create(Client client) {

		if (dao.findByEmail(client.getEmail()).isEmpty()) {
			URI location = UriBuilder.fromResource(ClientResource.class).path(dao.create(client).toString()).build();
			return Response.created(location).build();
		} else {
			return Response.status(Status.CONFLICT).entity("Client already exists").build();
		}

	}

	@PUT
	@Path("/{id}")
	@Operation(summary = "Update Client", description = "Update a representation of client", tags = {
			"clients" }, responses = { @ApiResponse(responseCode = "201"),
					@ApiResponse(responseCode = "409", description = "Client already exists") })
	public Response update(
			@Parameter(description = "ID of client", in = ParameterIn.PATH, required = true) @PathParam("id") Long id,
			Client client) {

		dao.find(id);
		if (dao.findByEmail(client.getEmail()).stream().filter(c -> c.getId() != id).collect(Collectors.toList())
				.isEmpty()) {
			URI location = UriBuilder.fromResource(ClientResource.class).path(id.toString()).build();
			client.setId(id);
			return Response.ok(dao.update(client)).header("Location", location).build();
		} else {
			return Response.status(Status.CONFLICT).build();
		}

	}

	@DELETE
	@Path("/{id}")
	@Operation(summary = "Remove a Client", description = "Remove a client representation", tags = {
			"clients" }, responses = { @ApiResponse(responseCode = "200"),
					@ApiResponse(responseCode = "404", description = "Client not found") })
	public Response remover(@PathParam("id") Long id) {
		dao.remove(dao.find(id));
		return Response.noContent().build();
	}

}

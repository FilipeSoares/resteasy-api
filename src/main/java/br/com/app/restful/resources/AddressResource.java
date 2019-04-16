package br.com.app.restful.resources;

import java.net.URI;

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
import javax.ws.rs.core.UriBuilder;

import br.com.app.restful.dao.AddressDAO;
import br.com.app.restful.model.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {
	
	@Inject
	AddressDAO dao;
	
	@GET
	@Operation(summary = "List addresses",  
			   description = "Retrieve a list of all addresses in database",
			   tags = {"addresses"},
			   responses = @ApiResponse(responseCode = "200", content = @Content( schema = @Schema(implementation=Address.class) ), description = "List of all addresses" ))
	public Response list(@Parameter(description="Street of Address", example="street=Killington", in = ParameterIn.QUERY, required = false) @QueryParam("street") String street) {
		
		return Response.ok(dao.list(street)).build();
		
	}
	
	@GET
	@Path("/{id}")
	@Operation(	summary = "Retrieve Address",  
	   			description = "Retrieve a Address representation by id",
	   			tags = {"addresses"},
	   			responses = {
	   					@ApiResponse(responseCode = "200", content = @Content( schema = @Schema(implementation=Address.class) ), description = "Address representation" ),
	   					@ApiResponse(responseCode = "404", description = "Address not found" ),
	   			})
	public Response fetch(@Parameter(description="ID of Address", in = ParameterIn.PATH, required = true) @PathParam("id") Long id) {
		
		return Response.ok(dao.find(id)).build();
		
	}
	
	@POST
	@Operation(	summary = "Create Address",  
				description = "Create a new representation of Address",
				tags = {"addresses"},
				responses = { @ApiResponse(responseCode = "201") })
	public Response create(Address Address) {
		
		URI location = UriBuilder.fromResource(AddressResource.class).path(dao.create(Address).toString()).build();
		return Response.created(location).build();
		
	}
	
	@PUT
	@Path("/{id}")
	@Operation(	summary = "Update Address",  
				description = "Update a representation of Address",
				tags = {"addresses"},
						responses = { @ApiResponse(responseCode = "201") })
	public Response update(@Parameter(description="ID of Address", in = ParameterIn.PATH, required = true)  @PathParam("id") Long id, Address Address) {
		
		dao.find(id);
		URI location = UriBuilder.fromResource(AddressResource.class).path(id.toString()).build();
		Address.setId(id);
		return Response.ok(dao.update(Address)).header("Location", location).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Operation(	summary = "Remove a Address",  
	description = "Remove a Address representation",
	tags = {"addresses"},
			responses = { 
					@ApiResponse(responseCode = "200"), 
					@ApiResponse(responseCode = "404", description = "Address not found") 
					})
	public Response remover(@PathParam("id") Long id) {
		dao.remove(dao.find(id));
		return Response.noContent().build();
	}

}

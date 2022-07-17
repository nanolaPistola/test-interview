package com.multicert.test.resource;

import com.multicert.test.service.dto.StorageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Api(value = "Hello resource Version 1", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ClientResource {

    @POST
    @Path("v1/client")
    @ApiOperation(value = "Gets a hello resource. Version 1 - (version in Accept Header)")
    Response createClient(StorageDTO storageDTO);

    @GET
    @Path("v1/client")
    @ApiOperation(value = "Gets a hello resource. Version 1 - (version in Accept Header)")
    Response getClientList();

    @DELETE
    @Path("v1/client/{id}")
    @ApiOperation(value = "Gets a hello resource. Version 1 - (version in Accept Header)")
    Response deleteClient(@PathParam("id") Long id);

    @GET
    @Path("v1/client/nif/{nif}")
    @ApiOperation(value = "Gets a hello resource. Version 1 - (version in Accept Header)")
    Response getClientByNif(@PathParam("nif") String nif);

    @GET
    @Path("v1/client/name/{name}")
    @ApiOperation(value = "Gets a hello resource. Version 1 - (version in Accept Header)")
    Response getClientByName(@PathParam("name") String name);
}

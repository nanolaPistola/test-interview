package com.multicert.test.resource;

import com.multicert.test.service.dto.EsClientDTO;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

public interface GeneralActionsInterface {


    Response getClientList( Integer page, Integer size);

    Response deleteClient(  Long id);


    Response getClientByNif( String nif);

    Response getClientByName(String name);

    String getCountry();

    Response createClient(Map<String, Object> client);
}

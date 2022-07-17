package com.multicert.test.resource;

import com.multicert.test.service.ClientService;
import com.multicert.test.service.dto.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class ClientImplementation implements ClientResource{


    private ClientService clientService;

    public static final Integer DEFAULT_PAGE  = 0;
    public static final Integer DEFAULT_SIZE= 10;

    @Override
    public Response getClientList(Integer page, Integer size) {
        Page<ClientDTO> clientDTOPage =  clientService.findAll(PageRequest.of(page != null ? page : DEFAULT_PAGE, size != null ? size : DEFAULT_SIZE));
        return  Response.status(Response.Status.OK)
            .entity(clientDTOPage).build();
    }


    @Override
    public Response createClient(ClientDTO clientDTO) {
        return Response.status(Response.Status.CREATED).entity(clientService.save(clientDTO)).build();
    }

    @Override
    public Response deleteClient(Long id) {
        clientService.delete(id);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response getClientByNif(String nif) {
        if(nif.length() != 9) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "NIF must have 9 characters")).build();
        }
        Optional<ClientDTO> storageDTOOptional =  clientService.findByNif(nif);
        if(storageDTOOptional.isPresent()) {
            return Response.status(Response.Status.OK).entity(storageDTOOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response getClientByName(String name) {
        List<ClientDTO> clientDTOOptional = clientService.findByNameLike(name);
            return Response.status(Response.Status.OK).entity(clientDTOOptional).build();

    }
}

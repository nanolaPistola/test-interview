package com.multicert.test.resource;

import com.multicert.test.service.StorageService;
import com.multicert.test.service.dto.StorageDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ClientImplementation implements ClientResource{


    private StorageService storageService;

    @Override
    public Response getClientList() {
        return  Response.status(Response.Status.OK).entity(storageService.findAll(Pageable.unpaged()).getContent()).build();
    }

    @Override
    public Response createClient(StorageDTO storageDTO) {
        return Response.status(Response.Status.CREATED).entity(storageService.save(storageDTO)).build();
    }

    @Override
    public Response deleteClient(Long id) {
        storageService.delete(id);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response getClientByNif(String nif) {
        if(nif.length() != 9) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "NIF must have 9 characters")).build();
        }
        Optional<StorageDTO> storageDTOOptional =  storageService.findByNif(nif);
        if(storageDTOOptional.isPresent()) {
            return Response.status(Response.Status.OK).entity(storageDTOOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response getClientByName(String name) {
        List<StorageDTO> storageDTOOptional = storageService.findByNameLike(name);
            return Response.status(Response.Status.OK).entity(storageDTOOptional).build();

    }
}

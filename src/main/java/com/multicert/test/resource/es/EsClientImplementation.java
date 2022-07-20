package com.multicert.test.resource.es;

import com.multicert.test.common.Common;
import com.multicert.test.resource.GeneralActionsInterface;
import com.multicert.test.service.ClientService;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.dto.EsClientDTO;
import com.multicert.test.service.dto.PtClientDTO;
import com.multicert.test.service.mapper.EsClientMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(value = "${provider.es:false}", havingValue = "true")
public class EsClientImplementation implements GeneralActionsInterface {


    private ClientService clientService;
    private EsClientMapper esClientMapper;
    private static Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");


    public static final Integer DEFAULT_PAGE  = 0;
    public static final Integer DEFAULT_SIZE= 10;

    @Override
    public Response getClientList(Integer page, Integer size) {
        Page<EsClientDTO> clientDTOPage =  clientService.findAll(PageRequest.of(page != null ? page : DEFAULT_PAGE, size != null ? size : DEFAULT_SIZE), getCountry()).map(esClientMapper::toDto);
        return  Response.status(Response.Status.OK)
            .entity(clientDTOPage).build();
    }


    @Override
    public Response createClient(Map<String, Object> client) {
        try {
            ClientDTO clientDTO = constructClientDto(client);
            if(clientDTO.getFiscalNumber() == null || Boolean.FALSE.equals(pat.matcher(clientDTO.getFiscalNumber()).matches())){
                return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "DNI not valid")).build();
            }
            return Response.status(Response.Status.CREATED)
                .entity(esClientMapper.toDto(clientService.save(clientDTO, getCountry()))).build();
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            log.error("createClient | Exception {}; message: {}, localizedMessage: {}", e.getClass().getName(), e.getMessage(), e.getLocalizedMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", e.getMessage())).build();

        } catch (Exception e) {
            log.error("createClient | Exception {}; message: {}, localizedMessage: {}", e.getClass().getName(), e.getMessage(), e.getLocalizedMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Collections.singletonMap("error", e.getMessage())).build();
        }
    }

    @Override
    public Response deleteClient(Long id) {
        try {
            clientService.delete(id, getCountry());
            return Response.status(Response.Status.OK).build();
        }catch (Exception e) {
            log.error("createClient | Exception {}; message: {}, localizedMessage: {}", e.getClass().getName(), e.getMessage(), e.getLocalizedMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", e.getMessage())).build();
        }

    }

    @Override
    public Response getClientByNif(String nif) {
        if(nif.length() != 9) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "DNI must have 9 characters")).build();
        }
        Optional<EsClientDTO> storageDTOOptional =  clientService.findByNif(nif, getCountry()).map(esClientMapper::toDto);
        if(storageDTOOptional.isPresent()) {
            return Response.status(Response.Status.OK).entity(storageDTOOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response getClientByName(String name) {
        List<EsClientDTO> ptClientDTOOptional = clientService.findByNameLike(name, getCountry()).stream().map(esClientMapper::toDto).toList();
            return Response.status(Response.Status.OK).entity(ptClientDTOOptional).build();

    }


    private ClientDTO constructClientDto(Map<String, Object> client) {
        ClientDTO clientDTO = new ClientDTO();
        Common.resolve(() -> client.get("name")).filter(Objects::nonNull).ifPresent(value -> clientDTO.setName((String) value));
        Common.resolve(() -> client.get("phone")).filter(Objects::nonNull).ifPresent(value -> clientDTO.setPhone((Integer) value));
        Common.resolve(() -> client.get("address")).filter(Objects::nonNull).ifPresent(value -> clientDTO.setAddress((String) value));
        Common.resolve(() -> client.get("dni")).filter(Objects::nonNull).ifPresent(value -> clientDTO.setFiscalNumber((String) value));
        return clientDTO;
    }

    @Override
    public String getCountry() {
        return "spain";
    }
}

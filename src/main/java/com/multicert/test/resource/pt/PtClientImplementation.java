package com.multicert.test.resource.pt;

import com.multicert.test.common.Common;
import com.multicert.test.resource.GeneralActionsInterface;
import com.multicert.test.service.ClientService;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.dto.PtClientDTO;
import com.multicert.test.service.mapper.PtClientMapper;
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
@ConditionalOnProperty(value = "${provider.pt:false}", havingValue = "true")
public class PtClientImplementation implements GeneralActionsInterface {


    private ClientService clientService;
    private PtClientMapper ptClientMapper;
    private static Pattern pat = Pattern.compile("[0-9]{9}");

    public static final Integer DEFAULT_PAGE  = 0;
    public static final Integer DEFAULT_SIZE= 10;


    @Override
    public Response getClientList(Integer page, Integer size) {
        Page<PtClientDTO> clientDTOPage =  clientService.findAll(PageRequest.of(page != null ? page : DEFAULT_PAGE, size != null ? size : DEFAULT_SIZE), getCountry()).map(ptClientMapper::toDto);
        return  Response.status(Response.Status.OK)
            .entity(clientDTOPage).build();
    }


    @Override
    public Response createClient(Map<String, Object> client) {

        try {
            ClientDTO clientDTO = constructClientDto(client);
            if( clientDTO.getFiscalNumber() == null || Boolean.FALSE.equals(pat.matcher(clientDTO.getFiscalNumber()).matches())){
                return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "NIF not valid")).build();
            }
            return Response.status(Response.Status.CREATED)
                .entity(ptClientMapper.toDto(clientService.save((constructClientDto(client)), getCountry()))).build();
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            log.error("createClient | Exception {}; cause : {} message: {}, localizedMessage: {}", e.getClass().getName(), e.getCause(), e.getMessage(), e.getLocalizedMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", e.getMessage())).build();

        }
    }

    @Override
    public Response deleteClient(Long id) {
        try {
            clientService.delete(id, getCountry());
            return Response.status(Response.Status.OK).build();
        }catch (Exception e) {
            log.error("createClient | Exception {}; cause : {} message: {}, localizedMessage: {}", e.getClass().getName(), e.getCause(), e.getMessage(), e.getLocalizedMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", e.getMessage())).build();
        }

    }

    @Override
    public Response getClientByNif(String nif) {
        if(nif.length() != 9) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("error", "NIF must have 9 characters")).build();
        }
        Optional<PtClientDTO> storageDTOOptional =  clientService.findByNif(nif, getCountry()).map(ptClientMapper::toDto);
        if(storageDTOOptional.isPresent()) {
            return Response.status(Response.Status.OK).entity(storageDTOOptional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response getClientByName(String name) {
        List<PtClientDTO> ptClientDTOOptional = clientService.findByNameLike(name, getCountry()).stream().map(ptClientMapper::toDto).toList();
            return Response.status(Response.Status.OK).entity(ptClientDTOOptional).build();

    }


    private ClientDTO constructClientDto(Map<String, Object> client) {
        ClientDTO ptClientDTO = new ClientDTO();
        Common.resolve(() -> client.get("name")).filter(Objects::nonNull).ifPresent(value -> ptClientDTO.setName((String) value));
        Common.resolve(() -> client.get("phone")).filter(Objects::nonNull).ifPresent(value -> ptClientDTO.setPhone((Integer) value));
        Common.resolve(() -> client.get("address")).filter(Objects::nonNull).ifPresent(value -> ptClientDTO.setAddress((String) value));
        Common.resolve(() -> client.get("nif")).filter(Objects::nonNull).ifPresent(value -> ptClientDTO.setFiscalNumber((String) value));
        ptClientDTO.setCountry(getCountry());
        return ptClientDTO;
    }
    @Override
    public String getCountry() {
        return "portugal";
    }
}

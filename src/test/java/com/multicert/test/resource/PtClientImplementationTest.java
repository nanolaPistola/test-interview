package com.multicert.test.resource;


import com.multicert.test.resource.pt.PtClientImplementation;
import com.multicert.test.service.ClientService;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.dto.PtClientDTO;
import com.multicert.test.service.mapper.PtClientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class PtClientImplementationTest {


    private ClientService clientService = mock(ClientService.class);

    @Autowired
    private PtClientMapper ptClientMapper;

    @Test
    void findAllTestWithZeroEntries() {
        when(clientService.findAll(any(), any())).thenReturn(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0));
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.getClientList(PtClientImplementation.DEFAULT_PAGE, PtClientImplementation.DEFAULT_SIZE);
        Page<PtClientDTO> clientDTOPage = (Page<PtClientDTO>) response.getEntity();
        Assertions.assertEquals( Collections.emptyList(), clientDTOPage.getContent());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void findAllTestWithEntries() {
        when(clientService.findAll(any(), any())).thenReturn(new PageImpl<>(Collections.singletonList(new ClientDTO()), Pageable.unpaged(), 0));
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.getClientList(PtClientImplementation.DEFAULT_PAGE, PtClientImplementation.DEFAULT_SIZE);
        Page<PtClientDTO> clientDTOPage = (Page<PtClientDTO>) response.getEntity();
        Assertions.assertEquals( 1, clientDTOPage.getContent().size());
        Assertions.assertEquals( new PtClientDTO().toString(), clientDTOPage.getContent().get(0).toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus() );
    }


    @Test
    void createClientTest() {
        when(clientService.save(any(), any())).thenReturn(new ClientDTO());
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.createClient(Collections.singletonMap("nif", "123456789"));
        Assertions.assertEquals( (new PtClientDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus() );
    }

    @Test
    void deleteClientTest() {
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.deleteClient(1L);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNifInvalidNifTest() {
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.getClientByNif("0");
        Assertions.assertEquals( Collections.singletonMap("error", "NIF must have 9 characters"), response.getEntity());
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus() );
    }

    @Test
    void getClientByNifTest() {
        when(clientService.findByNif(any(), any())).thenReturn(Optional.of(new ClientDTO()));
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.getClientByNif("123456789");
        Assertions.assertTrue(response.getEntity() instanceof PtClientDTO);
        Assertions.assertEquals( (new PtClientDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNifNotFoundTest() {
        when(clientService.findByNif(any(), any())).thenReturn(Optional.empty());
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.getClientByNif("123456789");
        Assertions.assertNull( response.getEntity());
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNameEmptyListTest() {
        when(clientService.findByNameLike(any(), any())).thenReturn(Collections.emptyList());
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.getClientByName("test");
        Assertions.assertEquals( Collections.emptyList(), response.getEntity());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNameTest() {
        when(clientService.findByNameLike(any(), any())).thenReturn(Collections.singletonList(new ClientDTO()));
        PtClientImplementation ptClientImplementation = new PtClientImplementation(clientService, ptClientMapper);
        Response response = ptClientImplementation.getClientByName("test");
        Assertions.assertEquals( Collections.singletonList(new PtClientDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

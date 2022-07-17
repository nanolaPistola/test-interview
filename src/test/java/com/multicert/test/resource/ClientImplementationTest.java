package com.multicert.test.resource;


import com.multicert.test.service.ClientService;
import com.multicert.test.service.dto.ClientDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ClientImplementationTest {


    private ClientService clientService = mock(ClientService.class);


    @Test
    void findAllTestWithZeroEntries() {
        when(clientService.findAll(any())).thenReturn(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0));
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.getClientList(ClientImplementation.DEFAULT_PAGE, ClientImplementation.DEFAULT_SIZE);
        Assertions.assertEquals( Collections.emptyList(), response.getEntity());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void findAllTestWithEntries() {
        when(clientService.findAll(any())).thenReturn(new PageImpl<>(Collections.singletonList(new ClientDTO()), Pageable.unpaged(), 0));
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.getClientList(ClientImplementation.DEFAULT_PAGE, ClientImplementation.DEFAULT_SIZE);
        Assertions.assertTrue(response.getEntity() instanceof List);
        List body = ((List)response.getEntity());
        Assertions.assertEquals( 1, body.size());
        Assertions.assertEquals( new ClientDTO().toString(), body.get(0).toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus() );
    }


    @Test
    void createClientTest() {
        when(clientService.save(any())).thenReturn(new ClientDTO());
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.createClient(new ClientDTO());
        Assertions.assertTrue(response.getEntity() instanceof ClientDTO);
        Assertions.assertEquals( (new ClientDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus() );
    }

    @Test
    void deleteClientTest() {
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.deleteClient(1L);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNifInvalidNifTest() {
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.getClientByNif("0");
        Assertions.assertEquals( Collections.singletonMap("error", "NIF must have 9 characters"), response.getEntity());
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus() );
    }

    @Test
    void getClientByNifTest() {
        when(clientService.findByNif(any())).thenReturn(Optional.of(new ClientDTO()));
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.getClientByNif("123456789");
        Assertions.assertTrue(response.getEntity() instanceof ClientDTO);
        Assertions.assertEquals( (new ClientDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNifNotFoundTest() {
        when(clientService.findByNif(any())).thenReturn(Optional.empty());
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.getClientByNif("123456789");
        Assertions.assertNull( response.getEntity());
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNameEmptyListTest() {
        when(clientService.findByNameLike(any())).thenReturn(Collections.emptyList());
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.getClientByName("test");
        Assertions.assertEquals( Collections.emptyList(), response.getEntity());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNameTest() {
        when(clientService.findByNameLike(any())).thenReturn(Collections.singletonList(new ClientDTO()));
        ClientImplementation clientImplementation = new ClientImplementation(clientService);
        Response response = clientImplementation.getClientByName("test");
        Assertions.assertEquals( Collections.singletonList(new ClientDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

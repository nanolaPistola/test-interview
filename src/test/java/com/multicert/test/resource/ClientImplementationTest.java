package com.multicert.test.resource;


import com.multicert.test.service.StorageService;
import com.multicert.test.service.dto.StorageDTO;
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


    private StorageService storageService = mock(StorageService.class);


    @Test
    void findAllTestWithZeroEntries() {
        when(storageService.findAll(any())).thenReturn(new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0));
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.getClientList();
        Assertions.assertEquals( Collections.emptyList(), response.getEntity());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void findAllTestWithEntries() {
        when(storageService.findAll(any())).thenReturn(new PageImpl<>(Collections.singletonList(new StorageDTO()), Pageable.unpaged(), 0));
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.getClientList();
        Assertions.assertTrue(response.getEntity() instanceof List);
        List body = ((List)response.getEntity());
        Assertions.assertEquals( 1, body.size());
        Assertions.assertEquals( new StorageDTO().toString(), body.get(0).toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus() );
    }


    @Test
    void createClientTest() {
        when(storageService.save(any())).thenReturn(new StorageDTO());
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.createClient(new StorageDTO());
        Assertions.assertTrue(response.getEntity() instanceof StorageDTO);
        Assertions.assertEquals( (new StorageDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus() );
    }

    @Test
    void deleteClientTest() {
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.deleteClient(1L);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNifInvalidNifTest() {
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.getClientByNif("0");
        Assertions.assertEquals( Collections.singletonMap("error", "NIF must have 9 characters"), response.getEntity());
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus() );
    }

    @Test
    void getClientByNifTest() {
        when(storageService.findByNif(any())).thenReturn(Optional.of(new StorageDTO()));
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.getClientByNif("123456789");
        Assertions.assertTrue(response.getEntity() instanceof StorageDTO);
        Assertions.assertEquals( (new StorageDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNifNotFoundTest() {
        when(storageService.findByNif(any())).thenReturn(Optional.empty());
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.getClientByNif("123456789");
        Assertions.assertNull( response.getEntity());
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNameEmptyListTest() {
        when(storageService.findByNameLike(any())).thenReturn(Collections.emptyList());
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.getClientByName("test");
        Assertions.assertEquals( Collections.emptyList(), response.getEntity());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getClientByNameTest() {
        when(storageService.findByNameLike(any())).thenReturn(Collections.singletonList(new StorageDTO()));
        ClientImplementation clientImplementation = new ClientImplementation(storageService);
        Response response = clientImplementation.getClientByName("test");
        Assertions.assertEquals( Collections.singletonList(new StorageDTO()).toString(), response.getEntity().toString());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

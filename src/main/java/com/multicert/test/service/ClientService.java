package com.multicert.test.service;

import com.multicert.test.domain.Client;
import com.multicert.test.repository.ClientRepository;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.mapper.StorageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final StorageMapper storageMapper;

    public ClientService(ClientRepository clientRepository, StorageMapper storageMapper) {
        this.clientRepository = clientRepository;
        this.storageMapper = storageMapper;
    }

    /**
     * Save a storage.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientDTO save(ClientDTO clientDTO) {
        log.trace("Request to save Storage : {}", clientDTO);
        Client client = storageMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return storageMapper.toDto(client);
    }


    /**
     * Get all the storages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        log.trace("Request to get all Storages");
        return clientRepository.findAll(pageable).map(storageMapper::toDto);
    }


    /**
     * Delete the storage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.trace("Request to delete Storage : {}", id);
        clientRepository.deleteById(id);
    }

    public Optional<ClientDTO> findByNif(String nif) {
        log.trace("Request to findByNif Storage : {}", nif);
        return clientRepository.findByNif(nif).map(storageMapper::toDto);
    }

    public List<ClientDTO> findByNameLike(String name) {
        log.trace("Request to findByNameLike Storage : {}", name);
        return clientRepository.findByNameContainingIgnoreCase(name).stream().map(storageMapper::toDto).toList();
    }
}

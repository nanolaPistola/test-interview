package com.multicert.test.service;

import com.multicert.test.domain.Client;
import com.multicert.test.repository.ClientRepository;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.mapper.ClientMapper;
import com.multicert.test.service.mapper.PtClientMapper;
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

    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    /**
     * Save a storage.
     *
     * @param ClientDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientDTO save(ClientDTO ClientDTO, String country) {
        log.trace("Request to save Storage : {}", ClientDTO);
        Client client = clientMapper.toEntity(ClientDTO);
        client.setCountry(country);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }


    /**
     * Get all the storages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable, String country) {
        log.trace("Request to get all Storages");
        return clientRepository.findByCountry(country, pageable).map(clientMapper::toDto);
    }


    /**
     * Delete the storage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id, String country) {
        log.trace("Request to delete Storage : {}", id);
        clientRepository.deleteByIdAndCountry(id, country);
    }

    public Optional<ClientDTO> findByNif(String nif, String country) {
        log.trace("Request to findByNif Storage : {}", nif);
        return clientRepository.findByFiscalNumberAndCountry(nif, country).map(clientMapper::toDto);
    }

    public List<ClientDTO> findByNameLike(String name, String country) {
        log.trace("Request to findByNameLike Storage : {}", name);
        return clientRepository.findByNameContainingIgnoreCaseAndCountry(name, country).stream().map(clientMapper::toDto).toList();
    }
}

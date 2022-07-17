package com.multicert.test.service;

import com.multicert.test.domain.Storage;
import com.multicert.test.repository.StorageRepository;
import com.multicert.test.service.dto.StorageDTO;
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
 * Service Implementation for managing {@link Storage}.
 */
@Service
@Transactional
public class StorageService {

    private final Logger log = LoggerFactory.getLogger(StorageService.class);

    private final StorageRepository storageRepository;

    private final StorageMapper storageMapper;

    public StorageService(StorageRepository storageRepository, StorageMapper storageMapper) {
        this.storageRepository = storageRepository;
        this.storageMapper = storageMapper;
    }

    /**
     * Save a storage.
     *
     * @param storageDTO the entity to save.
     * @return the persisted entity.
     */
    public StorageDTO save(StorageDTO storageDTO) {
        log.debug("Request to save Storage : {}", storageDTO);
        Storage storage = storageMapper.toEntity(storageDTO);
        storage = storageRepository.save(storage);
        return storageMapper.toDto(storage);
    }


    /**
     * Get all the storages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StorageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Storages");
        return storageRepository.findAll(pageable).map(storageMapper::toDto);
    }


    /**
     * Delete the storage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Storage : {}", id);
        storageRepository.deleteById(id);
    }

    public Optional<StorageDTO> findByNif(String nif) {
        log.debug("Request to findByNif Storage : {}", nif);
        return storageRepository.findByNif(nif).map(storageMapper::toDto);
    }

    public List<StorageDTO> findByNameLike(String name) {
        log.debug("Request to findByNameLike Storage : {}", name);
        return storageRepository.findByNameContainingIgnoreCase(name).stream().map(storageMapper::toDto).toList();
    }
}

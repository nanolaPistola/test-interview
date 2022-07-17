package com.multicert.test.repository;

import com.multicert.test.domain.Storage;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Storage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

    Optional<Storage> findByNif(String nif);

    List<Storage> findByNameContainingIgnoreCase(@Param("name")String name);
}

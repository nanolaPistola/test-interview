package com.multicert.test.repository;

import com.multicert.test.domain.Client;
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
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByNif(String nif);

    List<Client> findByNameContainingIgnoreCase(@Param("name")String name);
}

package com.multicert.test.repository;

import com.multicert.test.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Client> findByCountry(@Param("country") String country,Pageable pageable);

    Optional<Client> findByFiscalNumberAndCountry(String nif, String country);

    List<Client> findByNameContainingIgnoreCaseAndCountry(@Param("name")String name,@Param("country")  String country);

    void deleteByIdAndCountry(Long id, String country);
}

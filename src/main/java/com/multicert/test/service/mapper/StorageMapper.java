package com.multicert.test.service.mapper;

import com.multicert.test.domain.Client;
import com.multicert.test.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface StorageMapper extends EntityMapper<ClientDTO, Client> {}

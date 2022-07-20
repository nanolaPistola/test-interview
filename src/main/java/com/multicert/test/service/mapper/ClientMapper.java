package com.multicert.test.service.mapper;

import com.multicert.test.domain.Client;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.dto.PtClientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
}

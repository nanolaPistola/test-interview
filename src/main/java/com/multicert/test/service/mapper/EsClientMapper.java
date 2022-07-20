package com.multicert.test.service.mapper;

import com.multicert.test.domain.Client;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.dto.EsClientDTO;
import com.multicert.test.service.dto.PtClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity {@link Client} and its DTO {@link PtClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface EsClientMapper extends EntityMapper<EsClientDTO, ClientDTO> {

    @Override
    @Mapping(source = "dni", target = "fiscalNumber")
    ClientDTO toEntity(EsClientDTO dto);

    @Override
    @Mapping(target = "dni", source = "fiscalNumber")
    EsClientDTO toDto(ClientDTO entity);

    @Override
    @Mapping(source = "dni", target = "fiscalNumber")
    List<ClientDTO> toEntity(List<EsClientDTO> dtoList);

    @Override
    @Mapping(target = "dni", source = "fiscalNumber")
    List<EsClientDTO> toDto(List<ClientDTO> entityList);

}

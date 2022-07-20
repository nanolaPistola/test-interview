package com.multicert.test.service.mapper;

import com.multicert.test.domain.Client;
import com.multicert.test.service.dto.ClientDTO;
import com.multicert.test.service.dto.PtClientDTO;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link Client} and its DTO {@link PtClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface PtClientMapper extends EntityMapper<PtClientDTO, ClientDTO> {

    @Override
    @Mapping(source = "nif", target = "fiscalNumber")
    ClientDTO toEntity(PtClientDTO dto);

    @Override
    @Mapping(target = "nif", source = "fiscalNumber")
    PtClientDTO toDto(ClientDTO entity);

    @Override
    @Mapping(source = "nif", target = "fiscalNumber")
    List<ClientDTO> toEntity(List<PtClientDTO> dtoList);

    @Override
    @Mapping(target = "nif", source = "fiscalNumber")
    List<PtClientDTO> toDto(List<ClientDTO> entityList);

}

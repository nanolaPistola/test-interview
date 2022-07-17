package com.multicert.test.service.mapper;

import com.multicert.test.domain.Storage;
import com.multicert.test.service.dto.StorageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Storage} and its DTO {@link StorageDTO}.
 */
@Mapper(componentModel = "spring")
public interface StorageMapper extends EntityMapper<StorageDTO, Storage> {}

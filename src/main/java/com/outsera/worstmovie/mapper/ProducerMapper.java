package com.outsera.worstmovie.mapper;

import com.outsera.worstmovie.dto.ProducerDTO;
import com.outsera.worstmovie.entity.ProducerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProducerMapper extends GenericMapper<ProducerEntity, ProducerDTO> {
}

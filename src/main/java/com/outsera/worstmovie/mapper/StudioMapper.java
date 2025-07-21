package com.outsera.worstmovie.mapper;

import com.outsera.worstmovie.dto.StudioDTO;
import com.outsera.worstmovie.entity.StudioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudioMapper extends GenericMapper<StudioEntity, StudioDTO> {
}

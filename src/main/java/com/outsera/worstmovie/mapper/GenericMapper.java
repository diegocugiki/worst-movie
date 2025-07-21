package com.outsera.worstmovie.mapper;

import java.util.List;

public interface GenericMapper<E, D> {
    E toEntity(D dto);
    D toDTO(E entity);
    List<D> toDTO(List<E> entities);
    List<E> toEntity(List<D> dtos);
}

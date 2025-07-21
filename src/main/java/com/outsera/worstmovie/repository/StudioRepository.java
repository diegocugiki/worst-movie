package com.outsera.worstmovie.repository;

import com.outsera.worstmovie.entity.StudioEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends GenericNamedRepository<StudioEntity, Long> {
}

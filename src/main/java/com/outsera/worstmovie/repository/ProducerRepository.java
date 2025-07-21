package com.outsera.worstmovie.repository;

import com.outsera.worstmovie.entity.ProducerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends GenericNamedRepository<ProducerEntity, Long> {
}

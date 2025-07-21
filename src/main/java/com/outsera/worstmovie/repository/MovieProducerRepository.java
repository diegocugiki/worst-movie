package com.outsera.worstmovie.repository;

import com.outsera.worstmovie.entity.MovieProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieProducerRepository extends JpaRepository<MovieProducerEntity, Long> {
}
package com.outsera.worstmovie.repository;

import com.outsera.worstmovie.entity.MovieStudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStudioRepository extends JpaRepository<MovieStudioEntity, Long> {
}
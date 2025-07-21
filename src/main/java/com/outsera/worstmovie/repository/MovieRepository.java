package com.outsera.worstmovie.repository;

import com.outsera.worstmovie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Optional<MovieEntity> findByTitle(String title);

    MovieEntity findByUid(String uid);
}

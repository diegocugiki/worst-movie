package com.outsera.worstmovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface GenericNamedRepository<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findByName(String name);
    T findByUid(String uid);
    void deleteByUid(String uid);
}

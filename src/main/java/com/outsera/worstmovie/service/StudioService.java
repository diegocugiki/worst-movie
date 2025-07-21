package com.outsera.worstmovie.service;

import com.outsera.worstmovie.dto.StudioDTO;
import com.outsera.worstmovie.entity.MovieEntity;
import com.outsera.worstmovie.entity.MovieStudioEntity;
import com.outsera.worstmovie.entity.StudioEntity;
import com.outsera.worstmovie.mapper.StudioMapper;
import com.outsera.worstmovie.repository.MovieStudioRepository;
import com.outsera.worstmovie.repository.StudioRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class StudioService extends GenericCrudService<StudioEntity, StudioDTO, StudioRepository, StudioMapper> {

    private final MovieStudioRepository movieStudioRepository;

    public StudioService(StudioRepository repository,
                         StudioMapper mapper,
                         MovieStudioRepository movieStudioRepository) {
        super(repository, mapper, "Estúdio");

        this.movieStudioRepository = movieStudioRepository;
    }

    /**
     * Salva os estúdios e vincula o filme com os dados do CSV
     * @param record registro (linha) do CSV, contendo os dados dos estúdios para inserção
     * @param movie filme a ser vínculado com o estúdio
     */
    public void saveFromCsv(CSVRecord record, MovieEntity movie) {
        List<StudioEntity> studios = Arrays.stream(record.get("studios").split(","))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .map(name -> repository.findByName(name)
                        .orElseGet(() ->
                        {
                            StudioEntity entity = new StudioEntity();
                            entity.setName(name);
                            entity.setUid(UUID.randomUUID().toString());
                            repository.save(entity);
                            return entity;
                        }))
                .toList();

        for (StudioEntity studio : studios) {
            MovieStudioEntity entity = new MovieStudioEntity();
            entity.setUid(UUID.randomUUID().toString());
            entity.setStudio(studio);
            entity.setMovie(movie);
            movieStudioRepository.save(entity);
        }
    }
}
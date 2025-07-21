package com.outsera.worstmovie.service;

import com.outsera.worstmovie.dto.ProducerDTO;
import com.outsera.worstmovie.entity.MovieEntity;
import com.outsera.worstmovie.entity.MovieProducerEntity;
import com.outsera.worstmovie.entity.ProducerEntity;
import com.outsera.worstmovie.mapper.ProducerMapper;
import com.outsera.worstmovie.repository.MovieProducerRepository;
import com.outsera.worstmovie.repository.ProducerRepository;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProducerService extends GenericCrudService<ProducerEntity, ProducerDTO, ProducerRepository, ProducerMapper> {

    private final MovieProducerRepository movieProducerRepository;

    public ProducerService(ProducerRepository repository, ProducerMapper mapper, MovieProducerRepository movieProducerRepository) {
        super(repository, mapper, "Produtor");

        this.movieProducerRepository = movieProducerRepository;
    }

    /**
     * Salva os produtores e vincula o filme com os dados do CSV
     * @param record registro (linha) do CSV, contendo os dados dos produtores para inserção
     * @param movie filme a ser vínculado com os produtores
     */
    public void saveFromCsv(CSVRecord record, MovieEntity movie) {
        List<ProducerEntity> producers = Arrays.stream(record.get("producers").split("and|,"))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .map(name -> repository.findByName(name)
                        .orElseGet(() -> {
                            ProducerEntity entity = new ProducerEntity();
                            entity.setName(name);
                            entity.setUid(UUID.randomUUID().toString());
                            repository.save(entity);
                            return entity;
                        }))
                .toList();

        for (ProducerEntity producer : producers) {
            MovieProducerEntity entity = new MovieProducerEntity();
            entity.setUid(UUID.randomUUID().toString());
            entity.setProducer(producer);
            entity.setMovie(movie);
            movieProducerRepository.save(entity);
        }
    }
}
package com.outsera.worstmovie.service;

import com.outsera.worstmovie.dto.DefaultReturnDTO;
import com.outsera.worstmovie.dto.MovieDTO;
import com.outsera.worstmovie.entity.*;
import com.outsera.worstmovie.mapper.MovieMapper;
import com.outsera.worstmovie.repository.MovieRepository;
import com.outsera.worstmovie.repository.ProducerRepository;
import com.outsera.worstmovie.repository.StudioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repository;
    private final ProducerRepository producerRepository;
    private final StudioRepository studioRepository;
    private final MovieMapper mapper;

    /**
     * Obtém todos os filmes cadastrados, exibindo seu produtor e estúdio.
     */
    public DefaultReturnDTO<List<MovieDTO>> findAll() {
        try {
            List<MovieEntity> movieEntities = repository.findAll(Sort.by("year").ascending());

            return DefaultReturnDTO.<List<MovieDTO>>builder()
                    .success(true)
                    .message("Filmes obtidos com sucesso!")
                    .data(mapper.toDTO(movieEntities))
                    .build();
        } catch (Exception e) {
            return DefaultReturnDTO.<List<MovieDTO>>builder()
                    .success(false)
                    .message("Ocorreu um erro ao listar os filmes: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Cria um novo filme no sistema.
     * @param dto DTO contendo as informações para cadastro do filme, incluindo UIDs de estúdios e produtores.
     */
    @Transactional
    public DefaultReturnDTO<MovieDTO> create(MovieDTO dto) {
        try {
            Optional<MovieEntity> existingMovie = repository.findByTitle(dto.getTitle());
            if (existingMovie.isPresent()) {
                return DefaultReturnDTO.<MovieDTO>builder()
                        .success(false)
                        .message("Já existe um filme cadastrado com este título.")
                        .build();
            }

            MovieEntity movie = mapper.toEntity(dto);
            movie.setUid(UUID.randomUUID().toString());
            movie.setMovieProducers(new ArrayList<>());
            movie.setMovieStudios(new ArrayList<>());

            MovieEntity savedMovie = repository.save(movie);

            DefaultReturnDTO<MovieDTO> result = addProducersAndStudios(savedMovie, dto);
            if (!result.isSuccess()) {
                return result;
            }

            repository.save(savedMovie);

            return DefaultReturnDTO.<MovieDTO>builder()
                    .success(true)
                    .message("Filme criado com sucesso!")
                    .data(mapper.toDTO(savedMovie))
                    .build();
        } catch (Exception ex) {
            return DefaultReturnDTO.<MovieDTO>builder()
                    .success(false)
                    .message("Erro ao criar filme: " + ex.getMessage())
                    .build();
        }
    }

    /**
     * Atualiza o registro do filme no sistema.
     * @param dto DTO contendo as informações para edição do filme, incluindo UIDs de estúdios e produtores.
     */
    @Transactional
    public DefaultReturnDTO<MovieDTO> edit(MovieDTO dto) {
        try {
            MovieEntity movieToUpdate = repository.findByUid(dto.getUid());
            if (movieToUpdate == null) {
                return DefaultReturnDTO.<MovieDTO>builder()
                        .success(false)
                        .message("Filme não encontrado para atualização.")
                        .build();
            }

            Optional<MovieEntity> existingMovie = repository.findByTitle(dto.getTitle());
            if (existingMovie.isPresent() &&
                    !existingMovie.get().getUid().equals(movieToUpdate.getUid())) {
                return DefaultReturnDTO.<MovieDTO>builder()
                        .success(false)
                        .message("Já existe outro filme com este título.")
                        .build();
            }

            movieToUpdate.setTitle(dto.getTitle());
            movieToUpdate.setYear(dto.getYear());
            movieToUpdate.setWinner(dto.getWinner());
            movieToUpdate.getMovieProducers().clear();
            movieToUpdate.getMovieStudios().clear();

            DefaultReturnDTO<MovieDTO> result = addProducersAndStudios(movieToUpdate, dto);
            if (!result.isSuccess()) {
                return result;
            }

            MovieEntity updatedMovie = repository.save(movieToUpdate);

            return DefaultReturnDTO.<MovieDTO>builder()
                    .success(true)
                    .message("Filme atualizado com sucesso!")
                    .data(mapper.toDTO(updatedMovie))
                    .build();
        } catch (Exception ex) {
            return DefaultReturnDTO.<MovieDTO>builder()
                    .success(false)
                    .message("Erro ao atualizar filme: " + ex.getMessage())
                    .build();
        }
    }

    /**
     * Remove o registro do filme do sistema.
     * @param uid UID do filme
     */
    @Transactional
    public DefaultReturnDTO<String> deleteByUid(String uid) {
        try {
            MovieEntity movieToDelete = repository.findByUid(uid);
            if (movieToDelete == null) {
                return DefaultReturnDTO.<String>builder()
                        .success(false)
                        .message("Filme não encontrado para remoção.")
                        .build();
            }

            repository.delete(movieToDelete);

            return DefaultReturnDTO.<String>builder()
                    .success(true)
                    .message("Filme removido com sucesso.")
                    .build();
        } catch (Exception e) {
            return DefaultReturnDTO.<String>builder()
                    .success(false)
                    .message("Erro ao remover o filme pelo UID: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Adiciona produtores e estúdios a um filme existente ou recém-criado
     * Retorna DefaultReturnDTO para indicar sucesso ou falha na validação de UIDs
     * @param movieEntity O filme ao qual os relacionamentos serão adicionados
     * @param movieDTO O DTO contendo os UIDs de produtores e estúdios
     */
    private DefaultReturnDTO<MovieDTO> addProducersAndStudios(MovieEntity movieEntity, MovieDTO movieDTO) {
        if (movieDTO.getProducer() != null && !movieDTO.getProducer().isEmpty()) {
            for (String producerUid : movieDTO.getProducer()) {
                ProducerEntity producer = producerRepository.findByUid(producerUid);
                if (producer != null) {
                    MovieProducerEntity movieProducer = new MovieProducerEntity();
                    movieProducer.setMovie(movieEntity);
                    movieProducer.setProducer(producer);
                    movieProducer.setUid(UUID.randomUUID().toString());
                    movieEntity.getMovieProducers().add(movieProducer);
                } else {
                    return DefaultReturnDTO.<MovieDTO>builder()
                            .success(false)
                            .message("Produtor com UID " + producerUid + " não encontrado.")
                            .build();
                }
            }
        }

        if (movieDTO.getStudio() != null && !movieDTO.getStudio().isEmpty()) {
            for (String studioUid : movieDTO.getStudio()) {
                StudioEntity studio = studioRepository.findByUid(studioUid);
                if (studio != null) {
                    MovieStudioEntity movieStudio = new MovieStudioEntity();
                    movieStudio.setMovie(movieEntity);
                    movieStudio.setStudio(studio);
                    movieStudio.setUid(UUID.randomUUID().toString());
                    movieEntity.getMovieStudios().add(movieStudio);
                } else {
                    return DefaultReturnDTO.<MovieDTO>builder()
                            .success(false)
                            .message("Estúdio com UID " + studioUid + " não encontrado.")
                            .build();
                }
            }
        }
        return DefaultReturnDTO.<MovieDTO>builder().success(true).build();
    }

    /**
     * Salva o filme com os dados do CSV
     * Retorna MovieEntity com dados do filme armazenado
     * @param record registro (linha) do CSV, contendo os dados do filme para inserção
     */
    public MovieEntity saveFromCSV(CSVRecord record) {
        MovieEntity movie = new MovieEntity();
        movie.setUid(UUID.randomUUID().toString());
        movie.setYear(Integer.parseInt(record.get("year")));
        movie.setTitle(record.get("title"));
        movie.setWinner(record.isSet("winner") && record.get("winner").equalsIgnoreCase("yes"));
        return repository.save(movie);
    }
}

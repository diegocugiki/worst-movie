package com.outsera.worstmovie.service;

import com.outsera.worstmovie.dto.AwardIntervalResponseDTO;
import com.outsera.worstmovie.dto.ProducerAwardIntervalDTO;
import com.outsera.worstmovie.entity.MovieEntity;
import com.outsera.worstmovie.entity.MovieProducerEntity;
import com.outsera.worstmovie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AwardService {

    private final MovieRepository movieRepository;

    public AwardIntervalResponseDTO getProducerAwardIntervals() {
        List<MovieEntity> winnerMovies = movieRepository.findAll().stream()
                .filter(MovieEntity::getWinner)
                .toList();

        Map<String, List<Integer>> producerWins = new HashMap<>();

        for (MovieEntity movie : winnerMovies) {
            for (MovieProducerEntity mp : movie.getMovieProducers()) {
                String producerName = mp.getProducer().getName();
                producerWins.computeIfAbsent(producerName, k -> new ArrayList<>()).add(movie.getYear());
            }
        }

        List<ProducerAwardIntervalDTO> allProducerIntervals = new ArrayList<>();

        producerWins.forEach((producerName, years) -> {
            if (years.size() >= 2) {
                years.sort(Integer::compareTo);

                for (int i = 0; i < years.size() - 1; i++) {
                    Integer previousWin = years.get(i);
                    Integer followingWin = years.get(i + 1);
                    Integer interval = followingWin - previousWin;

                    allProducerIntervals.add(ProducerAwardIntervalDTO.builder()
                            .producer(producerName)
                            .interval(interval)
                            .previousWin(previousWin)
                            .followingWin(followingWin)
                            .build());
                }
            }
        });

        List<ProducerAwardIntervalDTO> minIntervals = allProducerIntervals.stream()
                .min(Comparator.comparing(ProducerAwardIntervalDTO::getInterval))
                .map(minDto -> allProducerIntervals.stream()
                        .filter(dto -> dto.getInterval().equals(minDto.getInterval()))
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        List<ProducerAwardIntervalDTO> maxIntervals = allProducerIntervals.stream()
                .max(Comparator.comparing(ProducerAwardIntervalDTO::getInterval))
                .map(maxDto -> allProducerIntervals.stream()
                        .filter(dto -> dto.getInterval().equals(maxDto.getInterval()))
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        return AwardIntervalResponseDTO
                .builder()
                .min(minIntervals)
                .max(maxIntervals)
                .build();
    }
}

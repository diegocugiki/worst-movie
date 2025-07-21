package com.outsera.worstmovie.loader;

import com.outsera.worstmovie.entity.*;
import com.outsera.worstmovie.service.MovieService;
import com.outsera.worstmovie.service.ProducerService;
import com.outsera.worstmovie.service.StudioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
@Slf4j
public class LoadCSVData implements CommandLineRunner {

    private final MovieService movieService;
    private final ProducerService producerService;
    private final StudioService studioService;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Carregando dados do CSV");

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("movielist.csv").getInputStream(), StandardCharsets.UTF_8));

             CSVParser parser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.builder()
                             .setHeader("year", "title", "studios", "producers", "winner")
                             .setSkipHeaderRecord(true)
                             .setTrim(true)
                             .setDelimiter(';')
                             .build())) {

            Iterable<CSVRecord> records = parser.getRecords();

            for (CSVRecord record : records) {
                try {
                    MovieEntity movie = movieService.saveFromCSV(record);
                    studioService.saveFromCsv(record, movie);
                    producerService.saveFromCsv(record, movie);
                } catch (NumberFormatException e) {
                    log.error("Ao ler a linha: {}. Ocorre o erro: {}", record.getRecordNumber(), e.getMessage());
                } catch (Exception e) {
                    log.error("Ao processar a linha {} ocorre um erro: {}. Dados: {}", record.getRecordNumber(), e.getMessage(), record.toMap());
                }
            }

            log.info("Dados do CSV carregados com sucesso.");
        } catch (Exception e) {
            log.error("Ao carregar os dados do CSV, ocorreu um erro: {}", e.getMessage(), e);
        }
    }
}

package com.outsera.worstmovie.controller;

import com.outsera.worstmovie.dto.DefaultReturnDTO;
import com.outsera.worstmovie.dto.MovieDTO;
import com.outsera.worstmovie.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/movie")
@Tag(name = "Filmes", description = "Gerenciar os filmes cadastrados no sistema")
public class MovieController {

    private final MovieService service;

    @GetMapping("/list")
    @Operation(summary = "Obter todos os filmes cadastrados")
    public ResponseEntity<DefaultReturnDTO<List<MovieDTO>>> findAll() {
        return new ResponseEntity<>(service.findAll(), OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Criar um novo filme")
    public ResponseEntity<DefaultReturnDTO<MovieDTO>> create(@RequestBody @Valid MovieDTO movieDTO) {
        return new ResponseEntity<>(service.create(movieDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    @Operation(summary = "Atualizar o registro do filme")
    public ResponseEntity<DefaultReturnDTO<MovieDTO>> edit(@RequestBody @Valid MovieDTO movieDTO) {
        return new ResponseEntity<>(service.edit(movieDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Deletar o filme")
    public ResponseEntity<DefaultReturnDTO<String>> deleteByUid(@Parameter(description = "UID do filme") @RequestParam String uid) {
        return new ResponseEntity<>(service.deleteByUid(uid), OK);
    }
}

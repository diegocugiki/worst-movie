package com.outsera.worstmovie.controller;

import com.outsera.worstmovie.dto.DefaultReturnDTO;
import com.outsera.worstmovie.dto.StudioDTO;
import com.outsera.worstmovie.service.StudioService;
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
@RequestMapping("/studio")
@Tag(name = "Estúdios", description = "Gerenciar os estúdios cadastrados no sistema")
public class StudioController {

    private final StudioService service;

    @GetMapping("/list")
    @Operation(summary = "Obter todos os estúdios cadastrados")
    public ResponseEntity<DefaultReturnDTO<List<StudioDTO>>> findAll() {
        return new ResponseEntity<>(service.findAll(), OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Criar um novo estúdio")
    public ResponseEntity<DefaultReturnDTO<StudioDTO>> create(@RequestBody @Valid StudioDTO studioDTO) {
        return new ResponseEntity<>(service.create(studioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    @Operation(summary = "Atualizar o registro do estúdio")
    public ResponseEntity<DefaultReturnDTO<StudioDTO>> edit(@RequestBody @Valid StudioDTO studioDTO) {
        return new ResponseEntity<>(service.edit(studioDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Deletar o estúdio")
    public ResponseEntity<DefaultReturnDTO<String>> deleteByUid(@Parameter(description = "UID do estúdio") @RequestParam String uid) {
        return new ResponseEntity<>(service.deleteByUid(uid), OK);
    }
}

package com.outsera.worstmovie.controller;

import com.outsera.worstmovie.dto.DefaultReturnDTO;
import com.outsera.worstmovie.dto.ProducerDTO;
import com.outsera.worstmovie.service.ProducerService;
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
@RequestMapping("/producer")
@Tag(name = "Produtores", description = "Gerenciar os produtores cadastrados no sistema")
public class ProducerController {

    private final ProducerService service;

    @GetMapping("/list")
    @Operation(summary = "Obter todos os produtores cadastrados")
    public ResponseEntity<DefaultReturnDTO<List<ProducerDTO>>> findAll() {
        return new ResponseEntity<>(service.findAll(), OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Criar um novo produtor")
    public ResponseEntity<DefaultReturnDTO<ProducerDTO>> create(@RequestBody @Valid ProducerDTO producerDTO) {
        return new ResponseEntity<>(service.create(producerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    @Operation(summary = "Atualizar o registro do produtor")
    public ResponseEntity<DefaultReturnDTO<ProducerDTO>> edit(@RequestBody @Valid ProducerDTO producerDTO) {
        return new ResponseEntity<>(service.edit(producerDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Deletar o produtor")
    public ResponseEntity<DefaultReturnDTO<String>> deleteByUid(@Parameter(description = "UID do Produtor") @RequestParam String uid) {
        return new ResponseEntity<>(service.deleteByUid(uid), OK);
    }
}

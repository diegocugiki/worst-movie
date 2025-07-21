package com.outsera.worstmovie.controller;

import com.outsera.worstmovie.dto.AwardIntervalResponseDTO;
import com.outsera.worstmovie.service.AwardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/awards")
@AllArgsConstructor
@Tag(name = "Prêmios", description = "Operações relacionadas a intervalos de prêmios de produtores")
public class AwardController {
    private final AwardService awardService;

    @GetMapping("/producer-intervals")
    @Operation(summary = "Obter intervalos de prêmios para produtores")
    public ResponseEntity<AwardIntervalResponseDTO> getProducerAwardIntervals() {
        return ResponseEntity.ok(awardService.getProducerAwardIntervals());
    }
}

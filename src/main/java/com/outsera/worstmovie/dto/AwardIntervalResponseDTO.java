package com.outsera.worstmovie.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AwardIntervalResponseDTO {
    private List<ProducerAwardIntervalDTO> min;
    private List<ProducerAwardIntervalDTO> max;
}

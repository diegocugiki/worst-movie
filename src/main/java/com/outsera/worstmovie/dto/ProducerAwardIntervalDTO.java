package com.outsera.worstmovie.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProducerAwardIntervalDTO {
    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}

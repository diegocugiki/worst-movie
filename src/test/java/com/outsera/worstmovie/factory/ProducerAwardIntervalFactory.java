package com.outsera.worstmovie.factory;

import com.outsera.worstmovie.dto.ProducerAwardIntervalDTO;

public class ProducerAwardIntervalFactory {
    public static ProducerAwardIntervalDTO createMinProducer() {
        return ProducerAwardIntervalDTO
                .builder()
                .producer("Joel Silver")
                .interval(1)
                .previousWin(1990)
                .followingWin(1991)
                .build();
    }

    public static ProducerAwardIntervalDTO createMaxProducer() {
        return ProducerAwardIntervalDTO
                .builder()
                .producer("Matthew Vaughn")
                .interval(13)
                .previousWin(2002)
                .followingWin(2015)
                .build();
    }
}

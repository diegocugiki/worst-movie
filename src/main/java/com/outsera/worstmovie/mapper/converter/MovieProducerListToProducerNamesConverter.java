package com.outsera.worstmovie.mapper.converter;

import com.outsera.worstmovie.entity.MovieProducerEntity;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class MovieProducerListToProducerNamesConverter extends AbstractConverter<List<MovieProducerEntity>, List<String>> {

    @Override
    protected List<String> convert(List<MovieProducerEntity> source) {
        if (source == null) {
            return null;
        }

        return source.stream()
                .map(mp -> mp.getProducer().getName())
                .collect(Collectors.toList());
    }
}
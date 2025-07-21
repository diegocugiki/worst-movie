package com.outsera.worstmovie.mapper.converter;

import com.outsera.worstmovie.entity.MovieStudioEntity;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class MovieStudioListToStudioNamesConverter extends AbstractConverter<List<MovieStudioEntity>, List<String>> {

    @Override
    protected List<String> convert(List<MovieStudioEntity> source) {
        if (source == null) {
            return null;
        }

        return source.stream()
                .map(mp -> mp.getStudio().getName())
                .collect(Collectors.toList());
    }
}

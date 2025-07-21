package com.outsera.worstmovie.mapper;

import com.outsera.worstmovie.dto.MovieDTO;
import com.outsera.worstmovie.entity.MovieEntity;
import com.outsera.worstmovie.mapper.converter.MovieProducerListToProducerNamesConverter;
import com.outsera.worstmovie.mapper.converter.MovieStudioListToStudioNamesConverter;
import com.outsera.worstmovie.utils.BaseMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper extends BaseMapper<MovieEntity, MovieDTO> {
    @Override
    protected void configure(ModelMapper modelMapper) {
        modelMapper.addConverter(new MovieProducerListToProducerNamesConverter());
        modelMapper.addConverter(new MovieStudioListToStudioNamesConverter());

        modelMapper.addMappings(new PropertyMap<MovieEntity, MovieDTO>() {
            @Override
            protected void configure() {
                using(new MovieProducerListToProducerNamesConverter())
                        .map(source.getMovieProducers(), destination.getProducer());

                using(new MovieStudioListToStudioNamesConverter())
                        .map(source.getMovieStudios(), destination.getStudio());
            }
        });
    }
}
package com.outsera.worstmovie.utils;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public abstract class BaseMapper<TEntity, DTO> {

    @Autowired
    protected ModelMapper modelMapper;

    @PostConstruct
    private void setup() {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        new Thread(() -> this.configure(this.modelMapper)).start();
    }

    private final Class<TEntity> typeEntity;

    private final Class<DTO> typeDTO;

    @SuppressWarnings("unchecked")
    public BaseMapper() {
        this.typeEntity = (Class<TEntity>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.typeDTO = (Class<DTO>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
    }

    protected abstract void configure(ModelMapper modelMapper);

    public DTO toDTO(TEntity entity) {
        return entity != null ? this.getModelMapper().map(entity, this.typeDTO) : null;
    }

    public DTO toDTO(Optional<TEntity> optional) {
        return optional.map(tEntity -> this.getModelMapper().map(tEntity, this.typeDTO)).orElse(null);
    }

    public void toDTO(DTO dtoSource, DTO dtoDestination) {
        this.getModelMapper().map(dtoSource, dtoDestination);
    }

    public TEntity toEntity(DTO dto) {
        return dto != null ? this.getModelMapper().map(dto, this.typeEntity) : null;
    }

    public void toEntity(TEntity entitySource, TEntity entityDestination) {
        this.getModelMapper().map(entitySource, entityDestination);
    }

    public List<DTO> toDTO(List<TEntity> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<TEntity> toEntity(List<DTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public Page<DTO> toDTO(Page<TEntity> listEntity) {
        return listEntity.map(BaseMapper.this::toDTO);
    }

    public Page<TEntity> toEntity(Page<DTO> dtos) {
        return dtos.map(BaseMapper.this::toEntity);
    }
}

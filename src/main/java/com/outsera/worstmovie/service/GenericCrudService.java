package com.outsera.worstmovie.service;

import com.outsera.worstmovie.dto.DefaultReturnDTO;
import com.outsera.worstmovie.mapper.GenericMapper;
import com.outsera.worstmovie.repository.GenericNamedRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(force = true)
public abstract class GenericCrudService<E, D, R extends GenericNamedRepository<E, Long>, M extends GenericMapper<E, D>> {

    protected final R repository;
    protected final M mapper;
    protected final String entityName;

    public GenericCrudService(R repository, M mapper, String entityName) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityName = entityName;
    }

    /**
     * Obtém todas as entidades cadastradas.
     */
    public DefaultReturnDTO<List<D>> findAll() {
        try {
            List<E> entities = repository.findAll(Sort.by("name").ascending());

            return DefaultReturnDTO.<List<D>>builder()
                    .success(true)
                    .message(entityName + "s obtidos com sucesso!")
                    .data(mapper.toDTO(entities))
                    .build();
        } catch (Exception e) {
            return DefaultReturnDTO.<List<D>>builder()
                    .success(false)
                    .message("Ocorreu um erro ao listar os " + entityName.toLowerCase() + "s: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Cria uma nova entidade no sistema.
     * Vefiica se existe entidade com mesmo nome, caso existir não cria.
     * @param dto DTO contendo as informações para cadastro da entidade
     */
    @Transactional
    public DefaultReturnDTO<D> create(D dto) {
        try {
            // Assumimos que o DTO tem um método getName()
            String name = (String) dto.getClass().getMethod("getName").invoke(dto);
            Optional<E> existingEntity = repository.findByName(name);
            if (existingEntity.isPresent()) {
                return DefaultReturnDTO.<D>builder()
                        .success(false)
                        .message(entityName + " já cadastrado(a) com este nome.")
                        .build();
            }

            E entity = mapper.toEntity(dto);
            // Assumimos que a entidade tem um método setUid(String)
            Method setUidMethod = entity.getClass().getMethod("setUid", String.class);
            setUidMethod.invoke(entity, UUID.randomUUID().toString());

            repository.save(entity);

            return DefaultReturnDTO.<D>builder()
                    .success(true)
                    .message("Cadastro criado com sucesso!")
                    .data(mapper.toDTO(entity))
                    .build();
        } catch (Exception ex) {
            return DefaultReturnDTO.<D>builder()
                    .success(false)
                    .message("Erro ao criar cadastro do " + entityName.toLowerCase() + ": " + ex.getMessage())
                    .build();
        }
    }

    /**
     * Atualiza o registro da entidade no sistema.
     * Vefiica se existe entidade com mesmo nome, caso existir não edita.
     * @param dto DTO contendo as informações para edição da entidade
     */
    @Transactional
    public DefaultReturnDTO<D> edit(D dto) {
        try {
            // Assumimos que o DTO tem um método getUid()
            String dtoUid = (String) dto.getClass().getMethod("getUid").invoke(dto);
            E entityToUpdate = repository.findByUid(dtoUid);

            if (entityToUpdate == null) { // Caso a entidade não seja encontrada pelo UID
                return DefaultReturnDTO.<D>builder()
                        .success(false)
                        .message(entityName + " não encontrado(a) para atualização.")
                        .build();
            }

            // Assumimos que o DTO tem um método getName()
            String newName = (String) dto.getClass().getMethod("getName").invoke(dto);
            Optional<E> existingEntityWithName = repository.findByName(newName);

            // Assumimos que a entidade tem um método getUid()
            String entityToUpdateUid = (String) entityToUpdate.getClass().getMethod("getUid").invoke(entityToUpdate);

            if (existingEntityWithName.isPresent() && !Objects.equals(
                    (String) existingEntityWithName.get().getClass().getMethod("getUid").invoke(existingEntityWithName.get()),
                    entityToUpdateUid)) {
                return DefaultReturnDTO.<D>builder()
                        .success(false)
                        .message("Já existe um(a) " + entityName.toLowerCase() + " com este nome.")
                        .build();
            }

            // Assumimos que a entidade tem um método setName(String)
            Method setNameMethod = entityToUpdate.getClass().getMethod("setName", String.class);
            setNameMethod.invoke(entityToUpdate, newName);

            repository.save(entityToUpdate);

            return DefaultReturnDTO.<D>builder()
                    .success(true)
                    .message("Cadastro atualizado com sucesso!")
                    .data(mapper.toDTO(entityToUpdate))
                    .build();
        } catch (Exception ex) {
            return DefaultReturnDTO.<D>builder()
                    .success(false)
                    .message("Erro ao atualizar cadastro do " + entityName.toLowerCase() + ": " + ex.getMessage())
                    .build();
        }
    }

    /**
     * Remove o registro da entidade do sistema.
     * @param uid UID da entidade
     */
    @Transactional
    public DefaultReturnDTO<String> deleteByUid(String uid) {
        try {
            repository.deleteByUid(uid);

            return DefaultReturnDTO.<String>builder()
                    .success(true)
                    .message(entityName + " removido(a) com sucesso.")
                    .build();
        } catch (Exception e) {
            return DefaultReturnDTO.<String>builder()
                    .success(false)
                    .message("Erro ao remover o " + entityName.toLowerCase() + " pelo UID: " + e.getMessage())
                    .build();
        }
    }
}

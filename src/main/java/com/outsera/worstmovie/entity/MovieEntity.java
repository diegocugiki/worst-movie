package com.outsera.worstmovie.entity;

import com.outsera.worstmovie.converter.BooleanTypeConversor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FILME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {

    @Id
    @Column(name = "COD_FILME", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cod;

    @Column(name = "GUID_HASH", length = 36, nullable = false)
    private String uid;

    @Column(name = "NR_ANO")
    private Integer year;

    @Column(name = "STR_TITULO")
    private String title;

    @Column(name = "FLG_GANHADOR")
    @Convert(converter = BooleanTypeConversor.class)
    private Boolean winner;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MovieStudioEntity> movieStudios = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MovieProducerEntity> movieProducers = new ArrayList<>();

}

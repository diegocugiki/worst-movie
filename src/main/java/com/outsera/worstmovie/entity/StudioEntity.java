package com.outsera.worstmovie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ESTUDIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudioEntity {

    @Id
    @Column(name = "COD_ESTUDIO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cod;

    @Column(name = "GUID_HASH", length = 36, nullable = false)
    private String uid;

    @Column(name = "STR_NOME", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MovieStudioEntity> movieStudios = new ArrayList<>();
}

package com.outsera.worstmovie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FILME_ESTUDIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieStudioEntity {

    @Id
    @Column(name = "COD_FILME_ESTUDIO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GUID_HASH", length = 36, nullable = false)
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_FILME", referencedColumnName = "COD_FILME", nullable = false)
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_ESTUDIO", referencedColumnName = "COD_ESTUDIO", nullable = false)
    private StudioEntity studio;
}

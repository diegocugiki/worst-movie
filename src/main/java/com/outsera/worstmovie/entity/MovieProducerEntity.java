package com.outsera.worstmovie.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "FILME_PRODUTOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieProducerEntity implements Serializable {

    @Id
    @Column(name = "COD_FILME_PRODUTOR", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GUID_HASH", length = 36, nullable = false)
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_FILME", referencedColumnName = "COD_FILME", nullable = false)
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_PRODUTOR", referencedColumnName = "COD_PRODUTOR", nullable = false)
    private ProducerEntity producer;
}

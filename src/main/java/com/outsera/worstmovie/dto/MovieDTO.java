package com.outsera.worstmovie.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDTO {
    private String uid;
    private Integer year;
    private String title;
    private List<String> studio;
    private List<String> producer;
    private Boolean winner;
}

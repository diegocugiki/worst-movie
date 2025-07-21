package com.outsera.worstmovie.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefaultReturnDTO<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> DefaultReturnDTOBuilder<T> builder() {
        return new DefaultReturnDTOBuilder<T>().data(null);
    }
}

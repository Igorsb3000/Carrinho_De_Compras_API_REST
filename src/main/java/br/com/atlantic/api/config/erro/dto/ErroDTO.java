package br.com.atlantic.api.config.erro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErroDTO {
    private int code;
    private String message;
}

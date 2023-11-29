package br.com.atlantic.api.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CheckoutResponseDTO {

    private BigDecimal total;
}

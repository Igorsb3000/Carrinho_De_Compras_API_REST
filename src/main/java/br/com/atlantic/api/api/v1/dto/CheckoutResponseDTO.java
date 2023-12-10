package br.com.atlantic.api.api.v1.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter 
@Setter
public class CheckoutResponseDTO {
	
	private BigDecimal subTotal;
	
	private BigDecimal frete;
    
	// total = subTotal (valor dos itens) + valor do frete
	private BigDecimal total;
}

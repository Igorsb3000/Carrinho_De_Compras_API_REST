package br.com.atlantic.api.domain.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.atlantic.api.api.v1.dto.CheckoutResponseDTO;
import br.com.atlantic.api.domain.model.Carrinho;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CarrinhoMapper {
    private final ModelMapper mapper;

    /**
     * Converte um Carrinho em CheckoutResponseDTO
     * @param carrinhoCreated
     * @return responseDTO
     */
    public CheckoutResponseDTO toCheckoutResponseDTO(Carrinho carrinhoCreated){
        CheckoutResponseDTO responseDTO = mapper.map(carrinhoCreated, CheckoutResponseDTO.class);
        return responseDTO;
    }
}

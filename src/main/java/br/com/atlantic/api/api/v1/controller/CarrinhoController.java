package br.com.atlantic.api.api.v1.controller;

import br.com.atlantic.api.api.v1.dto.CheckoutResponseDTO;
import br.com.atlantic.api.config.ApiVersion;
import br.com.atlantic.api.domain.mapper.CarrinhoMapper;
import br.com.atlantic.api.domain.model.Carrinho;
import br.com.atlantic.api.domain.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.V1 + "/checkout")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;
    
    private final CarrinhoMapper mapper;

    @PostMapping()
    public CheckoutResponseDTO post(@Valid @RequestBody Carrinho carrinho){
    	Carrinho carrinhoObtido = carrinhoService.insert(carrinho);
        CheckoutResponseDTO responseDTO = mapper.toCheckoutResponseDTO(carrinhoObtido);
    	return responseDTO;
    }
}

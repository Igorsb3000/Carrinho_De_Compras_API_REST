package br.com.atlantic.api.api.v1.controller;

import br.com.atlantic.api.config.ApiVersion;
import br.com.atlantic.api.domain.model.Carrinho;
import br.com.atlantic.api.domain.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.V1 + "/checkout")
public class AppCheckout {

    private final CarrinhoService carrinhoService;

    @PostMapping()
    public Carrinho post(@Valid @RequestBody Carrinho carrinho){
        return carrinhoService.insert(carrinho);
    }
}

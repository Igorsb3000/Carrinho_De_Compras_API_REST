package br.com.atlantic.api.api.v1.controller;

import br.com.atlantic.api.api.v1.dto.CheckoutResponseDTO;
import br.com.atlantic.api.api.v1.dto.PingResponseDTO;
import br.com.atlantic.api.config.ApiVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.V1 + "/checkout")

public class AppCheckout {

    @PostMapping()
    public CheckoutResponseDTO post(){
        return new CheckoutResponseDTO();
    }
}

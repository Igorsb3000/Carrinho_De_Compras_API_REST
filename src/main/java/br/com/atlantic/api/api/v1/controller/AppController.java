package br.com.atlantic.api.api.v1.controller;

import br.com.atlantic.api.config.ApiVersion;
import br.com.atlantic.api.api.v1.dto.PingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiVersion.V1 + "/app")

public class AppController {

    @GetMapping("/ping")
    public PingResponseDTO ping(){
        return new PingResponseDTO();
    }
}

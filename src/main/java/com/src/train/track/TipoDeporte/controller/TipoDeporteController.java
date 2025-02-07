package com.src.train.track.TipoDeporte.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.src.train.track.TipoDeporte.domain.TipoDeporteDto;
import com.src.train.track.TipoDeporte.proxy.TipoDeporteProxy;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/tipoDeporte")
@RequiredArgsConstructor
public class TipoDeporteController {
    
    private static final Logger logger = LoggerFactory.getLogger(TipoDeporteController.class);

    @Autowired
    private TipoDeporteProxy proxy;
    
	@GetMapping()
    public List<TipoDeporteDto> getAllTiposDeDeporte() {
        return proxy.findAll();
    }
}

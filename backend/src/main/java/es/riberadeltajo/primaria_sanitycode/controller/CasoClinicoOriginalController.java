package es.riberadeltajo.primaria_sanitycode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoOriginalService;

@RestController
@RequestMapping("/api/originales")
@CrossOrigin(origins = "*")
public class CasoClinicoOriginalController {

    @Autowired
    private CasoClinicoOriginalService service;

    // Obtener un caso original aleatorio
    @GetMapping("/random")
    public CasoClinicoOriginal obtenerRandom() {
        return service.obtenerRandom();
    }

    // Obtener un caso original por ID
    @GetMapping("/{id}")
    public CasoClinicoOriginal obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }
}

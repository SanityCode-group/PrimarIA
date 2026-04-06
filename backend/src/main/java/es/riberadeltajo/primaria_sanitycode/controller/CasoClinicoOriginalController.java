package es.riberadeltajo.primaria_sanitycode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoOriginalService;

/**
 * @author María C García Correas
 * @since 15/2/26
 */

/*
 * Controlador para gestionar los casos clínicos originales, que son los casos clínicos completos y sin anonimizar que se almacenan en la base de datos.
 * Estos casos clínicos originales se utilizan para generar las muestras que se presentan a los médicos para su validación.
 * El controlador expone endpoints para obtener casos clínicos originales, ya sea de forma aleatoria o por ID.
 * La lógica de negocio relacionada con los casos clínicos originales se encuentra en el servicio CasoClinicoOriginalService.
 */

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

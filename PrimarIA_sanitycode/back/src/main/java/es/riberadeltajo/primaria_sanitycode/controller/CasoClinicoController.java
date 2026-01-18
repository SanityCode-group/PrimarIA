package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinico;
import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author María C García Correas
 * @since 6/1/26
 */

@CrossOrigin(origins = "*") //permitir llamadas desde html
@RestController //indica que es un controlador REST
@RequestMapping("/api/casos") //ruta base para los endpoints de casos clínicos
public class CasoClinicoController {

    @Autowired
    private CasoClinicoService casoClinicoService;

    // Obtener caso clínico original aleatorio
    @GetMapping("/random") //endpoint para obtener un caso clínico aleatorio
    public CasoClinicoOriginal obtenerCasoClinicoAleatorio() {
        return casoClinicoService.obtenerCasoClinicoAleatorio();
    }

    // Preparar caso para MODIFICAR
    @PostMapping("/modificar/{idOriginal}/{idMedico}")
    public CasoClinico prepararModificarCaso(@PathVariable Long idOriginal, @PathVariable Long idMedico) {
        return casoClinicoService.prepararParaModificar(idOriginal, idMedico);
    }

    // Preparar caso para VALIDAR
    @PostMapping("/validar/{idOriginal}/{idMedico}")
    public CasoClinico prepararValidarCaso(@PathVariable Long idOriginal, @PathVariable Long idMedico) {
        return casoClinicoService.prepararParaValidar(idOriginal, idMedico);
    }

    // Preparar caso para DESCARTAR
    @PostMapping("/descartar/{idOriginal}/{idMedico}")
    public CasoClinico prepararDescartarCaso(@PathVariable Long idOriginal, @PathVariable Long idMedico) {
        return casoClinicoService.prepararParaDescartar(idOriginal, idMedico);
    }

    // Guardar caso clínico
    @PostMapping("/guardar")
    public CasoClinico guardarCasoClinico(@RequestBody CasoClinico casoClinico) {
        return casoClinicoService.guardarCasoClinico(casoClinico);
    }

}

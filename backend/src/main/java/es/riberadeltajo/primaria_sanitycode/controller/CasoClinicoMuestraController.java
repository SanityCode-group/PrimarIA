package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
//import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoMuestraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author María C García Correas
 * @since 6/1/26
 */

@CrossOrigin(origins = "*") //permitir llamadas desde html
@RestController //indica que es un controlador REST
@RequestMapping("/api/casos") //ruta base para los endpoints de casos clínicos
public class CasoClinicoMuestraController {

    @Autowired
    private CasoClinicoMuestraService casoClinicoMuestraService;

    // Obtener caso clínico original aleatorio
    @GetMapping("/random") //endpoint para obtener un caso clínico aleatorio
    public CasoClinicoMuestra obtenerCasoClinicoAleatorio() {
        return casoClinicoMuestraService.obtenerCasoClinicoAleatorio();
    }

    // Preparar caso para VALIDAR
    // @PostMapping("/validar/{idOriginal}/{idMedico}")
    // public CasoClinicoMuestra prepararValidarCaso(@PathVariable Long idOriginal, @PathVariable Long idMedico) {
    //     return casoClinicoMuestraService.prepararParaValidar(idOriginal, idMedico);
    // }

}

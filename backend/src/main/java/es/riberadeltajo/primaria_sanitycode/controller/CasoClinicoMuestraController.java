package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
//import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.service.CasoClinicoMuestraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

/**
 * @author María C García Correas
 * @since 6/1/26
 */

/*

*/

//@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"}, allowCredentials = "true") // permitir llamadas desde el frontend con cookie de sesión
@RestController //indica que es un controlador REST
@RequestMapping("/api/casos") //ruta base para los endpoints de casos clínicos
public class CasoClinicoMuestraController {

    @Autowired
    private CasoClinicoMuestraService casoClinicoMuestraService;

    // Obtener caso clínico original aleatorio
    @GetMapping("/random") //endpoint para obtener un caso clínico aleatorio
    public ResponseEntity<CasoClinicoOriginal> obtenerCasoClinicoAleatorio(
            Authentication authentication
    ) {

        //String email = authentication.getName();
        //extraer email del principal OAuth2
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");

        CasoClinicoMuestra muestra =
                casoClinicoMuestraService.obtenerCasoClinicoAleatorioPorEmail(email);

        if (muestra == null || muestra.getCasoOriginal() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(muestra.getCasoOriginal());
    }

    // Preparar caso para VALIDAR
    // @PostMapping("/validar/{idOriginal}/{idMedico}")
    // public CasoClinicoMuestra prepararValidarCaso(@PathVariable Long idOriginal, @PathVariable Long idMedico) {
    //     return casoClinicoMuestraService.prepararParaValidar(idOriginal, idMedico);
    // }
    //PUT /api/casos/{id}/aprobar
    //PUT /api/casos/{id}/rechazar
    //PUT /api/casos/{id}/completar-validacion


}

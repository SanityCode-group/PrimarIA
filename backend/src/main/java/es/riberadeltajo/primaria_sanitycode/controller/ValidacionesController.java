package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.model.dto.ValidacionRequest;
import es.riberadeltajo.primaria_sanitycode.service.ValidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validaciones")
public class ValidacionesController {

    @Autowired
    private ValidacionService validacionService;

    @PostMapping
    public ResponseEntity<?> guardarValidacion(@RequestBody ValidacionRequest request, 
                                             @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        String email = principal.getAttribute("email");
        try {
            validacionService.guardarValidacion(request, email);
            return ResponseEntity.ok("Validación guardada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la validación: " + e.getMessage());
        }
    }
}

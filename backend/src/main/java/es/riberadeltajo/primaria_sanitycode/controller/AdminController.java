package es.riberadeltajo.primaria_sanitycode.controller;

import es.riberadeltajo.primaria_sanitycode.model.dto.CasoPuntuadoDTO;
import es.riberadeltajo.primaria_sanitycode.model.dto.MetricaAgenteDTO;
import es.riberadeltajo.primaria_sanitycode.model.entity.Usuario;
import es.riberadeltajo.primaria_sanitycode.model.entity.Whitelist;
import es.riberadeltajo.primaria_sanitycode.repository.UsuarioRepository;
import es.riberadeltajo.primaria_sanitycode.repository.ValidacionRepository;
import es.riberadeltajo.primaria_sanitycode.repository.WhitelistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private ValidacionRepository  validacionRepository;
    @Autowired private UsuarioRepository     usuarioRepository;
    @Autowired private WhitelistRepository   whitelistRepository;

    //  Guard helper 

    private boolean esAdmin(OAuth2User principal) {
        if (principal == null) return false;
        String email = principal.getAttribute("email");
        return usuarioRepository.findByEmail(email)
                .map(u -> "ADMIN".equalsIgnoreCase(u.getRol()))
                .orElse(false);
    }

    private ResponseEntity<?> forbidden() {
        return ResponseEntity.status(403).body("Acceso restringido a administradores");
    }

    //  USUARIOS 

    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios(@AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();
        List<Usuario> usuarios = usuarioRepository.findAllByOrderByFechaCreacionDesc();
        return ResponseEntity.ok(usuarios.stream().map(u -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id",            u.getId());
            m.put("nombre",        u.getNombre());
            m.put("email",         u.getEmail());
            m.put("rol",           u.getRol());
            m.put("fechaCreacion", u.getFechaCreacion());
            m.put("ultimoAcceso",  u.getUltimoAcceso());
            return m;
        }).collect(Collectors.toList()));
    }

    //  WHITELIST 

    @GetMapping("/whitelist")
    public ResponseEntity<?> listarWhitelist(@AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();
        return ResponseEntity.ok(whitelistRepository.findAll());
    }

    @PostMapping("/whitelist")
    public ResponseEntity<?> añadirWhitelist(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();

        String email = body.get("email");
        if (email == null || email.isBlank())
            return ResponseEntity.badRequest().body("El email es obligatorio");

        if (whitelistRepository.findByEmail(email.trim().toLowerCase()).isPresent())
            return ResponseEntity.badRequest().body("El email ya está en la whitelist");

        Whitelist w = new Whitelist();
        w.setEmail(email.trim().toLowerCase());
        w.setFechaAlta(LocalDateTime.now());
        whitelistRepository.save(w);
        return ResponseEntity.ok("Email añadido a la whitelist");
    }

    @DeleteMapping("/whitelist/{id}")
    public ResponseEntity<?> eliminarWhitelist(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();

        if (!whitelistRepository.existsById(id))
            return ResponseEntity.notFound().build();

        whitelistRepository.deleteById(id);
        return ResponseEntity.ok("Email eliminado de la whitelist");
    }

    //  MÉTRICAS 

    /** Resumen global: total validaciones, casos validados */
    @GetMapping("/metricas/resumen")
    public ResponseEntity<?> resumenGlobal(@AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();

        Object[] media = validacionRepository.mediaGlobalCriterios();

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("totalValidaciones",  validacionRepository.totalValidaciones());
        res.put("totalCasosValidados", validacionRepository.totalCasosValidados());
        res.put("totalUsuarios",      usuarioRepository.count());

        if (media != null && media.length == 5) {
            Map<String, Double> criterios = new LinkedHashMap<>();
            String[] nombres = {"precisionDiagnostica","claridadTextual",
                                "relevanciaClinica","adecuacionContextual","nivelTecnico"};
            for (int i = 0; i < 5; i++) {
                double v = media[i] != null ? Math.round(((Number)media[i]).doubleValue()*100)/100.0 : 0.0;
                criterios.put(nombres[i], v);
            }
            res.put("mediaGlobalCriterios", criterios);

            // Criterio con mejor media
            String mejorCriterio = criterios.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A");
            res.put("mejorCriterio", mejorCriterio);
        }

        return ResponseEntity.ok(res);
    }

    /** Métricas agrupadas por modelo de IA */
    @GetMapping("/metricas/por-agente")
    public ResponseEntity<?> metricasPorAgente(@AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();
        List<MetricaAgenteDTO> result = validacionRepository.metricasPorAgente()
                .stream().map(MetricaAgenteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** Top 10 casos mejor valorados */
    @GetMapping("/metricas/top-casos")
    public ResponseEntity<?> topCasos(@AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();
        List<CasoPuntuadoDTO> result = validacionRepository.top10CasosPorPuntuacion()
                .stream().map(CasoPuntuadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** Casos con puntuación perfecta en precisión diagnóstica */
    @GetMapping("/metricas/perfectos/precision")
    public ResponseEntity<?> perfectosPrecision(@AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();
        List<CasoPuntuadoDTO> result = validacionRepository.casosPerfectosPrecision()
                .stream().map(CasoPuntuadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /** Casos con puntuación perfecta en claridad textual */
    @GetMapping("/metricas/perfectos/claridad")
    public ResponseEntity<?> perfectosClaridad(@AuthenticationPrincipal OAuth2User principal) {
        if (!esAdmin(principal)) return forbidden();
        List<CasoPuntuadoDTO> result = validacionRepository.casosPerfectosClaridad()
                .stream().map(CasoPuntuadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
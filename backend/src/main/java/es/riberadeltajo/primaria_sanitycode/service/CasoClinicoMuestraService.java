package es.riberadeltajo.primaria_sanitycode.service;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
import es.riberadeltajo.primaria_sanitycode.model.entity.Usuario;
import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoMuestraRepository;
import es.riberadeltajo.primaria_sanitycode.repository.UsuarioRepository;
import es.riberadeltajo.primaria_sanitycode.repository.ValidacionRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author María C García Correas
 * @since 6/1/26
 */

@Service
public class CasoClinicoMuestraService {

    //logger para optimizar algormito de selección de casos clínicos
     private static final Logger log =
            LoggerFactory.getLogger(CasoClinicoMuestraService.class);

    @Autowired
    private CasoClinicoMuestraRepository casoClinicoMuestraRepository;

    @Autowired
    private ValidacionRepository validacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    /**
     * Obtiene un caso clínico respetando reglas:
     * - Un médico no puede repetir caso
     * - Un caso no puede tener más de 2 médicos distintos
     */
    public CasoClinicoMuestra obtenerCasoClinicoAleatorio(Long usuarioId) {
        // return Optional.ofNullable(
        //         casoClinicoMuestraRepository.findCasoClinicoDisponible(usuarioId)
        // ).orElseThrow(() ->
        //         new RuntimeException("No hay casos disponibles para este usuario")
    //     );
    // }

        log.info(">>> INICIO SELECCIÓN CASO | usuario={}", usuarioId);

        long total = casoClinicoMuestraRepository.count();
        log.info("Total casos en muestra: {}", total);

        CasoClinicoMuestra caso =
                casoClinicoMuestraRepository.findCasoValido(usuarioId);

        if (caso == null) {
            log.warn("⚠ No hay casos disponibles | usuario={}", usuarioId);
            return null;
        }

        Long casoOriginalId = caso.getCasoOriginal().getId();

        long numMedicos = validacionRepository.countDistinctUsuariosByCasoOriginalId(casoOriginalId);

        log.info("📊 CASO ANALIZADO | usuario={} | casoOriginal={} | médicos={}",
                usuarioId, casoOriginalId, numMedicos);

        if (numMedicos >= 2) {
            log.info("❌ RECHAZADO | motivo=Ya tiene 2 médicos | casoOriginal={}", casoOriginalId);
            return null;
        }
        log.info("✅ SELECCIONADO | usuario={} | caso={}", usuarioId, casoOriginalId);

        //long casoId = caso.getId();

        // long numMedicos =
        //         validacionRepository.countDistinctUsuariosByCasoOriginalId(casoId);

        // log.info("📊 CASO ANALIZADO | usuario={} | caso={} | médicos={}",
        //         usuarioId, casoId, numMedicos);

        // if (numMedicos >= 2) {
        //     log.info("❌ RECHAZADO | motivo=Ya tiene 2 médicos | caso={}", casoId);
        //     return null;
        // }

        // log.info("✅ SELECCIONADO | usuario={} | caso={}", usuarioId, casoId);

        return caso;

        
    }





    public CasoClinicoMuestra obtenerCasoClinicoAleatorioPorEmail(String email) {

    log.info("🔎 Buscando usuario en BD | email={}", email);

    Usuario usuario = usuarioRepository.findByEmail(email.trim().toLowerCase())
        .orElseThrow(() -> {
            log.error("❌ Usuario no encontrado en BD | email={}", email);
            return new RuntimeException("Usuario no encontrado");
        });

    Long usuarioId = usuario.getId();

    return obtenerCasoClinicoAleatorio(usuarioId);
}
}
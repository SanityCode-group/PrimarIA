package es.riberadeltajo.primaria_sanitycode.service;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoMuestra;
//import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
//import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoOriginalRepository;
import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoMuestraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author María C García Correas
 * @since 6/1/26
 */

@Service
public class CasoClinicoMuestraService {

    // Acceso a tablas casos_clinicos_originales y casos_clinicos_muestra
    //@Autowired
    //private CasoClinicoOriginalRepository casoClinicoOriginalRepository;

    @Autowired
    private CasoClinicoMuestraRepository casoClinicoMuestraRepository;

    // Función para obtener un caso clínico original aleatorio desde la tabla MUESTRA
    public CasoClinicoMuestra obtenerCasoClinicoAleatorio() {
        return casoClinicoMuestraRepository.findRandomCasoClinico();

        // TODO - modificar algoritmo
    }

    // Función para crear un nuevo registro en la tabla casos_clinicoos_muestra
    // public CasoClinicoMuestra prepararCasoDesdeOriginal(Integer idOriginal) {

    //     CasoClinicoOriginal original = casoClinicoOriginalRepository.findById(idOriginal)
    //             .orElseThrow(() -> new RuntimeException("Caso original no encontrado"));

    //     return CasoClinicoMuestra.builder()
    //             .casoOriginal(original)
    //             .build();
    // }


}

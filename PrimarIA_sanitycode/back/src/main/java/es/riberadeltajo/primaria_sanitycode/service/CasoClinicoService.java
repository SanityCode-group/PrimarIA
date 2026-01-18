package es.riberadeltajo.primaria_sanitycode.service;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinico;
import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoOriginalRepository;
import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author María C García Correas
 * @since 6/1/26
 */

@Service
public class CasoClinicoService {

    //Acceso a tablas casos_clinicos_originales y casos_clinicos
    @Autowired
    private CasoClinicoOriginalRepository casoClinicoOriginalRepository;
    @Autowired
    private CasoClinicoRepository casoClinicoRepository;

    public CasoClinicoOriginal obtenerCasoClinicoAleatorio() {
        return casoClinicoOriginalRepository.findRandomCasoClinico();
    }

    // Función para copiar un registro de casos_clinicos_originales y guardarlo en un objeto tipo CasoClinico
    public CasoClinico prepararCasoDesdeOriginal(CasoClinicoOriginal original, Long idMedico, String estado){

        return CasoClinico.builder()
                .id_original(original.getId())
                .estado(estado)
                .fecha(LocalDateTime.now())
                .edad(original.getEdad())
                .sexo(original.getSexo())
                .antecedentes_medicos(original.getAntecedentes_medicos())
                .antecedentes_quirurgicos(original.getAntecedentes_quirurgicos())
                .habitos(original.getHabitos())
                .situacion_basal(original.getSituacion_basal())
                .medicacion_actual(original.getMedicacion_actual())
                .antecedentes_familiares(original.getAntecedentes_familiares())
                .motivo(original.getMotivo())
                .sintomas(original.getSintomas())
                .exploracion_general(original.getExploracion_general())
                .signos(original.getSignos())
                .resultados_pruebas(original.getResultados_pruebas())
                .razonamiento_clinico(original.getRazonamiento_clinico())
                .diagnostico_final(original.getDiagnostico_final())
                .tratamiento_farmacologico(original.getTratamiento_farmacologico())
                .tratamiento_no_farmacologico(original.getTratamiento_no_farmacologico())
                .factores_sociales(original.getFactores_sociales())
                .alergias(original.getAlergias())
                .referencias_bibliograficas(original.getReferencias_bibliograficas())
                .categoria(original.getCategoria())
                .keywords(original.getKeywords())
                .codigo_cie_10(original.getCodigo_cie_10())
                .dificultad(original.getDificultad())
                .chunk_id(original.getChunk_id())
                .chunk(original.getChunk())
                .id_medico(idMedico)
                .build();
    }

    // Funciones para preparar casos clínicos en diferentes estados
    public CasoClinico prepararParaModificar(Long idOriginal, Long idMedico){
        CasoClinicoOriginal original = casoClinicoOriginalRepository.findById(idOriginal)
                .orElseThrow( () -> new RuntimeException("Caso clínico original no encontrado con id: " + idOriginal));
        return prepararCasoDesdeOriginal(original, idMedico, "MODIFICADO");
    }
    public CasoClinico prepararParaValidar(Long idOriginal, Long idMedico){
        CasoClinicoOriginal original = casoClinicoOriginalRepository.findById(idOriginal)
                .orElseThrow( () -> new RuntimeException("Caso clínico original no encontrado con id: " + idOriginal));
        return prepararCasoDesdeOriginal(original, idMedico, "VALIDADO");
    }
    public CasoClinico prepararParaDescartar(Long idOriginal, Long idMedico){
        CasoClinicoOriginal original = casoClinicoOriginalRepository.findById(idOriginal)
                .orElseThrow( () -> new RuntimeException("Caso clínico original no encontrado con id: " + idOriginal));
        return prepararCasoDesdeOriginal(original, idMedico, "DESCARTADO");
    }


    // Función para guardar caso clínico en la base de datos (en tabla casos_clinicos)
    public CasoClinico guardarCasoClinico(CasoClinico casoClinico) {
        return casoClinicoRepository.save(casoClinico);
    }

}

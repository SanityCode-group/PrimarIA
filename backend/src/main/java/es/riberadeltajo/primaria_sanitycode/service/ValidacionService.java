package es.riberadeltajo.primaria_sanitycode.service;

import es.riberadeltajo.primaria_sanitycode.model.dto.ValidacionRequest;
import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.model.entity.Usuario;
import es.riberadeltajo.primaria_sanitycode.model.entity.Validacion;
import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoOriginalRepository;
import es.riberadeltajo.primaria_sanitycode.repository.UsuarioRepository;
import es.riberadeltajo.primaria_sanitycode.repository.ValidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ValidacionService {

    @Autowired
    private ValidacionRepository validacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CasoClinicoOriginalRepository casoClinicoOriginalRepository;

    @Transactional
    public void guardarValidacion(ValidacionRequest request, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CasoClinicoOriginal caso = casoClinicoOriginalRepository.findById(request.getIdCasoOriginal())
                .orElseThrow(() -> new RuntimeException("Caso clínico no encontrado"));

        Validacion validacion = Validacion.builder()
                .usuario(usuario)
                .casoOriginal(caso)
                .precisionDiagnostica(request.getPrecisionDiagnostica())
                .claridadTextual(request.getClaridadTextual())
                .relevanciaClinica(request.getRelevanciaClinica())
                .adecuacionContextual(request.getAdecuacionContextual())
                .nivelTecnico(request.getNivelTecnico())
                .observaciones(request.getObservaciones())
                .fechaValidacion(LocalDateTime.now())
                .build();

        validacionRepository.save(validacion);
    }
}

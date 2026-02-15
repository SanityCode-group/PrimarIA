package es.riberadeltajo.primaria_sanitycode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.riberadeltajo.primaria_sanitycode.model.entity.CasoClinicoOriginal;
import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoOriginalRepository;

/**
 * @author María C García Correas
 * @since 15/2/26
 */

@Service
public class CasoClinicoOriginalService {
 
    @Autowired
    private CasoClinicoOriginalRepository repo;

    public CasoClinicoOriginal obtenerRandom(){
        return repo.findRandomCasoClinico();
    }

    public CasoClinicoOriginal obtenerPorId(Long id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("No se ha encontrado el caso clínico original con id: " + id));
    }
    
}

package es.riberadeltajo.primaria_sanitycode.model.entity;

import jakarta.persistence.*;
import lombok.*;



/*
CREATE TABLE IF NOT EXISTS casos_clinicos_muestra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    -- Relación con el caso original
    id_original INT NOT NULL,
        
    -- Foreign key SIEMPRE al final 
    CONSTRAINT fk_caso_original 
		FOREIGN KEY (id_original) 
        REFERENCES casos_clinicos_originales(id)
);
 */

@Entity
@Table(name = "casos_clinicos_muestra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CasoClinicoMuestra {

    //id INT AUTO_INCREMENT PRIMARY KEY,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //id_original INT NOT NULL, con foreign key a casos_clinicos_originales(id)
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_original", referencedColumnName = "id")
    private CasoClinicoOriginal casoOriginal;


    // Constructor vacío requerido por JPA
    //public CasoClinico() {}
}



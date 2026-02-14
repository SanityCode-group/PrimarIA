package es.riberadeltajo.primaria_sanitycode.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/*
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    rol VARCHAR(50) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    ultimo_acceso DATETIME
);
 */

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    //id BIGINT AUTO_INCREMENT PRIMARY KEY,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //nombre VARCHAR(100) NOT NULL,
    @Column(nullable = false, length = 100)
    private String nombre;

    //email VARCHAR(150) NOT NULL UNIQUE,
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    //@Column(nullable = false)
    //private String password;

    //rol VARCHAR(50) NOT NULL,
    @Column(nullable = false, length = 50)
    private String rol;

    //fecha_creacion DATETIME NOT NULL,
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    //ultimo_acceso DATETIME
    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    // Constructor vacío requerido por JPA
    public Usuario() {
    }

}

package es.riberadeltajo.primaria_sanitycode.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mensajes_conversacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeConversacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conversacion")
    private Conversacion conversacion;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private int orden;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public enum Role {
        user, assistant
    }
}

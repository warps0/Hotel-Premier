package edu.utn.tp_disenyo.hotel_premier.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estadia_servicio")
@Getter
@Setter
@NoArgsConstructor
public class EstadiaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estadia_id")
    private Long estadiaId;

    @Column(name = "servicio_id")
    private Long servicioId;

    @Column(nullable = false)
    private boolean incluido;
}
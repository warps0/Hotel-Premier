package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.utn.tp_disenyo.hotel_premier.util.Estado;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "estado_habitacion")
public class EstadoHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Estado estado;

    @Transient
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @ManyToOne
    @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;
}

package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;

import edu.utn.tp_disenyo.hotel_premier.util.Estado;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Estado estado;
}

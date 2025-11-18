package edu.utn.tp_disenyo.hotel_premier.model;

import java.util.List;

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
@Table(name = "habitacion")
public class Habitacion {
    private Long id;
    private Integer capacidad;
    private Float precio;
    
    //private EnumPiso piso;
    private List<EstadoHabitacion> estados;
}
